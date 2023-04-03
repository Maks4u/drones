package application.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("airplanes")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Airplane {
    @Id
    private String id;
    private AirplaneCharacteristics characteristics;
    private TemporaryPoint position;
    private List<Flight> flights;
    private Operation operation = Operation.NO_ACTION;

    public enum Operation {
        NO_ACTION,
        PLACE_ROTATION,
        ACCELERATION,
        MAX_SPEED_FLYING,
        DROPPING_SPEED,
        MOVEMENT_ROTATION;
    }
}
