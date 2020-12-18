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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fmourabrasil.booknow.model.enums.SituacaoReserva;

import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usaurio")
	private Usuario usaurio;

	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo veiculo;

	@Column(name = "valor_dia")
	private BigDecimal valorDia;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "data_inicio")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)	
	private LocalDate dataInicio;

	@Column(name = "data_fim")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)	
	private LocalDate dataFim;
	
	@Column(name = "situacao")
	@Enumerated(value = EnumType.STRING)	
	private SituacaoReserva situacao;

	
}
