package com.chatop.api.repositories;

import com.chatop.api.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Integer>{
}
