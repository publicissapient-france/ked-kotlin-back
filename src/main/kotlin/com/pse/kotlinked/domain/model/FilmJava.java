package com.pse.kotlinked.domain.model;

import java.util.Objects;

/*
java code to generate to have equivalent code to Java Kotlin data class
 */
public class FilmJava {

  private String id;
  private String title;
  private String description;

  private FilmJava(String id, String description, String title) {
    this.title = title;
    this.description = description;
    this.id = id;
  }

  @Override
  public String toString() {
    return "FilmJava{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilmJava filmJava = (FilmJava) o;
    return id.equals(filmJava.id) && Objects.equals(title, filmJava.title) && Objects
        .equals(description, filmJava.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String id;
    private String title;
    private String description;

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public FilmJava build() {
      return new FilmJava(id, description, title);
    }
  }
}
