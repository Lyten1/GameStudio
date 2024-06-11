package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{
    public Gun(int maxBull) {
        super(maxBull);
    }

    public Gun(int actualBull, int maxBull) {
        super(actualBull, maxBull);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
