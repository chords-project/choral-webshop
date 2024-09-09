package webshop.loopback.events;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class EventsImpl<T> {
    protected LinkedBlockingQueue<Optional<T>> queue;

    public EventsImpl() {
        this.queue = new LinkedBlockingQueue<Optional<T>>();
    }

    protected void sendLoop(Consumer<T> handler) {
        while (true) {
            try {
                Optional<T> event = queue.take();

                if (event.isEmpty()) {
                    // localHandler.onStop();
                    break;
                }

                handler.accept(event.get());
            } catch (InterruptedException e) {
                // Ignore
            } catch (Exception e) {
                // if (!localHandler.onError(e)) {
                // break;
                // }
            }
        }
    }

    protected void recvLoop(Runnable handler) {
        while (true) {
            try {
                handler.run();
            } catch (Exception e) {
                e.printStackTrace();
                break;
                // if (!localHandler.onError(e)) {
                // break;
                // }
            }
        }
    }
}
