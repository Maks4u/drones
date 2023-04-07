package application.data;

import application.model.Airplane;
import application.strategy.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {
    private final List<OperationHandler> handlers;

    public StrategyConfig(List<OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Bean
    Map<Airplane.Operation, OperationHandler> setMap() {
        Map<Airplane.Operation, OperationHandler> action = new HashMap<>();
        handlers.forEach(handler -> action.put(handler.getOperation(), handler));
        return action;
    }
}
