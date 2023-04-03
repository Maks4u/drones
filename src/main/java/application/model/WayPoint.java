package application.model;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("way_points")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WayPoint {
    private Status status = Status.NOT_PASSED;
    private String latitude;
    private String longitude;
    private BigDecimal flyHeight;
    private BigDecimal speed;

    public enum Status {
        PASSED,
        NOT_PASSED;
    }
}
