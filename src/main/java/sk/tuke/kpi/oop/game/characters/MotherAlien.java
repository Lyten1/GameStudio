package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.Objects;

public class MotherAlien extends Alien{

    public MotherAlien() {
        super();
        Health health;
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(200);
        health.onFatigued(() ->
            Objects.requireNonNull(getScene()).removeActor(this)
        );
    }

    public MotherAlien(int healthValue, Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        Health health;
        setAnimation(new Animation("sprites/mother.png", 112, 162, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health = new Health(200);
        health.onFatigued(() ->
            Objects.requireNonNull(getScene()).removeActor(this)
        );
    }
}
