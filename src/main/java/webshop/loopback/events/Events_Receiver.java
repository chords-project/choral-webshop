package webshop.loopback.events;

import choral.lang.Unit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Events_Receiver<T> extends EventsImpl<T> {
	public Events_Receiver() {

	}

	public void run(Unit executorSender, ExecutorService executorReceiver, EventHandler_Receiver<T> eventHandler) {
		run(executorReceiver, eventHandler);
	}

	public Unit queue() {
		return Unit.id;
	}

	public void run(ExecutorService executorReceiver, EventHandler_Receiver<T> eventHandler) {
		Future<?> f2 = executorReceiver.submit(
				() -> recvLoop(() -> eventHandler.on()));

		executorReceiver.execute(() -> {
			try {
				f2.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			// localHandlerReceiver.onStop();
		});
	}

}
