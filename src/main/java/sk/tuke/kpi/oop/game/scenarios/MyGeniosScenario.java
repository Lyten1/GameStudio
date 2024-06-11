package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.behaviours.MyExtraMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;



public class MyGeniosScenario implements SceneListener {
    private Ripley player;
    private Disposable moveController;
    private Disposable keepController;
    private Disposable shootController;


    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                switch (name) {
                    case "locker":
                        return new Locker();
                    case "energy":
                        return new Energy();
                    case "beacon":
                        return new Beacon();
                    case "access card":
                        return new AccessCard();
                    case "ammo":
                        return new Ammo();
                    case "ellen":
                        return new Ripley();
                    case "darkzone":
                        return new DarkZone();
                    default:
                        return null;
                }
            }
            return null;
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        player = scene.getFirstActorByType(Ripley.class);
        startGame(scene);

        ShooterController shooterController = new ShooterController(player);
        shootController = scene.getInput().registerListener(shooterController);

        MovableController movableController = new MovableController(player);
        moveController = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(player);
        keepController = scene.getInput().registerListener(keeperController);

        Ventilation ventilation1 = new Ventilation(null);
        Ventilation ventilation2 = new Ventilation(ventilation1);
        scene.addActor(ventilation1, 305,15);
        scene.addActor(ventilation2, 432,175);
        ventilation1.setNextExit(ventilation2);


        BrokenDoor brokenDoor = new BrokenDoor();
        scene.addActor(brokenDoor, 80, 30);

        BreakableDoor breakableDoor = new BreakableDoor();
        scene.addActor(breakableDoor, 319, 385);



        //teelport
        MySmartAlien mySmartAlien1 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.VERTICAL, 1.4f,1f);
        scene.addActor(mySmartAlien1, 150,310);
        //last-Hor
        MySmartAlien mySmartAlien2 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.HORIZONTAL, 0.5f,2);
        scene.addActor(mySmartAlien2, 190,420);
        //fake-door
        MySmartAlien mySmartAlien3 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.VERTICAL, 0.7f,2);
        scene.addActor(mySmartAlien3, 280,260);
        //last-Ver
        MySmartAlien mySmartAlien4 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.VERTICAL, 1.1f,2);
        scene.addActor(mySmartAlien4, 215,380);
        //dark
        MySmartAlien mySmartAlien5 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.HORIZONTAL, 0.85f,1);
        scene.addActor(mySmartAlien5, 340,280);
        //ammo
        MySmartAlien mySmartAlien6 = new MySmartAlien(100, new MyExtraMoving(), MySmartAlien.Orientation.HORIZONTAL, 1.7f,1);
        scene.addActor(mySmartAlien6, 218,135);

        scene.getGame().pushActorContainer(player.getBackpack());
        scene.follow(player);
        scene.getMessageBus().subscribe(Beacon.SEND_ALARM, (Beacon) -> gameWin(scene));
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> disposeControllers());
    }

    public void startGame(@NotNull Scene scene){
        Overlay over = scene.getGame().getOverlay();
        new ActionSequence<>(
            new Invoke<>(()-> over.drawText("Ohhh noooo, its aliens...", player.getPosX()-30 , player.getPosY()).showFor(3)),
            new Wait<>(3),
            new Invoke<>(()->  over.drawText("They are everywhere, ", player.getPosX()-30 , player.getPosY()).showFor(3)),
            new Wait<>(3),
            new Invoke<>(()->  over.drawText("i must protect others", player.getPosX()-30 , player.getPosY()).showFor(3)),
            new Wait<>(3),
            new Invoke<>(()->  over.drawText("I need to send message...", player.getPosX()-30 , player.getPosY()).showFor(3)),
            new Wait<>(3)
        ).scheduleFor(player);
    }

    public void gameWin(@NotNull Scene scene){
        Overlay over = scene.getGame().getOverlay();
        over.drawText("Congrats! You done that", player.getPosX()-30 , player.getPosY()).showFor(5);
        disposeControllers();
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(()->scene.getGame().stop())
        ).scheduleFor(player);

    }

    public void disposeControllers() {
        moveController.dispose();
        keepController.dispose();
        shootController.dispose();
    }


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Overlay over = scene.getGame().getOverlay();
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        over.drawText(player.showRipleyState(), 120, yTextPos);

    }
}
