package application.service;

import application.model.Airplane;

public interface AirplaneService {
    Airplane get(String id);

    Airplane save(Airplane airplane);

    void delete(String id);

    Airplane update(Airplane airplane);
}
