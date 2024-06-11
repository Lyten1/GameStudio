package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {

    @Override
    public void setUp(Movable actor) {
        if(actor != null) {
            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::randomMove),
                    new Wait<>(2)
                )
            ).scheduleFor(actor);
        }


    }

    public void randomMove(Movable actor) {
        int dX = (int) (Math.random()*3  - 1);
        int dY = (int) (Math.random()*3 - 1);

        Direction dist = null;

        for (Direction value : Direction.values()) {
            if (dX == value.getDx() && dY == value.getDy()) {
                dist = value;
            }
        }
        assert dist != null;
        actor.getAnimation().setRotation(dist.getAngle());
        new Move<>(dist, 2).scheduleFor(actor);
    }

}
