package com.sinacloud.java.kvdb.util;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TKvdbService {

  public interface Iface {

    /**
     * Method for Authorization.
     * 
     * @return the authorization result.
     * 
     * @param kp
     */
    public KeyPair auth(KeyPair kp) throws TIOError, AuthFailure, org.apache.thrift.TException;

    /**
     * Method for getting item.
     * 
     * @return the TItem
     * 
     * @param kp
     * @param key
     */
    public TItem getItem(KeyPair kp, ByteBuffer key) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * Method for getting multiple items.
     * 
     * If a key cannot be found there will be a null
     * value in the result list for that key at the
     * same position.
     * 
     * So the Results are in the same order as the keys.
     * 
     * @param kp a list of keys to fetch, the Item list
     * will have the Results at corresponding positions
     * or null if there was an error
     * 
     * @param keys
     */
    public Map<String,TItem> getItems(KeyPair kp, List<ByteBuffer> keys) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * Method for getting multiple items.
     * 
     * If a key cannot be found there will be a null
     * value in the result list for that key at the
     * same position.
     * 
     * So the Results are in the same order as the keys.
     * 
     * @param kp Key prefix to fetch, the Item list
     * 
     * @param prefix
     * @param count
     */
    public Map<String,TItem> getItemsByPrefix(KeyPair kp, ByteBuffer prefix, int count) throws TIOError, TIllegalArgument, AuthExpire, org.apache.thrift.TException;

    /**
     * Set a Item to Kvdb.
     * 
     * @param kp the key to set data in
     * 
     * @param key
     * @param item the TPut to put
     */
    public void setItem(KeyPair kp, ByteBuffer key, TItem item) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * Delete a Item.
     * 
     * @param kp the key to delete
     * 
     * @param key
     */
    public void deleteItem(KeyPair kp, ByteBuffer key) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * delete range data(only java)
     * 
     * @param kp
     * @param startKey
     * @param stopKey
     * @param count
     */
    public Map<String,TItem> rget(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * range search
     * 
     * @param kp
     * @param prefixKey
     * @param count
     * @param isContainValue
     * @param compareKey
     */
    public Map<String,TItem> prkget(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey) throws TIOError, AuthExpire, org.apache.thrift.TException;

    /**
     * range delete(only java)
     * 
     * @param kp
     * @param startKey
     * @param stopKey
     * @param count
     */
    public void rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws TIOError, AuthExpire, org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void auth(KeyPair kp, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.auth_call> resultHandler) throws org.apache.thrift.TException;

    public void getItem(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.getItem_call> resultHandler) throws org.apache.thrift.TException;

    public void getItems(KeyPair kp, List<ByteBuffer> keys, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.getItems_call> resultHandler) throws org.apache.thrift.TException;

    public void getItemsByPrefix(KeyPair kp, ByteBuffer prefix, int count, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.getItemsByPrefix_call> resultHandler) throws org.apache.thrift.TException;

    public void setItem(KeyPair kp, ByteBuffer key, TItem item, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.setItem_call> resultHandler) throws org.apache.thrift.TException;

    public void deleteItem(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.deleteItem_call> resultHandler) throws org.apache.thrift.TException;

    public void rget(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.rget_call> resultHandler) throws org.apache.thrift.TException;

    public void prkget(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.prkget_call> resultHandler) throws org.apache.thrift.TException;

    public void rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.rdel_call> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public KeyPair auth(KeyPair kp) throws TIOError, AuthFailure, org.apache.thrift.TException
    {
      send_auth(kp);
      return recv_auth();
    }

    public void send_auth(KeyPair kp) throws org.apache.thrift.TException
    {
      auth_args args = new auth_args();
      args.setKp(kp);
      sendBase("auth", args);
    }

    public KeyPair recv_auth() throws TIOError, AuthFailure, org.apache.thrift.TException
    {
      auth_result result = new auth_result();
      receiveBase(result, "auth");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "auth failed: unknown result");
    }

    public TItem getItem(KeyPair kp, ByteBuffer key) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_getItem(kp, key);
      return recv_getItem();
    }

    public void send_getItem(KeyPair kp, ByteBuffer key) throws org.apache.thrift.TException
    {
      getItem_args args = new getItem_args();
      args.setKp(kp);
      args.setKey(key);
      sendBase("getItem", args);
    }

    public TItem recv_getItem() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      getItem_result result = new getItem_result();
      receiveBase(result, "getItem");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getItem failed: unknown result");
    }

    public Map<String,TItem> getItems(KeyPair kp, List<ByteBuffer> keys) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_getItems(kp, keys);
      return recv_getItems();
    }

    public void send_getItems(KeyPair kp, List<ByteBuffer> keys) throws org.apache.thrift.TException
    {
      getItems_args args = new getItems_args();
      args.setKp(kp);
      args.setKeys(keys);
      sendBase("getItems", args);
    }

    public Map<String,TItem> recv_getItems() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      getItems_result result = new getItems_result();
      receiveBase(result, "getItems");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getItems failed: unknown result");
    }

    public Map<String,TItem> getItemsByPrefix(KeyPair kp, ByteBuffer prefix, int count) throws TIOError, TIllegalArgument, AuthExpire, org.apache.thrift.TException
    {
      send_getItemsByPrefix(kp, prefix, count);
      return recv_getItemsByPrefix();
    }

    public void send_getItemsByPrefix(KeyPair kp, ByteBuffer prefix, int count) throws org.apache.thrift.TException
    {
      getItemsByPrefix_args args = new getItemsByPrefix_args();
      args.setKp(kp);
      args.setPrefix(prefix);
      args.setCount(count);
      sendBase("getItemsByPrefix", args);
    }

    public Map<String,TItem> recv_getItemsByPrefix() throws TIOError, TIllegalArgument, AuthExpire, org.apache.thrift.TException
    {
      getItemsByPrefix_result result = new getItemsByPrefix_result();
      receiveBase(result, "getItemsByPrefix");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.ia != null) {
        throw result.ia;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "getItemsByPrefix failed: unknown result");
    }

    public void setItem(KeyPair kp, ByteBuffer key, TItem item) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_setItem(kp, key, item);
      recv_setItem();
    }

    public void send_setItem(KeyPair kp, ByteBuffer key, TItem item) throws org.apache.thrift.TException
    {
      setItem_args args = new setItem_args();
      args.setKp(kp);
      args.setKey(key);
      args.setItem(item);
      sendBase("setItem", args);
    }

    public void recv_setItem() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      setItem_result result = new setItem_result();
      receiveBase(result, "setItem");
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      return;
    }

    public void deleteItem(KeyPair kp, ByteBuffer key) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_deleteItem(kp, key);
      recv_deleteItem();
    }

    public void send_deleteItem(KeyPair kp, ByteBuffer key) throws org.apache.thrift.TException
    {
      deleteItem_args args = new deleteItem_args();
      args.setKp(kp);
      args.setKey(key);
      sendBase("deleteItem", args);
    }

    public void recv_deleteItem() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      deleteItem_result result = new deleteItem_result();
      receiveBase(result, "deleteItem");
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      return;
    }

    public Map<String,TItem> rget(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_rget(kp, startKey, stopKey, count);
      return recv_rget();
    }

    public void send_rget(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws org.apache.thrift.TException
    {
      rget_args args = new rget_args();
      args.setKp(kp);
      args.setStartKey(startKey);
      args.setStopKey(stopKey);
      args.setCount(count);
      sendBase("rget", args);
    }

    public Map<String,TItem> recv_rget() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      rget_result result = new rget_result();
      receiveBase(result, "rget");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "rget failed: unknown result");
    }

    public Map<String,TItem> prkget(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_prkget(kp, prefixKey, count, isContainValue, compareKey);
      return recv_prkget();
    }

    public void send_prkget(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey) throws org.apache.thrift.TException
    {
      prkget_args args = new prkget_args();
      args.setKp(kp);
      args.setPrefixKey(prefixKey);
      args.setCount(count);
      args.setIsContainValue(isContainValue);
      args.setCompareKey(compareKey);
      sendBase("prkget", args);
    }

    public Map<String,TItem> recv_prkget() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      prkget_result result = new prkget_result();
      receiveBase(result, "prkget");
      if (result.isSetSuccess()) {
        return result.success;
      }
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "prkget failed: unknown result");
    }

    public void rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      send_rdel(kp, startKey, stopKey, count);
      recv_rdel();
    }

    public void send_rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count) throws org.apache.thrift.TException
    {
      rdel_args args = new rdel_args();
      args.setKp(kp);
      args.setStartKey(startKey);
      args.setStopKey(stopKey);
      args.setCount(count);
      sendBase("rdel", args);
    }

    public void recv_rdel() throws TIOError, AuthExpire, org.apache.thrift.TException
    {
      rdel_result result = new rdel_result();
      receiveBase(result, "rdel");
      if (result.io != null) {
        throw result.io;
      }
      if (result.e != null) {
        throw result.e;
      }
      return;
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void auth(KeyPair kp, org.apache.thrift.async.AsyncMethodCallback<auth_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      auth_call method_call = new auth_call(kp, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class auth_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      public auth_call(KeyPair kp, org.apache.thrift.async.AsyncMethodCallback<auth_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("auth", org.apache.thrift.protocol.TMessageType.CALL, 0));
        auth_args args = new auth_args();
        args.setKp(kp);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public KeyPair getResult() throws TIOError, AuthFailure, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_auth();
      }
    }

    public void getItem(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<getItem_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      getItem_call method_call = new getItem_call(kp, key, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getItem_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer key;
      public getItem_call(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<getItem_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.key = key;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getItem", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getItem_args args = new getItem_args();
        args.setKp(kp);
        args.setKey(key);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public TItem getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getItem();
      }
    }

    public void getItems(KeyPair kp, List<ByteBuffer> keys, org.apache.thrift.async.AsyncMethodCallback<getItems_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      getItems_call method_call = new getItems_call(kp, keys, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getItems_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private List<ByteBuffer> keys;
      public getItems_call(KeyPair kp, List<ByteBuffer> keys, org.apache.thrift.async.AsyncMethodCallback<getItems_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.keys = keys;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getItems", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getItems_args args = new getItems_args();
        args.setKp(kp);
        args.setKeys(keys);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Map<String,TItem> getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getItems();
      }
    }

    public void getItemsByPrefix(KeyPair kp, ByteBuffer prefix, int count, org.apache.thrift.async.AsyncMethodCallback<getItemsByPrefix_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      getItemsByPrefix_call method_call = new getItemsByPrefix_call(kp, prefix, count, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class getItemsByPrefix_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer prefix;
      private int count;
      public getItemsByPrefix_call(KeyPair kp, ByteBuffer prefix, int count, org.apache.thrift.async.AsyncMethodCallback<getItemsByPrefix_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.prefix = prefix;
        this.count = count;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("getItemsByPrefix", org.apache.thrift.protocol.TMessageType.CALL, 0));
        getItemsByPrefix_args args = new getItemsByPrefix_args();
        args.setKp(kp);
        args.setPrefix(prefix);
        args.setCount(count);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Map<String,TItem> getResult() throws TIOError, TIllegalArgument, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_getItemsByPrefix();
      }
    }

    public void setItem(KeyPair kp, ByteBuffer key, TItem item, org.apache.thrift.async.AsyncMethodCallback<setItem_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      setItem_call method_call = new setItem_call(kp, key, item, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class setItem_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer key;
      private TItem item;
      public setItem_call(KeyPair kp, ByteBuffer key, TItem item, org.apache.thrift.async.AsyncMethodCallback<setItem_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.key = key;
        this.item = item;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("setItem", org.apache.thrift.protocol.TMessageType.CALL, 0));
        setItem_args args = new setItem_args();
        args.setKp(kp);
        args.setKey(key);
        args.setItem(item);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_setItem();
      }
    }

    public void deleteItem(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<deleteItem_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      deleteItem_call method_call = new deleteItem_call(kp, key, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class deleteItem_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer key;
      public deleteItem_call(KeyPair kp, ByteBuffer key, org.apache.thrift.async.AsyncMethodCallback<deleteItem_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.key = key;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("deleteItem", org.apache.thrift.protocol.TMessageType.CALL, 0));
        deleteItem_args args = new deleteItem_args();
        args.setKp(kp);
        args.setKey(key);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_deleteItem();
      }
    }

    public void rget(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<rget_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      rget_call method_call = new rget_call(kp, startKey, stopKey, count, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class rget_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer startKey;
      private ByteBuffer stopKey;
      private int count;
      public rget_call(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<rget_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.startKey = startKey;
        this.stopKey = stopKey;
        this.count = count;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("rget", org.apache.thrift.protocol.TMessageType.CALL, 0));
        rget_args args = new rget_args();
        args.setKp(kp);
        args.setStartKey(startKey);
        args.setStopKey(stopKey);
        args.setCount(count);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Map<String,TItem> getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_rget();
      }
    }

    public void prkget(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey, org.apache.thrift.async.AsyncMethodCallback<prkget_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      prkget_call method_call = new prkget_call(kp, prefixKey, count, isContainValue, compareKey, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class prkget_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer prefixKey;
      private int count;
      private boolean isContainValue;
      private ByteBuffer compareKey;
      public prkget_call(KeyPair kp, ByteBuffer prefixKey, int count, boolean isContainValue, ByteBuffer compareKey, org.apache.thrift.async.AsyncMethodCallback<prkget_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.prefixKey = prefixKey;
        this.count = count;
        this.isContainValue = isContainValue;
        this.compareKey = compareKey;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("prkget", org.apache.thrift.protocol.TMessageType.CALL, 0));
        prkget_args args = new prkget_args();
        args.setKp(kp);
        args.setPrefixKey(prefixKey);
        args.setCount(count);
        args.setIsContainValue(isContainValue);
        args.setCompareKey(compareKey);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public Map<String,TItem> getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_prkget();
      }
    }

    public void rdel(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<rdel_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      rdel_call method_call = new rdel_call(kp, startKey, stopKey, count, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class rdel_call extends org.apache.thrift.async.TAsyncMethodCall {
      private KeyPair kp;
      private ByteBuffer startKey;
      private ByteBuffer stopKey;
      private int count;
      public rdel_call(KeyPair kp, ByteBuffer startKey, ByteBuffer stopKey, int count, org.apache.thrift.async.AsyncMethodCallback<rdel_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.kp = kp;
        this.startKey = startKey;
        this.stopKey = stopKey;
        this.count = count;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("rdel", org.apache.thrift.protocol.TMessageType.CALL, 0));
        rdel_args args = new rdel_args();
        args.setKp(kp);
        args.setStartKey(startKey);
        args.setStopKey(stopKey);
        args.setCount(count);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws TIOError, AuthExpire, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_rdel();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("auth", new auth());
      processMap.put("getItem", new getItem());
      processMap.put("getItems", new getItems());
      processMap.put("getItemsByPrefix", new getItemsByPrefix());
      processMap.put("setItem", new setItem());
      processMap.put("deleteItem", new deleteItem());
      processMap.put("rget", new rget());
      processMap.put("prkget", new prkget());
      processMap.put("rdel", new rdel());
      return processMap;
    }

    private static class auth<I extends Iface> extends org.apache.thrift.ProcessFunction<I, auth_args> {
      public auth() {
        super("auth");
      }

      protected auth_args getEmptyArgsInstance() {
        return new auth_args();
      }

      protected auth_result getResult(I iface, auth_args args) throws org.apache.thrift.TException {
        auth_result result = new auth_result();
        try {
          result.success = iface.auth(args.kp);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthFailure e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class getItem<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getItem_args> {
      public getItem() {
        super("getItem");
      }

      protected getItem_args getEmptyArgsInstance() {
        return new getItem_args();
      }

      protected getItem_result getResult(I iface, getItem_args args) throws org.apache.thrift.TException {
        getItem_result result = new getItem_result();
        try {
          result.success = iface.getItem(args.kp, args.key);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class getItems<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getItems_args> {
      public getItems() {
        super("getItems");
      }

      protected getItems_args getEmptyArgsInstance() {
        return new getItems_args();
      }

      protected getItems_result getResult(I iface, getItems_args args) throws org.apache.thrift.TException {
        getItems_result result = new getItems_result();
        try {
          result.success = iface.getItems(args.kp, args.keys);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class getItemsByPrefix<I extends Iface> extends org.apache.thrift.ProcessFunction<I, getItemsByPrefix_args> {
      public getItemsByPrefix() {
        super("getItemsByPrefix");
      }

      protected getItemsByPrefix_args getEmptyArgsInstance() {
        return new getItemsByPrefix_args();
      }

      protected getItemsByPrefix_result getResult(I iface, getItemsByPrefix_args args) throws org.apache.thrift.TException {
        getItemsByPrefix_result result = new getItemsByPrefix_result();
        try {
          result.success = iface.getItemsByPrefix(args.kp, args.prefix, args.count);
        } catch (TIOError io) {
          result.io = io;
        } catch (TIllegalArgument ia) {
          result.ia = ia;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class setItem<I extends Iface> extends org.apache.thrift.ProcessFunction<I, setItem_args> {
      public setItem() {
        super("setItem");
      }

      protected setItem_args getEmptyArgsInstance() {
        return new setItem_args();
      }

      protected setItem_result getResult(I iface, setItem_args args) throws org.apache.thrift.TException {
        setItem_result result = new setItem_result();
        try {
          iface.setItem(args.kp, args.key, args.item);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class deleteItem<I extends Iface> extends org.apache.thrift.ProcessFunction<I, deleteItem_args> {
      public deleteItem() {
        super("deleteItem");
      }

      protected deleteItem_args getEmptyArgsInstance() {
        return new deleteItem_args();
      }

      protected deleteItem_result getResult(I iface, deleteItem_args args) throws org.apache.thrift.TException {
        deleteItem_result result = new deleteItem_result();
        try {
          iface.deleteItem(args.kp, args.key);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class rget<I extends Iface> extends org.apache.thrift.ProcessFunction<I, rget_args> {
      public rget() {
        super("rget");
      }

      protected rget_args getEmptyArgsInstance() {
        return new rget_args();
      }

      protected rget_result getResult(I iface, rget_args args) throws org.apache.thrift.TException {
        rget_result result = new rget_result();
        try {
          result.success = iface.rget(args.kp, args.startKey, args.stopKey, args.count);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class prkget<I extends Iface> extends org.apache.thrift.ProcessFunction<I, prkget_args> {
      public prkget() {
        super("prkget");
      }

      protected prkget_args getEmptyArgsInstance() {
        return new prkget_args();
      }

      protected prkget_result getResult(I iface, prkget_args args) throws org.apache.thrift.TException {
        prkget_result result = new prkget_result();
        try {
          result.success = iface.prkget(args.kp, args.prefixKey, args.count, args.isContainValue, args.compareKey);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

    private static class rdel<I extends Iface> extends org.apache.thrift.ProcessFunction<I, rdel_args> {
      public rdel() {
        super("rdel");
      }

      protected rdel_args getEmptyArgsInstance() {
        return new rdel_args();
      }

      protected rdel_result getResult(I iface, rdel_args args) throws org.apache.thrift.TException {
        rdel_result result = new rdel_result();
        try {
          iface.rdel(args.kp, args.startKey, args.stopKey, args.count);
        } catch (TIOError io) {
          result.io = io;
        } catch (AuthExpire e) {
          result.e = e;
        }
        return result;
      }
    }

  }

  public static class auth_args implements org.apache.thrift.TBase<auth_args, auth_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("auth_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new auth_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new auth_argsTupleSchemeFactory());
    }

    public KeyPair kp; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      KP((short)1, "kp");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(auth_args.class, metaDataMap);
    }

    public auth_args() {
    }

    public auth_args(
      KeyPair kp)
    {
      this();
      this.kp = kp;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public auth_args(auth_args other) {
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
    }

    public auth_args deepCopy() {
      return new auth_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
    }

    public KeyPair getKp() {
      return this.kp;
    }

    public auth_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof auth_args)
        return this.equals((auth_args)that);
      return false;
    }

    public boolean equals(auth_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(auth_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      auth_args typedOther = (auth_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("auth_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class auth_argsStandardSchemeFactory implements SchemeFactory {
      public auth_argsStandardScheme getScheme() {
        return new auth_argsStandardScheme();
      }
    }

    private static class auth_argsStandardScheme extends StandardScheme<auth_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, auth_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, auth_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class auth_argsTupleSchemeFactory implements SchemeFactory {
      public auth_argsTupleScheme getScheme() {
        return new auth_argsTupleScheme();
      }
    }

    private static class auth_argsTupleScheme extends TupleScheme<auth_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, auth_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, auth_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
      }
    }

  }

  public static class auth_result implements org.apache.thrift.TBase<auth_result, auth_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("auth_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new auth_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new auth_resultTupleSchemeFactory());
    }

    public KeyPair success; // required
    public TIOError io; // required
    public AuthFailure e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(auth_result.class, metaDataMap);
    }

    public auth_result() {
    }

    public auth_result(
      KeyPair success,
      TIOError io,
      AuthFailure e)
    {
      this();
      this.success = success;
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public auth_result(auth_result other) {
      if (other.isSetSuccess()) {
        this.success = new KeyPair(other.success);
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthFailure(other.e);
      }
    }

    public auth_result deepCopy() {
      return new auth_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.e = null;
    }

    public KeyPair getSuccess() {
      return this.success;
    }

    public auth_result setSuccess(KeyPair success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public auth_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthFailure getE() {
      return this.e;
    }

    public auth_result setE(AuthFailure e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((KeyPair)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthFailure)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof auth_result)
        return this.equals((auth_result)that);
      return false;
    }

    public boolean equals(auth_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(auth_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      auth_result typedOther = (auth_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("auth_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class auth_resultStandardSchemeFactory implements SchemeFactory {
      public auth_resultStandardScheme getScheme() {
        return new auth_resultStandardScheme();
      }
    }

    private static class auth_resultStandardScheme extends StandardScheme<auth_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, auth_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new KeyPair();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthFailure();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, auth_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class auth_resultTupleSchemeFactory implements SchemeFactory {
      public auth_resultTupleScheme getScheme() {
        return new auth_resultTupleScheme();
      }
    }

    private static class auth_resultTupleScheme extends TupleScheme<auth_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, auth_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetE()) {
          optionals.set(2);
        }
        oprot.writeBitSet(optionals, 3);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, auth_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(3);
        if (incoming.get(0)) {
          struct.success = new KeyPair();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.e = new AuthFailure();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class getItem_args implements org.apache.thrift.TBase<getItem_args, getItem_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItem_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("key", org.apache.thrift.protocol.TType.STRING, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItem_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItem_argsTupleSchemeFactory());
    }

    public KeyPair kp; // required
    public ByteBuffer key; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      KP((short)1, "kp"),
      KEY((short)2, "key");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // KEY
            return KEY;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.KEY, new org.apache.thrift.meta_data.FieldMetaData("key", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItem_args.class, metaDataMap);
    }

    public getItem_args() {
    }

    public getItem_args(
      KeyPair kp,
      ByteBuffer key)
    {
      this();
      this.kp = kp;
      this.key = key;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItem_args(getItem_args other) {
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetKey()) {
        this.key = org.apache.thrift.TBaseHelper.copyBinary(other.key);
;
      }
    }

    public getItem_args deepCopy() {
      return new getItem_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.key = null;
    }

    public KeyPair getKp() {
      return this.kp;
    }

    public getItem_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getKey() {
      setKey(org.apache.thrift.TBaseHelper.rightSize(key));
      return key == null ? null : key.array();
    }

    public ByteBuffer bufferForKey() {
      return key;
    }

    public getItem_args setKey(byte[] key) {
      setKey(key == null ? (ByteBuffer)null : ByteBuffer.wrap(key));
      return this;
    }

    public getItem_args setKey(ByteBuffer key) {
      this.key = key;
      return this;
    }

    public void unsetKey() {
      this.key = null;
    }

    /** Returns true if field key is set (has been assigned a value) and false otherwise */
    public boolean isSetKey() {
      return this.key != null;
    }

    public void setKeyIsSet(boolean value) {
      if (!value) {
        this.key = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case KEY:
        if (value == null) {
          unsetKey();
        } else {
          setKey((ByteBuffer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case KEY:
        return getKey();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case KEY:
        return isSetKey();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItem_args)
        return this.equals((getItem_args)that);
      return false;
    }

    public boolean equals(getItem_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_key = true && this.isSetKey();
      boolean that_present_key = true && that.isSetKey();
      if (this_present_key || that_present_key) {
        if (!(this_present_key && that_present_key))
          return false;
        if (!this.key.equals(that.key))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItem_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItem_args typedOther = (getItem_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetKey()).compareTo(typedOther.isSetKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key, typedOther.key);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItem_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("key:");
      if (this.key == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.key, sb);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (key == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'key' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItem_argsStandardSchemeFactory implements SchemeFactory {
      public getItem_argsStandardScheme getScheme() {
        return new getItem_argsStandardScheme();
      }
    }

    private static class getItem_argsStandardScheme extends StandardScheme<getItem_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItem_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.key = iprot.readBinary();
                struct.setKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItem_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.key != null) {
          oprot.writeFieldBegin(KEY_FIELD_DESC);
          oprot.writeBinary(struct.key);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItem_argsTupleSchemeFactory implements SchemeFactory {
      public getItem_argsTupleScheme getScheme() {
        return new getItem_argsTupleScheme();
      }
    }

    private static class getItem_argsTupleScheme extends TupleScheme<getItem_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.key);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.key = iprot.readBinary();
        struct.setKeyIsSet(true);
      }
    }

  }

  public static class getItem_result implements org.apache.thrift.TBase<getItem_result, getItem_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItem_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRUCT, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItem_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItem_resultTupleSchemeFactory());
    }

    public TItem success; // required
    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class)));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItem_result.class, metaDataMap);
    }

    public getItem_result() {
    }

    public getItem_result(
      TItem success,
      TIOError io,
      AuthExpire e)
    {
      this();
      this.success = success;
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItem_result(getItem_result other) {
      if (other.isSetSuccess()) {
        this.success = new TItem(other.success);
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public getItem_result deepCopy() {
      return new getItem_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.e = null;
    }

    public TItem getSuccess() {
      return this.success;
    }

    public getItem_result setSuccess(TItem success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public getItem_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public getItem_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((TItem)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItem_result)
        return this.equals((getItem_result)that);
      return false;
    }

    public boolean equals(getItem_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItem_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItem_result typedOther = (getItem_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItem_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItem_resultStandardSchemeFactory implements SchemeFactory {
      public getItem_resultStandardScheme getScheme() {
        return new getItem_resultStandardScheme();
      }
    }

    private static class getItem_resultStandardScheme extends StandardScheme<getItem_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItem_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.success = new TItem();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItem_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItem_resultTupleSchemeFactory implements SchemeFactory {
      public getItem_resultTupleScheme getScheme() {
        return new getItem_resultTupleScheme();
      }
    }

    private static class getItem_resultTupleScheme extends TupleScheme<getItem_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetE()) {
          optionals.set(2);
        }
        oprot.writeBitSet(optionals, 3);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(3);
        if (incoming.get(0)) {
          struct.success = new TItem();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class getItems_args implements org.apache.thrift.TBase<getItems_args, getItems_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItems_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField KEYS_FIELD_DESC = new org.apache.thrift.protocol.TField("keys", org.apache.thrift.protocol.TType.LIST, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItems_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItems_argsTupleSchemeFactory());
    }

    /**
     * a list of keys to fetch, the Item list
     * will have the Results at corresponding positions
     * or null if there was an error
     */
    public KeyPair kp; // required
    public List<ByteBuffer> keys; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      /**
       * a list of keys to fetch, the Item list
       * will have the Results at corresponding positions
       * or null if there was an error
       */
      KP((short)1, "kp"),
      KEYS((short)2, "keys");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // KEYS
            return KEYS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.KEYS, new org.apache.thrift.meta_data.FieldMetaData("keys", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING              , true))));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItems_args.class, metaDataMap);
    }

    public getItems_args() {
    }

    public getItems_args(
      KeyPair kp,
      List<ByteBuffer> keys)
    {
      this();
      this.kp = kp;
      this.keys = keys;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItems_args(getItems_args other) {
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetKeys()) {
        List<ByteBuffer> __this__keys = new ArrayList<ByteBuffer>();
        for (ByteBuffer other_element : other.keys) {
          ByteBuffer temp_binary_element = org.apache.thrift.TBaseHelper.copyBinary(other_element);
;
          __this__keys.add(temp_binary_element);
        }
        this.keys = __this__keys;
      }
    }

    public getItems_args deepCopy() {
      return new getItems_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.keys = null;
    }

    /**
     * a list of keys to fetch, the Item list
     * will have the Results at corresponding positions
     * or null if there was an error
     */
    public KeyPair getKp() {
      return this.kp;
    }

    /**
     * a list of keys to fetch, the Item list
     * will have the Results at corresponding positions
     * or null if there was an error
     */
    public getItems_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public int getKeysSize() {
      return (this.keys == null) ? 0 : this.keys.size();
    }

    public java.util.Iterator<ByteBuffer> getKeysIterator() {
      return (this.keys == null) ? null : this.keys.iterator();
    }

    public void addToKeys(ByteBuffer elem) {
      if (this.keys == null) {
        this.keys = new ArrayList<ByteBuffer>();
      }
      this.keys.add(elem);
    }

    public List<ByteBuffer> getKeys() {
      return this.keys;
    }

    public getItems_args setKeys(List<ByteBuffer> keys) {
      this.keys = keys;
      return this;
    }

    public void unsetKeys() {
      this.keys = null;
    }

    /** Returns true if field keys is set (has been assigned a value) and false otherwise */
    public boolean isSetKeys() {
      return this.keys != null;
    }

    public void setKeysIsSet(boolean value) {
      if (!value) {
        this.keys = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case KEYS:
        if (value == null) {
          unsetKeys();
        } else {
          setKeys((List<ByteBuffer>)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case KEYS:
        return getKeys();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case KEYS:
        return isSetKeys();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItems_args)
        return this.equals((getItems_args)that);
      return false;
    }

    public boolean equals(getItems_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_keys = true && this.isSetKeys();
      boolean that_present_keys = true && that.isSetKeys();
      if (this_present_keys || that_present_keys) {
        if (!(this_present_keys && that_present_keys))
          return false;
        if (!this.keys.equals(that.keys))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItems_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItems_args typedOther = (getItems_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetKeys()).compareTo(typedOther.isSetKeys());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKeys()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keys, typedOther.keys);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItems_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("keys:");
      if (this.keys == null) {
        sb.append("null");
      } else {
        sb.append(this.keys);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (keys == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'keys' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItems_argsStandardSchemeFactory implements SchemeFactory {
      public getItems_argsStandardScheme getScheme() {
        return new getItems_argsStandardScheme();
      }
    }

    private static class getItems_argsStandardScheme extends StandardScheme<getItems_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItems_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // KEYS
              if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
                {
                  org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                  struct.keys = new ArrayList<ByteBuffer>(_list0.size);
                  for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                  {
                    ByteBuffer _elem2; // required
                    _elem2 = iprot.readBinary();
                    struct.keys.add(_elem2);
                  }
                  iprot.readListEnd();
                }
                struct.setKeysIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItems_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.keys != null) {
          oprot.writeFieldBegin(KEYS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.keys.size()));
            for (ByteBuffer _iter3 : struct.keys)
            {
              oprot.writeBinary(_iter3);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItems_argsTupleSchemeFactory implements SchemeFactory {
      public getItems_argsTupleScheme getScheme() {
        return new getItems_argsTupleScheme();
      }
    }

    private static class getItems_argsTupleScheme extends TupleScheme<getItems_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItems_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        {
          oprot.writeI32(struct.keys.size());
          for (ByteBuffer _iter4 : struct.keys)
          {
            oprot.writeBinary(_iter4);
          }
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItems_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.keys = new ArrayList<ByteBuffer>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            ByteBuffer _elem7; // required
            _elem7 = iprot.readBinary();
            struct.keys.add(_elem7);
          }
        }
        struct.setKeysIsSet(true);
      }
    }

  }

  public static class getItems_result implements org.apache.thrift.TBase<getItems_result, getItems_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItems_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.MAP, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItems_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItems_resultTupleSchemeFactory());
    }

    public Map<String,TItem> success; // required
    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
              new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class))));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItems_result.class, metaDataMap);
    }

    public getItems_result() {
    }

    public getItems_result(
      Map<String,TItem> success,
      TIOError io,
      AuthExpire e)
    {
      this();
      this.success = success;
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItems_result(getItems_result other) {
      if (other.isSetSuccess()) {
        Map<String,TItem> __this__success = new HashMap<String,TItem>();
        for (Map.Entry<String, TItem> other_element : other.success.entrySet()) {

          String other_element_key = other_element.getKey();
          TItem other_element_value = other_element.getValue();

          String __this__success_copy_key = other_element_key;

          TItem __this__success_copy_value = new TItem(other_element_value);

          __this__success.put(__this__success_copy_key, __this__success_copy_value);
        }
        this.success = __this__success;
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public getItems_result deepCopy() {
      return new getItems_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.e = null;
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public void putToSuccess(String key, TItem val) {
      if (this.success == null) {
        this.success = new HashMap<String,TItem>();
      }
      this.success.put(key, val);
    }

    public Map<String,TItem> getSuccess() {
      return this.success;
    }

    public getItems_result setSuccess(Map<String,TItem> success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public getItems_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public getItems_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Map<String,TItem>)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItems_result)
        return this.equals((getItems_result)that);
      return false;
    }

    public boolean equals(getItems_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItems_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItems_result typedOther = (getItems_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItems_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItems_resultStandardSchemeFactory implements SchemeFactory {
      public getItems_resultStandardScheme getScheme() {
        return new getItems_resultStandardScheme();
      }
    }

    private static class getItems_resultStandardScheme extends StandardScheme<getItems_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItems_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
                {
                  org.apache.thrift.protocol.TMap _map8 = iprot.readMapBegin();
                  struct.success = new HashMap<String,TItem>(2*_map8.size);
                  for (int _i9 = 0; _i9 < _map8.size; ++_i9)
                  {
                    String _key10; // required
                    TItem _val11; // required
                    _key10 = iprot.readString();
                    _val11 = new TItem();
                    _val11.read(iprot);
                    struct.success.put(_key10, _val11);
                  }
                  iprot.readMapEnd();
                }
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItems_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.success.size()));
            for (Map.Entry<String, TItem> _iter12 : struct.success.entrySet())
            {
              oprot.writeString(_iter12.getKey());
              _iter12.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItems_resultTupleSchemeFactory implements SchemeFactory {
      public getItems_resultTupleScheme getScheme() {
        return new getItems_resultTupleScheme();
      }
    }

    private static class getItems_resultTupleScheme extends TupleScheme<getItems_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItems_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetE()) {
          optionals.set(2);
        }
        oprot.writeBitSet(optionals, 3);
        if (struct.isSetSuccess()) {
          {
            oprot.writeI32(struct.success.size());
            for (Map.Entry<String, TItem> _iter13 : struct.success.entrySet())
            {
              oprot.writeString(_iter13.getKey());
              _iter13.getValue().write(oprot);
            }
          }
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItems_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(3);
        if (incoming.get(0)) {
          {
            org.apache.thrift.protocol.TMap _map14 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
            struct.success = new HashMap<String,TItem>(2*_map14.size);
            for (int _i15 = 0; _i15 < _map14.size; ++_i15)
            {
              String _key16; // required
              TItem _val17; // required
              _key16 = iprot.readString();
              _val17 = new TItem();
              _val17.read(iprot);
              struct.success.put(_key16, _val17);
            }
          }
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class getItemsByPrefix_args implements org.apache.thrift.TBase<getItemsByPrefix_args, getItemsByPrefix_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItemsByPrefix_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField PREFIX_FIELD_DESC = new org.apache.thrift.protocol.TField("prefix", org.apache.thrift.protocol.TType.STRING, (short)2);
    private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItemsByPrefix_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItemsByPrefix_argsTupleSchemeFactory());
    }

    /**
     * Key prefix to fetch, the Item list
     */
    public KeyPair kp; // required
    public ByteBuffer prefix; // required
    public int count; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      /**
       * Key prefix to fetch, the Item list
       */
      KP((short)1, "kp"),
      PREFIX((short)2, "prefix"),
      COUNT((short)3, "count");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // PREFIX
            return PREFIX;
          case 3: // COUNT
            return COUNT;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __COUNT_ISSET_ID = 0;
    private BitSet __isset_bit_vector = new BitSet(1);
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.PREFIX, new org.apache.thrift.meta_data.FieldMetaData("prefix", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItemsByPrefix_args.class, metaDataMap);
    }

    public getItemsByPrefix_args() {
    }

    public getItemsByPrefix_args(
      KeyPair kp,
      ByteBuffer prefix,
      int count)
    {
      this();
      this.kp = kp;
      this.prefix = prefix;
      this.count = count;
      setCountIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItemsByPrefix_args(getItemsByPrefix_args other) {
      __isset_bit_vector.clear();
      __isset_bit_vector.or(other.__isset_bit_vector);
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetPrefix()) {
        this.prefix = org.apache.thrift.TBaseHelper.copyBinary(other.prefix);
;
      }
      this.count = other.count;
    }

    public getItemsByPrefix_args deepCopy() {
      return new getItemsByPrefix_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.prefix = null;
      setCountIsSet(false);
      this.count = 0;
    }

    /**
     * Key prefix to fetch, the Item list
     */
    public KeyPair getKp() {
      return this.kp;
    }

    /**
     * Key prefix to fetch, the Item list
     */
    public getItemsByPrefix_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getPrefix() {
      setPrefix(org.apache.thrift.TBaseHelper.rightSize(prefix));
      return prefix == null ? null : prefix.array();
    }

    public ByteBuffer bufferForPrefix() {
      return prefix;
    }

    public getItemsByPrefix_args setPrefix(byte[] prefix) {
      setPrefix(prefix == null ? (ByteBuffer)null : ByteBuffer.wrap(prefix));
      return this;
    }

    public getItemsByPrefix_args setPrefix(ByteBuffer prefix) {
      this.prefix = prefix;
      return this;
    }

    public void unsetPrefix() {
      this.prefix = null;
    }

    /** Returns true if field prefix is set (has been assigned a value) and false otherwise */
    public boolean isSetPrefix() {
      return this.prefix != null;
    }

    public void setPrefixIsSet(boolean value) {
      if (!value) {
        this.prefix = null;
      }
    }

    public int getCount() {
      return this.count;
    }

    public getItemsByPrefix_args setCount(int count) {
      this.count = count;
      setCountIsSet(true);
      return this;
    }

    public void unsetCount() {
      __isset_bit_vector.clear(__COUNT_ISSET_ID);
    }

    /** Returns true if field count is set (has been assigned a value) and false otherwise */
    public boolean isSetCount() {
      return __isset_bit_vector.get(__COUNT_ISSET_ID);
    }

    public void setCountIsSet(boolean value) {
      __isset_bit_vector.set(__COUNT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case PREFIX:
        if (value == null) {
          unsetPrefix();
        } else {
          setPrefix((ByteBuffer)value);
        }
        break;

      case COUNT:
        if (value == null) {
          unsetCount();
        } else {
          setCount((Integer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case PREFIX:
        return getPrefix();

      case COUNT:
        return Integer.valueOf(getCount());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case PREFIX:
        return isSetPrefix();
      case COUNT:
        return isSetCount();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItemsByPrefix_args)
        return this.equals((getItemsByPrefix_args)that);
      return false;
    }

    public boolean equals(getItemsByPrefix_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_prefix = true && this.isSetPrefix();
      boolean that_present_prefix = true && that.isSetPrefix();
      if (this_present_prefix || that_present_prefix) {
        if (!(this_present_prefix && that_present_prefix))
          return false;
        if (!this.prefix.equals(that.prefix))
          return false;
      }

      boolean this_present_count = true;
      boolean that_present_count = true;
      if (this_present_count || that_present_count) {
        if (!(this_present_count && that_present_count))
          return false;
        if (this.count != that.count)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItemsByPrefix_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItemsByPrefix_args typedOther = (getItemsByPrefix_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetPrefix()).compareTo(typedOther.isSetPrefix());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetPrefix()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prefix, typedOther.prefix);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCount()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, typedOther.count);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItemsByPrefix_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("prefix:");
      if (this.prefix == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.prefix, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("count:");
      sb.append(this.count);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (prefix == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'prefix' was not present! Struct: " + toString());
      }
      // alas, we cannot check 'count' because it's a primitive and you chose the non-beans generator.
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bit_vector = new BitSet(1);
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItemsByPrefix_argsStandardSchemeFactory implements SchemeFactory {
      public getItemsByPrefix_argsStandardScheme getScheme() {
        return new getItemsByPrefix_argsStandardScheme();
      }
    }

    private static class getItemsByPrefix_argsStandardScheme extends StandardScheme<getItemsByPrefix_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItemsByPrefix_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // PREFIX
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.prefix = iprot.readBinary();
                struct.setPrefixIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // COUNT
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.count = iprot.readI32();
                struct.setCountIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        if (!struct.isSetCount()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'count' was not found in serialized data! Struct: " + toString());
        }
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItemsByPrefix_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.prefix != null) {
          oprot.writeFieldBegin(PREFIX_FIELD_DESC);
          oprot.writeBinary(struct.prefix);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldBegin(COUNT_FIELD_DESC);
        oprot.writeI32(struct.count);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItemsByPrefix_argsTupleSchemeFactory implements SchemeFactory {
      public getItemsByPrefix_argsTupleScheme getScheme() {
        return new getItemsByPrefix_argsTupleScheme();
      }
    }

    private static class getItemsByPrefix_argsTupleScheme extends TupleScheme<getItemsByPrefix_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItemsByPrefix_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.prefix);
        oprot.writeI32(struct.count);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItemsByPrefix_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.prefix = iprot.readBinary();
        struct.setPrefixIsSet(true);
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
    }

  }

  public static class getItemsByPrefix_result implements org.apache.thrift.TBase<getItemsByPrefix_result, getItemsByPrefix_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("getItemsByPrefix_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.MAP, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField IA_FIELD_DESC = new org.apache.thrift.protocol.TField("ia", org.apache.thrift.protocol.TType.STRUCT, (short)2);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new getItemsByPrefix_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new getItemsByPrefix_resultTupleSchemeFactory());
    }

    public Map<String,TItem> success; // required
    public TIOError io; // required
    /**
     * if the count is invalid
     */
    public TIllegalArgument ia; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      /**
       * if the count is invalid
       */
      IA((short)2, "ia"),
      E((short)3, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // IA
            return IA;
          case 3: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
              new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class))));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.IA, new org.apache.thrift.meta_data.FieldMetaData("ia", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(getItemsByPrefix_result.class, metaDataMap);
    }

    public getItemsByPrefix_result() {
    }

    public getItemsByPrefix_result(
      Map<String,TItem> success,
      TIOError io,
      TIllegalArgument ia,
      AuthExpire e)
    {
      this();
      this.success = success;
      this.io = io;
      this.ia = ia;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public getItemsByPrefix_result(getItemsByPrefix_result other) {
      if (other.isSetSuccess()) {
        Map<String,TItem> __this__success = new HashMap<String,TItem>();
        for (Map.Entry<String, TItem> other_element : other.success.entrySet()) {

          String other_element_key = other_element.getKey();
          TItem other_element_value = other_element.getValue();

          String __this__success_copy_key = other_element_key;

          TItem __this__success_copy_value = new TItem(other_element_value);

          __this__success.put(__this__success_copy_key, __this__success_copy_value);
        }
        this.success = __this__success;
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetIa()) {
        this.ia = new TIllegalArgument(other.ia);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public getItemsByPrefix_result deepCopy() {
      return new getItemsByPrefix_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.ia = null;
      this.e = null;
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public void putToSuccess(String key, TItem val) {
      if (this.success == null) {
        this.success = new HashMap<String,TItem>();
      }
      this.success.put(key, val);
    }

    public Map<String,TItem> getSuccess() {
      return this.success;
    }

    public getItemsByPrefix_result setSuccess(Map<String,TItem> success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public getItemsByPrefix_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    /**
     * if the count is invalid
     */
    public TIllegalArgument getIa() {
      return this.ia;
    }

    /**
     * if the count is invalid
     */
    public getItemsByPrefix_result setIa(TIllegalArgument ia) {
      this.ia = ia;
      return this;
    }

    public void unsetIa() {
      this.ia = null;
    }

    /** Returns true if field ia is set (has been assigned a value) and false otherwise */
    public boolean isSetIa() {
      return this.ia != null;
    }

    public void setIaIsSet(boolean value) {
      if (!value) {
        this.ia = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public getItemsByPrefix_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Map<String,TItem>)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case IA:
        if (value == null) {
          unsetIa();
        } else {
          setIa((TIllegalArgument)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case IA:
        return getIa();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case IA:
        return isSetIa();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof getItemsByPrefix_result)
        return this.equals((getItemsByPrefix_result)that);
      return false;
    }

    public boolean equals(getItemsByPrefix_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_ia = true && this.isSetIa();
      boolean that_present_ia = true && that.isSetIa();
      if (this_present_ia || that_present_ia) {
        if (!(this_present_ia && that_present_ia))
          return false;
        if (!this.ia.equals(that.ia))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(getItemsByPrefix_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      getItemsByPrefix_result typedOther = (getItemsByPrefix_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIa()).compareTo(typedOther.isSetIa());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIa()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ia, typedOther.ia);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("getItemsByPrefix_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("ia:");
      if (this.ia == null) {
        sb.append("null");
      } else {
        sb.append(this.ia);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class getItemsByPrefix_resultStandardSchemeFactory implements SchemeFactory {
      public getItemsByPrefix_resultStandardScheme getScheme() {
        return new getItemsByPrefix_resultStandardScheme();
      }
    }

    private static class getItemsByPrefix_resultStandardScheme extends StandardScheme<getItemsByPrefix_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, getItemsByPrefix_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
                {
                  org.apache.thrift.protocol.TMap _map18 = iprot.readMapBegin();
                  struct.success = new HashMap<String,TItem>(2*_map18.size);
                  for (int _i19 = 0; _i19 < _map18.size; ++_i19)
                  {
                    String _key20; // required
                    TItem _val21; // required
                    _key20 = iprot.readString();
                    _val21 = new TItem();
                    _val21.read(iprot);
                    struct.success.put(_key20, _val21);
                  }
                  iprot.readMapEnd();
                }
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // IA
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.ia = new TIllegalArgument();
                struct.ia.read(iprot);
                struct.setIaIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, getItemsByPrefix_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.success.size()));
            for (Map.Entry<String, TItem> _iter22 : struct.success.entrySet())
            {
              oprot.writeString(_iter22.getKey());
              _iter22.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.ia != null) {
          oprot.writeFieldBegin(IA_FIELD_DESC);
          struct.ia.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class getItemsByPrefix_resultTupleSchemeFactory implements SchemeFactory {
      public getItemsByPrefix_resultTupleScheme getScheme() {
        return new getItemsByPrefix_resultTupleScheme();
      }
    }

    private static class getItemsByPrefix_resultTupleScheme extends TupleScheme<getItemsByPrefix_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, getItemsByPrefix_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetIa()) {
          optionals.set(2);
        }
        if (struct.isSetE()) {
          optionals.set(3);
        }
        oprot.writeBitSet(optionals, 4);
        if (struct.isSetSuccess()) {
          {
            oprot.writeI32(struct.success.size());
            for (Map.Entry<String, TItem> _iter23 : struct.success.entrySet())
            {
              oprot.writeString(_iter23.getKey());
              _iter23.getValue().write(oprot);
            }
          }
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetIa()) {
          struct.ia.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, getItemsByPrefix_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(4);
        if (incoming.get(0)) {
          {
            org.apache.thrift.protocol.TMap _map24 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
            struct.success = new HashMap<String,TItem>(2*_map24.size);
            for (int _i25 = 0; _i25 < _map24.size; ++_i25)
            {
              String _key26; // required
              TItem _val27; // required
              _key26 = iprot.readString();
              _val27 = new TItem();
              _val27.read(iprot);
              struct.success.put(_key26, _val27);
            }
          }
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.ia = new TIllegalArgument();
          struct.ia.read(iprot);
          struct.setIaIsSet(true);
        }
        if (incoming.get(3)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class setItem_args implements org.apache.thrift.TBase<setItem_args, setItem_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("setItem_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("key", org.apache.thrift.protocol.TType.STRING, (short)2);
    private static final org.apache.thrift.protocol.TField ITEM_FIELD_DESC = new org.apache.thrift.protocol.TField("item", org.apache.thrift.protocol.TType.STRUCT, (short)3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new setItem_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new setItem_argsTupleSchemeFactory());
    }

    /**
     * the key to set data in
     */
    public KeyPair kp; // required
    public ByteBuffer key; // required
    /**
     * the TPut to put
     */
    public TItem item; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      /**
       * the key to set data in
       */
      KP((short)1, "kp"),
      KEY((short)2, "key"),
      /**
       * the TPut to put
       */
      ITEM((short)3, "item");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // KEY
            return KEY;
          case 3: // ITEM
            return ITEM;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.KEY, new org.apache.thrift.meta_data.FieldMetaData("key", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.ITEM, new org.apache.thrift.meta_data.FieldMetaData("item", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(setItem_args.class, metaDataMap);
    }

    public setItem_args() {
    }

    public setItem_args(
      KeyPair kp,
      ByteBuffer key,
      TItem item)
    {
      this();
      this.kp = kp;
      this.key = key;
      this.item = item;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public setItem_args(setItem_args other) {
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetKey()) {
        this.key = org.apache.thrift.TBaseHelper.copyBinary(other.key);
;
      }
      if (other.isSetItem()) {
        this.item = new TItem(other.item);
      }
    }

    public setItem_args deepCopy() {
      return new setItem_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.key = null;
      this.item = null;
    }

    /**
     * the key to set data in
     */
    public KeyPair getKp() {
      return this.kp;
    }

    /**
     * the key to set data in
     */
    public setItem_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getKey() {
      setKey(org.apache.thrift.TBaseHelper.rightSize(key));
      return key == null ? null : key.array();
    }

    public ByteBuffer bufferForKey() {
      return key;
    }

    public setItem_args setKey(byte[] key) {
      setKey(key == null ? (ByteBuffer)null : ByteBuffer.wrap(key));
      return this;
    }

    public setItem_args setKey(ByteBuffer key) {
      this.key = key;
      return this;
    }

    public void unsetKey() {
      this.key = null;
    }

    /** Returns true if field key is set (has been assigned a value) and false otherwise */
    public boolean isSetKey() {
      return this.key != null;
    }

    public void setKeyIsSet(boolean value) {
      if (!value) {
        this.key = null;
      }
    }

    /**
     * the TPut to put
     */
    public TItem getItem() {
      return this.item;
    }

    /**
     * the TPut to put
     */
    public setItem_args setItem(TItem item) {
      this.item = item;
      return this;
    }

    public void unsetItem() {
      this.item = null;
    }

    /** Returns true if field item is set (has been assigned a value) and false otherwise */
    public boolean isSetItem() {
      return this.item != null;
    }

    public void setItemIsSet(boolean value) {
      if (!value) {
        this.item = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case KEY:
        if (value == null) {
          unsetKey();
        } else {
          setKey((ByteBuffer)value);
        }
        break;

      case ITEM:
        if (value == null) {
          unsetItem();
        } else {
          setItem((TItem)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case KEY:
        return getKey();

      case ITEM:
        return getItem();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case KEY:
        return isSetKey();
      case ITEM:
        return isSetItem();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof setItem_args)
        return this.equals((setItem_args)that);
      return false;
    }

    public boolean equals(setItem_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_key = true && this.isSetKey();
      boolean that_present_key = true && that.isSetKey();
      if (this_present_key || that_present_key) {
        if (!(this_present_key && that_present_key))
          return false;
        if (!this.key.equals(that.key))
          return false;
      }

      boolean this_present_item = true && this.isSetItem();
      boolean that_present_item = true && that.isSetItem();
      if (this_present_item || that_present_item) {
        if (!(this_present_item && that_present_item))
          return false;
        if (!this.item.equals(that.item))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(setItem_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      setItem_args typedOther = (setItem_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetKey()).compareTo(typedOther.isSetKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key, typedOther.key);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetItem()).compareTo(typedOther.isSetItem());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetItem()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.item, typedOther.item);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("setItem_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("key:");
      if (this.key == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.key, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("item:");
      if (this.item == null) {
        sb.append("null");
      } else {
        sb.append(this.item);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (key == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'key' was not present! Struct: " + toString());
      }
      if (item == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'item' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class setItem_argsStandardSchemeFactory implements SchemeFactory {
      public setItem_argsStandardScheme getScheme() {
        return new setItem_argsStandardScheme();
      }
    }

    private static class setItem_argsStandardScheme extends StandardScheme<setItem_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, setItem_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.key = iprot.readBinary();
                struct.setKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // ITEM
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.item = new TItem();
                struct.item.read(iprot);
                struct.setItemIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, setItem_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.key != null) {
          oprot.writeFieldBegin(KEY_FIELD_DESC);
          oprot.writeBinary(struct.key);
          oprot.writeFieldEnd();
        }
        if (struct.item != null) {
          oprot.writeFieldBegin(ITEM_FIELD_DESC);
          struct.item.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class setItem_argsTupleSchemeFactory implements SchemeFactory {
      public setItem_argsTupleScheme getScheme() {
        return new setItem_argsTupleScheme();
      }
    }

    private static class setItem_argsTupleScheme extends TupleScheme<setItem_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, setItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.key);
        struct.item.write(oprot);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, setItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.key = iprot.readBinary();
        struct.setKeyIsSet(true);
        struct.item = new TItem();
        struct.item.read(iprot);
        struct.setItemIsSet(true);
      }
    }

  }

  public static class setItem_result implements org.apache.thrift.TBase<setItem_result, setItem_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("setItem_result");

    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new setItem_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new setItem_resultTupleSchemeFactory());
    }

    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(setItem_result.class, metaDataMap);
    }

    public setItem_result() {
    }

    public setItem_result(
      TIOError io,
      AuthExpire e)
    {
      this();
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public setItem_result(setItem_result other) {
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public setItem_result deepCopy() {
      return new setItem_result(this);
    }

    @Override
    public void clear() {
      this.io = null;
      this.e = null;
    }

    public TIOError getIo() {
      return this.io;
    }

    public setItem_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public setItem_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof setItem_result)
        return this.equals((setItem_result)that);
      return false;
    }

    public boolean equals(setItem_result that) {
      if (that == null)
        return false;

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(setItem_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      setItem_result typedOther = (setItem_result)other;

      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("setItem_result(");
      boolean first = true;

      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class setItem_resultStandardSchemeFactory implements SchemeFactory {
      public setItem_resultStandardScheme getScheme() {
        return new setItem_resultStandardScheme();
      }
    }

    private static class setItem_resultStandardScheme extends StandardScheme<setItem_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, setItem_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, setItem_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class setItem_resultTupleSchemeFactory implements SchemeFactory {
      public setItem_resultTupleScheme getScheme() {
        return new setItem_resultTupleScheme();
      }
    }

    private static class setItem_resultTupleScheme extends TupleScheme<setItem_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, setItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetIo()) {
          optionals.set(0);
        }
        if (struct.isSetE()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, setItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(1)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class deleteItem_args implements org.apache.thrift.TBase<deleteItem_args, deleteItem_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("deleteItem_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("key", org.apache.thrift.protocol.TType.STRING, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new deleteItem_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new deleteItem_argsTupleSchemeFactory());
    }

    /**
     * the key to delete
     */
    public KeyPair kp; // required
    public ByteBuffer key; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      /**
       * the key to delete
       */
      KP((short)1, "kp"),
      KEY((short)2, "key");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // KEY
            return KEY;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.KEY, new org.apache.thrift.meta_data.FieldMetaData("key", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(deleteItem_args.class, metaDataMap);
    }

    public deleteItem_args() {
    }

    public deleteItem_args(
      KeyPair kp,
      ByteBuffer key)
    {
      this();
      this.kp = kp;
      this.key = key;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public deleteItem_args(deleteItem_args other) {
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetKey()) {
        this.key = org.apache.thrift.TBaseHelper.copyBinary(other.key);
;
      }
    }

    public deleteItem_args deepCopy() {
      return new deleteItem_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.key = null;
    }

    /**
     * the key to delete
     */
    public KeyPair getKp() {
      return this.kp;
    }

    /**
     * the key to delete
     */
    public deleteItem_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getKey() {
      setKey(org.apache.thrift.TBaseHelper.rightSize(key));
      return key == null ? null : key.array();
    }

    public ByteBuffer bufferForKey() {
      return key;
    }

    public deleteItem_args setKey(byte[] key) {
      setKey(key == null ? (ByteBuffer)null : ByteBuffer.wrap(key));
      return this;
    }

    public deleteItem_args setKey(ByteBuffer key) {
      this.key = key;
      return this;
    }

    public void unsetKey() {
      this.key = null;
    }

    /** Returns true if field key is set (has been assigned a value) and false otherwise */
    public boolean isSetKey() {
      return this.key != null;
    }

    public void setKeyIsSet(boolean value) {
      if (!value) {
        this.key = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case KEY:
        if (value == null) {
          unsetKey();
        } else {
          setKey((ByteBuffer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case KEY:
        return getKey();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case KEY:
        return isSetKey();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof deleteItem_args)
        return this.equals((deleteItem_args)that);
      return false;
    }

    public boolean equals(deleteItem_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_key = true && this.isSetKey();
      boolean that_present_key = true && that.isSetKey();
      if (this_present_key || that_present_key) {
        if (!(this_present_key && that_present_key))
          return false;
        if (!this.key.equals(that.key))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(deleteItem_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      deleteItem_args typedOther = (deleteItem_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetKey()).compareTo(typedOther.isSetKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key, typedOther.key);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("deleteItem_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("key:");
      if (this.key == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.key, sb);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (key == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'key' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class deleteItem_argsStandardSchemeFactory implements SchemeFactory {
      public deleteItem_argsStandardScheme getScheme() {
        return new deleteItem_argsStandardScheme();
      }
    }

    private static class deleteItem_argsStandardScheme extends StandardScheme<deleteItem_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, deleteItem_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.key = iprot.readBinary();
                struct.setKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, deleteItem_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.key != null) {
          oprot.writeFieldBegin(KEY_FIELD_DESC);
          oprot.writeBinary(struct.key);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class deleteItem_argsTupleSchemeFactory implements SchemeFactory {
      public deleteItem_argsTupleScheme getScheme() {
        return new deleteItem_argsTupleScheme();
      }
    }

    private static class deleteItem_argsTupleScheme extends TupleScheme<deleteItem_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, deleteItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.key);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, deleteItem_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.key = iprot.readBinary();
        struct.setKeyIsSet(true);
      }
    }

  }

  public static class deleteItem_result implements org.apache.thrift.TBase<deleteItem_result, deleteItem_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("deleteItem_result");

    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new deleteItem_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new deleteItem_resultTupleSchemeFactory());
    }

    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(deleteItem_result.class, metaDataMap);
    }

    public deleteItem_result() {
    }

    public deleteItem_result(
      TIOError io,
      AuthExpire e)
    {
      this();
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public deleteItem_result(deleteItem_result other) {
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public deleteItem_result deepCopy() {
      return new deleteItem_result(this);
    }

    @Override
    public void clear() {
      this.io = null;
      this.e = null;
    }

    public TIOError getIo() {
      return this.io;
    }

    public deleteItem_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public deleteItem_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof deleteItem_result)
        return this.equals((deleteItem_result)that);
      return false;
    }

    public boolean equals(deleteItem_result that) {
      if (that == null)
        return false;

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(deleteItem_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      deleteItem_result typedOther = (deleteItem_result)other;

      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("deleteItem_result(");
      boolean first = true;

      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class deleteItem_resultStandardSchemeFactory implements SchemeFactory {
      public deleteItem_resultStandardScheme getScheme() {
        return new deleteItem_resultStandardScheme();
      }
    }

    private static class deleteItem_resultStandardScheme extends StandardScheme<deleteItem_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, deleteItem_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, deleteItem_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class deleteItem_resultTupleSchemeFactory implements SchemeFactory {
      public deleteItem_resultTupleScheme getScheme() {
        return new deleteItem_resultTupleScheme();
      }
    }

    private static class deleteItem_resultTupleScheme extends TupleScheme<deleteItem_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, deleteItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetIo()) {
          optionals.set(0);
        }
        if (struct.isSetE()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, deleteItem_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(1)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class rget_args implements org.apache.thrift.TBase<rget_args, rget_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("rget_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField START_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("startKey", org.apache.thrift.protocol.TType.STRING, (short)2);
    private static final org.apache.thrift.protocol.TField STOP_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("stopKey", org.apache.thrift.protocol.TType.STRING, (short)3);
    private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)4);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new rget_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new rget_argsTupleSchemeFactory());
    }

    public KeyPair kp; // required
    public ByteBuffer startKey; // required
    public ByteBuffer stopKey; // required
    public int count; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      KP((short)1, "kp"),
      START_KEY((short)2, "startKey"),
      STOP_KEY((short)3, "stopKey"),
      COUNT((short)4, "count");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // START_KEY
            return START_KEY;
          case 3: // STOP_KEY
            return STOP_KEY;
          case 4: // COUNT
            return COUNT;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __COUNT_ISSET_ID = 0;
    private BitSet __isset_bit_vector = new BitSet(1);
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.START_KEY, new org.apache.thrift.meta_data.FieldMetaData("startKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.STOP_KEY, new org.apache.thrift.meta_data.FieldMetaData("stopKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(rget_args.class, metaDataMap);
    }

    public rget_args() {
    }

    public rget_args(
      KeyPair kp,
      ByteBuffer startKey,
      ByteBuffer stopKey,
      int count)
    {
      this();
      this.kp = kp;
      this.startKey = startKey;
      this.stopKey = stopKey;
      this.count = count;
      setCountIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public rget_args(rget_args other) {
      __isset_bit_vector.clear();
      __isset_bit_vector.or(other.__isset_bit_vector);
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetStartKey()) {
        this.startKey = org.apache.thrift.TBaseHelper.copyBinary(other.startKey);
;
      }
      if (other.isSetStopKey()) {
        this.stopKey = org.apache.thrift.TBaseHelper.copyBinary(other.stopKey);
;
      }
      this.count = other.count;
    }

    public rget_args deepCopy() {
      return new rget_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.startKey = null;
      this.stopKey = null;
      setCountIsSet(false);
      this.count = 0;
    }

    public KeyPair getKp() {
      return this.kp;
    }

    public rget_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getStartKey() {
      setStartKey(org.apache.thrift.TBaseHelper.rightSize(startKey));
      return startKey == null ? null : startKey.array();
    }

    public ByteBuffer bufferForStartKey() {
      return startKey;
    }

    public rget_args setStartKey(byte[] startKey) {
      setStartKey(startKey == null ? (ByteBuffer)null : ByteBuffer.wrap(startKey));
      return this;
    }

    public rget_args setStartKey(ByteBuffer startKey) {
      this.startKey = startKey;
      return this;
    }

    public void unsetStartKey() {
      this.startKey = null;
    }

    /** Returns true if field startKey is set (has been assigned a value) and false otherwise */
    public boolean isSetStartKey() {
      return this.startKey != null;
    }

    public void setStartKeyIsSet(boolean value) {
      if (!value) {
        this.startKey = null;
      }
    }

    public byte[] getStopKey() {
      setStopKey(org.apache.thrift.TBaseHelper.rightSize(stopKey));
      return stopKey == null ? null : stopKey.array();
    }

    public ByteBuffer bufferForStopKey() {
      return stopKey;
    }

    public rget_args setStopKey(byte[] stopKey) {
      setStopKey(stopKey == null ? (ByteBuffer)null : ByteBuffer.wrap(stopKey));
      return this;
    }

    public rget_args setStopKey(ByteBuffer stopKey) {
      this.stopKey = stopKey;
      return this;
    }

    public void unsetStopKey() {
      this.stopKey = null;
    }

    /** Returns true if field stopKey is set (has been assigned a value) and false otherwise */
    public boolean isSetStopKey() {
      return this.stopKey != null;
    }

    public void setStopKeyIsSet(boolean value) {
      if (!value) {
        this.stopKey = null;
      }
    }

    public int getCount() {
      return this.count;
    }

    public rget_args setCount(int count) {
      this.count = count;
      setCountIsSet(true);
      return this;
    }

    public void unsetCount() {
      __isset_bit_vector.clear(__COUNT_ISSET_ID);
    }

    /** Returns true if field count is set (has been assigned a value) and false otherwise */
    public boolean isSetCount() {
      return __isset_bit_vector.get(__COUNT_ISSET_ID);
    }

    public void setCountIsSet(boolean value) {
      __isset_bit_vector.set(__COUNT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case START_KEY:
        if (value == null) {
          unsetStartKey();
        } else {
          setStartKey((ByteBuffer)value);
        }
        break;

      case STOP_KEY:
        if (value == null) {
          unsetStopKey();
        } else {
          setStopKey((ByteBuffer)value);
        }
        break;

      case COUNT:
        if (value == null) {
          unsetCount();
        } else {
          setCount((Integer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case START_KEY:
        return getStartKey();

      case STOP_KEY:
        return getStopKey();

      case COUNT:
        return Integer.valueOf(getCount());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case START_KEY:
        return isSetStartKey();
      case STOP_KEY:
        return isSetStopKey();
      case COUNT:
        return isSetCount();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof rget_args)
        return this.equals((rget_args)that);
      return false;
    }

    public boolean equals(rget_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_startKey = true && this.isSetStartKey();
      boolean that_present_startKey = true && that.isSetStartKey();
      if (this_present_startKey || that_present_startKey) {
        if (!(this_present_startKey && that_present_startKey))
          return false;
        if (!this.startKey.equals(that.startKey))
          return false;
      }

      boolean this_present_stopKey = true && this.isSetStopKey();
      boolean that_present_stopKey = true && that.isSetStopKey();
      if (this_present_stopKey || that_present_stopKey) {
        if (!(this_present_stopKey && that_present_stopKey))
          return false;
        if (!this.stopKey.equals(that.stopKey))
          return false;
      }

      boolean this_present_count = true;
      boolean that_present_count = true;
      if (this_present_count || that_present_count) {
        if (!(this_present_count && that_present_count))
          return false;
        if (this.count != that.count)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(rget_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      rget_args typedOther = (rget_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetStartKey()).compareTo(typedOther.isSetStartKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetStartKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startKey, typedOther.startKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetStopKey()).compareTo(typedOther.isSetStopKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetStopKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stopKey, typedOther.stopKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCount()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, typedOther.count);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("rget_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("startKey:");
      if (this.startKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.startKey, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("stopKey:");
      if (this.stopKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.stopKey, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("count:");
      sb.append(this.count);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (startKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'startKey' was not present! Struct: " + toString());
      }
      if (stopKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'stopKey' was not present! Struct: " + toString());
      }
      // alas, we cannot check 'count' because it's a primitive and you chose the non-beans generator.
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bit_vector = new BitSet(1);
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class rget_argsStandardSchemeFactory implements SchemeFactory {
      public rget_argsStandardScheme getScheme() {
        return new rget_argsStandardScheme();
      }
    }

    private static class rget_argsStandardScheme extends StandardScheme<rget_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, rget_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // START_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.startKey = iprot.readBinary();
                struct.setStartKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // STOP_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.stopKey = iprot.readBinary();
                struct.setStopKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 4: // COUNT
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.count = iprot.readI32();
                struct.setCountIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        if (!struct.isSetCount()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'count' was not found in serialized data! Struct: " + toString());
        }
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, rget_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.startKey != null) {
          oprot.writeFieldBegin(START_KEY_FIELD_DESC);
          oprot.writeBinary(struct.startKey);
          oprot.writeFieldEnd();
        }
        if (struct.stopKey != null) {
          oprot.writeFieldBegin(STOP_KEY_FIELD_DESC);
          oprot.writeBinary(struct.stopKey);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldBegin(COUNT_FIELD_DESC);
        oprot.writeI32(struct.count);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class rget_argsTupleSchemeFactory implements SchemeFactory {
      public rget_argsTupleScheme getScheme() {
        return new rget_argsTupleScheme();
      }
    }

    private static class rget_argsTupleScheme extends TupleScheme<rget_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, rget_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.startKey);
        oprot.writeBinary(struct.stopKey);
        oprot.writeI32(struct.count);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, rget_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.startKey = iprot.readBinary();
        struct.setStartKeyIsSet(true);
        struct.stopKey = iprot.readBinary();
        struct.setStopKeyIsSet(true);
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
    }

  }

  public static class rget_result implements org.apache.thrift.TBase<rget_result, rget_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("rget_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.MAP, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new rget_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new rget_resultTupleSchemeFactory());
    }

    public Map<String,TItem> success; // required
    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
              new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class))));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(rget_result.class, metaDataMap);
    }

    public rget_result() {
    }

    public rget_result(
      Map<String,TItem> success,
      TIOError io,
      AuthExpire e)
    {
      this();
      this.success = success;
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public rget_result(rget_result other) {
      if (other.isSetSuccess()) {
        Map<String,TItem> __this__success = new HashMap<String,TItem>();
        for (Map.Entry<String, TItem> other_element : other.success.entrySet()) {

          String other_element_key = other_element.getKey();
          TItem other_element_value = other_element.getValue();

          String __this__success_copy_key = other_element_key;

          TItem __this__success_copy_value = new TItem(other_element_value);

          __this__success.put(__this__success_copy_key, __this__success_copy_value);
        }
        this.success = __this__success;
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public rget_result deepCopy() {
      return new rget_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.e = null;
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public void putToSuccess(String key, TItem val) {
      if (this.success == null) {
        this.success = new HashMap<String,TItem>();
      }
      this.success.put(key, val);
    }

    public Map<String,TItem> getSuccess() {
      return this.success;
    }

    public rget_result setSuccess(Map<String,TItem> success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public rget_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public rget_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Map<String,TItem>)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof rget_result)
        return this.equals((rget_result)that);
      return false;
    }

    public boolean equals(rget_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(rget_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      rget_result typedOther = (rget_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("rget_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class rget_resultStandardSchemeFactory implements SchemeFactory {
      public rget_resultStandardScheme getScheme() {
        return new rget_resultStandardScheme();
      }
    }

    private static class rget_resultStandardScheme extends StandardScheme<rget_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, rget_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
                {
                  org.apache.thrift.protocol.TMap _map28 = iprot.readMapBegin();
                  struct.success = new HashMap<String,TItem>(2*_map28.size);
                  for (int _i29 = 0; _i29 < _map28.size; ++_i29)
                  {
                    String _key30; // required
                    TItem _val31; // required
                    _key30 = iprot.readString();
                    _val31 = new TItem();
                    _val31.read(iprot);
                    struct.success.put(_key30, _val31);
                  }
                  iprot.readMapEnd();
                }
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, rget_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.success.size()));
            for (Map.Entry<String, TItem> _iter32 : struct.success.entrySet())
            {
              oprot.writeString(_iter32.getKey());
              _iter32.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class rget_resultTupleSchemeFactory implements SchemeFactory {
      public rget_resultTupleScheme getScheme() {
        return new rget_resultTupleScheme();
      }
    }

    private static class rget_resultTupleScheme extends TupleScheme<rget_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, rget_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetE()) {
          optionals.set(2);
        }
        oprot.writeBitSet(optionals, 3);
        if (struct.isSetSuccess()) {
          {
            oprot.writeI32(struct.success.size());
            for (Map.Entry<String, TItem> _iter33 : struct.success.entrySet())
            {
              oprot.writeString(_iter33.getKey());
              _iter33.getValue().write(oprot);
            }
          }
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, rget_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(3);
        if (incoming.get(0)) {
          {
            org.apache.thrift.protocol.TMap _map34 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
            struct.success = new HashMap<String,TItem>(2*_map34.size);
            for (int _i35 = 0; _i35 < _map34.size; ++_i35)
            {
              String _key36; // required
              TItem _val37; // required
              _key36 = iprot.readString();
              _val37 = new TItem();
              _val37.read(iprot);
              struct.success.put(_key36, _val37);
            }
          }
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class prkget_args implements org.apache.thrift.TBase<prkget_args, prkget_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("prkget_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField PREFIX_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("prefixKey", org.apache.thrift.protocol.TType.STRING, (short)2);
    private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)3);
    private static final org.apache.thrift.protocol.TField IS_CONTAIN_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("isContainValue", org.apache.thrift.protocol.TType.BOOL, (short)4);
    private static final org.apache.thrift.protocol.TField COMPARE_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("compareKey", org.apache.thrift.protocol.TType.STRING, (short)5);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new prkget_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new prkget_argsTupleSchemeFactory());
    }

    public KeyPair kp; // required
    public ByteBuffer prefixKey; // required
    public int count; // required
    public boolean isContainValue; // required
    public ByteBuffer compareKey; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      KP((short)1, "kp"),
      PREFIX_KEY((short)2, "prefixKey"),
      COUNT((short)3, "count"),
      IS_CONTAIN_VALUE((short)4, "isContainValue"),
      COMPARE_KEY((short)5, "compareKey");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // PREFIX_KEY
            return PREFIX_KEY;
          case 3: // COUNT
            return COUNT;
          case 4: // IS_CONTAIN_VALUE
            return IS_CONTAIN_VALUE;
          case 5: // COMPARE_KEY
            return COMPARE_KEY;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __COUNT_ISSET_ID = 0;
    private static final int __ISCONTAINVALUE_ISSET_ID = 1;
    private BitSet __isset_bit_vector = new BitSet(2);
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.PREFIX_KEY, new org.apache.thrift.meta_data.FieldMetaData("prefixKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      tmpMap.put(_Fields.IS_CONTAIN_VALUE, new org.apache.thrift.meta_data.FieldMetaData("isContainValue", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
      tmpMap.put(_Fields.COMPARE_KEY, new org.apache.thrift.meta_data.FieldMetaData("compareKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(prkget_args.class, metaDataMap);
    }

    public prkget_args() {
    }

    public prkget_args(
      KeyPair kp,
      ByteBuffer prefixKey,
      int count,
      boolean isContainValue,
      ByteBuffer compareKey)
    {
      this();
      this.kp = kp;
      this.prefixKey = prefixKey;
      this.count = count;
      setCountIsSet(true);
      this.isContainValue = isContainValue;
      setIsContainValueIsSet(true);
      this.compareKey = compareKey;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public prkget_args(prkget_args other) {
      __isset_bit_vector.clear();
      __isset_bit_vector.or(other.__isset_bit_vector);
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetPrefixKey()) {
        this.prefixKey = org.apache.thrift.TBaseHelper.copyBinary(other.prefixKey);
;
      }
      this.count = other.count;
      this.isContainValue = other.isContainValue;
      if (other.isSetCompareKey()) {
        this.compareKey = org.apache.thrift.TBaseHelper.copyBinary(other.compareKey);
;
      }
    }

    public prkget_args deepCopy() {
      return new prkget_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.prefixKey = null;
      setCountIsSet(false);
      this.count = 0;
      setIsContainValueIsSet(false);
      this.isContainValue = false;
      this.compareKey = null;
    }

    public KeyPair getKp() {
      return this.kp;
    }

    public prkget_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getPrefixKey() {
      setPrefixKey(org.apache.thrift.TBaseHelper.rightSize(prefixKey));
      return prefixKey == null ? null : prefixKey.array();
    }

    public ByteBuffer bufferForPrefixKey() {
      return prefixKey;
    }

    public prkget_args setPrefixKey(byte[] prefixKey) {
      setPrefixKey(prefixKey == null ? (ByteBuffer)null : ByteBuffer.wrap(prefixKey));
      return this;
    }

    public prkget_args setPrefixKey(ByteBuffer prefixKey) {
      this.prefixKey = prefixKey;
      return this;
    }

    public void unsetPrefixKey() {
      this.prefixKey = null;
    }

    /** Returns true if field prefixKey is set (has been assigned a value) and false otherwise */
    public boolean isSetPrefixKey() {
      return this.prefixKey != null;
    }

    public void setPrefixKeyIsSet(boolean value) {
      if (!value) {
        this.prefixKey = null;
      }
    }

    public int getCount() {
      return this.count;
    }

    public prkget_args setCount(int count) {
      this.count = count;
      setCountIsSet(true);
      return this;
    }

    public void unsetCount() {
      __isset_bit_vector.clear(__COUNT_ISSET_ID);
    }

    /** Returns true if field count is set (has been assigned a value) and false otherwise */
    public boolean isSetCount() {
      return __isset_bit_vector.get(__COUNT_ISSET_ID);
    }

    public void setCountIsSet(boolean value) {
      __isset_bit_vector.set(__COUNT_ISSET_ID, value);
    }

    public boolean isIsContainValue() {
      return this.isContainValue;
    }

    public prkget_args setIsContainValue(boolean isContainValue) {
      this.isContainValue = isContainValue;
      setIsContainValueIsSet(true);
      return this;
    }

    public void unsetIsContainValue() {
      __isset_bit_vector.clear(__ISCONTAINVALUE_ISSET_ID);
    }

    /** Returns true if field isContainValue is set (has been assigned a value) and false otherwise */
    public boolean isSetIsContainValue() {
      return __isset_bit_vector.get(__ISCONTAINVALUE_ISSET_ID);
    }

    public void setIsContainValueIsSet(boolean value) {
      __isset_bit_vector.set(__ISCONTAINVALUE_ISSET_ID, value);
    }

    public byte[] getCompareKey() {
      setCompareKey(org.apache.thrift.TBaseHelper.rightSize(compareKey));
      return compareKey == null ? null : compareKey.array();
    }

    public ByteBuffer bufferForCompareKey() {
      return compareKey;
    }

    public prkget_args setCompareKey(byte[] compareKey) {
      setCompareKey(compareKey == null ? (ByteBuffer)null : ByteBuffer.wrap(compareKey));
      return this;
    }

    public prkget_args setCompareKey(ByteBuffer compareKey) {
      this.compareKey = compareKey;
      return this;
    }

    public void unsetCompareKey() {
      this.compareKey = null;
    }

    /** Returns true if field compareKey is set (has been assigned a value) and false otherwise */
    public boolean isSetCompareKey() {
      return this.compareKey != null;
    }

    public void setCompareKeyIsSet(boolean value) {
      if (!value) {
        this.compareKey = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case PREFIX_KEY:
        if (value == null) {
          unsetPrefixKey();
        } else {
          setPrefixKey((ByteBuffer)value);
        }
        break;

      case COUNT:
        if (value == null) {
          unsetCount();
        } else {
          setCount((Integer)value);
        }
        break;

      case IS_CONTAIN_VALUE:
        if (value == null) {
          unsetIsContainValue();
        } else {
          setIsContainValue((Boolean)value);
        }
        break;

      case COMPARE_KEY:
        if (value == null) {
          unsetCompareKey();
        } else {
          setCompareKey((ByteBuffer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case PREFIX_KEY:
        return getPrefixKey();

      case COUNT:
        return Integer.valueOf(getCount());

      case IS_CONTAIN_VALUE:
        return Boolean.valueOf(isIsContainValue());

      case COMPARE_KEY:
        return getCompareKey();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case PREFIX_KEY:
        return isSetPrefixKey();
      case COUNT:
        return isSetCount();
      case IS_CONTAIN_VALUE:
        return isSetIsContainValue();
      case COMPARE_KEY:
        return isSetCompareKey();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof prkget_args)
        return this.equals((prkget_args)that);
      return false;
    }

    public boolean equals(prkget_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_prefixKey = true && this.isSetPrefixKey();
      boolean that_present_prefixKey = true && that.isSetPrefixKey();
      if (this_present_prefixKey || that_present_prefixKey) {
        if (!(this_present_prefixKey && that_present_prefixKey))
          return false;
        if (!this.prefixKey.equals(that.prefixKey))
          return false;
      }

      boolean this_present_count = true;
      boolean that_present_count = true;
      if (this_present_count || that_present_count) {
        if (!(this_present_count && that_present_count))
          return false;
        if (this.count != that.count)
          return false;
      }

      boolean this_present_isContainValue = true;
      boolean that_present_isContainValue = true;
      if (this_present_isContainValue || that_present_isContainValue) {
        if (!(this_present_isContainValue && that_present_isContainValue))
          return false;
        if (this.isContainValue != that.isContainValue)
          return false;
      }

      boolean this_present_compareKey = true && this.isSetCompareKey();
      boolean that_present_compareKey = true && that.isSetCompareKey();
      if (this_present_compareKey || that_present_compareKey) {
        if (!(this_present_compareKey && that_present_compareKey))
          return false;
        if (!this.compareKey.equals(that.compareKey))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(prkget_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      prkget_args typedOther = (prkget_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetPrefixKey()).compareTo(typedOther.isSetPrefixKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetPrefixKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.prefixKey, typedOther.prefixKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCount()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, typedOther.count);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIsContainValue()).compareTo(typedOther.isSetIsContainValue());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIsContainValue()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isContainValue, typedOther.isContainValue);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetCompareKey()).compareTo(typedOther.isSetCompareKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCompareKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.compareKey, typedOther.compareKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("prkget_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("prefixKey:");
      if (this.prefixKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.prefixKey, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("count:");
      sb.append(this.count);
      first = false;
      if (!first) sb.append(", ");
      sb.append("isContainValue:");
      sb.append(this.isContainValue);
      first = false;
      if (!first) sb.append(", ");
      sb.append("compareKey:");
      if (this.compareKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.compareKey, sb);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (prefixKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'prefixKey' was not present! Struct: " + toString());
      }
      // alas, we cannot check 'count' because it's a primitive and you chose the non-beans generator.
      // alas, we cannot check 'isContainValue' because it's a primitive and you chose the non-beans generator.
      if (compareKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'compareKey' was not present! Struct: " + toString());
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bit_vector = new BitSet(1);
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class prkget_argsStandardSchemeFactory implements SchemeFactory {
      public prkget_argsStandardScheme getScheme() {
        return new prkget_argsStandardScheme();
      }
    }

    private static class prkget_argsStandardScheme extends StandardScheme<prkget_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, prkget_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // PREFIX_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.prefixKey = iprot.readBinary();
                struct.setPrefixKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // COUNT
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.count = iprot.readI32();
                struct.setCountIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 4: // IS_CONTAIN_VALUE
              if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
                struct.isContainValue = iprot.readBool();
                struct.setIsContainValueIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 5: // COMPARE_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.compareKey = iprot.readBinary();
                struct.setCompareKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        if (!struct.isSetCount()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'count' was not found in serialized data! Struct: " + toString());
        }
        if (!struct.isSetIsContainValue()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'isContainValue' was not found in serialized data! Struct: " + toString());
        }
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, prkget_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.prefixKey != null) {
          oprot.writeFieldBegin(PREFIX_KEY_FIELD_DESC);
          oprot.writeBinary(struct.prefixKey);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldBegin(COUNT_FIELD_DESC);
        oprot.writeI32(struct.count);
        oprot.writeFieldEnd();
        oprot.writeFieldBegin(IS_CONTAIN_VALUE_FIELD_DESC);
        oprot.writeBool(struct.isContainValue);
        oprot.writeFieldEnd();
        if (struct.compareKey != null) {
          oprot.writeFieldBegin(COMPARE_KEY_FIELD_DESC);
          oprot.writeBinary(struct.compareKey);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class prkget_argsTupleSchemeFactory implements SchemeFactory {
      public prkget_argsTupleScheme getScheme() {
        return new prkget_argsTupleScheme();
      }
    }

    private static class prkget_argsTupleScheme extends TupleScheme<prkget_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, prkget_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.prefixKey);
        oprot.writeI32(struct.count);
        oprot.writeBool(struct.isContainValue);
        oprot.writeBinary(struct.compareKey);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, prkget_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.prefixKey = iprot.readBinary();
        struct.setPrefixKeyIsSet(true);
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
        struct.isContainValue = iprot.readBool();
        struct.setIsContainValueIsSet(true);
        struct.compareKey = iprot.readBinary();
        struct.setCompareKeyIsSet(true);
      }
    }

  }

  public static class prkget_result implements org.apache.thrift.TBase<prkget_result, prkget_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("prkget_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.MAP, (short)0);
    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new prkget_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new prkget_resultTupleSchemeFactory());
    }

    public Map<String,TItem> success; // required
    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success"),
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
              new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
              new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TItem.class))));
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(prkget_result.class, metaDataMap);
    }

    public prkget_result() {
    }

    public prkget_result(
      Map<String,TItem> success,
      TIOError io,
      AuthExpire e)
    {
      this();
      this.success = success;
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public prkget_result(prkget_result other) {
      if (other.isSetSuccess()) {
        Map<String,TItem> __this__success = new HashMap<String,TItem>();
        for (Map.Entry<String, TItem> other_element : other.success.entrySet()) {

          String other_element_key = other_element.getKey();
          TItem other_element_value = other_element.getValue();

          String __this__success_copy_key = other_element_key;

          TItem __this__success_copy_value = new TItem(other_element_value);

          __this__success.put(__this__success_copy_key, __this__success_copy_value);
        }
        this.success = __this__success;
      }
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public prkget_result deepCopy() {
      return new prkget_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
      this.io = null;
      this.e = null;
    }

    public int getSuccessSize() {
      return (this.success == null) ? 0 : this.success.size();
    }

    public void putToSuccess(String key, TItem val) {
      if (this.success == null) {
        this.success = new HashMap<String,TItem>();
      }
      this.success.put(key, val);
    }

    public Map<String,TItem> getSuccess() {
      return this.success;
    }

    public prkget_result setSuccess(Map<String,TItem> success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public TIOError getIo() {
      return this.io;
    }

    public prkget_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public prkget_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((Map<String,TItem>)value);
        }
        break;

      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof prkget_result)
        return this.equals((prkget_result)that);
      return false;
    }

    public boolean equals(prkget_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(prkget_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      prkget_result typedOther = (prkget_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("prkget_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class prkget_resultStandardSchemeFactory implements SchemeFactory {
      public prkget_resultStandardScheme getScheme() {
        return new prkget_resultStandardScheme();
      }
    }

    private static class prkget_resultStandardScheme extends StandardScheme<prkget_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, prkget_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
                {
                  org.apache.thrift.protocol.TMap _map38 = iprot.readMapBegin();
                  struct.success = new HashMap<String,TItem>(2*_map38.size);
                  for (int _i39 = 0; _i39 < _map38.size; ++_i39)
                  {
                    String _key40; // required
                    TItem _val41; // required
                    _key40 = iprot.readString();
                    _val41 = new TItem();
                    _val41.read(iprot);
                    struct.success.put(_key40, _val41);
                  }
                  iprot.readMapEnd();
                }
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, prkget_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.success.size()));
            for (Map.Entry<String, TItem> _iter42 : struct.success.entrySet())
            {
              oprot.writeString(_iter42.getKey());
              _iter42.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class prkget_resultTupleSchemeFactory implements SchemeFactory {
      public prkget_resultTupleScheme getScheme() {
        return new prkget_resultTupleScheme();
      }
    }

    private static class prkget_resultTupleScheme extends TupleScheme<prkget_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, prkget_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        if (struct.isSetIo()) {
          optionals.set(1);
        }
        if (struct.isSetE()) {
          optionals.set(2);
        }
        oprot.writeBitSet(optionals, 3);
        if (struct.isSetSuccess()) {
          {
            oprot.writeI32(struct.success.size());
            for (Map.Entry<String, TItem> _iter43 : struct.success.entrySet())
            {
              oprot.writeString(_iter43.getKey());
              _iter43.getValue().write(oprot);
            }
          }
        }
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, prkget_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(3);
        if (incoming.get(0)) {
          {
            org.apache.thrift.protocol.TMap _map44 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
            struct.success = new HashMap<String,TItem>(2*_map44.size);
            for (int _i45 = 0; _i45 < _map44.size; ++_i45)
            {
              String _key46; // required
              TItem _val47; // required
              _key46 = iprot.readString();
              _val47 = new TItem();
              _val47.read(iprot);
              struct.success.put(_key46, _val47);
            }
          }
          struct.setSuccessIsSet(true);
        }
        if (incoming.get(1)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(2)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

  public static class rdel_args implements org.apache.thrift.TBase<rdel_args, rdel_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("rdel_args");

    private static final org.apache.thrift.protocol.TField KP_FIELD_DESC = new org.apache.thrift.protocol.TField("kp", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField START_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("startKey", org.apache.thrift.protocol.TType.STRING, (short)2);
    private static final org.apache.thrift.protocol.TField STOP_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("stopKey", org.apache.thrift.protocol.TType.STRING, (short)3);
    private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)4);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new rdel_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new rdel_argsTupleSchemeFactory());
    }

    public KeyPair kp; // required
    public ByteBuffer startKey; // required
    public ByteBuffer stopKey; // required
    public int count; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      KP((short)1, "kp"),
      START_KEY((short)2, "startKey"),
      STOP_KEY((short)3, "stopKey"),
      COUNT((short)4, "count");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // KP
            return KP;
          case 2: // START_KEY
            return START_KEY;
          case 3: // STOP_KEY
            return STOP_KEY;
          case 4: // COUNT
            return COUNT;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    private static final int __COUNT_ISSET_ID = 0;
    private BitSet __isset_bit_vector = new BitSet(1);
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.KP, new org.apache.thrift.meta_data.FieldMetaData("kp", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, KeyPair.class)));
      tmpMap.put(_Fields.START_KEY, new org.apache.thrift.meta_data.FieldMetaData("startKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.STOP_KEY, new org.apache.thrift.meta_data.FieldMetaData("stopKey", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING          , true)));
      tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.REQUIRED, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(rdel_args.class, metaDataMap);
    }

    public rdel_args() {
    }

    public rdel_args(
      KeyPair kp,
      ByteBuffer startKey,
      ByteBuffer stopKey,
      int count)
    {
      this();
      this.kp = kp;
      this.startKey = startKey;
      this.stopKey = stopKey;
      this.count = count;
      setCountIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public rdel_args(rdel_args other) {
      __isset_bit_vector.clear();
      __isset_bit_vector.or(other.__isset_bit_vector);
      if (other.isSetKp()) {
        this.kp = new KeyPair(other.kp);
      }
      if (other.isSetStartKey()) {
        this.startKey = org.apache.thrift.TBaseHelper.copyBinary(other.startKey);
;
      }
      if (other.isSetStopKey()) {
        this.stopKey = org.apache.thrift.TBaseHelper.copyBinary(other.stopKey);
;
      }
      this.count = other.count;
    }

    public rdel_args deepCopy() {
      return new rdel_args(this);
    }

    @Override
    public void clear() {
      this.kp = null;
      this.startKey = null;
      this.stopKey = null;
      setCountIsSet(false);
      this.count = 0;
    }

    public KeyPair getKp() {
      return this.kp;
    }

    public rdel_args setKp(KeyPair kp) {
      this.kp = kp;
      return this;
    }

    public void unsetKp() {
      this.kp = null;
    }

    /** Returns true if field kp is set (has been assigned a value) and false otherwise */
    public boolean isSetKp() {
      return this.kp != null;
    }

    public void setKpIsSet(boolean value) {
      if (!value) {
        this.kp = null;
      }
    }

    public byte[] getStartKey() {
      setStartKey(org.apache.thrift.TBaseHelper.rightSize(startKey));
      return startKey == null ? null : startKey.array();
    }

    public ByteBuffer bufferForStartKey() {
      return startKey;
    }

    public rdel_args setStartKey(byte[] startKey) {
      setStartKey(startKey == null ? (ByteBuffer)null : ByteBuffer.wrap(startKey));
      return this;
    }

    public rdel_args setStartKey(ByteBuffer startKey) {
      this.startKey = startKey;
      return this;
    }

    public void unsetStartKey() {
      this.startKey = null;
    }

    /** Returns true if field startKey is set (has been assigned a value) and false otherwise */
    public boolean isSetStartKey() {
      return this.startKey != null;
    }

    public void setStartKeyIsSet(boolean value) {
      if (!value) {
        this.startKey = null;
      }
    }

    public byte[] getStopKey() {
      setStopKey(org.apache.thrift.TBaseHelper.rightSize(stopKey));
      return stopKey == null ? null : stopKey.array();
    }

    public ByteBuffer bufferForStopKey() {
      return stopKey;
    }

    public rdel_args setStopKey(byte[] stopKey) {
      setStopKey(stopKey == null ? (ByteBuffer)null : ByteBuffer.wrap(stopKey));
      return this;
    }

    public rdel_args setStopKey(ByteBuffer stopKey) {
      this.stopKey = stopKey;
      return this;
    }

    public void unsetStopKey() {
      this.stopKey = null;
    }

    /** Returns true if field stopKey is set (has been assigned a value) and false otherwise */
    public boolean isSetStopKey() {
      return this.stopKey != null;
    }

    public void setStopKeyIsSet(boolean value) {
      if (!value) {
        this.stopKey = null;
      }
    }

    public int getCount() {
      return this.count;
    }

    public rdel_args setCount(int count) {
      this.count = count;
      setCountIsSet(true);
      return this;
    }

    public void unsetCount() {
      __isset_bit_vector.clear(__COUNT_ISSET_ID);
    }

    /** Returns true if field count is set (has been assigned a value) and false otherwise */
    public boolean isSetCount() {
      return __isset_bit_vector.get(__COUNT_ISSET_ID);
    }

    public void setCountIsSet(boolean value) {
      __isset_bit_vector.set(__COUNT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case KP:
        if (value == null) {
          unsetKp();
        } else {
          setKp((KeyPair)value);
        }
        break;

      case START_KEY:
        if (value == null) {
          unsetStartKey();
        } else {
          setStartKey((ByteBuffer)value);
        }
        break;

      case STOP_KEY:
        if (value == null) {
          unsetStopKey();
        } else {
          setStopKey((ByteBuffer)value);
        }
        break;

      case COUNT:
        if (value == null) {
          unsetCount();
        } else {
          setCount((Integer)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case KP:
        return getKp();

      case START_KEY:
        return getStartKey();

      case STOP_KEY:
        return getStopKey();

      case COUNT:
        return Integer.valueOf(getCount());

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case KP:
        return isSetKp();
      case START_KEY:
        return isSetStartKey();
      case STOP_KEY:
        return isSetStopKey();
      case COUNT:
        return isSetCount();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof rdel_args)
        return this.equals((rdel_args)that);
      return false;
    }

    public boolean equals(rdel_args that) {
      if (that == null)
        return false;

      boolean this_present_kp = true && this.isSetKp();
      boolean that_present_kp = true && that.isSetKp();
      if (this_present_kp || that_present_kp) {
        if (!(this_present_kp && that_present_kp))
          return false;
        if (!this.kp.equals(that.kp))
          return false;
      }

      boolean this_present_startKey = true && this.isSetStartKey();
      boolean that_present_startKey = true && that.isSetStartKey();
      if (this_present_startKey || that_present_startKey) {
        if (!(this_present_startKey && that_present_startKey))
          return false;
        if (!this.startKey.equals(that.startKey))
          return false;
      }

      boolean this_present_stopKey = true && this.isSetStopKey();
      boolean that_present_stopKey = true && that.isSetStopKey();
      if (this_present_stopKey || that_present_stopKey) {
        if (!(this_present_stopKey && that_present_stopKey))
          return false;
        if (!this.stopKey.equals(that.stopKey))
          return false;
      }

      boolean this_present_count = true;
      boolean that_present_count = true;
      if (this_present_count || that_present_count) {
        if (!(this_present_count && that_present_count))
          return false;
        if (this.count != that.count)
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(rdel_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      rdel_args typedOther = (rdel_args)other;

      lastComparison = Boolean.valueOf(isSetKp()).compareTo(typedOther.isSetKp());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetKp()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.kp, typedOther.kp);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetStartKey()).compareTo(typedOther.isSetStartKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetStartKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startKey, typedOther.startKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetStopKey()).compareTo(typedOther.isSetStopKey());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetStopKey()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stopKey, typedOther.stopKey);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetCount()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, typedOther.count);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("rdel_args(");
      boolean first = true;

      sb.append("kp:");
      if (this.kp == null) {
        sb.append("null");
      } else {
        sb.append(this.kp);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("startKey:");
      if (this.startKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.startKey, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("stopKey:");
      if (this.stopKey == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.stopKey, sb);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("count:");
      sb.append(this.count);
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      if (kp == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'kp' was not present! Struct: " + toString());
      }
      if (startKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'startKey' was not present! Struct: " + toString());
      }
      if (stopKey == null) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'stopKey' was not present! Struct: " + toString());
      }
      // alas, we cannot check 'count' because it's a primitive and you chose the non-beans generator.
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
        __isset_bit_vector = new BitSet(1);
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class rdel_argsStandardSchemeFactory implements SchemeFactory {
      public rdel_argsStandardScheme getScheme() {
        return new rdel_argsStandardScheme();
      }
    }

    private static class rdel_argsStandardScheme extends StandardScheme<rdel_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, rdel_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // KP
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.kp = new KeyPair();
                struct.kp.read(iprot);
                struct.setKpIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // START_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.startKey = iprot.readBinary();
                struct.setStartKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 3: // STOP_KEY
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.stopKey = iprot.readBinary();
                struct.setStopKeyIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 4: // COUNT
              if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                struct.count = iprot.readI32();
                struct.setCountIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        if (!struct.isSetCount()) {
          throw new org.apache.thrift.protocol.TProtocolException("Required field 'count' was not found in serialized data! Struct: " + toString());
        }
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, rdel_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.kp != null) {
          oprot.writeFieldBegin(KP_FIELD_DESC);
          struct.kp.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.startKey != null) {
          oprot.writeFieldBegin(START_KEY_FIELD_DESC);
          oprot.writeBinary(struct.startKey);
          oprot.writeFieldEnd();
        }
        if (struct.stopKey != null) {
          oprot.writeFieldBegin(STOP_KEY_FIELD_DESC);
          oprot.writeBinary(struct.stopKey);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldBegin(COUNT_FIELD_DESC);
        oprot.writeI32(struct.count);
        oprot.writeFieldEnd();
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class rdel_argsTupleSchemeFactory implements SchemeFactory {
      public rdel_argsTupleScheme getScheme() {
        return new rdel_argsTupleScheme();
      }
    }

    private static class rdel_argsTupleScheme extends TupleScheme<rdel_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, rdel_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        struct.kp.write(oprot);
        oprot.writeBinary(struct.startKey);
        oprot.writeBinary(struct.stopKey);
        oprot.writeI32(struct.count);
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, rdel_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        struct.kp = new KeyPair();
        struct.kp.read(iprot);
        struct.setKpIsSet(true);
        struct.startKey = iprot.readBinary();
        struct.setStartKeyIsSet(true);
        struct.stopKey = iprot.readBinary();
        struct.setStopKeyIsSet(true);
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
    }

  }

  public static class rdel_result implements org.apache.thrift.TBase<rdel_result, rdel_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("rdel_result");

    private static final org.apache.thrift.protocol.TField IO_FIELD_DESC = new org.apache.thrift.protocol.TField("io", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField E_FIELD_DESC = new org.apache.thrift.protocol.TField("e", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new rdel_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new rdel_resultTupleSchemeFactory());
    }

    public TIOError io; // required
    public AuthExpire e; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      IO((short)1, "io"),
      E((short)2, "e");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // IO
            return IO;
          case 2: // E
            return E;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.IO, new org.apache.thrift.meta_data.FieldMetaData("io", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      tmpMap.put(_Fields.E, new org.apache.thrift.meta_data.FieldMetaData("e", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(rdel_result.class, metaDataMap);
    }

    public rdel_result() {
    }

    public rdel_result(
      TIOError io,
      AuthExpire e)
    {
      this();
      this.io = io;
      this.e = e;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public rdel_result(rdel_result other) {
      if (other.isSetIo()) {
        this.io = new TIOError(other.io);
      }
      if (other.isSetE()) {
        this.e = new AuthExpire(other.e);
      }
    }

    public rdel_result deepCopy() {
      return new rdel_result(this);
    }

    @Override
    public void clear() {
      this.io = null;
      this.e = null;
    }

    public TIOError getIo() {
      return this.io;
    }

    public rdel_result setIo(TIOError io) {
      this.io = io;
      return this;
    }

    public void unsetIo() {
      this.io = null;
    }

    /** Returns true if field io is set (has been assigned a value) and false otherwise */
    public boolean isSetIo() {
      return this.io != null;
    }

    public void setIoIsSet(boolean value) {
      if (!value) {
        this.io = null;
      }
    }

    public AuthExpire getE() {
      return this.e;
    }

    public rdel_result setE(AuthExpire e) {
      this.e = e;
      return this;
    }

    public void unsetE() {
      this.e = null;
    }

    /** Returns true if field e is set (has been assigned a value) and false otherwise */
    public boolean isSetE() {
      return this.e != null;
    }

    public void setEIsSet(boolean value) {
      if (!value) {
        this.e = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case IO:
        if (value == null) {
          unsetIo();
        } else {
          setIo((TIOError)value);
        }
        break;

      case E:
        if (value == null) {
          unsetE();
        } else {
          setE((AuthExpire)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case IO:
        return getIo();

      case E:
        return getE();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case IO:
        return isSetIo();
      case E:
        return isSetE();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof rdel_result)
        return this.equals((rdel_result)that);
      return false;
    }

    public boolean equals(rdel_result that) {
      if (that == null)
        return false;

      boolean this_present_io = true && this.isSetIo();
      boolean that_present_io = true && that.isSetIo();
      if (this_present_io || that_present_io) {
        if (!(this_present_io && that_present_io))
          return false;
        if (!this.io.equals(that.io))
          return false;
      }

      boolean this_present_e = true && this.isSetE();
      boolean that_present_e = true && that.isSetE();
      if (this_present_e || that_present_e) {
        if (!(this_present_e && that_present_e))
          return false;
        if (!this.e.equals(that.e))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(rdel_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      rdel_result typedOther = (rdel_result)other;

      lastComparison = Boolean.valueOf(isSetIo()).compareTo(typedOther.isSetIo());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetIo()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.io, typedOther.io);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetE()).compareTo(typedOther.isSetE());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetE()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.e, typedOther.e);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("rdel_result(");
      boolean first = true;

      sb.append("io:");
      if (this.io == null) {
        sb.append("null");
      } else {
        sb.append(this.io);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("e:");
      if (this.e == null) {
        sb.append("null");
      } else {
        sb.append(this.e);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class rdel_resultStandardSchemeFactory implements SchemeFactory {
      public rdel_resultStandardScheme getScheme() {
        return new rdel_resultStandardScheme();
      }
    }

    private static class rdel_resultStandardScheme extends StandardScheme<rdel_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, rdel_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // IO
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.io = new TIOError();
                struct.io.read(iprot);
                struct.setIoIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // E
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.e = new AuthExpire();
                struct.e.read(iprot);
                struct.setEIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, rdel_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.io != null) {
          oprot.writeFieldBegin(IO_FIELD_DESC);
          struct.io.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.e != null) {
          oprot.writeFieldBegin(E_FIELD_DESC);
          struct.e.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class rdel_resultTupleSchemeFactory implements SchemeFactory {
      public rdel_resultTupleScheme getScheme() {
        return new rdel_resultTupleScheme();
      }
    }

    private static class rdel_resultTupleScheme extends TupleScheme<rdel_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, rdel_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetIo()) {
          optionals.set(0);
        }
        if (struct.isSetE()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetIo()) {
          struct.io.write(oprot);
        }
        if (struct.isSetE()) {
          struct.e.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, rdel_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.io = new TIOError();
          struct.io.read(iprot);
          struct.setIoIsSet(true);
        }
        if (incoming.get(1)) {
          struct.e = new AuthExpire();
          struct.e.read(iprot);
          struct.setEIsSet(true);
        }
      }
    }

  }

}