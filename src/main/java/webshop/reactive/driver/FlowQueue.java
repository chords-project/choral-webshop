package webshop.reactive.driver;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FlowQueue {
    // private final LinkedList<FlowMessage<Object>> sendQueue = new LinkedList<>();
    // private final LinkedList<CompletableFuture<FlowMessage<Object>>> recvQueue =
    // new LinkedList<>();

    public interface FlowEvents {
        void onNewFlowReceived(Flow flow);
    }

    private final HashMap<Flow, LinkedList<Object>> sendQueue = new HashMap<>();
    private final HashMap<Flow, LinkedList<CompletableFuture<Object>>> recvQueue = new HashMap<>();
    private FlowEvents events;

    public FlowQueue() {
        this.events = null;
    }

    public void onNewFlowReceived(FlowEvents events) {
        this.events = events;
    }

    public void send(FlowMessage<Object> msg) {
        synchronized (this) {
            if (this.recvQueue.containsKey(msg.flow)) {
                // the flow already exists, pass the message to recv...

                if (this.recvQueue.get(msg.flow).isEmpty()) {
                    enqueueSend(msg);
                } else {
                    CompletableFuture<Object> future = this.recvQueue.get(msg.flow).removeFirst();
                    future.complete(msg.message);
                }
            } else {
                // this is a new flow, enqueue the message and notify the event handler
                enqueueSend(msg);
                events.onNewFlowReceived(msg.flow);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T recv(Flow flow) throws ExecutionException, InterruptedException {
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

        return (T) future.get();
    }

    // should synchronize on 'this' before calling this method
    private void enqueueSend(FlowMessage<Object> msg) {
        if (!this.sendQueue.containsKey(msg.flow)) {
            this.sendQueue.put(msg.flow, new LinkedList<>());
            this.recvQueue.put(msg.flow, new LinkedList<>());
        }

        this.sendQueue.get(msg.flow).add(msg.message);
    }

    private void enqueueRecv(Flow flow, CompletableFuture<Object> future) {
        if (!this.recvQueue.containsKey(flow)) {
            this.recvQueue.put(flow, new LinkedList<>());
            this.sendQueue.put(flow, new LinkedList<>());
        }

        this.recvQueue.get(flow).add(future);
    }
}
