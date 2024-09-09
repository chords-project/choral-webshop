package webshop.reactive.driver;

public class FlowMessage<T> {
	public final Flow flow;
	public final T message;

	public FlowMessage(Flow flow, T message) {
		this.flow = flow;
		this.message = message;
	}

	public FlowMessage<Object> typeErased() {
		return new FlowMessage<Object>(flow, message);
	}

	@SuppressWarnings("unchecked")
	public <S extends T> FlowMessage<S> downcast() {
		return new FlowMessage<S>(flow, (S) message);
	}
}
