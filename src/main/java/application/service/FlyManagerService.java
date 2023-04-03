package application.service;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import java.util.List;

public interface FlyManagerService {
    List<TemporaryPoint> calculateRoute(Airplane airplane, List<WayPoint> wayPoints);
}
