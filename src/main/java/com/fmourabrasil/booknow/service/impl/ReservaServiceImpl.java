package com.fmourabrasil.booknow.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;
import com.fmourabrasil.booknow.model.repository.ReservaRepository;
import com.fmourabrasil.booknow.model.repository.VeiculoRepository;
import com.fmourabrasil.booknow.service.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	ReservaRepository repository;

	@Autowired
	VeiculoRepository veiculoRepository;

	@Override
	public Reserva novaReserva(Reserva reserva) {
		this.verificaPossibilidadeDeReserva(reserva);
		reserva.preparaNovaReserva();
		return repository.save(reserva);
	}

	@Override
	public Reserva atualizar(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva deletar(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva atualizarStatus(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verificaPossibilidadeDeReserva(Reserva reserva) {
		boolean veiculoJaReservadoNoPeriodoEscolhido = repository
				.verificaPossibilidadeDeReservaDoVeiculoNoPeriodoEscolhido(
						reserva.getVeiculo().getId(),
						reserva.getDataInicio(), 
						reserva.getDataFim());
		
		if ( veiculoJaReservadoNoPeriodoEscolhido ) {
			throw new RegraNegocioException("Veículo está indisonível no período selecionado, tente outro período!");
		}
	}

	@Override
	public List<Reserva> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Reserva> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
