package com.fmourabrasil.booknow.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fmourabrasil.booknow.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
