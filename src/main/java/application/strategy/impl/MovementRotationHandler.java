package application.strategy.impl;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.strategy.OperationHandler;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class MovementRotationHandler implements OperationHandler {
    @Override
    public TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint) {
        double wayDegree = DegreeHandler.getWayDegree(airplane, wayPoint);
        BigDecimal degreeToRotateLeft = getDegreeToRotateLeft(airplane, wayDegree);
        BigDecimal leftTimeToRotate = degreeToRotateLeft.divide(airplane.getCharacteristics()
                .getChangeDegreeSpeed(), MathContext.DECIMAL128).abs();
        if (leftTimeToRotate.compareTo(BigDecimal.ONE) < 0) {
            airplane.getPosition().setDegree(BigDecimal.valueOf(wayDegree)
                    .setScale(3, RoundingMode.CEILING));
            AccelerationHandler accelerationHandler = new AccelerationHandler();
            accelerationHandler.calculateTemporaryPoint(airplane, wayPoint,
                    BigDecimal.ONE.subtract(leftTimeToRotate));
            airplane.setOperation(Airplane.Operation.ACCELERATION);
        } else {
            calculateTemporaryPoint(airplane, wayPoint, BigDecimal.ONE);
        }
        return airplane.getPosition();
    }

    public void calculateTemporaryPoint(Airplane airplane, WayPoint wayPoint, BigDecimal time) {
        List<Double> coords = CoordinateHandler.calculateNewCoordinatesRotating(airplane, time);
        airplane.getPosition().setLatitude(String.valueOf(Math.toDegrees(coords.get(0))));
        airplane.getPosition().setLongitude(String.valueOf(Math.toDegrees(coords.get(1))));

        //calculate new degree
        double wayDegree = DegreeHandler.getWayDegree(airplane, wayPoint);
        BigDecimal degreeToRotateLeft = getDegreeToRotateLeft(airplane, wayDegree);
        BigDecimal rotation = airplane.getCharacteristics().getChangeDegreeSpeed();
        BigDecimal rotatedValue = airplane.getPosition().getDegree();

        if (degreeToRotateLeft.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal plusDegree = rotatedValue.add(rotation.multiply(time));
            if (plusDegree.compareTo(BigDecimal.valueOf(360L)) < 0) {
                rotatedValue = plusDegree;
            } else {
                rotatedValue = plusDegree
                        .subtract(BigDecimal.valueOf(360L));
            }
        } else {
            BigDecimal minusDegree = rotatedValue.subtract(rotation.multiply(time));
            if (minusDegree.compareTo(BigDecimal.ZERO) > 0) {
                rotatedValue = minusDegree;
            } else {
                rotatedValue = minusDegree
                        .add(BigDecimal.valueOf(360L));
            }
        }

        //set new degree
        airplane.getPosition().setDegree(rotatedValue.setScale(3, RoundingMode.CEILING));
    }

    private BigDecimal getDegreeToRotateLeft(Airplane airplane, double wayDegree) {
        BigDecimal degreeToRotateLeft = DegreeHandler.getDegreeToRotateLeft(airplane, wayDegree);
        if (degreeToRotateLeft.compareTo(BigDecimal.valueOf(180L)) > 0) {
            degreeToRotateLeft = degreeToRotateLeft.subtract(BigDecimal.valueOf(360L));
        }
        return degreeToRotateLeft;
    }
}
