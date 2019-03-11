package fr.isen.java2.db.entities;

import java.time.LocalDate;

public class Film {

	private Integer id;
	private String title;
	private LocalDate releaseDate;
	private Genre genre;
	private Integer duration;
	private String director;
	private String summary;

	public Film() {
	}

	public Film(Integer id, String title, LocalDate releaseDate, Genre genre, Integer duration, String director,
			String summary) {
		super();
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.genre = genre;
		this.duration = duration;
		this.director = director;
		this.summary = summary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
