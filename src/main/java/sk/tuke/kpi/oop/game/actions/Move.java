package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;


public class Move<A extends Movable> implements Action<A> {

    private A actor;

    private boolean isDone;

    private Direction direction;
    private float duration;

    private boolean firstTime;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        isDone = false;
        firstTime = true;
    }

    public Move(Direction direction) {
        this.direction = direction;
        this.duration = 0f;
        isDone = false;
        firstTime = true;
    }

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null) return;
        actor = getActor();
        Scene scene = Objects.requireNonNull(getActor().getScene());
        if(isDone) return;
        if(duration - deltaTime <= 0){
            stop();
            duration -= deltaTime;
            return;
        }
        if (firstTime) {
            actor.startedMoving(direction);
            firstTime = false;
        }
        if (direction != null) {
            actor.setPosition((actor.getPosX() + (actor.getSpeed() * direction.getDx())), (actor.getPosY() + (actor.getSpeed() * direction.getDy())));
            if (scene.getMap().intersectsWithWall(actor) ) {
                actor.setPosition((actor.getPosX() - (actor.getSpeed() * direction.getDx())), (actor.getPosY() - (actor.getSpeed() * direction.getDy())));
                actor.collidedWithWall();
            }
        }
        duration -= deltaTime;


    }

    public void reset() {
        isDone = false;
        duration = 0;
    }

    public void stop() {
        if (actor != null) {
            actor.stoppedMoving();
            isDone = true;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
