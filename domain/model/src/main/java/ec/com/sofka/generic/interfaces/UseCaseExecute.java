package ec.com.sofka.generic.interfaces;

import ec.com.sofka.generic.util.Command;

public interface UseCaseExecute<T extends Command, R> {
    R execute(T request);
}