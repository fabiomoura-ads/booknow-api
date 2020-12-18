package com.fmourabrasil.booknow.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fmourabrasil.booknow.model.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	boolean existsByPlaca(String placa);
}
