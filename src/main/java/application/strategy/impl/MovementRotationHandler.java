package application.strategy.impl;

import application.model.Airplane;
import application.model.TemporaryPoint;
import application.model.WayPoint;
import application.strategy.OperationHandler;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MovementRotationHandler implements OperationHandler {
    private static final Integer LATITUDE_INDEX = 0;
    private static final Integer LONGITUDE_INDEX = 1;

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

    @Override
    public Airplane.Operation getOperation() {
        return Airplane.Operation.MOVEMENT_ROTATION;
    }

    public void calculateTemporaryPoint(Airplane airplane, WayPoint wayPoint, BigDecimal time) {
        List<Double> coords = CoordinateHandler.calculateNewCoordinatesRotating(airplane, time);
        airplane.getPosition().setLatitude(String
                .valueOf(Math.toDegrees(coords.get(LATITUDE_INDEX))));
        airplane.getPosition().setLongitude(String
                .valueOf(Math.toDegrees(coords.get(LONGITUDE_INDEX))));

        //calculate new degree
        double wayDegree = DegreeHandler.getWayDegree(airplane, wayPoint);
        BigDecimal degreeToRotateLeft = getDegreeToRotateLeft(airplane, wayDegree);
        BigDecimal rotation = airplane.getCharacteristics().getChangeDegreeSpeed();
        BigDecimal rotatedValue = airplane.getPosition().getDegree();

        if (degreeToRotateLeft.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal plusDegree = rotatedValue.add(rotation.multiply(time));
            if (plusDegree.compareTo(DegreeHandler.MAX_CIRCLE_DEGREE) < 0) {
                rotatedValue = plusDegree;
            } else {
                rotatedValue = plusDegree
                        .subtract(DegreeHandler.MAX_CIRCLE_DEGREE);
            }
        } else {
            BigDecimal minusDegree = rotatedValue.subtract(rotation.multiply(time));
            if (minusDegree.compareTo(BigDecimal.ZERO) > 0) {
                rotatedValue = minusDegree;
            } else {
                rotatedValue = minusDegree
                        .add(DegreeHandler.MAX_CIRCLE_DEGREE);
            }
        }

        //set new degree
        airplane.getPosition().setDegree(rotatedValue.setScale(3, RoundingMode.CEILING));
    }

    private BigDecimal getDegreeToRotateLeft(Airplane airplane, double wayDegree) {
        BigDecimal degreeToRotateLeft = DegreeHandler.getDegreeToRotateLeft(airplane, wayDegree);
        if (degreeToRotateLeft.compareTo(DegreeHandler.HALF_CIRCLE_DEGREE) > 0) {
            degreeToRotateLeft = degreeToRotateLeft.subtract(DegreeHandler.MAX_CIRCLE_DEGREE);
        }
        return degreeToRotateLeft;
    }
}
