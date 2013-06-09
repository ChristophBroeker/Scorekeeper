package com.github.scorekeeper.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderColumn
	private List<Score> scoreHistory = new ArrayList<>();

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

	public List<Score> getScoreHistory() {
		return scoreHistory;
	}

	public void setScoreHistory(List<Score> scoreHistory) {
		this.scoreHistory = scoreHistory;
	}

	public void addScore(Score score) {
		if (this.scoreHistory == null) {
			this.scoreHistory = new ArrayList<>();
		}

		this.scoreHistory.add(score);
	}

	public Score getLatestScore() {
		if (scoreHistory.isEmpty()) {
			return null;
		}

		return scoreHistory.get(scoreHistory.size() - 1);
	}

}
