package Model;

import java.util.Objects;

public class Date {
    private String day;
    private String hour;
    private int availableSits;
    private int occupiedSits;

    public Date() {
    }

    public Date(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return Objects.equals(day, date.day) &&
                Objects.equals(hour, date.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getAvaliableSits() {
        return availableSits;
    }

    public void setAvaliableSits(int availableSits) {
        this.availableSits = availableSits;
    }

    public int getOccupiedSits() {
        return occupiedSits;
    }

    public void setOccupiedSits(int occupiedSits) {
        this.occupiedSits = occupiedSits;
    }


}
