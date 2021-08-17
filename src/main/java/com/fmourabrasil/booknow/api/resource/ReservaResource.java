package com.fmourabrasil.booknow.api.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fmourabrasil.booknow.api.dto.ReservaDTO;
import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;
import com.fmourabrasil.booknow.model.enums.SituacaoReserva;
import com.fmourabrasil.booknow.service.ReservaService;
import com.fmourabrasil.booknow.service.UsuarioService;
import com.fmourabrasil.booknow.service.VeiculoService;
import com.fmourabrasil.booknow.util.Auxiliares;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReservaResource {

	private final ReservaService service;
	private final UsuarioService usuarioService;
	private final VeiculoService veiculoService;

	@GetMapping("/minhas-reservas")
	public ResponseEntity minhasReservas(@RequestParam("usuario") Long idUsuario) {
	
		try {
						
			Usuario usuario = usuarioService.obterPorId(idUsuario);
			
			List<Reserva> reservas = service.retornaReservasDoUsuario(usuario);

			return ResponseEntity.ok(reservas);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}
		
	@PostMapping
	public ResponseEntity novaReserva(@RequestBody ReservaDTO dto) {

		Reserva reserva = converteDtoParaModelo(dto);

		try {

			Reserva novaReserva = service.novaReserva(reserva);

			return ResponseEntity.ok(novaReserva);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}

	@PutMapping("/{id}/atualizar-situacao")
	public ResponseEntity atualizarSituacao(@PathVariable("id") Long id, @RequestBody ReservaDTO dto) {

		Reserva reserva = service.buscarPorId(id);
		System.out.println("Reserva: " +reserva.getSituacao().name());
		
		try {
			
			service.atualizaSituacao(reserva, SituacaoReserva.valueOf(dto.getSituacao()) );
			
			return ResponseEntity.ok(reserva);

		} catch (Exception e) {

			return ResponseEntity.badRequest().body(e.getMessage());

		}

	}
	
	private Reserva converteDtoParaModelo(ReservaDTO dto) {
		Usuario usuario = usuarioService.obterPorId(dto.getIdUsuario());
		Veiculo veiculo = veiculoService.buscarPorId(dto.getIdVeiculo()).orElseThrow(() -> new RegraNegocioException("Veículo não localizado pelo ID informado!"));

		Reserva reserva = Reserva.builder().veiculo(veiculo).usuario(usuario)
				.dataInicio(Auxiliares.converteDataStringParaLocalDate(dto.getDataInicio()))
				.dataFim(Auxiliares.converteDataStringParaLocalDate(dto.getDataFim())).build();

		return reserva;
	}
}
