package application.service;

import application.model.Airplane;

public interface AirplaneService {
    Airplane get(Long id);
    Airplane save(Airplane airplane);
    void delete(Long id);
    Airplane update(Airplane airplane);
}
