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
public class PlaceRotationHandler implements OperationHandler {
    @Override
    public TemporaryPoint getTemporaryPoint(Airplane airplane, WayPoint wayPoint) {
        double wayDegree = DegreeHandler.getWayDegree(airplane, wayPoint);
        BigDecimal amountDegreeToRotateLeft = DegreeHandler.getDegreeToRotateLeftStart(airplane,
                wayDegree);

        /* check the time to rotate is bigger than 1sec,
        if yes create new point without change coordinates */
        BigDecimal leftTimeToRotate = amountDegreeToRotateLeft.abs().divide(airplane
                .getCharacteristics().getChangeDegreeSpeed(), MathContext.DECIMAL128);
        if (leftTimeToRotate.compareTo(BigDecimal.ONE) >= 0) {
            BigDecimal rotation = airplane.getCharacteristics().getChangeDegreeSpeed();
            if (BigDecimal.valueOf(wayDegree).compareTo(BigDecimal.ZERO) >= 0) {
                if (airplane.getPosition().getDegree().add(rotation)
                        .compareTo(DegreeHandler.MAX_CIRCLE_DEGREE) >= 0) {
                    airplane.getPosition().setDegree(airplane.getPosition()
                            .getDegree().subtract(DegreeHandler.MAX_CIRCLE_DEGREE));
                }
                airplane.getPosition().setDegree(airplane.getPosition()
                        .getDegree().add(rotation));
            } else {
                if (airplane.getPosition().getDegree().subtract(rotation)
                        .compareTo(BigDecimal.ZERO) < 0) {
                    airplane.getPosition().setDegree(airplane.getPosition().getDegree()
                            .add(DegreeHandler.MAX_CIRCLE_DEGREE));
                }
                airplane.getPosition().setDegree(airplane
                        .getPosition().getDegree().subtract(rotation));
            }
            airplane.setPosition(airplane.getPosition());
            if (airplane.getPosition().getDegree().equals(BigDecimal.valueOf(wayDegree))) {
                airplane.setOperation(Airplane.Operation.ACCELERATION);
            }
            return airplane.getPosition();
        }

        /* calculate time that left before the new update and call
        the other method based on our needs(here is the acceleration) */
        BigDecimal timeLeftToOtherAction = BigDecimal.ONE.subtract(leftTimeToRotate);
        if (wayDegree < 0) {
            airplane.getPosition().setDegree(airplane.getPosition().getDegree()
                    .subtract(amountDegreeToRotateLeft).setScale(3, RoundingMode.CEILING));
        } else {
            airplane.getPosition().setDegree(airplane.getPosition().getDegree()
                    .add(amountDegreeToRotateLeft).setScale(3, RoundingMode.CEILING));
        }
        airplane.setPosition(airplane.getPosition());
        AccelerationHandler accelerationHandler = new AccelerationHandler();
        accelerationHandler.calculateTemporaryPoint(airplane, wayPoint, timeLeftToOtherAction);
        return airplane.getPosition();
    }

    @Override
    public Airplane.Operation getOperation() {
        return Airplane.Operation.PLACE_ROTATION;
    }
}
