package application.service.impl;

import application.model.Airplane;
import application.model.Flight;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.service.AirplaneService;
import application.service.FlightService;
import application.service.FlyManagerService;
import application.strategy.OperationHandler;
import application.strategy.StrategyHandler;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FlyManagerServiceImpl implements FlyManagerService {
    private static final Logger logger = LoggerFactory.getLogger(FlyManagerServiceImpl.class);
    private final StrategyHandler strategyHandler;
    private final AirplaneService airplaneService;
    private final FlightService flightService;

    public FlyManagerServiceImpl(StrategyHandler strategyHandler,
                                 AirplaneService airplaneService,
                                 FlightService flightService) {
        this.strategyHandler = strategyHandler;
        this.airplaneService = airplaneService;
        this.flightService = flightService;
    }

    @Override
    public List<TemporaryPoint> calculateRoute(Airplane airplane, List<WayPoint> wayPoints) {
        getStartMessage(airplane);
        Flight flight = getFlight(airplane, wayPoints);
        List<TemporaryPoint> temporaryPoints = new ArrayList<>();
        temporaryPoints.add(createNewPoint(airplane));
        logger.info("Point #{} {} {}", temporaryPoints.size(), airplane.getPosition(),
                airplane.getOperation());
        airplane.setOperation(Airplane.Operation.PLACE_ROTATION);
        for (WayPoint point: wayPoints) {
            while (!airplane.getOperation().equals(Airplane.Operation.NO_ACTION)
                    && point.getStatus().equals(WayPoint.Status.NOT_PASSED)) {
                OperationHandler operationHandler = strategyHandler
                        .getOperationHandler(airplane.getOperation());
                operationHandler.getTemporaryPoint(airplane, point);
                TemporaryPoint temporaryPoint = createNewPoint(airplane);
                airplaneService.update(airplane);
                temporaryPoints.add(temporaryPoint);
                logger.info("Point #{} {} {}", temporaryPoints.size(), temporaryPoint,
                        airplane.getOperation());
                flight.setPassedPoints(temporaryPoints);
            }
        }
        logger.info("FLIGHT END" + System.lineSeparator());
        airplaneService.update(airplane);
        flightService.update(flight);
        return temporaryPoints;
    }

    private Flight getFlight(Airplane airplane, List<WayPoint> wayPoints) {
        Flight flight = new Flight();
        flight.setWayPoints(wayPoints);
        List<Flight> flights = new ArrayList<>();
        if (airplane.getFlights() != null) {
            flights = airplane.getFlights();
            flight.setNumber(flights.size() + 1L);
        } else {
            flight.setNumber(1L);
        }
        flights.add(flight);
        flightService.save(flight);
        airplane.setFlights(flights);
        airplaneService.update(airplane);
        return flight;
    }

    private void getStartMessage(Airplane airplane) {
        if (airplane.getFlights() == null) {
            logger.info("Count of flights: 0 and their total duration: 0s");
        } else {
            logger.info(String.format("Count of flights: %d and their total duration: %ds",
                    airplane.getFlights().size(), airplane.getFlights().stream()
                    .mapToLong(x -> x.getPassedPoints().size()).sum()));
        }
    }

    private TemporaryPoint createNewPoint(Airplane airplane) {
        TemporaryPoint temporaryPoint = new TemporaryPoint();
        temporaryPoint.setLatitude(airplane.getPosition().getLatitude());
        temporaryPoint.setLongitude(airplane.getPosition().getLongitude());
        temporaryPoint.setDegree(airplane.getPosition().getDegree());
        temporaryPoint.setFlyHeight(airplane.getPosition().getFlyHeight());
        temporaryPoint.setSpeed(airplane.getPosition().getSpeed());
        return temporaryPoint;
    }
}
