package application.service;

import application.model.Flight;

public interface FlightService {
    Flight get(String id);

    Flight save(Flight flight);

    void delete(String id);

    Flight update(Flight flight);
}
