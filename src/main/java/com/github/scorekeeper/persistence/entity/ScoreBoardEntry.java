/*
Creation date: 13.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.persistence.entity;

import java.math.BigDecimal;

public class ScoreBoardEntry {
	private String playerName;
	private BigDecimal currentMean;
	private BigDecimal playedGames;
	private BigDecimal wonGames;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public BigDecimal getCurrentMean() {
		return currentMean;
	}

	public void setCurrentMean(BigDecimal currentMean) {
		this.currentMean = currentMean;
	}

	public BigDecimal getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(BigDecimal playedGames) {
		this.playedGames = playedGames;
	}

	public BigDecimal getWonGames() {
		return wonGames;
	}

	public void setWonGames(BigDecimal wonGames) {
		this.wonGames = wonGames;
	}
}
