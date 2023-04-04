package application.strategy.impl;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.strategy.OperationHandler;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class DroppingSpeedHandler implements OperationHandler {
    @Override
    public TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint) {
        BigDecimal timeNeededToDropSpeedToWayPointValue = getTimeNeededToDropSpeed(airplane,
                wayPoint);
        if (timeNeededToDropSpeedToWayPointValue.compareTo(BigDecimal.ONE) <= 0) {
            calculateTemporaryPoint(airplane, wayPoint, timeNeededToDropSpeedToWayPointValue);
            if (airplane.getFlights().get(airplane.getFlights().size() - 1)
                    .getWayPoints().size() == airplane.getFlights().get(airplane
                    .getFlights().size() - 1).getWayPoints().indexOf(wayPoint) + 1) {
                airplane.setOperation(Airplane.Operation.NO_ACTION);
                wayPoint.setStatus(WayPoint.Status.PASSED);
                return airplane.getPosition();
            }
            WayPoint nextPoint = airplane.getFlights().get(airplane.getFlights().size() - 1)
                    .getWayPoints().get(airplane.getFlights().get(airplane.getFlights()
                            .size() - 1).getWayPoints().indexOf(wayPoint) + 1);
            double wayDegree = DegreeHandler.getWayDegree(airplane, nextPoint);
            BigDecimal degreeToRotateLeft = DegreeHandler.getDegreeToRotateLeft(airplane,
                    wayDegree);
            wayPoint.setStatus(WayPoint.Status.PASSED);
            if (degreeToRotateLeft.compareTo(BigDecimal.ZERO) == 0) {
                AccelerationHandler accelerationHandler = new AccelerationHandler();
                accelerationHandler.calculateTemporaryPoint(airplane, nextPoint,
                        BigDecimal.ONE.subtract(timeNeededToDropSpeedToWayPointValue));
                airplane.setOperation(Airplane.Operation.ACCELERATION);
            } else {
                MovementRotationHandler movementRotationHandler = new MovementRotationHandler();
                movementRotationHandler.calculateTemporaryPoint(airplane, nextPoint,
                        BigDecimal.ONE.subtract(timeNeededToDropSpeedToWayPointValue));
                airplane.setOperation(Airplane.Operation.MOVEMENT_ROTATION);
            }
        } else {
            calculateTemporaryPoint(airplane, wayPoint, BigDecimal.ONE);
        }
        return airplane.getPosition();
    }

    @Override
    public Airplane.Operation getOperation() {
        return Airplane.Operation.DROPPING_SPEED;
    }

    public void calculateTemporaryPoint(Airplane airplane,
                                                  WayPoint wayPoint,
                                                  BigDecimal time) {
        //speed after drop in T(time)
        BigDecimal amountDropSpeed = time.multiply(airplane.getCharacteristics().getAcceleration());

        //calculate passed distance
        BigDecimal distancePass = ((airplane.getPosition().getSpeed().add(amountDropSpeed
                .negate()).multiply(airplane.getPosition().getSpeed().add(amountDropSpeed
                .negate()))).subtract((airplane.getPosition().getSpeed().multiply(airplane
                .getPosition().getSpeed())))).divide(BigDecimal.valueOf(2L).multiply(airplane
                .getCharacteristics().getAcceleration()), MathContext.DECIMAL128).abs();

        //calculate distance to WayPoint
        BigDecimal distanceToWayPoint = DistanceHandler.calculateAndSetDistanceToWayPoint(airplane,
                wayPoint);

        //handle with new coordinates
        if (distancePass.setScale(0, RoundingMode.UP).compareTo(distanceToWayPoint
                .setScale(0, RoundingMode.UP)) == 0) {
            airplane.getPosition().setLatitude(wayPoint.getLatitude());
            airplane.getPosition().setLongitude(wayPoint.getLongitude());
        } else {
            CoordinateHandler.calculateAndSetNewCoordinates(airplane, distancePass);
        }

        //set new speed
        if (getTimeNeededToDropSpeed(airplane, wayPoint).compareTo(time) == 0) {
            airplane.getPosition().setSpeed(wayPoint.getSpeed());
        } else {
            airplane.getPosition().setSpeed(airplane.getPosition().getSpeed().add(amountDropSpeed
                    .negate()).setScale(3, RoundingMode.CEILING));
        }

        //set new height if needed
        HeightHandler.setHeight(airplane, wayPoint, time);
    }

    private BigDecimal getTimeNeededToDropSpeed(Airplane airplane, WayPoint wayPoint) {
        return (wayPoint.getSpeed()
                .subtract(airplane.getPosition().getSpeed()))
                .divide(airplane.getCharacteristics().getAcceleration(),
                        MathContext.DECIMAL128).abs();
    }
}
