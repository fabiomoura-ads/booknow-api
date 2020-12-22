package com.fmourabrasil.booknow.service;

import java.util.List;

import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.enums.SituacaoReserva;

public interface ReservaService {

	Reserva novaReserva(Reserva reserva);
	
	Reserva atualizar(Reserva reserva);
	
	Reserva deletar(Reserva reserva);
	
	Reserva atualizaSituacao(Reserva reserva, SituacaoReserva situacao);	
	
	void verificaPossibilidadeDaReserva(Reserva reserva);
	
	List<Reserva> listar();
	
	Reserva buscarPorId(Long id);
	
	void validaReserva(Reserva reserva);
		
	void avaliaAtualizacaoSituacaoDaReserva(Reserva reserva, SituacaoReserva situacao);
	
	List<Reserva> retornaReservasDoUsuario(Usuario usuario);
}
