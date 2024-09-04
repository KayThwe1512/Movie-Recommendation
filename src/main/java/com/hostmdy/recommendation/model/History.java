package com.hostmdy.recommendation.model;

import java.time.LocalDate;

public class History {
	
	private Long id;
	private Long userId;
	private Long movieId;
	private LocalDate statusTime;
	
	public History() {}

	public History(Long id, Long userId, Long movieId, LocalDate statusTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
		this.statusTime = statusTime;
	}

	public History(Long userId, Long movieId, LocalDate statusTime) {
		super();
		this.userId = userId;
		this.movieId = movieId;
		this.statusTime = statusTime;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public LocalDate getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(LocalDate statusTime) {
		this.statusTime = statusTime;
	}

	@Override
	public String toString() {
		return "History [id=" + id + ", userId=" + userId + ", movieId=" + movieId +  ", statusTime=" + statusTime + "]";
	}
	
	

}
