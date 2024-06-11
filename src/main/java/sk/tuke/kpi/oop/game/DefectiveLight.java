package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.Random;

public class DefectiveLight extends Light implements Repairable{

    private boolean isRepairing = false;

    private Disposable repairLoopDisposable = null;

    public void letLightShine(){
        var randomNum = new Random().nextInt(21);
        if(randomNum == 1) {
            this.setState(!this.isOn());
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        repairLoopDisposable = new Loop<>(new Invoke<>(this::letLightShine)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (repairLoopDisposable != null && !isRepairing) {
            repairLoopDisposable.dispose();
            isRepairing = true;
            turnOn();
            new ActionSequence<>(
                new Wait<>(10.0f),
                new Invoke<>(this::handleWaitFinished)
            ).scheduleFor(this);
            return true;
        }
        return false;
    }

    private void handleWaitFinished() {
        repairLoopDisposable = new Loop<>(new Invoke<>(this::letLightShine)).scheduleFor(this);
        isRepairing = false;
    }
}
