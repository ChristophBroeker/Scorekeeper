package com.github.scorekeeper.rest.vo;

import java.math.BigDecimal;
import java.util.Calendar;

import com.github.scorekeeper.persistence.entity.Score;

public class ScoreVO {
	private Long id;

	private Calendar captured;

	private BigDecimal mean;

	private BigDecimal standardDeviation;

	public ScoreVO() {
	}

	public ScoreVO(Score score) {
		this.captured = (Calendar) score.getCaptured().clone();
		this.mean = score.getMean();
		this.standardDeviation = score.getStandardDeviation();
		this.id = score.getId();
	}

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
