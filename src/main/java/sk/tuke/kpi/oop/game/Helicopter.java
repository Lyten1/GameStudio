package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {


    public Helicopter() {
        Animation animation = new Animation("sprites/heli.png", 64, 64, 0.2f,
            Animation.PlayMode.LOOP);
        setAnimation(animation);

    }



    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::makeKarma)).scheduleFor(this);
    }

    public void makeKarma(){
        Scene scene = getScene();
        assert scene != null;
        Player player = scene.getFirstActorByType(Player.class);
        assert player != null;
        int newCoordX = this.getPosX();
        int newCoordY = this.getPosY();

        if (player.intersects(this)) {
            player.setEnergy(player.getEnergy() - 1);
        }

        if (player.getPosX() < this.getPosX())
            newCoordX--;
        else if (player.getPosX() > this.getPosX())
            newCoordX++;

        if (player.getPosY() < this.getPosY())
            newCoordY--;
        else if (player.getPosY() > this.getPosY())
            newCoordY++;

        this.setPosition(newCoordX, newCoordY);

    }
}
