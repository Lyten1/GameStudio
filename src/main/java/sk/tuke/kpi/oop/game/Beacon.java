package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.items.Usable;

public class Beacon extends AbstractActor implements Usable<Alive> {

    public static final Topic<Beacon> SEND_ALARM = Topic.create("send alarm", Beacon.class);

    public Beacon() {
        setAnimation(new Animation("sprites/beacon.png", 48,32));
    }

    @Override
    public void useWith(Alive actor) {
        actor.getScene().getMessageBus().publish(SEND_ALARM,this);
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }


}
