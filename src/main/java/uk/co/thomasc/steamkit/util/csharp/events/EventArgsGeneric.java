// Generated by delombok at Wed Jun 15 22:04:53 CDT 2016
package uk.co.thomasc.steamkit.util.cSharp.events;

public class EventArgsGeneric<T> extends EventArgs {
	private T value;

	public EventArgsGeneric(T value) {
		this.value = value;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public T getValue() {
		return this.value;
	}
}