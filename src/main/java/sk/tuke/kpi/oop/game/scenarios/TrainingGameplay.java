package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.items.Hammer;


public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 145, 257);
        reactor.turnOn();

        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 241, 319);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 168, 212);

        new Invoke<>(new Runnable() {
            public void run() {
                hammer.useWith(reactor);
            }
        });
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);

        Teleport teleport1 = new Teleport(null);
        Teleport teleport2 = new Teleport(teleport1);
        Teleport teleport3 = new Teleport(teleport2);
        //teleport1.setDestinationTeleport(teleport2);
        scene.addActor(teleport1, 48, 77);
        scene.addActor(teleport2, 258, 77);
        scene.addActor(teleport3, 145, 200);
    }
}
