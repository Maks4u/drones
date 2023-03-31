package application;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.service.FlyManagerService;
import application.service.impl.FlyManagerServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Airplane airplane = new Airplane();
        List<WayPoint> wayPoints = new ArrayList<>();
        IntStream.range(1, 4).forEach(n -> wayPoints.add(new WayPoint()));
        FlyManagerService planeCalculation = new FlyManagerServiceImpl();
        List<TemporaryPoint> points = planeCalculation
                .calculateRoute(airplane.getCharacteristics(), wayPoints);
    }
}