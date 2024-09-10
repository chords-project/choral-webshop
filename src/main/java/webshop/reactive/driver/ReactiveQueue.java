package webshop.reactive.driver;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ReactiveQueue<Action> {

    public interface NewFlowEvent<Action> {
        void onNewFlowReceived(IntegrityKey<Action> flow, Runnable cleanup);
    }

    private final HashMap<IntegrityKey<Action>, LinkedList<Object>> sendQueue = new HashMap<>();
    private final HashMap<IntegrityKey<Action>, LinkedList<CompletableFuture<Object>>> recvQueue = new HashMap<>();
    private NewFlowEvent<Action> events = null;

    public ReactiveQueue() {
    }

    public void onNewFlowReceived(NewFlowEvent<Action> events) {
        this.events = events;
    }

    public void send(IntegrityKey<Action> key, Object msg) {
        synchronized (this) {
            if (this.recvQueue.containsKey(key)) {
                // the flow already exists, pass the message to recv...

                if (this.recvQueue.get(key).isEmpty()) {
                    enqueueSend(key, msg);
                } else {
                    CompletableFuture<Object> future = this.recvQueue.get(key).removeFirst();
                    future.complete(msg);
                }
            } else {
                // this is a new flow, enqueue the message and notify the event handler
                enqueueSend(key, msg);
                events.onNewFlowReceived(key, () -> cleanupKey(key));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T recv(IntegrityKey<Action> flow) throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future = new CompletableFuture<>();

        synchronized (this) {
            if (this.sendQueue.containsKey(flow)) {
                // Flow already exists, receive message from send...

                if (this.sendQueue.get(flow).isEmpty()) {
                    this.recvQueue.get(flow).add(future);
                } else {
                    future.complete(this.sendQueue.get(flow).removeFirst());
                }
            } else {
                // Flow does not exist yet, wait for it to arrive...
                enqueueRecv(flow, future);
            }
        }

        // It's the responsibility of the choreography to have the type cast match
        return (T) future.get();
    }

    // should synchronize on 'this' before calling this method
    private void enqueueSend(IntegrityKey<Action> key, Object msg) {
        if (!this.sendQueue.containsKey(key)) {
            this.sendQueue.put(key, new LinkedList<>());
            this.recvQueue.put(key, new LinkedList<>());
        }

        this.sendQueue.get(key).add(msg);
    }

    // should synchronize on 'this' before calling this method
    private void enqueueRecv(IntegrityKey<Action> key, CompletableFuture<Object> future) {
        if (!this.recvQueue.containsKey(key)) {
            this.recvQueue.put(key, new LinkedList<>());
            this.sendQueue.put(key, new LinkedList<>());
        }

        this.recvQueue.get(key).add(future);
    }

    private void cleanupKey(IntegrityKey<Action> key) {
        synchronized (this) {
            this.sendQueue.remove(key);
            this.recvQueue.remove(key);
        }
    }
}
