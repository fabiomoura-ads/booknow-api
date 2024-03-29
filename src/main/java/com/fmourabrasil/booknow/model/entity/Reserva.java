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

import com.fmourabrasil.booknow.exceptions.RegraNegocioException;
import com.fmourabrasil.booknow.model.enums.SituacaoReserva;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reserva")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo veiculo;

	@Column(name = "valor_dia")
	private BigDecimal valorDia;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@Column(name = "data_inicio")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataInicio;

	@Column(name = "data_fim")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataFim;

	@Column(name = "situacao")
	@Enumerated(value = EnumType.STRING)
	private SituacaoReserva situacao;

	public Reserva calculaValorTotalDaReserva() {

		if (this.dataInicio == null) {
			throw new RegraNegocioException("A data início da reserva deve ser informado antes da data fim.");
		}

		Integer qtdDiasReserva = dataFim.compareTo(this.dataInicio) + 1;

		if (dataFim.compareTo(this.dataInicio) < 0) {
			throw new RegraNegocioException("A data fim da reserva não pode ser menor que a data início.");
		}

		this.valorDia = this.veiculo.getValorDia();

		this.valorTotal = this.valorDia.multiply(BigDecimal.valueOf(qtdDiasReserva));

		return this;
	}
	
	public Reserva validaReserva() {
		if (this.getVeiculo() == null || this.getVeiculo().getId() == null) {
			throw new RegraNegocioException("O veículo não foi informado para o cadastro da reserva.");
		}

		if (this.getUsuario() == null || this.getUsuario().getId() == null) {
			throw new RegraNegocioException("O usuário não foi informado para o cadastro da reserva.");
		}

		if (this.getDataInicio() == null) {
			throw new RegraNegocioException("A data início da reserva deve ser informada.");
		}

		if (this.getDataFim() == null) {
			throw new RegraNegocioException("A data fim da reserva deve ser informada.");
		}

		if (this.getDataFim().compareTo(this.getDataInicio()) < 0) {
			throw new RegraNegocioException("A data fim da reserva não pode ser menor que a data de início.");
		}	
		
		return this;

	}

	public void preparaNovaReserva() {
		this.validaReserva().calculaValorTotalDaReserva().setSituacao(SituacaoReserva.PENDENTE);
		
	}	


}
