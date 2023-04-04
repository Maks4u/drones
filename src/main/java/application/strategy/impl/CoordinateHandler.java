package application.strategy.impl;

import application.model.Airplane;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CoordinateHandler {
    public static void calculateAndSetNewCoordinates(Airplane airplane, BigDecimal distancePassed) {
        // degree to radiance
        double bearingRad = Math.toRadians(airplane.getPosition().getDegree().doubleValue());
        double distance = distancePassed.doubleValue();

        // calculate exact coordinates
        double cosDistance = Math.cos(distance / DistanceHandler.R.doubleValue());
        double sinDistance = Math.sin(distance / DistanceHandler.R.doubleValue());
        double lat2 = Math.asin(Math.sin(Math.toRadians(Double.parseDouble(airplane
                .getPosition().getLatitude()))) * cosDistance
                + Math.cos(Math.toRadians(Double.parseDouble(airplane
                .getPosition().getLatitude()))) * sinDistance * Math.cos(bearingRad));
        double lon2 = Math.toRadians(Double.parseDouble(airplane.getPosition().getLongitude()))
                + Math.atan2(Math.sin(bearingRad) * sinDistance * Math.cos(Math
                        .toRadians(Double.parseDouble(airplane.getPosition().getLatitude()))),
                cosDistance - Math.sin(Math.toRadians(Double.parseDouble(airplane
                        .getPosition().getLatitude()))) * Math.sin(lat2));

        // radiance to degree
        lat2 = Math.toDegrees(lat2);
        lon2 = Math.toDegrees(lon2);

        //set new coordinates
        airplane.getPosition().setLatitude(String.valueOf(BigDecimal
                .valueOf(lat2).setScale(14, RoundingMode.CEILING)));
        airplane.getPosition().setLongitude(String.valueOf(BigDecimal
                .valueOf(lon2).setScale(14, RoundingMode.CEILING)));
    }

    public static List<Double> calculateNewCoordinatesRotating(Airplane airplane, BigDecimal time) {
        double lat1 = Math.toRadians(Double.parseDouble(airplane.getPosition().getLatitude()));
        double lon1 = Math.toRadians(Double.parseDouble(airplane.getPosition().getLongitude()));
        double brng = Math.toRadians(airplane.getPosition().getDegree().doubleValue());
        double d = airplane.getPosition().getSpeed().doubleValue() * time.doubleValue();
        double cosR = Math.cos(d / DistanceHandler.R.doubleValue());
        double sinR = Math.sin(d / DistanceHandler.R.doubleValue());
        double lat2 = Math.asin(Math.sin(lat1) * cosR + Math.cos(lat1) * sinR * Math.cos(brng));
        double lon2 = lon1 + Math.atan2(Math.sin(brng) * sinR * Math.cos(lat1),
                cosR - Math.sin(lat1) * Math.sin(lat2));
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(lat2);
        coordinates.add(lon2);
        return coordinates;
    }
}
