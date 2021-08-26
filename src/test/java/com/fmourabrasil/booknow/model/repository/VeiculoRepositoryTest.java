package com.fmourabrasil.booknow.model.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class VeiculoRepositoryTest {

	@Autowired
	VeiculoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveEncontrarVeiculoCadastradoParaAPlacaInformada() {
		// cenário	
		entityManager.persist(retornaVeiculo());

		// execução 
		repository.findByPlaca("OCG-1111")
		
		//verificação
		.map(entity -> Assertions.assertThat(entity.getId()).isNotNull())
		.orElseThrow(() -> new RegraNegocioException("Veículo não localizado."));	

	}

	@Test
	public void naoDeveEncontrarVeiculoCadastradoParaAPlacaInformada() {
		// cenário
		entityManager.persist(retornaVeiculo());
		
		// execução
		repository.findByPlaca("OCG-1112")
		
		//verificação
		.map(entity -> {
			throw new RegraNegocioException("Veículo já cadastrado.");
		});
		
	}
	
	public Veiculo retornaVeiculo() {
		return Veiculo.builder()
				.nome("veiculoa")
				.descricao("Veiculo muito bom")
				.placa("OCG-1111")				
				.build();	
	}
	
}
