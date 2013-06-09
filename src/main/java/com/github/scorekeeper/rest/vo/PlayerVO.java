package com.github.scorekeeper.rest.vo;

import com.github.scorekeeper.persistence.entity.Player;

public class PlayerVO {
	private Long id;
	private String name;
	private ScoreVO currentScore;

	public PlayerVO() {
	}

	public PlayerVO(Player player) {
		this.id = player.getId();
		this.name = player.getName();
		this.currentScore = new ScoreVO(player.getScoreHistory().get(player.getScoreHistory().size() - 1));
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

}