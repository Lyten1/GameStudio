package sk.tuke.kpi.oop.game.characters;


import java.util.ArrayList;
import java.util.List;

public class Health {

    private int hp;
    private int maxHp;
    private List<FatigueEffect> effects;

    public Health(int health, int maxHealth) {
        this.hp = health;
        this.maxHp = maxHealth;
        effects = new ArrayList<>();
    }

    public Health(int health) {
        this.hp = health;
        this.maxHp = health;
        effects = new ArrayList<>();
    }

    public int getValue(){
        return hp;
    }

    public void refill(int amount){
        if(hp + amount < maxHp){
            hp += amount;
        }
        else restore();
    }
    public void restore(){
        hp = maxHp;
    }

    public void drain(int amount){
        if(hp - amount > 0){
            hp -= amount;
        }
        else
            exhaust();
    }

    public void exhaust(){
        if(hp != 0) {
            hp = 0;
            if(effects != null)
                effects.forEach(FatigueEffect::apply);
        }
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }

    public void onFatigued(FatigueEffect effect){
        if (effects!=null) {
            effects.add(effect);
        }

    }

}
