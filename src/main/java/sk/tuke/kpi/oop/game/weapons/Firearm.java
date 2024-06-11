package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm  {
    private int actualAmmo;
    private int maxAmmo;

    public Firearm(int maxBull) {
        this.actualAmmo = maxBull;
        this.maxAmmo = maxBull;
    }

    public Firearm(int actualBull, int maxBull) {
        this.actualAmmo = actualBull;
        this.maxAmmo = maxBull;
    }

    public int getAmmo(){
        return actualAmmo;
    }

    public void reload(int newAmmo){
        if(actualAmmo + newAmmo <= maxAmmo){
            actualAmmo += newAmmo;
        }
    }

    public Fireable fire(){
        if(actualAmmo != 0){
            actualAmmo--;
            return createBullet();
        }
        return null;
    }

    protected abstract Fireable createBullet();
}
