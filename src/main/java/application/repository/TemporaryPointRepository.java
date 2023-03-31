package application.repository;

import application.model.TemporaryPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryPointRepository extends MongoRepository<TemporaryPoint, String> {
}
