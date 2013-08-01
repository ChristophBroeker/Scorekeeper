/*
Creation date: 13.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.rest.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ScoreBoardEntryVO {

	private String playerName;
	private BigDecimal currentMean;
	private BigDecimal standarddeviation;
	private BigInteger playedGames;
	private BigInteger wonGames;
	private BigInteger lostGames;

	public ScoreBoardEntryVO(Object[] data) {
		setPlayerName((String) data[0]);
		setCurrentMean((BigDecimal) data[1]);
		setStandarddeviation((BigDecimal) data[2]);
		setPlayedGames((BigInteger) data[3]);
		setWonGames((BigInteger) data[4]);
		setLostGames(getPlayedGames().subtract(getWonGames()));
	}

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

	public BigInteger getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(BigInteger playedGames) {
		this.playedGames = playedGames;
	}

	public BigInteger getWonGames() {
		return wonGames;
	}

	public void setWonGames(BigInteger wonGames) {
		this.wonGames = wonGames;
	}

	public BigInteger getLostGames() {
		return lostGames;
	}

	public void setLostGames(BigInteger lostGames) {
		this.lostGames = lostGames;
	}

	public BigDecimal getStandarddeviation() {
		return standarddeviation;
	}

	public void setStandarddeviation(BigDecimal standarddeviation) {
		this.standarddeviation = standarddeviation;
	}

}
