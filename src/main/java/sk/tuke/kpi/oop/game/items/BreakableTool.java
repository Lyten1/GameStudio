package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.Repairable;


public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }




    @Override
    public void useWith(A actor){
        this.remainingUses--;
        if(this.remainingUses < 1){
            Scene scene = getScene();
            assert scene != null;
            scene.removeActor(this);
        }
    }

    public int getRemainingUses() {
        return this.remainingUses;
    }

    public abstract void useWith(Repairable obj);
}
