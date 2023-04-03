package application.strategy.impl;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.strategy.OperationHandler;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FlyMaxSpeedHandler implements OperationHandler {
    @Override
    public TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint) {
        BigDecimal distanceToWayPoint = DistanceHandler.calculateAndSetDistanceToWayPoint(airplane,
                wayPoint);

        //distance need to drop speed to WayPoint pass speed(m)
        BigDecimal distanceToDropSpeed = ((wayPoint.getSpeed().multiply(wayPoint.getSpeed()))
                .subtract(airplane.getPosition().getSpeed().multiply(airplane.getPosition()
                        .getSpeed()))).setScale(0, RoundingMode.DOWN).divide(BigDecimal
                .valueOf(2L).multiply(airplane.getCharacteristics().getAcceleration()),
                MathContext.DECIMAL128).abs();

        //check the distance to WayPoint
        if (distanceToWayPoint.compareTo(distanceToDropSpeed.add(airplane
                .getCharacteristics().getMaxSpeed())) <= 0) {
            BigDecimal leftDistanceOnMaxSpeed = distanceToWayPoint.subtract(distanceToDropSpeed);
            if (airplane.getFlights().get(airplane.getFlights().size() - 1)
                    .getWayPoints().size() == airplane.getFlights().get(airplane.getFlights()
                    .size() - 1).getWayPoints().indexOf(wayPoint) + 1) {
                calculateTemporaryPoint(airplane, wayPoint, distanceToWayPoint.divide(airplane
                        .getPosition().getSpeed(), MathContext.DECIMAL128));
                airplane.setOperation(Airplane.Operation.NO_ACTION);
                return airplane.getPosition();
            }
            BigDecimal timeLeft = leftDistanceOnMaxSpeed.divide(airplane.getCharacteristics()
                    .getMaxSpeed(), MathContext.DECIMAL128);
            calculateTemporaryPoint(airplane, wayPoint, timeLeft);
            DroppingSpeedHandler droppingSpeedHandler = new DroppingSpeedHandler();
            droppingSpeedHandler.calculateTemporaryPoint(airplane, wayPoint,
                    BigDecimal.ONE.subtract(timeLeft));
            airplane.setOperation(Airplane.Operation.DROPPING_SPEED);
            return airplane.getPosition();
        }
        TemporaryPoint temporaryPoint = calculateTemporaryPoint(airplane, wayPoint, BigDecimal.ONE);
        airplane.setOperation(Airplane.Operation.MAX_SPEED_FLYING);
        return temporaryPoint;
    }

    public TemporaryPoint calculateTemporaryPoint(Airplane airplane, WayPoint wayPoint,
                                                  BigDecimal time) {
        //Calculate passed distance on max speed
        BigDecimal totalPassedDistance = time.multiply(airplane.getCharacteristics()
                .getMaxSpeed()).setScale(3, RoundingMode.CEILING);

        //handle with new coordinates
        CoordinateHandler.calculateAndSetNewCoordinates(airplane, totalPassedDistance);

        //set new height
        HeightHandler.setHeight(airplane, wayPoint, time);
        airplane.setOperation(Airplane.Operation.MAX_SPEED_FLYING);
        return airplane.getPosition();
    }
}
