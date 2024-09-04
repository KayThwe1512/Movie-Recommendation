package com.hostmdy.recommendation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movie {

	private Long id;
	private String title;
	private String synopsis;
	private String casting;
	private String director;
	private String genre;
	private LocalDate releaseDate;
	private Integer duration_min;
	private String poster;
	private Long userId;
	private Double averageRating;
	
	public Movie() {
		// TODO Auto-generated constructor stub
	}

	//retrieve
	public Movie(Long id, String title, String synopsis, String casting, String director, String genre,
			LocalDate releaseDate, Integer duration_min, String poster, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.synopsis = synopsis;
		this.casting = casting;
		this.director = director;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.duration_min = duration_min;
		this.poster = poster;
		this.userId = userId;
	}
	
	public Movie(Long id, String title, String synopsis, String casting, String director, String genre,
			LocalDate releaseDate, Integer duration_min, String poster, Long userId,
			Double averageRating) {
		super();
		this.id = id;
		this.title = title;
		this.synopsis = synopsis;
		this.casting = casting;
		this.director = director;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.duration_min = duration_min;
		this.poster = poster;
		this.userId = userId;
		this.averageRating = averageRating;
	}
	
	public Movie(String title, String synopsis, String casting, String director, String genre,
			LocalDate releaseDate, Integer duration_min, String poster, Long userId,
			Double averageRating) {
		super();
		this.title = title;
		this.synopsis = synopsis;
		this.casting = casting;
		this.director = director;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.duration_min = duration_min;
		this.poster = poster;
		this.userId = userId;
		this.averageRating = averageRating;
	}


	//insert
	public Movie(String title, String synopsis, String casting, String director, String genre,
			LocalDate releaseDate, Integer duration_min, String poster, Long userId) {
		super();
		this.title = title;
		this.synopsis = synopsis;
		this.casting = casting;
		this.director = director;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.duration_min = duration_min;
		this.poster = poster;
		this.userId = userId;
	}

	//update
	public Movie(Long id, String title, String synopsis, String casting, String director, String genre,
			LocalDate releaseDate, Integer duration_min, String poster) {
		super();
		this.id = id;
		this.title = title;
		this.synopsis = synopsis;
		this.casting = casting;
		this.director = director;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.duration_min = duration_min;
		this.poster = poster;
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

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getCasting() {
		return casting;
	}

	public void setCasting(String casting) {
		this.casting = casting;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getDuration_min() {
		return duration_min;
	}

	public void setDuration_min(Integer duration_min) {
		this.duration_min = duration_min;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Double getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", synopsis=" + synopsis + ", casting=" + casting
				+ ", director=" + director + ", genre=" + genre + ", releaseDate=" + releaseDate + ", duration_min="
				+ duration_min + ", poster=" + poster + ", userId=" + userId + ", averageRating="+averageRating+ "]";
	}
	
	
	
}
