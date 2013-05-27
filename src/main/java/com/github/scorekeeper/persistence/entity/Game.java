package com.github.scorekeeper.persistence.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private List<Player> teamA;

	@OneToMany
	private List<Player> teamB;

	@Enumerated(EnumType.STRING)
	private ResultType result;

	private Calendar playedDate;

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

}
