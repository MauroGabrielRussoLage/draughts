package ec.com.sofka.generic.util;

import ec.com.sofka.generic.interfaces.ValueObject;

import java.util.Objects;
import java.util.UUID;

public abstract class Identity implements ValueObject<String> {
    private final String value;

    //Why protected? Because you want to use just it in the AR or Entity
    protected Identity() {
        this.value = UUID.randomUUID().toString();
    }

    //Why another one? Because maybe you want to establish your own logic to create the value
    protected Identity(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    //Other methods that can be useful
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity identity = (Identity) o;
        return Objects.equals(value, identity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}