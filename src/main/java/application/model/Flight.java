package application.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("flights")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Flight {
    @Id
    private String id;
    private Long number;
    private List<WayPoint> wayPoints;
    private List<TemporaryPoint> passedPoints;
}
