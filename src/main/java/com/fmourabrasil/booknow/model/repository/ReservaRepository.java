package com.fmourabrasil.booknow.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
	
	@Query(value = ""
			+ "select count(r.id) > 0 from Reserva r join r.veiculo v "
			+ "where v.id = :idVeiculo "
			+ "and ( r.dataInicio <= :dataFim and r.dataFim >= :dataInicio ) "
			+ "and r.situacao not in ('CANCELADA', 'CONCLUIDA' ) ")
	boolean verificaPossibilidadeDeReservaDoVeiculoNoPeriodoEscolhido(
			@Param("idVeiculo") Long idVeiculo,
			@Param("dataInicio") LocalDate dataInicio,
			@Param("dataFim") LocalDate dataFim);
	
	List<Reserva> findAllByUsuario(Usuario usuario);
}


