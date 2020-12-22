package com.fmourabrasil.booknow.api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmourabrasil.booknow.api.dto.ReservaDTO;
import com.fmourabrasil.booknow.model.entity.Reserva;
import com.fmourabrasil.booknow.model.entity.Usuario;
import com.fmourabrasil.booknow.model.entity.Veiculo;
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

	private Reserva converteDtoParaModelo(ReservaDTO dto) {
		Usuario usuario = usuarioService.obterPorId(dto.getIdUsuario());
		Veiculo veiculo = veiculoService.buscarPorId(dto.getIdVeiculo());

		Reserva reserva = Reserva.builder().veiculo(veiculo).usuario(usuario)
				.dataInicio(Auxiliares.converteDataStringParaLocalDate(dto.getDataInicio()))
				.dataFim(Auxiliares.converteDataStringParaLocalDate(dto.getDataFim())).build();

		return reserva;
	}
}
