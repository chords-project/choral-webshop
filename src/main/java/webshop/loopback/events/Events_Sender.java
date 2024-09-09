package webshop.loopback.events;

import choral.lang.Unit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Events_Sender<T> extends EventsImpl<T> {
	public Events_Sender() {

	}

	public void run(ExecutorService executorSender, Unit executorReceiver, EventHandler_Sender<T> eventHandler) {
		run(executorSender, eventHandler);
	}

	public EventQueue<T> queue() {
		return new EventQueue<T>(queue);
	}

	public void run(ExecutorService executorSender,
			EventHandler_Sender<T> eventHandler) {

		Future<?> f1 = executorSender.submit(
				() -> sendLoop(t -> eventHandler.on(t)));

		executorSender.execute(() -> {
			try {
				f1.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			// localHandlerSender.onStop();
		});
	}

}
