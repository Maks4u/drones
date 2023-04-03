package application.strategy.impl;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.strategy.OperationHandler;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class AccelerationHandler implements OperationHandler {

    @Override
    public TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint) {
        return calculateTemporaryPoint(airplane, wayPoint, BigDecimal.ONE);
    }

    public TemporaryPoint calculateTemporaryPoint(Airplane airplane,
                                                  WayPoint wayPoint,
                                                  BigDecimal time) {
        //calculate the acceleration needed to achieve before get max speed
        BigDecimal accelerationBeforeMaxSpeed = airplane.getCharacteristics()
                .getMaxSpeed().subtract(airplane.getPosition().getSpeed());
        //time needed before get max speed
        BigDecimal timeBeforeMaxSpeed = accelerationBeforeMaxSpeed.divide(airplane
                .getCharacteristics().getAcceleration(), MathContext.DECIMAL128)
                .setScale(14, RoundingMode.CEILING);

        /* if given time more to time need to achieve max speed we
         call max speed handler to end calculation */
        if (time.compareTo(timeBeforeMaxSpeed) > 0) {
            airplane.setPosition(getPointWithAccelerationFromOtherAction(airplane,
                    wayPoint, timeBeforeMaxSpeed));
            BigDecimal timeLeft = time.subtract(timeBeforeMaxSpeed);
            FlyMaxSpeedHandler flyMaxSpeedHandler = new FlyMaxSpeedHandler();
            flyMaxSpeedHandler.calculateTemporaryPoint(airplane, wayPoint, timeLeft);
            return airplane.getPosition();
        }
        TemporaryPoint point = getPointWithAccelerationFromOtherAction(airplane, wayPoint, time);
        airplane.setPosition(point);
        airplane.setOperation(Airplane.Operation.ACCELERATION);
        return point;
    }

    private TemporaryPoint getPointWithAccelerationFromOtherAction(Airplane airplane,
                                                                   WayPoint wayPoint,
                                                                   BigDecimal time) {
        BigDecimal digitalisedByStandardSpeed = airplane.getPosition().getSpeed().multiply(time);
        BigDecimal extraDistanceWithAcceleration = BigDecimal.valueOf(0.5).multiply(airplane
                .getCharacteristics().getAcceleration()).multiply(time).multiply(time);
        BigDecimal totalPassedDistance = digitalisedByStandardSpeed
                .add(extraDistanceWithAcceleration).setScale(14, RoundingMode.CEILING);

        //handle with new coordinates
        CoordinateHandler.calculateAndSetNewCoordinates(airplane, totalPassedDistance);

        //set new speed
        airplane.getPosition().setSpeed(airplane.getPosition().getSpeed()
                .add(airplane.getCharacteristics().getAcceleration().multiply(time))
                .setScale(3, RoundingMode.CEILING));

        //set new height
        HeightHandler.setHeight(airplane, wayPoint, time);
        return airplane.getPosition();
    }
}
