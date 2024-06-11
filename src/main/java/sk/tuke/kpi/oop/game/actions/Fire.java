package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        setDone(true);
        if (getActor() == null || getActor().getFirearm().getAmmo() == 0) {
            return;
        }

        Fireable fireable = getActor().getFirearm().fire();
        float rotation = getActor().getAnimation().getRotation();
        int dx = Direction.fromAngle(rotation).getDx();
        int dy = Direction.fromAngle(rotation).getDy();
        if (dx == 0 && dy == 0) {
            return;
        }
        if (fireable != null) {
            getActor().getScene().addActor(fireable, getActor().getPosX() + 8 + (dx * 24), getActor().getPosY() + 8 + (dy * 24));
            fireable.startedMoving(Direction.fromAngle(rotation));
            new Move<Fireable>(Direction.fromAngle(rotation), Float.MAX_VALUE).scheduleFor(fireable);
        }

    }
}
