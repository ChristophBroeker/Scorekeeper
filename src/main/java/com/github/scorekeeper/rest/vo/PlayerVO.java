package com.github.scorekeeper.rest.vo;

import java.math.BigDecimal;

import com.github.scorekeeper.persistence.entity.Player;

public class PlayerVO implements Comparable<PlayerVO> {
	private Long id;
	private String name;
	private ScoreVO currentScore;

	public PlayerVO() {
	}

	public PlayerVO(Player player) {
		this.id = player.getId();
		this.name = player.getName();
		try {
			this.currentScore = new ScoreVO(player.getScoreHistory().get(player.getScoreHistory().size() - 1));
		} catch (Exception e) {
			this.currentScore = new ScoreVO();
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ScoreVO getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(ScoreVO currentScore) {
		this.currentScore = currentScore;
	}

	@Override
	public int compareTo(PlayerVO o) {
		// TODO Auto-generated method stub
		BigDecimal diff = o.currentScore.getMean().subtract(getCurrentScore().getMean());
		return diff.multiply(new BigDecimal(100)).intValue();
	}
}
