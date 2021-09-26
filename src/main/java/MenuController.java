/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Mohamed Bakr, Dylan LoPresti, Cole Hausman, Ryan Moskenkis
 * Name: Dylan LoPresti
 * Section: 1:50pm
 * Date: 5/18/2021
 * Time: 4:14 PM
 *
 * Project: csci205SP21FinalProject
 * Class: MenuController
 *
 * Description: This is the controller for the menu. The controller connects to the model and view. The controller
 * is used to set event handlers and figure out when an object or events occurs. The menu controller tells the game
 * view what image to use for the character, and if the player is AI or not.
 *
 * ****************************************
 */

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController {

    private GameView gameView;

    private GameModel gameModel;

    private Scene menuScene;

    private ImageView player;


    /**
     * Constructor for the menu controller
     *
     * @param gameView is the view of the game and menu
     * @param gameModel is the logic model of the game
     */
    public MenuController(GameView gameView, GameModel gameModel) {
        this.gameView = gameView;
        this.gameModel = gameModel;


        menuScene = new Scene(gameView.getMenuView(), gameView.getW(), gameView.getH());

        showHelpInstructions(gameView);

        changeCharacter(gameView, gameModel);

        switchToBot(gameView);
    }

    /**
     * Switches the gameplay from manual to the AI by checking the checkbox
     *
     * @param gameView is the view of the game
     */
    private void switchToBot(GameView gameView) {
        // event handler to switch to the AI if the check box is clicked
        gameView.getPlayAsAi().setOnAction(event -> {
            if (gameView.getPlayAsAi().isSelected()) {
                gameView.setIsBot(true);
            }
        });
    }

    /**
     * Changes the character of the game from the default bison by clicking on a button
     *
     * @param gameView  is the view of the game and menu
     * @param gameModel is the logic model of the game
     */
    private void changeCharacter(GameView gameView, GameModel gameModel) {
        for (Button button : gameView.getButtonList()) {
            // event handler to change character by clicking a button
            button.setOnMouseClicked(event -> {
                ImageView imageView = (ImageView) button.getGraphic();
                Image imageCopy = imageView.getImage();
                player = gameView.getPlayerObject();
                player.setImage(imageCopy);
                menuScene.setRoot(gameView.getGameEnvironment());
                GameControl gameControl = new GameControl(gameView, gameModel, menuScene);
            });
        }
    }

    /**
     * Displays the help instructions of the game when the "HELP" button is clicked
     *
     * @param gameView is the view of the game and menu
     */
    private void showHelpInstructions(GameView gameView) {
        // event handler for showing the help instructions
        gameView.getHelpButton().setOnMouseClicked(event -> {
            menuScene.setRoot(gameView.gethBox());
            // event handler to switch back to the main after clicking anywhere
            menuScene.setOnMouseClicked(event1 -> {
                menuScene.setRoot(gameView.getMenuView());
            });
        });
    }

    /**
     * Gets the menu scene
     *
     * @return the menu scene
     */
    public Scene getMenuScene() {
        return menuScene;
    }

    /**
     * Constructor to create a Menu Controller to create event handlers for the game menu
     *
     * @param gameView is the view of the game and menu
     * @param gameModel is the logic model of the game
     */
}