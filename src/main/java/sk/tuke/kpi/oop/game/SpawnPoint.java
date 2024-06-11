package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class SpawnPoint extends AbstractActor {

    private Disposable creating;
    private int spawnAliens;

    private int spawnedAlli = 0;

    private boolean canSpawn = true;



    public SpawnPoint(int spawnAliens) {
        this.spawnAliens = spawnAliens;
        setAnimation(new Animation("sprites/spawn.png", 32, 32));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        creating = new Loop<>(
            new Invoke<>(this::createEnemy)
        ).scheduleFor(this);

    }

    public void createEnemy() {
        if(getScene() == null || !canSpawn)
            return;
        Ripley player = getScene().getFirstActorByType(Ripley.class);
        if(checkTheRipley(player)){
            getScene().addActor(new Alien(), getPosX(), getPosY());
            canSpawn =false;
            spawnedAlli++;
            new ActionSequence<>(
                new Wait<>(3),
                new Invoke<>(()-> canSpawn = true)
            ).scheduleFor(this);
        }
        if(spawnedAlli >= spawnAliens) {
            creating.dispose();
        }

    }



    public boolean checkTheRipley(Ripley player) {
        if (player != null) {
            int leftSideOfPlayer = player.getPosX();
            int rightSideOfPlayer = player.getPosX() + 32;
            int topSideOfPlayer = player.getPosY() + 32;
            int bottomSideOfPlayer = player.getPosY();

            int holeCenterX = getPosX() + 16;
            int holeCenterY = getPosY() + 16;


            boolean topSideDiapazon = (topSideOfPlayer >= holeCenterY - 50 && topSideOfPlayer <= holeCenterY + 50);
            boolean bottomSideDiapazon = (bottomSideOfPlayer >= holeCenterY - 50 && bottomSideOfPlayer <= holeCenterY + 50);
            if (leftSideOfPlayer < holeCenterX + 50 && (topSideDiapazon || bottomSideDiapazon)) {
                return true;
            } else if (rightSideOfPlayer < holeCenterX - 50 && (topSideDiapazon || bottomSideDiapazon)) {
                return true;
            }
        }
        return false;

    }


}
