/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Mohamed Bakr, Dylan LoPresti, Cole Hausman, Ryan Mosenkis
 * Section: 1:50PM section
 * Date: 5/13/21
 * Time: 6:31 PM
 *
 * Project: csci205SP21FinalProject
 * Class: GameMain
 *
 * Description: This is the main class for our game. This is a javafx application, which actually starts and runs the
 * game. This class connects with the MVC to create a working game.
 *
 * ****************************************
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMain extends Application {


    private GameView gameView;
    private GameModel gameModel;
    private MenuController menuController;


    /**
     * overriding the normal constructor to initialize the game view, game model, and menu controller
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.gameView = new GameView();
        this.gameModel = new GameModel();
        this.menuController = new MenuController(gameView, gameModel);
    }

    /**
     * Start method to start the application by setting up the scene
     *
     * @param stage the stage for which the game is played on
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Scene menuScene = menuController.getMenuScene();
        stage.setScene(menuScene);

        stage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
