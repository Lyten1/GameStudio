package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }


    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (key == Input.Key.ENTER) {
            new Take<>().scheduleFor(keeper);
        }
        if (key == Input.Key.BACKSPACE) {
            new Drop<>().scheduleFor(keeper);
        }
        if (key == Input.Key.S) {
            new Shift<>().scheduleFor(keeper);
        }
        if (key == Input.Key.U) {
            useFirst();
        }
        if (key == Input.Key.B) {
            useInvTop();
        }
    }

    public void useInvTop() {
        if (keeper.getBackpack().peek() instanceof Usable) {
            Use<?> usable = new Use<>((Usable<?>) keeper.getBackpack().peek());
            usable.scheduleForIntersectingWith(keeper);
        }
    }

    public void useFirst() {
        Usable<?> actor = keeper.getScene().getActors().stream()
            .filter(keeper::intersects)
            .filter(Usable.class::isInstance)
            .map(Usable.class::cast).findFirst().orElse(null);
        if (actor != null)
            new Use<>(actor).scheduleForIntersectingWith(keeper);
    }
}
