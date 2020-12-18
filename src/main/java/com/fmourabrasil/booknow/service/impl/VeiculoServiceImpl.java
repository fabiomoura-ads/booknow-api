package com.fmourabrasil.booknow.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Veiculo;
import com.fmourabrasil.booknow.model.repository.VeiculoRepository;
import com.fmourabrasil.booknow.service.VeiculoService;

@Service
public class VeiculoServiceImpl implements VeiculoService {

	@Autowired
	private VeiculoRepository repository;
	
	@Override
	public Veiculo salvar(Veiculo veiculo) {
		if (repository.existsByPlaca(veiculo.getPlaca())) {
			throw new RegraNegocioException("Já existe um veículo cadastrado com essa placa!");
		}
		validarDados(veiculo);
		return repository.save(veiculo);
	}

	@Override
	public Veiculo atualizar(Veiculo veiculo) {
		Objects.requireNonNull(veiculo.getId());
		return repository.save(veiculo);
	}

	@Override
	public void validarDados(Veiculo veiculo) {
		if (veiculo.getNome().isEmpty()) {
			throw new RegraNegocioException("Informe o nome do veículo.");
		}

		if (veiculo.getMarca() == null) {
			throw new RegraNegocioException("Informe a marca do veículo.");
		}

		if (veiculo.getAno() == null) {
			throw new RegraNegocioException("Informe o ano do veículo.");
		}

		if (veiculo.getPlaca().isEmpty()) {
			throw new RegraNegocioException("Informe a placa do veículo.");
		}
		if (veiculo.getValorDia() == null || veiculo.getValorDia().equals(BigDecimal.ZERO)) {
			throw new RegraNegocioException("Informe o valor do aluguel x dia do veículo.");
		}
	}

	@Override
	public List<Veiculo> listar() {
		return repository.findAll();
	}

	@Override
	public void deletar(Veiculo veiculo) {
		Objects.requireNonNull(veiculo.getId());
		repository.delete(veiculo);		
	}

	@Override
	public Veiculo buscarPorId(Long id) {
		Optional<Veiculo> optVeiculo = repository.findById(id);
		if ( !optVeiculo.isPresent() ) {
			throw new RegraNegocioException("Veículo não localizado pelo ID informado!");
		}
		return optVeiculo.get();
	}

}
