package webshop;

public class Util {
    @SuppressWarnings("unchecked")
    public static <S> S downcast(Object value) {
        return (S) value;
    }
}
