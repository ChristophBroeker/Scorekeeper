package com.github.scorekeeper.rest.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.scorekeeper.persistence.entity.Game;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.persistence.entity.ResultType;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class GameVO {
	private List<Long> teamA;
	private List<Long> teamB;
	private Integer teamAScore;
	private Integer teamBScore;
	private ResultType result;
	private Calendar playedDate;
	private BigDecimal quality;

	public GameVO() {
	}

	public GameVO(Game gameEntity) {
		this.setPlayedDate((Calendar) gameEntity.getPlayedDate().clone());
		this.setResult(gameEntity.getResult());
		this.setTeamA(convert(gameEntity.getTeamA()));
		this.setTeamB(convert(gameEntity.getTeamB()));
		this.setTeamAScore(gameEntity.getTeamAScore());
		this.setTeamBScore(gameEntity.getTeamBScore());
		this.setQuality(gameEntity.getQuality());
	}

	private ArrayList<Long> convert(List<Player> players) {
		return Lists.newArrayList(Lists.transform(players, new Function<Player, Long>() {
			@Override
			public Long apply(Player playerEntity) {
				return playerEntity.getId();
			}

		}));
	}

	public List<Long> getTeamA() {
		return teamA;
	}

	public void setTeamA(List<Long> teamA) {
		this.teamA = teamA;
	}

	public List<Long> getTeamB() {
		return teamB;
	}

	public void setTeamB(List<Long> teamB) {
		this.teamB = teamB;
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

	public BigDecimal getQuality() {
		return quality;
	}

	public void setQuality(BigDecimal quality) {
		this.quality = quality;
	}

}
