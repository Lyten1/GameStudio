package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;


public class Hammer extends BreakableTool<Repairable> implements Collectible {
    private Animation animation = new Animation("sprites/hammer.png");


    public Hammer() {
        super(1);
        setAnimation(this.animation);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
        setAnimation(this.animation);
    }

    @NotNull
    @Override
    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public void useWith(Repairable obj) {
        if(obj != null && obj.repair()) {
            super.useWith(obj);
        }
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
