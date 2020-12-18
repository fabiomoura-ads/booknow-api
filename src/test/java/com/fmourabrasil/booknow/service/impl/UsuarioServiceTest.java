package com.fmourabrasil.booknow.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

	UsuarioRepository repository;
	
	@Test
	public void deveVerificarExistenciaDeUmEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("teste").email("teste@email.com").senha("123456").build();
		repository.save(usuario);
		
		//execução
		boolean existe = repository.existsByEmail("teste@email.com");
		
		//verificação
		Assertions.assertThat(existe).isTrue();
		
	}
}
