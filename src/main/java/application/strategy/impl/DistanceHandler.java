package application.strategy.impl;

import application.model.Airplane;
import application.model.WayPoint;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DistanceHandler {
    public static final BigDecimal R = BigDecimal.valueOf(6378137L);

    public static BigDecimal calculateAndSetDistanceToWayPoint(Airplane airplane,
                                                               WayPoint wayPoint) {
        //calculate exact distance to WayPoint(m)
        double startLatitude = Math.toRadians(Double
                .parseDouble(airplane.getPosition().getLatitude()));
        double endLatitude = Math.toRadians(Double
                .parseDouble(wayPoint.getLatitude()));
        double deltaPhi = Math.toRadians(Double.parseDouble(wayPoint
                .getLatitude()) - Double.parseDouble(airplane.getPosition().getLatitude()));
        double deltaLambda = Math.toRadians(Double.parseDouble(wayPoint
                .getLongitude()) - Double.parseDouble(airplane.getPosition().getLongitude()));

        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) + Math.cos(startLatitude)
                * Math.cos(endLatitude) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double res = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return BigDecimal.valueOf(res * R.doubleValue()).setScale(3, RoundingMode.CEILING);
    }
}
