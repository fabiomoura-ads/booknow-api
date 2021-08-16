package com.fmourabrasil.booknow.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.enums.SituacaoReserva;
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
		reserva.preparaNovaReserva();
		this.verificaPossibilidadeDaReserva(reserva);
		return repository.save(reserva);
	}

	@Override
	public Reserva atualizar(Reserva reserva) {
		Objects.requireNonNull(reserva.getId());
		reserva.validaReserva().calculaValorTotalDaReserva();
		this.verificaPossibilidadeDaReserva(reserva);
		return repository.save(reserva);
	}

	@Override
	public Reserva deletar(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva atualizaSituacao(Reserva reserva, SituacaoReserva situacao) {
		Objects.requireNonNull(reserva.getId());
		this.avaliaAtualizacaoSituacaoDaReserva(reserva, situacao);
		return repository.save(reserva);
	}

	@Override
	public void verificaPossibilidadeDaReserva(Reserva reserva) {
		boolean veiculoJaReservadoNoPeriodoEscolhido = repository
				.verificaPossibilidadeDeReservaDoVeiculoNoPeriodoEscolhido(reserva.getVeiculo().getId(),
						reserva.getDataInicio(), reserva.getDataFim());

		if (veiculoJaReservadoNoPeriodoEscolhido) {
			throw new RegraNegocioException("Veículo está indisponível no período selecionado, tente outro período!");
		}
	}

	@Override
	public List<Reserva> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva buscarPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Reserva não localizada pelo ID informado."));
	}

	@Override
	public void avaliaAtualizacaoSituacaoDaReserva(Reserva reserva, SituacaoReserva situacao) {

		if (reserva.getSituacao().equals(SituacaoReserva.CANCELADA)
				|| reserva.getSituacao().equals(SituacaoReserva.CONCLUIDA)) {
			throw new RegraNegocioException(
					String.format("A reserva %s não pode ser alterada.", reserva.getSituacao().name()));
		}

		if (situacao.equals(SituacaoReserva.CONFIRMADA) || situacao.equals(SituacaoReserva.CANCELADA)) {

			if (reserva.getSituacao().equals(SituacaoReserva.PENDENTE)
					&& reserva.getDataInicio().compareTo(LocalDate.now()) < 0) {

				throw new RegraNegocioException(String.format(
						"A reserva está expirada, deve ser %s até 1 dia antes da data de início da reserva.",
						situacao.name()));
			}

			if (!reserva.getSituacao().equals(SituacaoReserva.PENDENTE)
					&& !reserva.getSituacao().equals(SituacaoReserva.CONFIRMADA)) {
				throw new RegraNegocioException(
						String.format("A reserva na situação %s não pode ser atualizda para %s.",
								reserva.getSituacao().name(), situacao.name()));
			}

		}

		if (situacao.equals(SituacaoReserva.PENDENTE) && !reserva.getSituacao().equals(situacao)) {
			throw new RegraNegocioException(
					"A reserva na situação " + reserva.getSituacao().name() + " não pode ser atualizda para PENDENTE.");
		}

		if (situacao.equals(SituacaoReserva.CONCLUIDA)) {
			if (reserva.getDataFim().compareTo(LocalDate.now()) != 0) {
				throw new RegraNegocioException(
						String.format("A reserva somente pode ser %s na data fim da reserva.", situacao.name()));
			}
		}

		if (situacao.equals(SituacaoReserva.EFETIVADA)) {
			if (reserva.getDataInicio().compareTo(LocalDate.now()) != 0) {
				throw new RegraNegocioException(
						String.format("A reserva somente pode ser %s na data de início da reserva.", situacao.name()));
			}
		}

		if (reserva.getSituacao().equals(SituacaoReserva.PENDENTE)) {

			if (situacao.equals(SituacaoReserva.EFETIVADA)) {
				throw new RegraNegocioException("A reserva não pode ser EFETIVADA sem antes ter sido CONFIRMADA.");

			} else if (situacao.equals(SituacaoReserva.CONCLUIDA)) {
				throw new RegraNegocioException("A reserva não pode ser CONCLUIDA sem antes ter sido EFETIVADA.");
			}
		}

		if (reserva.getSituacao().equals(SituacaoReserva.CONFIRMADA)) {

			if (situacao.equals(SituacaoReserva.CONCLUIDA)) {
				throw new RegraNegocioException("A reserva não pode ser CONCLUIDA sem antes ter sido EFETIVADA.");
			}
		}

		reserva.setSituacao(situacao);

	}

	@Override
	public List<Reserva> retornaReservasDoUsuario(Usuario usuario) {
		return repository.findAllByUsuario(usuario);
	}

}
