package ec.com.sofka.generic.interfaces;

import ec.com.sofka.generic.util.Query;
import ec.com.sofka.generic.util.QueryResponse;

public interface UseCaseGet<Q extends Query, R> {
    QueryResponse<R> get(Q request);
}