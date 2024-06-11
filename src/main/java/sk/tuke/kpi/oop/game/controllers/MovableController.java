package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Map;

public class MovableController implements KeyboardListener {
    private Disposable disposable;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );


    private Movable actor;
    private Move<Movable> move = null;


    private Direction first_dir = null;
    private Direction second_dir = null;

    public MovableController(Movable actor) {
        this.actor = actor;
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        KeyboardListener.super.keyReleased(key);

        if (keyDirectionMap.containsKey(key)) {

            if (first_dir == keyDirectionMap.get(key)) {
                first_dir = null;
            }
            if (second_dir == keyDirectionMap.get(key)) {
                second_dir = null;
            }
            updateAction();
        }


    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        KeyboardListener.super.keyPressed(key);
        if (keyDirectionMap.containsKey(key)) {
            if (first_dir == null && second_dir == null) {
                first_dir = keyDirectionMap.get(key);
            } else if (first_dir == null) {
                first_dir = keyDirectionMap.get(key);
            } else if (second_dir == null) {
                second_dir = keyDirectionMap.get(key);
            }
            updateAction();
        }
    }


    public void updateAction() {
        Direction main_dir = null;
        stopMoving();
        if (first_dir != null && second_dir != null) {
            main_dir = first_dir.combine(second_dir);
        } else if (first_dir != null || second_dir != null) {
            if (first_dir == null) {
                main_dir = second_dir;
            } else {
                main_dir = first_dir;
            }
        }
        move = new Move<Movable>(main_dir, Float.MAX_VALUE);
        disposable = move.scheduleFor(actor);
    }


    private void stopMoving() {
        if (move != null) {
            move.stop();
            disposable.dispose();
            move = null;
        }
    }


}
