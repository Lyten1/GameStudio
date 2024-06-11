package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;
import java.util.Objects;

public class Take<K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {

        if(getActor() == null || getActor().getScene() == null){
            setDone(true);
        }


        if(!isDone()){

            Scene scene = getActor().getScene();
            List<Actor> interList = Objects.requireNonNull(scene).getActors();
            for(Actor item : interList){
                if(item.intersects(getActor()) && item instanceof Collectible){
                    try{
                        getActor().getBackpack().add((Collectible) item);
                        scene.removeActor(item);
                    }
                    catch (IllegalStateException exception){
                        scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                    }
                }
            }
            setDone(true);

        }


    }
}
