package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.framework.actions.Rotate;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;


public class Ripley extends AbstractActor implements Armed, Alive, Movable, Keeper {
    private int speed;
    private int ammo;
    private Backpack backpack;

    private Health health;

    private Disposable disp;

    private Firearm gun;

    private boolean isInvis = false;

    public boolean isInvis() {
        return isInvis;
    }

    public void setInvis(boolean invis) {
        isInvis = invis;
        if(invis)
            getAnimation().setAlpha(0.5f);
        else
            getAnimation().setAlpha(1);
    }

    private Animation player_die = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);


    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private Animation anim = new Animation("sprites/player.png",
        32, 32, 0.1f,
        Animation.PlayMode.LOOP_PINGPONG);


    public Ripley() {
        super("Ellen");
        setAnimation(anim);
        anim.stop();
        speed = 2;
        backpack = new Backpack("Ripley's backpack", 10);
        health = new Health(100);
        gun = new Gun(0,200);
        ammo = 0;

        health.onFatigued(() ->
        {
            setAnimation(new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE));

            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        });
    }



    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction != null) {
            new Rotate<Movable>(direction.getAngle(), 0.2f, false).scheduleFor(this);
            anim.play();
        }

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void stoppedMoving() {
        anim.stop();
    }


    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public String showRipleyState(){
        return "| Health " + health.getValue() + " | Ammo " + gun.getAmmo();
    }

    public void decreaseEnergy() {
       disp = new Loop<>(
                new When<>(
                    () -> health.getValue() > 0,
                    new ActionSequence<>(
                        new Invoke<>(() -> health.drain(1)),
                        new Wait<>(2)
                    )
            )
        ).scheduleFor(this);
        new When<>(
            () -> health.getValue() <= 0,
            new ActionSequence<>(
                new Invoke<>(()->setAnimation(player_die)),
                new Invoke<>(() -> getScene().getMessageBus().publish(RIPLEY_DIED, this))
            )
        ).scheduleFor(this);

    }

    public void invisibility(){
        new ActionSequence<>(
            new Invoke<>(() -> setInvis(true)),
            new Wait<>(15),
            new Invoke<>(() -> setInvis(false))
        ).scheduleFor(this);
    }

    public void stopDecreasingEnergy() {
        disp.dispose();
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }
}
