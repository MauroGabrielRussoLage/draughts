package ec.com.sofka.mapper;

import ec.com.sofka.data.LogDocument;
import ec.com.sofka.entity.log.GameEventLog;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ModelToDocumentMapper {
    public static final Function<Mono<GameEventLog>, Mono<LogDocument>> toLog = log ->
            log.map(logModel -> {
                LogDocument logDocument = new LogDocument();
                BeanUtils.copyProperties(logModel, logDocument);
                return logDocument;
            });
}
