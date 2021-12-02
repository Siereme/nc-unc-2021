package model.Directors;

import model.Director.Director;
import java.util.Formatter;
import java.util.LinkedList;

public class Directors {
    LinkedList<Director> directors;

    public Directors() {
        this.directors = new LinkedList<Director>();
    }

    public Directors(LinkedList<Director> directors) {
        this.directors = directors;
    }

    public void add(Director director){
        this.directors.add(director);
    }

    public void set(int index, Director director){
        this.directors.add(index, director);
        this.directors.remove(index + 1);
    }

    public void delete(int index){this.directors.remove(index);}

    public Director get(int index){
        return this.directors.get(index);
    }

    public LinkedList<Director> getList(){
        return this.directors;
    }

    public void clear(){
        this.directors.clear();
    }

    public int size(){
        return this.directors.size();
    }

    @Override
    public String toString(){
        Formatter info = new Formatter();
        for(Director director : getList()){
            info.format("Name: %s \n Year: %s \n", director.getName(), director.getYear());
        }
        return info.toString();
    }
}
