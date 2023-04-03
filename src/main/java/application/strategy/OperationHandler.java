package application.strategy;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;

public interface OperationHandler {
    TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint);
}
