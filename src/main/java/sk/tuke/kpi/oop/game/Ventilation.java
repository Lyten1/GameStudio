package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.items.Usable;


public class Ventilation extends AbstractActor implements Usable<Alive> {

    public Ventilation getNextExit() {
        return nextExit;
    }

    public void setNextExit(Ventilation nextExit) {
        this.nextExit = nextExit;
    }

    private Ventilation nextExit;

    public static final Topic<Ventilation> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilation.class);

    public Ventilation(Ventilation nextExit) {
        setAnimation(new Animation("sprites/ventilator.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
        getAnimation().stop();
        this.nextExit = nextExit;
    }



    @Override
    public void useWith(Alive actor) {
        if(actor == null || nextExit == null) return;
        actor.setPosition(nextExit.getPosX()-32, nextExit.getPosY());
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
