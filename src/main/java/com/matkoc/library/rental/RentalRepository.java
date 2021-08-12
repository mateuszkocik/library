package com.matkoc.library.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>{

    @Query("SELECT COUNT(r) FROM Rental r WHERE r.fromDate >= :from AND r.fromDate <= :to")
    Long getRentalsFromRange(LocalDate from, LocalDate to);
}
