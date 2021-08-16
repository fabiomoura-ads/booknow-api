package com.fmourabrasil.booknow.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.repository.UsuarioRepository;
import com.fmourabrasil.booknow.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario autenticar(String email, String senha) {
		return repository.findByEmailAndSenha(email, senha)
				.orElseThrow(() -> new RegraNegocioException("Usuário ou senha inválido."));
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		this.validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		repository.findByEmail(email)
				.orElseThrow(() -> new RegraNegocioException("Já existe um usuário com o email informado."));
	}

	@Override
	public Usuario obterPorId(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Usuário não localizado pelo ID informado"));
	}

	@Override
	public List<Usuario> listar() {
		return repository.findAll();
	}

}
