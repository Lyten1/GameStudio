package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private Set<EnergyConsumer> devices;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation breakingAnimation;

    private Animation offAnimation;
    private Animation extinguishedAnimation;
    private boolean state;


    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.state = false;
        devices = new HashSet<>();
        this.normalAnimation = new Animation("sprites/reactor_on.png",
            80, 80, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png",
            80, 80, 0.05f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.breakingAnimation = new Animation("sprites/reactor_broken.png",
            80, 80, 0.05f,
            Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png");
         this.extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offAnimation);

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public void updateAnimation() {
        if (this.temperature >= 6000 || this.damage >= 100) {
            setAnimation(breakingAnimation);
        } else if (this.temperature >= 4000) {
            setAnimation(hotAnimation);
        }else if(!this.state){
            setAnimation(offAnimation);
        }
        else {
            setAnimation(normalAnimation);
        }
    }


    public int getTemperature() {
        return temperature;
    }


    public int getDamage() {
        return damage;
    }


    public void increaseTemperature(int increment) {
        if(isOn() && increment > 0) {
                if (this.damage >= 33 && this.damage <= 66)
                    this.temperature += (int) Math.ceil(increment * 1.5);
                else if (this.damage > 66)
                    this.temperature += (int) Math.ceil(increment * 2);
                else
                    this.temperature += (int) Math.ceil(increment);
                if (this.temperature >= 2000) {
                    this.damage = (this.temperature - 2000) / 40;
                    if (this.damage >= 100) {
                        this.damage = 100;
                        turnOff();
                    } else {
                        updateAnimation();
                    }
                }
        }
    }


    public void decreaseTemperature(int decrement) {
        if(isOn() && this.temperature >= 0 && decrement > 0) {
                if (this.damage >= 50 && this.damage < 100) {
                    this.temperature -= decrement / 2;
                } else if (this.damage > 100) {
                    updateAnimation();
                    return;
                }
                else
                    this.temperature -= decrement;
                updateAnimation();
        }
    }



    @Override
    public void turnOn(){
        if(getDamage() < 100) {
            this.state = true;
            for (EnergyConsumer device : devices)
                device.setPowered(true);
            setAnimation(normalAnimation);
        }
    }

    @Override
    public void turnOff(){
        this.state = false;
        for (EnergyConsumer device : devices )
            device.setPowered(false);
        if(this.damage < 100)
            setAnimation(offAnimation);
        else
            setAnimation(breakingAnimation);
    }

    @Override
    public boolean isOn() {
        return this.state;
    }

    public void addDevice(EnergyConsumer device){
        if(device != null){
            this.devices.add(device);
            if(this.state) device.setPowered(true);
        }
    }

    public void removeDevice(EnergyConsumer device){
        if(this.devices != null && device != null) {
            device.setPowered(false);
            this.devices.remove(device);
        }
    }

    public boolean extinguish(){
        if(this.damage >= 100){
            this.temperature -= 4000;
            setAnimation(extinguishedAnimation);
            return true;
        }
        return false;
    }


    @Override
    public boolean repair() {
        if(this.damage > 0 && this.damage < 100) {
            this.damage = Math.max(this.damage - 50, 0);
            temperature = Math.min((this.damage * 40) + 2000, temperature);
            updateAnimation();
            return true;
        }
        return false;
    }
}
