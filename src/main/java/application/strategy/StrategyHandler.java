package application.strategy;

import application.model.Airplane;

public interface StrategyHandler {
    OperationHandler getOperationHandler(Airplane.Operation operation);
}
