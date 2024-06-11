package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.graphics.Overlay;


import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;

import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {

    private Ripley player;
    private Disposable moveController;
    private Disposable keepController;
    public static class Factory implements ActorFactory{

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name!=null){
                switch (name)
                {
                    case "access card":
                        return new AccessCard();
                    case "door":
                        return new LockedDoor();
                    case "ellen":
                        return new Ripley();
                    case "locker":
                        return new Locker();
                    case "energy":
                        return new Energy();
                    case "ventilator":
                        return new Ventilator();
                    default:return null;
                }
            }
            return null;
        }
    }


    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        player = scene.getFirstActorByType(Ripley.class);

        MovableController movableController = new MovableController(player);
        moveController = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(player);
        keepController = scene.getInput().registerListener(keeperController);
        scene.getGame().pushActorContainer(player.getBackpack());
        scene.follow(player);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> player.decreaseEnergy());
        scene.getMessageBus().subscribe(Door.DOOR_CLOSED, (Ripley) -> player.stopDecreasingEnergy());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> player.stopDecreasingEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> disposeControllers());



    }


    public void disposeControllers(){
        moveController.dispose();
        keepController.dispose();
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Overlay over = scene.getGame().getOverlay();
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        over.drawText(player.showRipleyState(), 120, yTextPos);

    }
}
