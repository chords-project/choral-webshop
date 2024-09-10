package webshop.reactive.driver;

import scala.util.Random;

public class IntegrityKey<Action> {
    public final Action action;
    public final Integer id;

    public IntegrityKey(Action action, Integer id) {
        this.action = action;
        this.id = id;
    }

    public static <Action> IntegrityKey<Action> makeKey(Action action) {
        Random rand = new Random();
        return new IntegrityKey<>(action, rand.nextInt());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof IntegrityKey))
            return false;

        IntegrityKey<?> that = (IntegrityKey<?>) other;
        return this.action.equals(that.action) && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode() * 13 + action.hashCode();
    }

    @Override
    public String toString() {
        return action.toString() + "(" + id + ")";
    }
}
