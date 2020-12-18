package com.fmourabrasil.booknow.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fmourabrasil.booknow.model.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
	
}
