/*
Creation date: 21.01.2014
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2014
*/

package com.github.scorekeeper.rest.vo;

import java.util.List;

public class CalculatedGameVO {

	private double gameQuality;
	private List<PlayerVO> teamA;
	private List<PlayerVO> teamB;

	public double getGameQuality() {
		return gameQuality;
	}

	public void setGameQuality(double gameQuality) {
		this.gameQuality = gameQuality;
	}

	public List<PlayerVO> getTeamA() {
		return teamA;
	}

	public void setTeamA(List<PlayerVO> teamA) {
		this.teamA = teamA;
	}

	public List<PlayerVO> getTeamB() {
		return teamB;
	}

	public void setTeamB(List<PlayerVO> teamB) {
		this.teamB = teamB;
	}
}
