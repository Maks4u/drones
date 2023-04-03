package application.model;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("airplane_characteristics")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AirplaneCharacteristics {
    private BigDecimal maxSpeed;
    private BigDecimal acceleration;
    private BigDecimal changeHighSpeed;
    private BigDecimal changeDegreeSpeed;
}
