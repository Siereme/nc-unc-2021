package Film;

import java.util.Date;

// TODO добавить поля жанры, режисеры, актеры
public class Film {

    private String tittle;
    private Date date;

    public Film(String newTittle, Date newDate) {
        tittle = newTittle;
        date = newDate;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Tittle: ").append(tittle).append("\n").append("Date of release: ").append(date);
        return new String(sb);
    }

}
