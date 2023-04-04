package application.service.impl;

import application.model.Flight;
import application.repository.FlightRepository;
import application.service.FlightService;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight findById(String id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find such Flight by ID: " + id));
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Flight flight) {
        return flightRepository.save(flight);
    }
}
