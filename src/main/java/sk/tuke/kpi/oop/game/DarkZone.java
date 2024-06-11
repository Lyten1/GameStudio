package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class DarkZone extends AbstractActor {

    public DarkZone() {
        setAnimation(new Animation("sprites/computer.png", 80,48));
        getAnimation().stop();
        getAnimation().setAlpha(0);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<Actor>(this::interWithPlayer)
        ).scheduleFor(this);
    }

    public void interWithPlayer(){
        Ripley player = getScene().getFirstActorByType(Ripley.class);
        assert player != null;
        if(player.intersects(this) && !player.isInvis()){
            player.setSpeed(1);
        }

    }

}
