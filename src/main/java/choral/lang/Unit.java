package choral.lang;

public final class Unit {
    private Unit() {
    }

    public static final Unit id = new Unit();

    public static Unit id(Object... args) {
        return id;
    }
}
