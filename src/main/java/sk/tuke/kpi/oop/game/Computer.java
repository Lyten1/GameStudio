package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;



public class Computer extends AbstractActor implements EnergyConsumer{
    private boolean isPowered ;



    public Computer() {
        setPowered(false);
        updateAnimation();
    }

    public int add(int a, int b){
        if(this.isPowered)
            return a+b;
        else return 0;
    }

    public float add(float a, float b){
        if(this.isPowered)
            return a+b;
        else return (float) 0;
    }

    public int sub(int a, int b){
        if(this.isPowered)
            return a-b;
        else return 0;
    }

    public float sub(float a, float b){
        if(this.isPowered)
            return a-b;
        else return (float) 0;
    }

    @Override
    public void setPowered(boolean energy) {
        this.isPowered = energy;
        updateAnimation();
    }

    private void updateAnimation(){
        Animation onAnimation = new Animation("sprites/computer.png",
            80, 48, 0.2f,
            Animation.PlayMode.LOOP_PINGPONG);
        Animation offAnimation = new Animation("sprites/computer.png",
            80, 48, 0.0f,
            Animation.PlayMode.LOOP_PINGPONG);
        if(this.isPowered){
            setAnimation(onAnimation);
        }
        else {
            setAnimation(offAnimation);
        }
    }


}
