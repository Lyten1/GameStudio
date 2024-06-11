package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private Animation nonActive = new Animation("sprites/bomb.png", 16, 16);
    private Animation activeAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f, Animation.PlayMode.LOOP);
    private Animation explodeAnimation = new Animation("sprites/small_explosion.png", 16, 16, 0.1f,  Animation.PlayMode.ONCE);

    private float frames = 0.1f;
    private int framesCount = 8;
    private float removeTime = frames * framesCount;


    public float getRemoveTime() {
        return removeTime;
    }


    public float getSeconds() {
        return seconds;
    }

    private float seconds;
    private boolean isActivated;

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public TimeBomb(float seconds) {
        this.seconds = seconds;
        this.isActivated = false;
        setAnimation(nonActive);
    }

    public void setActiveAnimation() {
        setAnimation(activeAnimation);
    }

    public void setExplodeAnimation() {
        setAnimation(explodeAnimation);
    }

    public boolean isActivated() {
        return this.isActivated;
    }

    public void activate() {
        if (isActivated())
            return;
        Scene scene = getScene();
        if (scene == null)
            return;
        this.isActivated = true;
        setActiveAnimation();
        new ActionSequence<>(
            new Wait<>(this.seconds),
            new Invoke<>(this::setExplodeAnimation),
            new Wait<>(removeTime),
            new Invoke<>(scene::removeActor)
        ).scheduleFor(this);
    }
}
