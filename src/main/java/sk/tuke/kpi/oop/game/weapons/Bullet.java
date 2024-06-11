package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;

public class Bullet extends AbstractActor implements Fireable {

    private int speed;

    public Bullet() {
        setAnimation(new Animation("sprites/bullet.png", 16,16));
        speed = 4;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void collidedWithWall() {
        getScene().removeActor(this);
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction != null){
            this.getAnimation().setRotation(direction.getAngle());
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<Actor>(this::shoot)
        ).scheduleFor(this);
    }

    public void shoot(){
        Scene scene = getScene();
        assert scene != null;
        List<Actor> interAlive = scene.getActors();
        for (Actor actor : interAlive) {
            if (this.intersects(actor) && (actor instanceof Alive) && !(actor instanceof Ripley)) {
                ((Alive) actor).getHealth().drain(15);
                collidedWithWall();
            }
        }


    }

}
