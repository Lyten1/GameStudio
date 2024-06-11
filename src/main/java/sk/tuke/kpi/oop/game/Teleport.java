package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;




public class Teleport extends AbstractActor {



    private boolean canTeleport = true;
   private Teleport destinationTeleport = null;

    private Animation animation = new Animation("sprites/lift.png");

    public Teleport(Teleport destinationTeleport) {
        if(destinationTeleport != this) {
            this.destinationTeleport = destinationTeleport;
        }
        setAnimation(animation);
    }

    public void setDestination(Teleport destinationTeleport){
        if(destinationTeleport != this) {
            this.destinationTeleport = destinationTeleport;
        }
    }

        public Teleport getDestination(){
        return destinationTeleport;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Player player = getScene().getLastActorByType(Player.class);

        new Loop<>(new Invoke<>(this::coordinatesChecker)).scheduleFor(player);

        new Loop<>(new Invoke<>(this::refreshTeleportation)).scheduleFor(player);

    }


    public void teleportPlayer(Player player){
        if(player != null) {
            player.setPosition(this.getPosX()+8, this.getPosY()+8);
            this.canTeleport = false;
        }
    }

    public void coordinatesChecker(Player player){
        int centerPlayerX = player.getPosX() + 16;
        int centerPlayerY = player.getPosY() + 16;


        boolean x_in = (centerPlayerX >= this.getPosX()) && (centerPlayerX <= this.getPosX() + this.getWidth());
        boolean y_in = (centerPlayerY >= this.getPosY()) &&  (centerPlayerY <= (this.getPosY() + this.getWidth()));


        if(canTeleport && this.destinationTeleport != null && x_in && y_in){
            destinationTeleport.teleportPlayer(player);
        }
    }

    public boolean isCanTeleport() {
        return canTeleport;
    }

    public void setCanTeleport(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }


    public void refreshTeleportation(){
        Player player = getScene().getLastActorByType(Player.class);
        if(!canTeleport && !player.intersects(this)){
            canTeleport = true;
        }

    }
}
