package Actors;

import Actor.Actor;

import java.util.Formatter;
import java.util.LinkedList;

public class Actors {
    LinkedList<Actor> actors;

    public Actors() {
        this.actors = new LinkedList<Actor>();
    }

    public Actors(LinkedList<Actor> actors) {
        this.actors = actors;
    }

    public void add(Actor actor){
        this.actors.add(actor);
    }

    public void set(int index, Actor actor){
        this.actors.add(index, actor);
        this.actors.remove(index + 1);
    }

    public void delete(int index){this.actors.remove(index);}

    public Actor get(int index){
        return this.actors.get(index);
    }

    public LinkedList<Actor> getList(){
        return this.actors;
    }

    public void clear(){
        this.actors.clear();
    }

    public int size(){
        return this.actors.size();
    }

    @Override
    public String toString(){
        Formatter info = new Formatter();
        for(Actor actor : getList()){
            info.format("Name: %s \nYear: %s \n", actor.getName(), actor.getYear());
        }
        return info.toString();
    }
}
