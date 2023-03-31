package application.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("temporary_points")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TemporaryPoint {
    private String latitude;
    private String longitude;
    private BigDecimal flyHeight;
    private BigDecimal speed;
    private BigDecimal degree;
}
