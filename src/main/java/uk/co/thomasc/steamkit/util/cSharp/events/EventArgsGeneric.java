package uk.co.thomasc.steamkit.util.cSharp.events;

public class EventArgsGeneric<T> extends EventArgs {
    private T value;

    public EventArgsGeneric(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
