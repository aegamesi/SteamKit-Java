package uk.co.thomasc.steamkit.util;

public class Passable<T> {
    private T value;

    public Passable(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(final T value) {
        this.value = value;
    }
}
