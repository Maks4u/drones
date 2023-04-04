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
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInjector {
    private final FlyManagerService flyManagerService;
    private final AirplaneService airplaneService;

    public DataInjector(FlyManagerService flyManagerService,
                        AirplaneService airplaneService) {
        this.flyManagerService = flyManagerService;
        this.airplaneService = airplaneService;
    }

    @PostConstruct
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
        WayPoint firstWayPoint = new WayPoint();
        firstWayPoint.setLatitude("50.488730955851565");
        firstWayPoint.setLongitude("30.641924954435197");
        firstWayPoint.setFlyHeight(BigDecimal.valueOf(160L));
        firstWayPoint.setSpeed(BigDecimal.valueOf(10L));

        WayPoint secondWayPoint = new WayPoint();
        secondWayPoint.setLatitude("50.495836497886714");
        secondWayPoint.setLongitude("30.637089248335265");
        secondWayPoint.setFlyHeight(BigDecimal.valueOf(210L));
        secondWayPoint.setSpeed(BigDecimal.valueOf(5L));

        WayPoint thirdWayPoint = new WayPoint();
        thirdWayPoint.setLatitude("50.4939999496959");
        thirdWayPoint.setLongitude("30.64630956854819");
        thirdWayPoint.setFlyHeight(BigDecimal.valueOf(190L));
        thirdWayPoint.setSpeed(BigDecimal.valueOf(6L));

        WayPoint fourthWayPoint = new WayPoint();
        fourthWayPoint.setLatitude("50.49691895986775");
        fourthWayPoint.setLongitude("30.64828081715136");
        fourthWayPoint.setFlyHeight(BigDecimal.valueOf(120L));
        fourthWayPoint.setSpeed(BigDecimal.valueOf(26L));

        List<WayPoint> wayPoints = new ArrayList<>();
        wayPoints.add(firstWayPoint);
        wayPoints.add(secondWayPoint);
        wayPoints.add(thirdWayPoint);
        wayPoints.add(fourthWayPoint);

        airplaneService.save(airplane);
        flyManagerService.calculateRoute(airplane, wayPoints);
    }
}
