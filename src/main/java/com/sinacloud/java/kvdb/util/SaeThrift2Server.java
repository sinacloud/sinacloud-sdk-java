package com.sinacloud.java.kvdb.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.filter.ParseFilter;
import org.apache.hadoop.hbase.thrift.CallQueue;
import org.apache.hadoop.hbase.thrift.CallQueue.Call;
import org.apache.hadoop.hbase.thrift.ThriftMetrics;
import org.apache.hadoop.hbase.thrift2.generated.THBaseService;
import org.apache.hadoop.hbase.util.InfoServer;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * ThriftServer - this class starts up a Thrift server which implements the HBase API specified in the
 * HbaseClient.thrift IDL file.
 */
@InterfaceAudience.Private
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SaeThrift2Server {
  private static final Log log = LogFactory.getLog(SaeThrift2Server.class);

  public static final String DEFAULT_LISTEN_PORT = "9090";

  public static final String MIN_WORKER_THREADS_CONF_KEY = "hbase.thrift.minWorkerThreads";
  public static final int DEFAULT_MIN_WORKER_THREADS = 16;
  
  public static final String MAX_WORKER_THREADS_CONF_KEY = "hbase.thrift.maxWorkerThreads";
  public static final int DEFAULT_MAX_WORKER_THREADS = 1000;
  
  public static final String MAX_QUEUED_REQUESTS_CONF_KEY = "hbase.thrift.maxQueuedRequests";
  public static final int DEFAULT_MAX_QUEUED_REQUESTS = 1000;
  
  public static final String THREAD_KEEP_ALIVE_TIME_SEC_CONF_KEY = "hbase.thrift.threadKeepAliveTimeSec";
  private static final int DEFAULT_THREAD_KEEP_ALIVE_TIME_SEC = 60;
  
  public SaeThrift2Server() {
  }

  private static void printUsage() {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("Thrift", null, getOptions(),
        "To start the Thrift server run 'bin/hbase-daemon.sh start thrift2'\n" +
            "To shutdown the thrift server run 'bin/hbase-daemon.sh stop thrift2' or" +
            " send a kill signal to the thrift server pid",
        true);
  }

  private static Options getOptions() {
    Options options = new Options();
    options.addOption("b", "bind", true,
        "Address to bind the Thrift server to. [default: 0.0.0.0]");
    options.addOption("p", "port", true, "Port to bind to [default: " + DEFAULT_LISTEN_PORT + "]");
    options.addOption("f", "framed", false, "Use framed transport");
    options.addOption("c", "compact", false, "Use the compact protocol");
    options.addOption("h", "help", false, "Print help information");
    options.addOption(null, "infoport", true, "Port for web UI");

    OptionGroup servers = new OptionGroup();
    servers.addOption(
        new Option("nonblocking", false, "Use the TNonblockingServer. This implies the framed transport."));
    servers.addOption(new Option("hsha", false, "Use the THsHaServer. This implies the framed transport."));
    servers.addOption(new Option("threadpool", false, "Use the TThreadPoolServer. This is the default."));
    options.addOptionGroup(servers);
    return options;
  }

  private static CommandLine parseArguments(Configuration conf, Options options, String[] args)
      throws ParseException, IOException {
    GenericOptionsParser genParser = new GenericOptionsParser(conf, args);
    String[] remainingArgs = genParser.getRemainingArgs();
    CommandLineParser parser = new PosixParser();
    return parser.parse(options, remainingArgs);
  }

  private static TProtocolFactory getTProtocolFactory(boolean isCompact) {
    if (isCompact) {
      log.debug("Using compact protocol");
      return new TCompactProtocol.Factory();
    } else {
      log.debug("Using binary protocol");
      return new TBinaryProtocol.Factory();
    }
  }

  private static TTransportFactory getTTransportFactory(boolean framed) {
    if (framed) {
      log.debug("Using framed transport");
      return new TFramedTransport.Factory();
    } else {
      return new TTransportFactory();
    }
  }

  /*
   * If bindValue is null, we don't bind.
   */
  private static InetSocketAddress bindToPort(String bindValue, int listenPort)
      throws UnknownHostException {
    try {
      if (bindValue == null) {
        return new InetSocketAddress(listenPort);
      } else {
        return new InetSocketAddress(InetAddress.getByName(bindValue), listenPort);
      }
    } catch (UnknownHostException e) {
      throw new RuntimeException("Could not bind to provided ip address", e);
    }
  }

  @SuppressWarnings("unused")
private static TServer getTNonBlockingServer(TProtocolFactory protocolFactory, THBaseService.Processor processor,
      TTransportFactory transportFactory, InetSocketAddress inetSocketAddress) throws TTransportException {
    TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(inetSocketAddress);
    log.info("starting HBase Nonblocking Thrift server on " + inetSocketAddress.toString());
    TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
    serverArgs.processor(processor);
    serverArgs.transportFactory(transportFactory);
    serverArgs.protocolFactory(protocolFactory);
    return new TNonblockingServer(serverArgs);
  }

  private static TServer getTHsHaServer(TProtocolFactory protocolFactory,
      THBaseService.Processor processor, TTransportFactory transportFactory,
      InetSocketAddress inetSocketAddress, ThriftMetrics metrics)
      throws TTransportException {
    TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(inetSocketAddress);
    log.info("starting HBase HsHA Thrift server on " + inetSocketAddress.toString());
    THsHaServer.Args serverArgs = new THsHaServer.Args(serverTransport);
    ExecutorService executorService = createExecutor(
        serverArgs.getWorkerThreads(), metrics);
    serverArgs.executorService(executorService);
    serverArgs.processor(processor);
    serverArgs.transportFactory(transportFactory);
    serverArgs.protocolFactory(protocolFactory);
    return new THsHaServer(serverArgs);
  }

  private static ExecutorService createExecutor(
      int workerThreads, ThriftMetrics metrics) {
    CallQueue callQueue = new CallQueue(
        new LinkedBlockingQueue<Call>(), metrics);
    ThreadFactoryBuilder tfb = new ThreadFactoryBuilder();
    tfb.setDaemon(true);
    tfb.setNameFormat("thrift2-worker-%d");
    return new ThreadPoolExecutor(workerThreads, workerThreads,
            Long.MAX_VALUE, TimeUnit.SECONDS, callQueue, tfb.build());
  }

  private static TServer getTThreadPoolServer(TProtocolFactory protocolFactory, TKvdbService.Processor processor,
      TTransportFactory transportFactory, InetSocketAddress inetSocketAddress, Configuration conf) throws TTransportException {
	  TServerTransport serverTransport = new TServerSocket(inetSocketAddress);
	    log.info("starting HBase ThreadPool Thrift server on " + inetSocketAddress.toString());
	    SaeTThreadPoolServer.Args serverArgs = new SaeTThreadPoolServer.Args(serverTransport);
	    serverArgs.processor(processor);
	    serverArgs.transportFactory(transportFactory);
	    serverArgs.protocolFactory(protocolFactory);
	    
	    int minWorkerThreads = conf.getInt(MIN_WORKER_THREADS_CONF_KEY, DEFAULT_MIN_WORKER_THREADS);
	    int maxWorkerThreads = conf.getInt(MAX_WORKER_THREADS_CONF_KEY, DEFAULT_MAX_WORKER_THREADS);
	    int maxQueuedRequests = conf.getInt(MAX_QUEUED_REQUESTS_CONF_KEY, DEFAULT_MAX_QUEUED_REQUESTS);
	    int threadKeepAliveTimeSec = conf.getInt(THREAD_KEEP_ALIVE_TIME_SEC_CONF_KEY, DEFAULT_THREAD_KEEP_ALIVE_TIME_SEC);
	    
	    serverArgs.minWorkerThreads(minWorkerThreads);
	    serverArgs.maxWorkerThreads(maxWorkerThreads);
	    LinkedBlockingQueue<Runnable> callQueue =
	    	      new LinkedBlockingQueue<Runnable>(maxQueuedRequests);
	    
	    ThreadFactoryBuilder tfb = new ThreadFactoryBuilder();
	    tfb.setDaemon(true);
	    tfb.setNameFormat("saethrift2-worker-%d");
	    ExecutorService executorService =
	            new ThreadPoolExecutor(minWorkerThreads,
	                maxWorkerThreads, threadKeepAliveTimeSec,
	                TimeUnit.SECONDS, callQueue, tfb.build());
	    serverArgs.executorService = executorService;
	    return new SaeTThreadPoolServer(serverArgs);
  }

  /**
   * Adds the option to pre-load filters at startup.
   *
   * @param conf  The current configuration instance.
   */
  protected static void registerFilters(Configuration conf) {
    String[] filters = conf.getStrings("hbase.thrift.filters");
    if(filters != null) {
      for(String filterClass: filters) {
        String[] filterPart = filterClass.split(":");
        if(filterPart.length != 2) {
          log.warn("Invalid filter specification " + filterClass + " - skipping");
        } else {
          ParseFilter.registerFilter(filterPart[0], filterPart[1]);
        }
      }
    }
  }

  /**
   * Start up the Thrift2 server.
   *
   * @param args
   */
  public static void main(String[] args) throws Exception {
//	  args = new String[]{"start"};
    TServer server = null;
    Options options = getOptions();
    try {
//    	Configuration cfg = new Configuration();
//		cfg.set("hbase.zookeeper.quorum", "10.66.15.36");
//		cfg.set("hbase.zookeeper.property.clientPort", "2181");
    	
      Configuration conf = HBaseConfiguration.create();
      CommandLine cmd = parseArguments(conf, options, args);
//System.out.println(cmd.getArgList());
//System.out.println(java.util.Arrays.toString(args));      

      /**
       * This is to please both bin/hbase and bin/hbase-daemon. hbase-daemon provides "start" and "stop" arguments hbase
       * should print the help if no argument is provided
       */
      List<?> argList = cmd.getArgList();
      if (cmd.hasOption("help") || !argList.contains("start") || argList.contains("stop")) {
        printUsage();
        System.exit(1);
      }

      // Get port to bind to
      int listenPort = 0;
      try {
        listenPort = Integer.parseInt(cmd.getOptionValue("port", DEFAULT_LISTEN_PORT));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Could not parse the value provided for the port option", e);
      }

      boolean nonblocking = cmd.hasOption("nonblocking");
      boolean hsha = cmd.hasOption("hsha");

      ThriftMetrics metrics = new ThriftMetrics(listenPort, conf, TKvdbService.Iface.class);

      String implType = "threadpool";
      if (nonblocking) {
        implType = "nonblocking";
      } else if (hsha) {
        implType = "hsha";
      }

      conf.set("hbase.regionserver.thrift.server.type", implType);
      conf.setInt("hbase.regionserver.thrift.port", listenPort);
      registerFilters(conf);

      // Construct correct ProtocolFactory
      boolean compact = cmd.hasOption("compact") ||
        conf.getBoolean("hbase.regionserver.thrift.compact", false);
      TProtocolFactory protocolFactory = getTProtocolFactory(compact);
//      THBaseService.Iface handler =
//          ThriftHBaseServiceHandler.newInstance(conf, metrics);
//      THBaseService.Processor processor = new THBaseService.Processor(handler);
      
      
      TKvdbService.Iface handler = new SaeKVServer();
      TKvdbService.Processor processor = new TKvdbService.Processor(handler);
      
      conf.setBoolean("hbase.regionserver.thrift.compact", compact);

      boolean framed = cmd.hasOption("framed") ||
        conf.getBoolean("hbase.regionserver.thrift.framed", false) || nonblocking || hsha;
      TTransportFactory transportFactory = getTTransportFactory(framed);
      InetSocketAddress inetSocketAddress = bindToPort(cmd.getOptionValue("bind"), listenPort);
      conf.setBoolean("hbase.regionserver.thrift.framed", framed);
      
      System.out.println(inetSocketAddress);

      // check for user-defined info server port setting, if so override the conf
      try {
        if (cmd.hasOption("infoport")) {
          String val = cmd.getOptionValue("infoport");
          conf.setInt("hbase.thrift.info.port", Integer.valueOf(val));
          log.debug("Web UI port set to " + val);
        }
      } catch (NumberFormatException e) {
        log.error("Could not parse the value provided for the infoport option", e);
        printUsage();
        System.exit(1);
      }

      // Put up info server.
      int port = conf.getInt("hbase.thrift.info.port", 9095);
      if (port >= 0) {
        conf.setLong("startcode", System.currentTimeMillis());
        String a = conf.get("hbase.thrift.info.bindAddress", "0.0.0.0");
        InfoServer infoServer = new InfoServer("thrift", a, port, false, conf);
        infoServer.setAttribute("hbase.conf", conf);
        infoServer.start();
      }

      if (nonblocking) {
        //server = getTNonBlockingServer(protocolFactory, processor, transportFactory, inetSocketAddress);
      } else if (hsha) {
        //server = getTHsHaServer(protocolFactory, processor, transportFactory, inetSocketAddress, metrics);
      } else {
        server = getTThreadPoolServer(protocolFactory, processor, transportFactory, inetSocketAddress, conf);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      printUsage();
      System.exit(1);
    }
    server.serve();
  }
}