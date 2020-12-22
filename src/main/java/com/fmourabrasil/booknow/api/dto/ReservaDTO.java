package com.fmourabrasil.booknow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservaDTO {
	private Long idUsuario;
	private Long idVeiculo;
	private String dataInicio;
	private String dataFim;
}
