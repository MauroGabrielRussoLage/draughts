package ec.com.sofka.mapper;

import ec.com.sofka.data.LogDocument;
import ec.com.sofka.entity.log.GameEventLog;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class DocumentToModelMapper {
    public static final Function<Mono<LogDocument>, Mono<GameEventLog>> toLog = log ->
            log.map(logDocument -> {
                GameEventLog gameEventLogModel = new GameEventLog();
                BeanUtils.copyProperties(logDocument, gameEventLogModel);
                return gameEventLogModel;
            });
}