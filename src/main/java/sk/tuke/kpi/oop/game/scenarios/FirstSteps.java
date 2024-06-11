package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;


public class FirstSteps implements SceneListener {
    private Ripley rip;
    @Override
    public void sceneInitialized(@NotNull Scene scene) {




        Energy energy = new Energy();
        scene.addActor(energy, 100, 100);

        Ammo ammo = new Ammo();
        scene.addActor(ammo, -100, 100);


        rip = new Ripley();
        scene.addActor(rip);
        //rip.startedMoving(Direction.NORTH);
        MovableController movableController = new MovableController(rip);
        scene.getInput().registerListener(movableController);

        new When<>(
            () -> rip.intersects(energy),
            new Invoke<>(() -> energy.useWith(rip))
        ).scheduleFor(rip);



        new When<>(
            () -> rip.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(rip))
        ).scheduleFor(rip);


        Hammer hammer = new Hammer();
        scene.addActor(hammer, 100, -50);
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        scene.addActor(fireExtinguisher, -50, -50);
        Wrench wrench = new Wrench();
        scene.addActor(wrench, -80, -50);

        KeeperController keeperController = new KeeperController(rip);
        scene.getInput().registerListener(keeperController);

        scene.getGame().pushActorContainer(rip.getBackpack());
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Overlay over = scene.getGame().getOverlay();
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        over.drawText("| Energy " + rip.getHealth().getValue() + " | Ammo " + rip.getAmmo(), 120, yTextPos);


    }
}
