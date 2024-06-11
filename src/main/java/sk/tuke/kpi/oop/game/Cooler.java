package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Cooler extends AbstractActor implements Switchable {

    private boolean state;
    private Reactor reactor;

    private Animation animation_on = new Animation("sprites/fan.png", 32, 32, 0.2f,
        Animation.PlayMode.LOOP_PINGPONG);
    private Animation animation_off = new Animation("sprites/fan.png", 32, 32, 0.0f,
        Animation.PlayMode.LOOP_PINGPONG);

    public Cooler(Reactor reactor) {
        this.state = false;
        this.reactor = reactor;
        setAnimation(animation_off);
    }

    public Reactor getReactor() {
        return reactor;
    }

    public void setReactor(Reactor reactor) {
        this.reactor = reactor;
    }

    public boolean isState() {
        return state;
    }



    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

    private void coolReactor(){
        if(this.reactor != null && this.isOn()) {
                this.reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void turnOn(){
        this.state = true;
        setAnimation(animation_on);
    }
    @Override
    public void turnOff(){
        this.state = false;
        setAnimation(animation_off);
    }
    @Override
    public boolean isOn(){
        return this.state;
    }
}
