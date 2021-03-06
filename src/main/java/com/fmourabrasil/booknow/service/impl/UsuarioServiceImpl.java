package com.fmourabrasil.booknow.service.impl;

import java.util.List;
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
		Optional<Usuario> optUsuario = repository.findByEmail(email);

		if (!optUsuario.isPresent()) {
			throw new RegraNegocioException("Usuário não encontrado para o e-mail informado.");
		}

		if (!optUsuario.get().getSenha().equals(senha)) {
			throw new RegraNegocioException("Senha incorreta.");
		}

		return optUsuario.get();
	}

	@Override
	@Transactional
	public Usuario salvar(Usuario usuario) {
		this.validarEmail(usuario.getEmail());
		repository.save(usuario);
		return null;
	}

	@Override
	public void validarEmail(String email) {
		if (repository.existsByEmail(email)) {
			throw new RegraNegocioException("Já existe um usuário com o email informado.");
		}
	}

	@Override
	public Usuario obterPorId(Long id) {
		Optional<Usuario> optUsuario = repository.findById(id);
		if (!optUsuario.isPresent()) {
			throw new RegraNegocioException("Usuário não localizado pelo ID informado");
		}
		return optUsuario.get();
	}

	@Override
	public List<Usuario> listar() {
		return repository.findAll();				
	}

}
