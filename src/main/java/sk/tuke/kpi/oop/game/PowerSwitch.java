package sk.tuke.kpi.oop.game;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch<T extends Switchable> extends AbstractActor{

    private T device;

    public PowerSwitch(@NotNull T device) {
        this.device = device;
        var animation = new Animation("sprites/switch.png");
        setAnimation(animation);

    }

    public T getDevice(){
        return this.device;
    }

    public void switchOn(){
        if(this.device != null)
            this.device.turnOn();
        getAnimation().setTint(Color.WHITE);
    }

    public void switchOff(){
        if(this.device != null)
            this.device.turnOff();
        getAnimation().setTint(Color.GRAY);
    }


}
