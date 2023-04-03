package application.strategy;

import application.model.Airplane;

public interface StrategyHandler {
    OperationHandler doOperation(Airplane.Operation operation);
}
