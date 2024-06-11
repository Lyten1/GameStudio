package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class MySmartAlien extends Alien{

    public enum Orientation  {
        VERTICAL,
        HORIZONTAL
    }

   private Orientation orientation;
    private float timeDistance;
    private float waitDuration;



    public MySmartAlien(int healthValue, Behaviour<? super Alien> behaviour,
                        Orientation orientation, float timeDistance,
                        float waitDuration) {
        super(healthValue, behaviour);
        this.orientation = orientation;
        this.timeDistance = timeDistance;
        this.waitDuration = waitDuration;
    }



    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public float getTimeDistance() {
        return timeDistance;
    }

    public void setTimeDistance(float timeDistance) {
        this.timeDistance = timeDistance;
    }

    public float getWaitDuration() {
        return waitDuration;
    }

    public void setWaitDuration(float waitDuration) {
        this.waitDuration = waitDuration;
    }


    public void contactMe() {
        Scene scene = getScene();
        assert scene != null;
        List<Actor> interAlive = scene.getActors();
        for (Actor actor : interAlive) {
            if (actor.intersects(this) && actor instanceof Alive && (actor.getClass() != Alien.class) && (actor.getClass() != MySmartAlien.class) && (actor.getClass() != MotherAlien.class)) {
                if(!(actor instanceof Ripley)){
                    return;
                }
                if(!(((Ripley) actor).isInvis()))
                    ((Alive) actor).getHealth().drain(5);
            }

        }
    }
}
