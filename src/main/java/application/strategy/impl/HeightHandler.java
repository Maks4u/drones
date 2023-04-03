package application.strategy.impl;

import application.model.Airplane;
import application.model.WayPoint;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class HeightHandler {
    public static void setHeight(Airplane airplane, WayPoint wayPoint, BigDecimal time) {
        if (wayPoint.getFlyHeight().compareTo(airplane.getPosition().getFlyHeight()) < 0) {
            if (airplane.getPosition().getFlyHeight().subtract(wayPoint.getFlyHeight())
                    .compareTo(airplane.getCharacteristics().getChangeHighSpeed()) <= 0) {
                airplane.getPosition().setFlyHeight(wayPoint.getFlyHeight());
            } else {
                airplane.getPosition().setFlyHeight(airplane.getPosition().getFlyHeight()
                        .subtract(airplane.getCharacteristics().getChangeHighSpeed()
                                .multiply(time)).setScale(3, RoundingMode.CEILING));
            }
        } else if (wayPoint.getFlyHeight().compareTo(airplane.getPosition().getFlyHeight()) > 0) {
            if (wayPoint.getFlyHeight().subtract(airplane.getPosition().getFlyHeight())
                    .compareTo(airplane.getCharacteristics().getChangeHighSpeed()) <= 0) {
                airplane.getPosition().setFlyHeight(wayPoint.getFlyHeight());
            } else {
                airplane.getPosition().setFlyHeight(airplane.getPosition().getFlyHeight()
                        .add(airplane.getCharacteristics()
                        .getChangeHighSpeed().multiply(time)).setScale(3, RoundingMode.CEILING));
            }
        }
    }
}
