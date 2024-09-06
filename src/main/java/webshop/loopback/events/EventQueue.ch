package webshop.loopback.events;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.InterruptedException;

public class EventQueue@X<T@X> {
    private LinkedBlockingQueue@X<Optional<T>> queue;

    public EventQueue(LinkedBlockingQueue@X<Optional<T>> queue) {
        this.queue = queue;
    }

    public void enqueue(T@X event) {
        put(Optional@X.<T>of(event));
    }

    public void stop() {
        put(Optional@X.<T>empty());
    }

    protected void put(Optional@X<T> item) {
        try {
            queue.put(item);
        } catch (InterruptedException@X e) {
            // Ignore and keep trying
            put(item);
        }
        
    }
}