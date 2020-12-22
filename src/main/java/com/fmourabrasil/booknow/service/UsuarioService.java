package com.fmourabrasil.booknow.service;

import java.util.List;

import com.fmourabrasil.booknow.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
	
	Usuario obterPorId(Long id);
	
	List<Usuario> listar();
}
