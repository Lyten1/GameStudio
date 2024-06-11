package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Invisibility;
import sk.tuke.kpi.oop.game.items.Usable;



public class Locker extends AbstractActor implements Usable<Ripley> {
    private boolean isUsed;
    public Locker() {
        Animation anim = new Animation("sprites/locker.png", 16,16);
        setAnimation(anim);
        isUsed = false;
    }


    @Override
    public void useWith(Ripley actor) {
        if (!isUsed) {
            getScene().addActor(new Invisibility(), getPosX()+16, getPosY());
            getScene().removeActor(this);
            isUsed=true;
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
