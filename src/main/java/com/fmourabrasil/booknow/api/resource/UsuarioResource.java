package com.fmourabrasil.booknow.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmourabrasil.booknow.api.dto.UsuarioDTO;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioService service;

	@GetMapping
	public ResponseEntity listar() {		
		return ResponseEntity.ok(service.listar());				
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {

		Usuario usuario = Usuario.builder().nome(dto.getNome()).senha(dto.getSenha()).email(dto.getEmail()).build();

		try {

			Usuario usuarioSalvo = service.salvar(usuario);

			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

		} catch (Exception error) {
			return ResponseEntity.badRequest().body(error.getMessage());
		}

	}

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {

		try {

			Usuario usuario = service.autenticar(dto.getEmail(), dto.getSenha());

			return new ResponseEntity(usuario, HttpStatus.OK);

		} catch (Exception error) {

			return ResponseEntity.badRequest().body(error.getMessage());
		}

	}

}
