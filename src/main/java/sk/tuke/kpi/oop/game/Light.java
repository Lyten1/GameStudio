package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {

    private boolean state;
    private Animation turnOn;
    private Animation turnOff;
    private boolean electricityFlow;

    public Light() {
        this.state = false;
        this.turnOn = new Animation("sprites/light_on.png");
        this.turnOff = new Animation("sprites/light_off.png");
        this.electricityFlow = false;
        setAnimation(turnOff);
    }

    public void setState(boolean state) {
        this.state = state;
        updateLight();
    }

    public void toggle(){
        if(isOn())
            this.turnOff();
        else this.turnOn();
        updateLight();
    }

    private void updateLight(){
        if(isOn() && this.electricityFlow)
            setAnimation(this.turnOn);
        else
            setAnimation(this.turnOff);
    }

    @Override
    public void turnOn() {
        this.state = true;
        updateLight();
    }

    @Override
    public void turnOff() {
        this.state = false;
        updateLight();
    }

    @Override
    public boolean isOn() {
        return this.state;
    }

    @Override
    public void setPowered(boolean energy) {
        this.electricityFlow = energy;
        this.updateLight();
    }
}
