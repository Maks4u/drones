package application.service;

import application.model.AirplaneCharacteristics;
import application.model.TemporaryPoint;
import application.model.WayPoint;

import java.util.List;

public interface FlyManagerService {
    List<TemporaryPoint> calculateRoute(AirplaneCharacteristics airplaneCharacteristics, List<WayPoint> wayPoints);
}
