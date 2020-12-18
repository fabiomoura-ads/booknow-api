package com.fmourabrasil.booknow.service;

import java.util.List;
import java.util.Optional;

import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;

public interface ReservaService {

	Reserva salvar(Reserva reserva);
	
	Reserva atualizar(Reserva reserva);
	
	Reserva deletar(Reserva reserva);
	
	Reserva atualizarStatus(Reserva reserva);	
	
	void verificaPossibilidadeDeReserva(Veiculo veiculo, Usuario usuario);
	
	List<Reserva> listar();
	
	Optional<Reserva> buscarPorId(Long id);
}
