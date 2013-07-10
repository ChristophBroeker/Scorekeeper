package com.github.scorekeeper.persistence.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class SuggestedGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	@CollectionTable(name = "SuggestedGame_TeamA")
	private List<Player> teamA;

	@ManyToMany
	@CollectionTable(name = "SuggestedGame_TeamB")
	private List<Player> teamB;

	private Integer teamAScore;

	private Integer teamBScore;

	@Enumerated(EnumType.STRING)
	private ResultType result;

	private Calendar playedDate;

	private BigDecimal quality;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Player> getTeamA() {
		return teamA;
	}

	public void setTeamA(List<Player> teamA) {
		this.teamA = teamA;
	}

	public List<Player> getTeamB() {
		return teamB;
	}

	public void setTeamB(List<Player> teamB) {
		this.teamB = teamB;
	}

	public ResultType getResult() {
		return result;
	}

	public void setResult(ResultType result) {
		this.result = result;
	}

	public Calendar getPlayedDate() {
		return playedDate;
	}

	public void setPlayedDate(Calendar playedDate) {
		this.playedDate = playedDate;
	}

	public Integer getTeamAScore() {
		return teamAScore;
	}

	public void setTeamAScore(Integer teamAScore) {
		this.teamAScore = teamAScore;
	}

	public Integer getTeamBScore() {
		return teamBScore;
	}

	public void setTeamBScore(Integer teamBScore) {
		this.teamBScore = teamBScore;
	}

	public BigDecimal getQuality() {
		return quality;
	}

	public void setQuality(BigDecimal quality) {
		this.quality = quality;
	}

}
