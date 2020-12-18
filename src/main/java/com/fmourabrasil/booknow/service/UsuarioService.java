package com.fmourabrasil.booknow.service;

import com.fmourabrasil.booknow.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
}
