package com.fmourabrasil.booknow.service;

import java.util.List;
import java.util.Optional;

import com.fmourabrasil.booknow.model.entity.Veiculo;

public interface VeiculoService {

	Veiculo salvar(Veiculo veiculo);
	
	Veiculo atualizar(Veiculo veiculo);
	
	void validarDados(Veiculo veiculo);

	List<Veiculo> listar();	
	
	Veiculo buscarPorId(Long id);
	
	void deletar(Veiculo veiculo);
}
