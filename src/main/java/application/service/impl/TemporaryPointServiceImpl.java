package application.service.impl;

import application.model.TemporaryPoint;
import application.repository.TemporaryPointRepository;
import application.service.TemporaryPointService;
import org.springframework.stereotype.Service;

@Service
public class TemporaryPointServiceImpl implements TemporaryPointService {
    private final TemporaryPointRepository temporaryPointRepository;

    public TemporaryPointServiceImpl(TemporaryPointRepository temporaryPointRepository) {
        this.temporaryPointRepository = temporaryPointRepository;
    }

    @Override
    public TemporaryPoint save(TemporaryPoint temporaryPoint) {
        return temporaryPointRepository.save(temporaryPoint);
    }
}
