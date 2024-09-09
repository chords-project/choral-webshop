package webshop.reactive.driver;

import choral.channels.DiChannel_A;
import choral.lang.Unit;

public class FlowChannel_A implements DiChannel_A<Object> {

    public final Flow flow;
    private final FlowQueue queue;

    public FlowChannel_A(Flow flow, FlowQueue queue) {
        this.flow = flow;
        this.queue = queue;
    }

    @Override
    public <S> Unit com(S msg) {
        // Wraps each message in a flow
        queue.send(new FlowMessage<>(flow, msg).typeErased());
        return Unit.id;
    }

    @Override
    public <T extends Enum<T>> Unit select(T msg) {
        return com(msg);
    }
}
