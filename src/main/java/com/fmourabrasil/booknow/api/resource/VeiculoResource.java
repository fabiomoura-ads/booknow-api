package com.fmourabrasil.booknow.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fmourabrasil.booknow.api.dto.VeiculoDTO;
import com.fmourabrasil.booknow.model.entity.Veiculo;
import com.fmourabrasil.booknow.model.enums.MarcaVeiculo;
import com.fmourabrasil.booknow.model.enums.SituacaoVeiculo;
import com.fmourabrasil.booknow.service.VeiculoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoResource {

	private final VeiculoService service;

	@PostMapping
	public ResponseEntity salvar(@RequestBody VeiculoDTO dto) {
		try {

			Veiculo veiculo = converteDtoParaModelo(dto);

			System.out.println(veiculo);
			Veiculo veiculoSalvo = service.salvar(veiculo);

			return new ResponseEntity(veiculoSalvo, HttpStatus.CREATED);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VeiculoDTO dto) {
		try {

			Veiculo veiculo = converteDtoParaModelo(dto);

			service.buscarPorId(veiculo.getId());

			Veiculo veiculoSalvo = service.atualizar(veiculo);

			return new ResponseEntity(veiculoSalvo, HttpStatus.CREATED);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
	}

	@GetMapping
	public ResponseEntity listar() {
		try {

			List<Veiculo> lista = service.listar();

			return new ResponseEntity(lista, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private Veiculo converteDtoParaModelo(VeiculoDTO dto) {
		return Veiculo.builder().id(dto.getId()).nome(dto.getNome()).marca(MarcaVeiculo.valueOf(dto.getMarca())).placa(dto.getPlaca())
				.ano(dto.getAno()).situacao(SituacaoVeiculo.valueOf(dto.getSituacao())).valorDia(dto.getValorDia()).build();
	}

	@GetMapping("/marcas")
	public ResponseEntity<MarcaVeiculo[]> marcas() {

		MarcaVeiculo[] marcas = MarcaVeiculo.values();
		return ResponseEntity.ok(marcas);

	}

	@GetMapping("/situacoes")
	public ResponseEntity<SituacaoVeiculo[]> situacoes() {

		SituacaoVeiculo[] situacoes = SituacaoVeiculo.values();
		return ResponseEntity.ok(situacoes);

	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		try {

			Veiculo veiculo = service.buscarPorId(id);

			service.deletar(veiculo);

			return new ResponseEntity(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			System.out.println("Erro " + e.getMessage());
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
