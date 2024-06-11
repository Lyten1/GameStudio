package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;


import java.util.Objects;

public class BreakableDoor extends AbstractActor implements Alive {

    private Health health;

    public BreakableDoor() {
        setAnimation(new Animation("sprites/vdoor.png", 16,32));
        getAnimation().stop();
        health = new Health(100);
        health.onFatigued(() -> {
                Objects.requireNonNull(getScene()).removeActor(this);
            getScene().getMap().getTile(getPosX() / 16 +1, getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(getPosX() / 16 +1, getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);

            }
        );

    }

    @Override
    public Health getHealth() {
        return health;
    }
}
