package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;



public class EscapeRoom implements SceneListener {
    private Ripley player;

    private Disposable moveController;
    private Disposable keepController;
    private Disposable shootController;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        player = scene.getFirstActorByType(Ripley.class);

        ShooterController shooterController = new ShooterController(player);
        shootController = scene.getInput().registerListener(shooterController);

        MovableController movableController = new MovableController(player);
        moveController = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(player);
        keepController = scene.getInput().registerListener(keeperController);

        SpawnPoint spawnPoint = new SpawnPoint(3);
        scene.addActor(spawnPoint, 150, 180);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 180, 450);
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        scene.addActor(fireExtinguisher, 180, 430);
        Wrench wrench = new Wrench();
        scene.addActor(wrench, 180, 410);

        scene.getGame().pushActorContainer(player.getBackpack());
        scene.follow(player);
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> disposeControllers());
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

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {

            if (name != null) {
                switch (name) {
//                    case "front door":
//                        return new Door("front door", Door.Orientation.VERTICAL);
//                    case "back door":
//                        return new Door("back door", Door.Orientation.HORIZONTAL);
//                    case "exit door":
//                        return new Door("exit door", Door.Orientation.VERTICAL)
//                    case "alien mother":
//                        return new MotherAlien();
//                    case "energy":
//                        return new Energy();
//                    case "alien":
//                        return checkTheBehave(type);
                    case "ammo":
                        return new Ammo();
                    case "ellen":
                        return new Ripley();
                    default:
                        return null;
                }
            }
            return null;
        }


        public Alien checkTheBehave(String type) {
            if (type.equals("running")) {
                return new Alien(100, new RandomlyMoving());
            } else
                return new Alien();
        }


    }
}
