package application.strategy.impl;

import application.model.Airplane;
import application.strategy.OperationHandler;
import application.strategy.StrategyHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StrategyHandlerImpl implements StrategyHandler {
    public static final Map<Airplane.Operation, OperationHandler> action
            = new HashMap<>();

    static {
        action.put(Airplane.Operation.MOVEMENT_ROTATION, new MovementRotationHandler());
        action.put(Airplane.Operation.PLACE_ROTATION, new PlaceRotationHandler());
        action.put(Airplane.Operation.ACCELERATION, new AccelerationHandler());
        action.put(Airplane.Operation.DROPPING_SPEED, new DroppingSpeedHandler());
        action.put(Airplane.Operation.MAX_SPEED_FLYING, new FlyMaxSpeedHandler());
    }

    @Override
    public OperationHandler doOperation(Airplane.Operation operation) {
        return action.get(operation);
    }
}
