package Model;

import Services.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private String description;
    private String trailer;
    private String image;
    private  double price;
    private List<Date> date=new ArrayList<>();
    private List<String> comments=new ArrayList<>();
    private double winnings;
    private static double winningsTotal;

    public Movie(){
    }

    public Movie(String title, String description, String trailer, String image,  double price, List<Date> date) {
        this.title = title;
        this.description = description;
        this.trailer = trailer;
        this.image = image;
        this.price = price;
        this.date = date;
        this.winnings=0;
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

    public  double getPrice() {
        return price;
    }

    public void setPrice( double price) {
        this.price = price;
    }

    public List<Date> getDate() {
        return date;
    }

    public void setDate(List<Date> date) {
        this.date = date;
    }

    public void addDate(Date date){
        this.date.add(date);
    }

    public boolean deleteDate(String day, String hour){
        Date dateAux= new Date();
        boolean ok=false;
        for(Date i : date)
            if((hour.equals(i.getHour())) && (day.equals(i.getDay())))
            {
                dateAux=i;
                ok=true;
             }
         this.date.remove(dateAux);
            return ok;
    }

    public void increaseWinnings(int nr){
       winnings= winnings + nr*price;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public double getWinnings() {

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

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", trailer='" + trailer + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", comments=" + comments +
                ", winnings=" + winnings +
                '}';
    }
}
