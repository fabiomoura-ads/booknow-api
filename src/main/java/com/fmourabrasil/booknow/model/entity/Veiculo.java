package com.fmourabrasil.booknow.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmourabrasil.booknow.model.enums.MarcaVeiculo;
import com.fmourabrasil.booknow.model.enums.SituacaoVeiculo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "marca")
	@Enumerated(value = EnumType.STRING)	
	private MarcaVeiculo marca;

	@Column(name = "placa")
	private String placa;

	@Column(name = "ano")
	private Integer ano;

	@Column(name = "imagem")
	private String imagem;
	
	@Column(name = "situacao")
	@Enumerated(value = EnumType.STRING)
	private SituacaoVeiculo situacao;

	@Column(name = "valor_dia")
	private BigDecimal valorDia;
	
	@JsonFormat(pattern = "dd/MM/yyyy")	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;	

}
