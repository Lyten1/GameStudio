package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl2.Lwjgl2Backend;
import sk.tuke.kpi.oop.game.scenarios.MyGeniosScenario;


public class Main {

    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup, new Lwjgl2Backend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`

        //Scene scene = new World("world");
        //Scene scene = new World("world", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene scene = new World("world", "maps/myLevel.tmx", new MyGeniosScenario.Factory());
        //Scene scene = new World("world", "maps/escape-room.tmx", new EscapeRoom.Factory());

        // pridanie sceny do hry
        game.addScene(scene);




        MyGeniosScenario mgs = new MyGeniosScenario();
        scene.addListener(mgs);
//        EscapeRoom fs = new EscapeRoom();
//        scene.addListener(fs);





        // spustenie hry
        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
    }
}
