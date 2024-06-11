package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Invisibility extends AbstractActor implements Usable<Alive> {

    public Invisibility() {
        setAnimation(new Animation("sprites/invisibility.png", 16, 16));
    }


    @Override
    public void useWith(Alive actor) {
        if (actor instanceof Ripley) {
            ((Ripley) actor).invisibility();
            Scene scene = getScene();
            assert scene != null;
            scene.removeActor(this);
        }
    }


    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
