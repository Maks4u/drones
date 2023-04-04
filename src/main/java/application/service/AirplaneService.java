package application.service;

import application.model.Airplane;

public interface AirplaneService {
    Airplane findById(String id);

    Airplane save(Airplane airplane);

    Airplane update(Airplane airplane);
}
