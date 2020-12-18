package com.fmourabrasil.booknow.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VeiculoDTO {

	private Long id;
	private String nome;
	private String marca;
	private String placa;
	private Integer ano;
	private String situacao;
	private BigDecimal valorDia;
	
}
