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
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Alive, Enemy, Movable {

    private Health health;
    private Behaviour<? super Alien> behaviour;


    public Alien() {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(100);
        health.onFatigued(() ->
            Objects.requireNonNull(getScene()).removeActor(this)
        );

    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(healthValue, 100);
        this.behaviour = behaviour;
        health.onFatigued(() ->
            Objects.requireNonNull(getScene()).removeActor(this)
        );
    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (behaviour != null) {
            behaviour.setUp(this);
            //new Invoke<>(() -> behaviour.setUp(this)).scheduleFor(this);
        }
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
            if (actor.intersects(this) && actor instanceof Alive && (actor.getClass() != Alien.class) && (actor.getClass() != MySmartAlien.class) && (actor.getClass() != MotherAlien.class)) {
                ((Alive) actor).getHealth().drain(5);
            }

        }
    }



    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
    }

    @Override
    public Health getHealth() {
        return health;
    }


}
