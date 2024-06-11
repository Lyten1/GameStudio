package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;

import sk.tuke.kpi.oop.game.items.Usable;


public class Door extends AbstractActor implements Openable, Usable<Actor> {

    public enum Orientation  {
        VERTICAL,
        HORIZONTAL
    }

    private String vDoorPath = "sprites/vdoor.png";
    private String hDoorPath = "sprites/hdoor.png";

    private Orientation orientation;
    private Animation animationVOpen = new Animation(vDoorPath, 16,32, 0.1f, Animation.PlayMode.ONCE);
    private Animation animationVClose = new Animation(vDoorPath, 16,32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private Animation animationHOpen = new Animation(hDoorPath, 32,16, 0.1f, Animation.PlayMode.ONCE);
    private Animation animationHClose = new Animation(hDoorPath, 32,16, 0.1f, Animation.PlayMode.ONCE_REVERSED);

    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private  boolean isOpen;

    public Door() {
        Animation animation = new Animation(vDoorPath, 16, 32);
        setAnimation(animation);
        getAnimation().stop();
        isOpen = false;
    }

    public Door(String name, Orientation orientation){
        super(name);
        Animation animation;
        if(orientation == Orientation.VERTICAL)
            animation = new Animation(vDoorPath, 16, 32);
        else
            animation = new Animation(hDoorPath, 32, 16);
        setAnimation(animation);
        getAnimation().stop();
        this.orientation = orientation;
        isOpen = false;

    }

    @Override
    public void open() {
        isOpen = true;
        assert getScene() == null;
        if(orientation == Orientation.VERTICAL) {
            getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(getPosX() / 16 , getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
            setAnimation(animationVOpen);
        }
        else{
            getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().getMap().getTile(getPosX() / 16 + 1, getPosY() / 16).setType(MapTile.Type.CLEAR);
            setAnimation(animationHOpen);
        }
        getAnimation().resetToFirstFrame();
        getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        isOpen = false;
        assert getScene() == null;
        if(orientation == Orientation.VERTICAL) {
            getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
            setAnimation(animationVClose);
        }
        else{
            getScene().getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            getScene().getMap().getTile(getPosX() / 16 + 1, getPosY() / 16).setType(MapTile.Type.WALL);
            setAnimation(animationHClose);
        }

        getAnimation().resetToFirstFrame();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpen())
            close();
        else
            open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(orientation == Orientation.VERTICAL) {
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
        else{
            scene.getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            scene.getMap().getTile(getPosX() / 16 +1, getPosY() / 16).setType(MapTile.Type.WALL);
        }
    }
}
