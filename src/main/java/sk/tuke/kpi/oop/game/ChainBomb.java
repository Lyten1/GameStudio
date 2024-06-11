package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;


import java.awt.geom.Ellipse2D;

import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class ChainBomb extends TimeBomb {


    public ChainBomb(float time) {
        super(time);
    }

    @Override
    public void activate() {
        if (!isActivated() ) {
            super.activate();
            new ActionSequence<>(
                new Wait<>(this.getSeconds()),
                new Invoke<>(this::activateOthers)
            ).scheduleFor(this);
        }
    }

    private void activateOthers(){
        Ellipse2D.Float blastRadius = new Ellipse2D.Float(
            this.getPosX() - 42,
            this.getPosY() - 42,
            100,
            100
        );
        for (Actor actor : Objects.requireNonNull(getScene()).getActors()) {
            if (actor instanceof ChainBomb && actor != this && blastRadius.intersects(getBounds(actor))) {
                    ((ChainBomb) actor).activate();
            }
        }
    }

    public Rectangle2D getBounds(Actor actor) {
        return new Rectangle2D.Float(
            actor.getPosX(),
            actor.getPosY(),
            actor.getWidth(),
            actor.getHeight()
        );
    }


}
