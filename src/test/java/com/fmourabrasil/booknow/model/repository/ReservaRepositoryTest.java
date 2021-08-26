package com.fmourabrasil.booknow.model.repository;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ReservaRepositoryTest {

	@Autowired
	ReservaRepository repository;

	@Autowired
	TestEntityManager entityManager;

	
	
	public Reserva retornaReserva() {

		Usuario usuario = Usuario.builder()
						.nome("fulano")
						.email("fulano@email.com")
						.senha("123456")
						.dataCadastro(LocalDate.now())
						.build();

		Veiculo veiculo = Veiculo.builder()
						.nome("veiculoa")
						.descricao("Veiculo muito bom")
						.placa("OCG-1111")
						.build();
		
		LocalDate dataInicio = LocalDate.now();
		LocalDate dataFim = dataInicio.plusDays(5);
		
		Reserva reserva = Reserva.builder()
				.veiculo(veiculo)
				.usuario(usuario)
				.dataInicio(dataInicio)
				.dataFim(dataFim)
				.build();
		
		reserva.calculaValorTotalDaReserva();

		return reserva;
	}
}
