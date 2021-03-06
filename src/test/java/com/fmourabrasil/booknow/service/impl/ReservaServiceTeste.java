package com.fmourabrasil.booknow.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;
import com.fmourabrasil.booknow.model.enums.SituacaoReserva;
import com.fmourabrasil.booknow.model.repository.ReservaRepository;
import com.fmourabrasil.booknow.model.repository.UsuarioRepository;
import com.fmourabrasil.booknow.model.repository.VeiculoRepository;
import com.fmourabrasil.booknow.service.ReservaService;
import com.fmourabrasil.booknow.util.Auxiliares;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReservaServiceTeste {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	VeiculoRepository veiculoRepository;

	@Autowired
	ReservaRepository repository;

	@Autowired
	ReservaService service;
	
	
	public void deveGravarUmaReservaComSucesso() {

		// cenário
		Usuario usuario = Usuario.builder().email("teste@email.com").senha("123456").build();
		Veiculo veiculo = Veiculo.builder().nome("Carro1").valorDia(BigDecimal.valueOf(150)).build();
		usuarioRepository.save(usuario);
		veiculoRepository.save(veiculo);

		LocalDate dataInicio = Auxiliares.converteDataStringParaLocalDate("2020-01-01");
		LocalDate dataFim = Auxiliares.converteDataStringParaLocalDate("2020-01-05");
		Reserva reserva = Reserva.builder().usuario(usuario).veiculo(veiculo).dataInicio(dataInicio).dataFim(dataFim)
				.build();

		reserva.calculaValorTotalDaReserva();

		repository.save(reserva);

		dataInicio = Auxiliares.converteDataStringParaLocalDate("2020-01-06");
		dataFim = Auxiliares.converteDataStringParaLocalDate("2020-01-09");
		
		Reserva reserva2 = Reserva.builder().usuario(usuario).veiculo(veiculo).dataInicio(dataInicio).dataFim(dataFim)
				.build();
		
		boolean result = repository.verificaPossibilidadeDeReservaDoVeiculoNoPeriodoEscolhido(
				reserva2.getVeiculo().getId(), dataInicio, dataFim);

		if ( result ) {
			System.out.println("O veículo está indisponível");	
		} else {
			System.out.println("O veículo está disponível");
		}		

	}
	
	//@Test
	public void avaliaAtualizacaoSituacaoReserva() {

		// cenário
		Usuario usuario = Usuario.builder().email("teste@email.com").senha("123456").build();
		Veiculo veiculo = Veiculo.builder().nome("Carro1").valorDia(BigDecimal.valueOf(150)).build();
		usuarioRepository.save(usuario);
		veiculoRepository.save(veiculo);

		LocalDate dataInicio = Auxiliares.converteDataStringParaLocalDate("2020-12-23");
		LocalDate dataFim = Auxiliares.converteDataStringParaLocalDate("2020-12-30");
		Reserva reserva = Reserva.builder().usuario(usuario).veiculo(veiculo).dataInicio(dataInicio).dataFim(dataFim)
				.build();

		reserva.calculaValorTotalDaReserva();
		repository.save(reserva);
		
		reserva.setSituacao(SituacaoReserva.CONFIRMADA);
		
		//assertThrows(RegraNegocioException.class, () -> service.avaliaAtualizacaoSituacaoDaReserva(reserva));
		
		//service.avaliaAtualizacaoSituacaoDaReserva(reserva);
		
		reserva.setSituacao(SituacaoReserva.EFETIVADA);
		
		//assertThrows(RegraNegocioException.class, () -> service.avaliaAtualizacaoSituacaoDaReserva(reserva));
		
	}	

}
