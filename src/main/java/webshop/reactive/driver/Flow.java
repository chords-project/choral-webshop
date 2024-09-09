package webshop.reactive.driver;

import scala.util.Random;

public class Flow {
    public static enum Action {
        PLACE_ORDER, ADD_ITEM
    }

    public final Action action;
    public final int id;

    public Flow(Action action, int id) {
        this.action = action;
        this.id = id;
    }

    public static Flow generateFlow(Action action) {
        Random rand = new Random();
        return new Flow(action, rand.nextInt());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Flow))
            return false;

        Flow otherFlow = (Flow) other;
        return this.action == otherFlow.action && this.id == otherFlow.id;
    }

    @Override
    public int hashCode() {
        return id + action.hashCode();
    }
}
