package application.strategy.impl;

import application.model.Airplane;
import application.strategy.OperationHandler;
import application.strategy.StrategyHandler;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyHandlerImpl implements StrategyHandler {
    @Autowired
    private Map<Airplane.Operation, OperationHandler> action;

    @Override
    public OperationHandler getOperationHandler(Airplane.Operation operation) {
        return action.get(operation);
    }
}
