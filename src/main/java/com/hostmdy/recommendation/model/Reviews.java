package com.hostmdy.recommendation.model;

import java.time.LocalDate;

public class Reviews {
	
	private Long id;
	private String title;
	private String reviewtext;
	private Integer rating;
	private LocalDate createdAt;
	private Long userId;
	private Long movieId;
	private String username;
	
	public Reviews() {}

	public Reviews(Long id, String title, String reviewtext, Integer rating, LocalDate createdAt,Long userId, Long movieId,
			 String username) {
		super();
		this.id = id;
		this.title = title;
		this.reviewtext = reviewtext;
		this.rating = rating;
		this.createdAt = createdAt;
		this.userId = userId;
		this.movieId = movieId;
		this.username = username;
	}

	public Reviews(String title, String reviewtext, Integer rating, LocalDate createdAt, Long userId, Long movieId,
			String username) {
		super();
		this.title = title;
		this.reviewtext = reviewtext;
		this.rating = rating;
		this.createdAt = LocalDate.now();
		this.userId = userId;
		this.movieId = movieId;
		this.username = username;
	}

	public Reviews(Long id, String title, String reviewtext, Integer rating, Long userId, Long movieId) {
		super();
		this.id = id;
		this.title = title;
		this.reviewtext = reviewtext;
		this.rating = rating;
		this.userId = userId;
		this.movieId = movieId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReviewtext() {
		return reviewtext;
	}

	public void setReviewtext(String reviewtext) {
		this.reviewtext = reviewtext;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Reviews [id=" + id + ", title=" + title + ", reviewtext=" + reviewtext + ", rating=" + rating
				+ ", createdAt=" + createdAt + ", movieId=" + movieId + ", userId=" + userId + ", username=" + username
				+ "]";
	}

	
	
}
