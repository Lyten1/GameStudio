package sk.tuke.kpi.oop.game.actions;


import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;


public class Drop<K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getScene() == null){
            setDone(true);
        }

        if(!isDone()){
            Collectible lastItem = getActor().getBackpack().peek();
            if(lastItem != null){
                getActor().getScene().addActor(lastItem, (getActor().getPosX() + (getActor().getWidth()-lastItem.getWidth()/2)), (getActor().getPosY() + (getActor().getHeight()-lastItem.getHeight()/2)));
                getActor().getBackpack().remove(lastItem);
            }
            setDone(true);

        }

    }
}
