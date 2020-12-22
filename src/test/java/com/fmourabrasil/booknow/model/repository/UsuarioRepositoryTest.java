package com.fmourabrasil.booknow.model.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.util.Auxiliares;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarExistenciaDeUmEmail() {
		// cenário		
		LocalDate data = LocalDate.parse("09/07/1989", Auxiliares.retornaFormatoData("dmy"));
		Usuario usuario = Usuario.builder().nome("teste").email("teste@email.com").senha("123456").dataCadastro(data).build();
		repository.save(usuario);
		
		// execução
		boolean existe = repository.existsByEmail("teste@email.com");

		// verificação
		Assertions.assertThat(existe).isTrue();

	}
	
	@Test
	public void deveRetornarFalsoAoTentarLocalizarUsuarioPorEmail() {
		// cenário		
		Usuario usuario = Usuario.builder().nome("teste").email("teste@email.com").senha("123456").build();
		repository.save(usuario);
		
		// execução
		Optional<Usuario> optUsuario = repository.findByEmail("teste1@email.com");

		// verificação
		Assertions.assertThat(optUsuario.isPresent()).isFalse();

	}
	
	@Test
	public void deveLocalizarUsuarioPorEmail() {
		// cenário		
		Usuario usuario = Usuario.builder().nome("teste").email("teste1@email.com").senha("123456").build();
		repository.save(usuario);
		
		// execução
		Optional<Usuario> optUsuario = repository.findByEmail("teste1@email.com");

		// verificação
		Assertions.assertThat(optUsuario.isPresent()).isTrue();

	}
	
}
