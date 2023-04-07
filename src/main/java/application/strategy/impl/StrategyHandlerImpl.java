package application.strategy.impl;

import application.model.Airplane;
import application.strategy.OperationHandler;
import application.strategy.StrategyHandler;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StrategyHandlerImpl implements StrategyHandler {
    private final Map<Airplane.Operation, OperationHandler> action;

    public StrategyHandlerImpl(Map<Airplane.Operation, OperationHandler> action) {
        this.action = action;
    }

    @Override
    public OperationHandler getOperationHandler(Airplane.Operation operation) {
        return action.get(operation);
    }
}
