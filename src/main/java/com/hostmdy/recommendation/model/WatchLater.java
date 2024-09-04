package com.hostmdy.recommendation.model;

public class WatchLater {
	
	private Long id;
	private Long userId;
	private Long movieId;
	
	public WatchLater() {}

	public WatchLater(Long id, Long userId, Long movieId) {
		super();
		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
	}

	public WatchLater(Long userId, Long movieId) {
		super();
		this.userId = userId;
		this.movieId = movieId;
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

	@Override
	public String toString() {
		return "WatchLater [id=" + id + ", userId=" + userId + ", movieId=" + movieId + "]";
	}
	
	

}
