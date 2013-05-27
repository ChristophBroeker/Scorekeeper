package com.github.scorekeeper.persistence.entity;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Score {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	Calendar captured;

	BigDecimal mean;

	BigDecimal standardDeviation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCaptured() {
		return captured;
	}

	public void setCaptured(Calendar captured) {
		this.captured = captured;
	}

	public BigDecimal getMean() {
		return mean;
	}

	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}

	public BigDecimal getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(BigDecimal standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

}
