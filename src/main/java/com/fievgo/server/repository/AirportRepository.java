package com.fievgo.server.repository;

import com.fievgo.server.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
