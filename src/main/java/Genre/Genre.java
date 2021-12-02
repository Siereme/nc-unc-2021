package Genre;

import java.util.UUID;

public class Genre {

    private String id;
    private String tittle;

    Genre(String newGener){
        this.id = UUID.randomUUID().toString();
        tittle = newGener;
    }

    public String getId(){return this.id;}

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

}
