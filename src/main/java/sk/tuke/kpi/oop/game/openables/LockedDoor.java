package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{

    private boolean locked;

    public LockedDoor() {
        super();
        locked = true;
    }

    public void lock() {
        if(!locked) {
            super.close();
        }
    }

    public void unlock(){
        if(locked) {
            locked = false;
            super.open();
        }
    }

    @Override
    public void useWith(Actor actor) {

        if(!isLocked())
            super.useWith(actor);
    }

    boolean isLocked(){
        return locked;
    }
}
