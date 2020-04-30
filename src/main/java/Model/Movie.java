package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private String description;
    private String trailer;
    private String image;
    private Double price;
    private int sits;
    private List<Date> date;
    private List<String> comments=new ArrayList<>();
    private int winnings;

    public Movie(){
    }

    public Movie(String title, String description, String trailer, String image, Double price, int sits, List<Date> date) {
        this.title = title;
        this.description = description;
        this.trailer = trailer;
        this.image = image;
        this.price = price;
        this.sits = sits;
        this.date = date;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getSits() {
        return sits;
    }

    public void setSits(int sits) {
        this.sits = sits;
    }

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
