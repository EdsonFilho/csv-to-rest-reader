package br.com.eacf.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long  id;
    private String year;
    private String title;
    private String studios;
    private String producer;
    private String winner;

    protected Movie() {}

    public Movie(Long id, String year, String title, String studios, String producer, String winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producer = producer;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, year='%s', title='%s', studios='%s', producer='%s', winner='%s']", id, year, title, studios, producer, winner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
