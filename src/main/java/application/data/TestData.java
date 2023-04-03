package application.data;

import application.model.Airplane;
import application.model.AirplaneCharacteristics;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.service.AirplaneService;
import application.service.FlyManagerService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TestData {
    private final FlyManagerService flyManagerService;
    private final AirplaneService airplaneService;

    public TestData(FlyManagerService flyManagerService,
                    AirplaneService airplaneService) {
        this.flyManagerService = flyManagerService;
        this.airplaneService = airplaneService;
    }

    public void runMocData() {
        //create airplane
        final Airplane airplane = new Airplane();

        //create start position
        TemporaryPoint temporaryPoint = new TemporaryPoint();
        temporaryPoint.setLatitude("50.489571838291624");
        temporaryPoint.setLongitude("30.64857735830883");
        temporaryPoint.setDegree(BigDecimal.ZERO);
        temporaryPoint.setSpeed(BigDecimal.ZERO);
        temporaryPoint.setFlyHeight(BigDecimal.valueOf(120L));
        //set position to airplane
        airplane.setPosition(temporaryPoint);

        //create airplane characteristics
        AirplaneCharacteristics airplaneCharacteristics = new AirplaneCharacteristics();
        airplaneCharacteristics.setAcceleration(BigDecimal.valueOf(9L));
        airplaneCharacteristics.setChangeHighSpeed(BigDecimal.valueOf(4L));
        airplaneCharacteristics.setChangeDegreeSpeed(BigDecimal.valueOf(33L));
        airplaneCharacteristics.setMaxSpeed(BigDecimal.valueOf(31L));
        //set characteristics to airplane
        airplane.setCharacteristics(airplaneCharacteristics);

        //create 4 WayPoints
        WayPoint wayPoint = new WayPoint();
        wayPoint.setLatitude("50.488730955851565");
        wayPoint.setLongitude("30.641924954435197");
        wayPoint.setFlyHeight(BigDecimal.valueOf(160L));
        wayPoint.setSpeed(BigDecimal.valueOf(10L));

        WayPoint wayPoint1 = new WayPoint();
        wayPoint1.setLatitude("50.495836497886714");
        wayPoint1.setLongitude("30.637089248335265");
        wayPoint1.setFlyHeight(BigDecimal.valueOf(210L));
        wayPoint1.setSpeed(BigDecimal.valueOf(5L));

        WayPoint wayPoint2 = new WayPoint();
        wayPoint2.setLatitude("50.4939999496959");
        wayPoint2.setLongitude("30.64630956854819");
        wayPoint2.setFlyHeight(BigDecimal.valueOf(190L));
        wayPoint2.setSpeed(BigDecimal.valueOf(6L));

        WayPoint wayPoint3 = new WayPoint();
        wayPoint3.setLatitude("50.49691895986775");
        wayPoint3.setLongitude("30.64828081715136");
        wayPoint3.setFlyHeight(BigDecimal.valueOf(120L));
        wayPoint3.setSpeed(BigDecimal.valueOf(26L));

        List<WayPoint> wayPoints = new ArrayList<>();
        wayPoints.add(wayPoint);
        wayPoints.add(wayPoint1);
        wayPoints.add(wayPoint2);
        wayPoints.add(wayPoint3);

        airplaneService.save(airplane);
        flyManagerService.calculateRoute(airplane, wayPoints);
    }
}
