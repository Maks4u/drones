package application.service.impl;

import application.model.Airplane;
import application.repository.AirplaneRepository;
import application.service.AirplaneService;
import org.springframework.stereotype.Service;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    private final AirplaneRepository airplaneRepository;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public Airplane get(String id) {
        return airplaneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find such Airplane by ID: " + id));
    }

    @Override
    public Airplane save(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public void delete(String id) {
        airplaneRepository.deleteById(id);
    }

    @Override
    public Airplane update(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }
}
