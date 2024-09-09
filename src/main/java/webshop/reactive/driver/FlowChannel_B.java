package webshop.reactive.driver;

import java.util.concurrent.ExecutionException;

import choral.channels.DiChannel_B;
import choral.lang.Unit;

public class FlowChannel_B implements DiChannel_B<Object> {
    private final Flow flow;
    private final FlowQueue queue;

    public FlowChannel_B(Flow flow, FlowQueue queue) {
        this.flow = flow;
        this.queue = queue;
    }

    @Override
    public <S> S com() {
        try {
            return queue.<S>recv(flow);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <S> S com(Unit arg0) {
        return com();
    }

    @Override
    public <T extends Enum<T>> T select() {
        return com();
    }

    @Override
    public <T extends Enum<T>> T select(Unit arg0) {
        return select();
    }

}
