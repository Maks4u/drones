package application.service;

import application.model.Flight;

public interface FlightService {
    Flight findById(String id);

    Flight save(Flight flight);

    Flight update(Flight flight);
}
