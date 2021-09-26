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
 * Class: GameControl
 *
 * Description: This is the controller for the gameplay. The controller connects to the model and view. The controller
 * is used to set event handlers and figure out when an object or events occurs. The game controller tells the character
 * when to jump, and tells the obstacles when to move faster.
 *
 * ****************************************
 */

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;


public class GameControl {

    private GameView gameView;
    private GameModel gameModel;

    private MenuController menuController;


    /**
     * Constructor for the Game Control which has the event handlers and the timing system for the game
     *
     * @param gameView  is the view of the game and menu
     * @param gameModel is the logic model of the game
     * @param menuScene is the opening scene of the game
     */
    public GameControl(GameView gameView, GameModel gameModel, Scene menuScene) {
        this.gameView = gameView;
        this.gameModel = gameModel;


        // sets up the animation timer to start and end on a collision
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (!GameView.getIsBot()) {
                            switch (event.getCode()) {
                                case UP: {
                                    gameModel.initiatePlayerJump();
                                    gameView.makeJumpTone();
                                }
                                case W: {
                                    gameModel.initiatePlayerJump();
                                    gameView.makeJumpTone();
                                }
                                case SPACE: {
                                    gameModel.initiatePlayerJump();
                                    gameView.makeJumpTone();
                                }
                            }
                        }
                    }
                });

                gameModel.playerJump(gameView.getPlayerObject());

                gameModel.moveObstacle(gameView.getObstacle());

                gameModel.moveFood(gameView.getFood());

                // sets up the AI for the game
                if (GameView.getIsBot()) {
                    if (gameModel.think(gameView.getPlayerObject(), gameView.getPlayerImage(), gameView.getObstacle())) {
                        gameModel.initiatePlayerJump();
                    }
                }

                // tells the game when the character eats the carrot and then increases score
                if (gameModel.isColliding(gameView.getPlayerObject(), gameView.getPlayerImage(), gameView.getFood(), gameView.getFoodImage())) {
                    gameView.makeEatingTone();
                    gameView.getFood().setTranslateX(0);
                    for (int i = 0; i < 300; i++) {
                        gameModel.incrementScore(gameView.getScoreLabel());
                    }
                }


                // tells the game that the character collided with and obstacle and that the game is over
                if (gameModel.isColliding(gameView.getPlayerObject(), gameView.getPlayerImage(), gameView.getObstacle(), gameView.getObstacleImage())) {
                    Platform.exit();
                }

                gameModel.incrementScore(gameView.getScoreLabel());

            }
        };
        timer.start();

    }


}
