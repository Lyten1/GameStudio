package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Repairable;


public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {


    public FireExtinguisher() {
        super(1);
        var animation = new Animation("sprites/extinguisher.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Reactor reactor) {
        if(reactor != null && reactor.extinguish()){
            super.useWith(reactor);
        }
    }

    @Override
    public void useWith(Repairable obj) {

    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}
