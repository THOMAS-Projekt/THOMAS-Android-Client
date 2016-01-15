package de.waishon.thomas.WebSocket;

/**
 * Created by Sören on 10.01.2016.
 */
public class AbstractArgument<T> {

    String key;
    T value;

    public AbstractArgument(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
