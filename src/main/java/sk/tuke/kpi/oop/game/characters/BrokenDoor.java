package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.List;

public class BrokenDoor extends AbstractActor {

    public BrokenDoor() {
        setAnimation(new Animation("sprites/empty.png"));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::contactMe),
                new Wait<>(0.1f)
            )).scheduleFor(this);
    }

    public void contactMe() {
        Scene scene = getScene();
        assert scene != null;
        List<Actor> interAlive = scene.getActors();
        for (Actor actor : interAlive) {
            if (actor instanceof Ripley) {
                    if (actor.intersects(this)) {
                        ((Ripley) actor).getHealth().drain(5);
                        ((Ripley) actor).setSpeed(1);
                    } else
                        ((Ripley) actor).setSpeed(2);
            }

        }
    }
}
