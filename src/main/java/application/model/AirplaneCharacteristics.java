package application.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

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
