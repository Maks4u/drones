package application.strategy.impl;

import application.model.Airplane;
import application.model.WayPoint;
import java.math.BigDecimal;

public class DegreeHandler {
    public static final BigDecimal HALF_CIRCLE_DEGREE = BigDecimal.valueOf(180L);
    public static final BigDecimal MAX_CIRCLE_DEGREE = BigDecimal.valueOf(360L);

    public static BigDecimal getDegreeToRotateLeftStart(Airplane airplane, double wayDegree) {
        BigDecimal amountDegreeToRotateLeft;
        if (airplane.getPosition().getDegree().compareTo(HALF_CIRCLE_DEGREE) > 0) {
            amountDegreeToRotateLeft = BigDecimal.valueOf(wayDegree).abs()
                    .subtract((MAX_CIRCLE_DEGREE).subtract(airplane.getPosition().getDegree()).abs());
        } else {
            amountDegreeToRotateLeft = BigDecimal.valueOf(wayDegree).abs().subtract(airplane
                    .getPosition().getDegree().abs());
        }
        return amountDegreeToRotateLeft;
    }

    public static BigDecimal getDegreeToRotateLeft(Airplane airplane, double wayDegree) {
        BigDecimal amountDegreeToRotateLeft = BigDecimal.ZERO;
        if (airplane.getPosition().getDegree().compareTo(BigDecimal.valueOf(wayDegree)) == 0) {
            return amountDegreeToRotateLeft;
        } else {
            if (BigDecimal.valueOf(wayDegree).compareTo(airplane.getPosition().getDegree()) < 0) {
                if (airplane.getPosition().getDegree().subtract(BigDecimal.valueOf(wayDegree))
                        .abs().compareTo(HALF_CIRCLE_DEGREE) > 0) {
                    amountDegreeToRotateLeft = airplane.getPosition().getDegree()
                            .subtract(BigDecimal.valueOf(wayDegree)).abs()
                            .subtract(MAX_CIRCLE_DEGREE);
                } else {
                    amountDegreeToRotateLeft = (BigDecimal.valueOf(wayDegree).subtract(airplane
                            .getPosition().getDegree())).negate();
                }
            } else {
                if (airplane.getPosition().getDegree().subtract(BigDecimal.valueOf(wayDegree))
                        .abs().compareTo(HALF_CIRCLE_DEGREE) > 0) {
                    amountDegreeToRotateLeft = airplane.getPosition().getDegree()
                            .subtract(BigDecimal.valueOf(wayDegree)).abs()
                            .subtract(MAX_CIRCLE_DEGREE).abs();
                } else {
                    amountDegreeToRotateLeft = (BigDecimal.valueOf(wayDegree)
                            .subtract(airplane.getPosition().getDegree())).negate();
                }
            }

        }
        return amountDegreeToRotateLeft;
    }

    public static double getWayDegree(Airplane airplane, WayPoint wayPoint) {
        double lat1 = Math.toRadians(Double.parseDouble(airplane.getPosition().getLatitude()));
        double lon1 = Math.toRadians(Double.parseDouble(airplane.getPosition().getLongitude()));
        double lat2 = Math.toRadians(Double.parseDouble(wayPoint.getLatitude()));
        double lon2 = Math.toRadians(Double.parseDouble(wayPoint.getLongitude()));
        double deltaLongitude = lon2 - lon1;
        double y = Math.sin(deltaLongitude) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(deltaLongitude);
        double angle = Math.atan2(y, x);
        double bearing = (Math.toDegrees(angle) + MAX_CIRCLE_DEGREE.doubleValue())
                % MAX_CIRCLE_DEGREE.doubleValue();
        double wayDegree = (bearing + MAX_CIRCLE_DEGREE.doubleValue())
                % MAX_CIRCLE_DEGREE.doubleValue();
        if (airplane.getOperation().equals(Airplane.Operation.PLACE_ROTATION)) {
            if (wayDegree > HALF_CIRCLE_DEGREE.doubleValue()) {
                wayDegree = wayDegree - MAX_CIRCLE_DEGREE.doubleValue();
            }
        }
        return wayDegree;
    }
}
