package com.pse.kotlinked.domain.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * This is all what ne need to have an immutable Movie class in java
 * With control of not null parameters
 * Builder for readability
 * And equals(), hash(), toString()
 */
public class MovieJava {
  private MovieId id;
  private String title;
  private String description;
  private LocalDate releaseDate;
  private Double note;
  private String posterUrl;

  private MovieJava() {
  }

  public MovieId getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public Double getNote() {
    return note;
  }

  public String getPosterUrl() {
    return posterUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MovieJava movieJava = (MovieJava) o;
    return Objects.equals(id, movieJava.id) && Objects.equals(title, movieJava.title) && Objects.equals(description, movieJava.description)
        && Objects.equals(releaseDate, movieJava.releaseDate) && Objects.equals(note, movieJava.note) && Objects.equals(posterUrl, movieJava.posterUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, releaseDate, note, posterUrl);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", MovieJava.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("description='" + description + "'")
        .add("releaseDate=" + releaseDate)
        .add("note=" + note)
        .add("posterUrl='" + posterUrl + "'")
        .toString();
  }


  public static final class MovieJavaBuilder {

    private MovieId id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Double note;
    private String posterUrl;

    private MovieJavaBuilder() {
    }

    public static MovieJavaBuilder builder() {
      return new MovieJavaBuilder();
    }

    public MovieJavaBuilder withId(MovieId id) {
      this.id = id;
      return this;
    }

    public MovieJavaBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public MovieJavaBuilder withDescription(String description) {
      this.description = description;
      return this;
    }

    public MovieJavaBuilder withReleaseDate(LocalDate releaseDate) {
      this.releaseDate = releaseDate;
      return this;
    }

    public MovieJavaBuilder withNote(Double note) {
      this.note = note;
      return this;
    }

    public MovieJavaBuilder withPosterUrl(String posterUrl) {
      this.posterUrl = posterUrl;
      return this;
    }

    public MovieJava build() {
      requireNonNull(id, "id must be set");
      requireNonNull(id, "id must be set");
      requireNonNull(title, "title must be set");
      requireNonNull(description, "description must be set");
      requireNonNull(note, "description must be set");

      MovieJava movieJava = new MovieJava();
      movieJava.title = this.title;
      movieJava.note = this.note;
      movieJava.description = this.description;
      movieJava.id = this.id;
      movieJava.posterUrl = this.posterUrl;
      movieJava.releaseDate = this.releaseDate;
      return movieJava;
    }
  }
}