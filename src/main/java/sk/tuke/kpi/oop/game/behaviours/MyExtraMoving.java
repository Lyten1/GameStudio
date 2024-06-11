package sk.tuke.kpi.oop.game.behaviours;


import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.MySmartAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class MyExtraMoving implements Behaviour<Movable> {

    @Override
    public void setUp(Movable actor) {

        if (actor instanceof MySmartAlien) {
            MySmartAlien alli = (MySmartAlien) actor;
            setDirection(alli);
            if (actor != null) {
                 new Loop<>(
                    new When<>(() -> checkTheRipley(alli),
                        new ActionSequence<>(
                            new Invoke<>(this::following),
                            new Wait<>(0.1f))

                    )
                ).scheduleFor(alli);

                new Loop<>(
                    new ActionSequence<>(
                        new Invoke<>(this::moveCalm),
                        new Wait<>(alli.getWaitDuration() + alli.getTimeDistance()),
                        new Invoke<>(this::changeDirection)
                    )).scheduleFor(alli);

            }
        }

    }

    private int dX;
    private int dY;

    public void setDirection(MySmartAlien alli) {
        if (alli.getOrientation() == MySmartAlien.Orientation.VERTICAL) {
            dX = 0;
            dY = -1;
        } else if (alli.getOrientation() == MySmartAlien.Orientation.HORIZONTAL) {
            dX = 1;
            dY = 0;
        }
    }

    public void following(Movable actor) {
        if (actor instanceof MySmartAlien) {
            MySmartAlien alli = (MySmartAlien) actor;
            Ripley player = actor.getScene().getFirstActorByType(Ripley.class);
            moveToPlayer(alli, player);
        }

    }

    public boolean checkTheRipley(MySmartAlien actor) {
        Ripley player = Objects.requireNonNull(actor.getScene()).getFirstActorByType(Ripley.class);
        if (player != null && actor != null && !player.isInvis()) {
            int leftSideOfPlayer = player.getPosX();
            int rightSideOfPlayer = player.getPosX() + 32;
            int topSideOfPlayer = player.getPosY() + 32;
            int bottomSideOfPlayer = player.getPosY();

            int holeCenterX = actor.getPosX() + 16;
            int holeCenterY = actor.getPosY() + 16;


            boolean topSideDiapazon = (topSideOfPlayer >= holeCenterY - 30 && topSideOfPlayer <= holeCenterY + 30);
            boolean bottomSideDiapazon = (bottomSideOfPlayer >= holeCenterY - 30 && bottomSideOfPlayer <= holeCenterY + 30);
            boolean leftSideDiapazon = (leftSideOfPlayer <= holeCenterX + 30 && leftSideOfPlayer >= holeCenterX - 30);
            boolean rightSideDiapazon = (rightSideOfPlayer <= holeCenterX + 30 && rightSideOfPlayer >= holeCenterX - 30);
            if ((leftSideDiapazon || rightSideDiapazon) && (topSideDiapazon || bottomSideDiapazon)) {
                return true;
            }
        }
        return false;

    }

    public void moveToPlayer(MySmartAlien actor, Ripley player) {
        if (player == null || actor == null) return;

        Direction dist = null;

        dX = 0;
        dY = 0;
        getDirectionToPlayer( actor,  player);
        for (Direction value : Direction.values()) {
            if (dX == value.getDx() && dY == value.getDy()) {
                dist = value;
            }
        }
        assert dist != null;
        actor.getAnimation().setRotation(dist.getAngle());
        new Move<>(dist, 0.2f).scheduleFor(actor);

    }

    public void getDirectionToPlayer(MySmartAlien actor, Ripley player){
        if (actor.getPosX() < player.getPosX()) {
            dX = 1;
        } else if (actor.getPosX() > player.getPosX() ) {
            dX = -1;
        }

        if (actor.getPosY() < player.getPosY()) {
            dY = 1;
        } else if (actor.getPosY() > player.getPosY()) {
            dY = -1;
        }


    }

    public void changeDirection() {
        if (dX == 0)
            dY -= 2 * dY;
        if (dY == 0)
            dX -= 2 * dX;

    }

    public void moveCalm(Movable actor) {
        if (actor instanceof MySmartAlien) {
            MySmartAlien alli = (MySmartAlien) actor;

            Direction dist = null;

            for (Direction value : Direction.values()) {
                if (dX == value.getDx() && dY == value.getDy()) {
                    dist = value;
                }
            }
            assert dist != null;
            actor.getAnimation().setRotation(dist.getAngle());
            new Move<>(dist, alli.getTimeDistance()).scheduleFor(alli);
        }

    }
}
