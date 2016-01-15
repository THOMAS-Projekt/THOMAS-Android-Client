package de.waishon.thomas.WebSocket;
import java.util.ArrayList;

/**
 * Created by Sören on 10.01.2016.
 */
public class Arguments {

    public enum Typ {
        STRING,
        INTEGER
    }

    ArrayList<AbstractArgument> arguments = new ArrayList<AbstractArgument>();

    public <T> Arguments addArg(String key, T value) {
        arguments.add(new AbstractArgument(key, value));
        return this;
    }

    public ArrayList<AbstractArgument> getArgs() {
        return arguments;
    }
}
