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
 * Class: GameModel
 *
 * Description: This is the model of our MVC. This contains all the logic  and math for our application. The model
 * connects to the controller and the view to make the game work. This tells the character how to jump and
 * when there is a collision.
 *
 * ****************************************
 */

import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class GameModel {

    private int score = 0;
    private int sec_counter = 0;

    private boolean isGoingUp;
    private boolean isGoingDown;
    public static final int MAX_JUMP_HEIGHT = -200;
    public static final int UP_SPEED = -5;
    public static final int DOWN_SPEED = 7;

    public static final int OB_SPEED = -6;
    public static final int FOOD_SPEED = -5;


    /**
     * @param playerObject   - the node which represents the player
     * @param playerImage    - the image displayed as the player
     * @param obstacleObject - the node which represents the obstacle
     * @param obstacleImage  - the image which displays as the obstacle
     * @return - whether or not the player and object have touched
     */
    public boolean isColliding(Node playerObject, Image playerImage, Node obstacleObject, Image obstacleImage) {
        //creates the player's hit box
        BoundingBox playerHitBox = new BoundingBox(playerObject.getLayoutX() + playerObject.getTranslateX(),
                playerObject.getLayoutY() + playerObject.getTranslateY(), playerImage.getWidth(),
                playerImage.getHeight());
        //creates the obstacle's hit box
        BoundingBox obstHitBox = new BoundingBox((obstacleObject.getLayoutX() + obstacleObject.getTranslateX()) + obstacleImage.getWidth() / 2,
                obstacleObject.getLayoutY() + obstacleObject.getTranslateY(), obstacleImage.getWidth() / 2, obstacleImage.getHeight());

        return playerHitBox.intersects(obstHitBox);
    }
    /*
    This is all of the logic which handles the "AI"
     */

    /**
     * @param AI             - the node which is the AI
     * @param obstacleObject - the node which is the obstacle
     * @return - the distance between the AI and the obstacle
     */
    public double calculateDistance(Node AI, Node obstacleObject) {
        return Math.abs((obstacleObject.getLayoutX() + obstacleObject.getTranslateX()) - AI.getLayoutX());
    }

    /**
     * @param obstacleObject - the node which is the obstacle
     * @return - how far the object will have moved in the time it takes the player to jump
     */
    public double anticipateMovement(Node obstacleObject) {
        return ((obstacleObject.getLayoutX() + obstacleObject.getTranslateX()) - 400) - (0.0002 * getScore());
    }

    /**
     * @param carrot - the node which represents the carrot object
     * @return - determines how far the carrot will have moved in the time it takes the player to jump
     */
    public double anticipateCarrotMovement(Node carrot) {
        return ((carrot.getLayoutX()) + carrot.getTranslateX()) - (200);
    }

    /**
     * @param AI             - the node which represents the AI
     * @param obstacleObject - the node which represents the obstacle object
     * @return - whether the AI can jump and clear the obstacle
     */
    public boolean predict(Node AI, Node obstacleObject) {
        return calculateDistance(AI, obstacleObject) + anticipateMovement(obstacleObject) < AI.getLayoutX();
    }


    /**
     * @param AI      - the node which is the AI
     * @param AIImage - the image which is displayed as the AI
     * @param carrot  - the node which represents the carrot object
     * @return - if the player can jump and touch the carrot
     */
    public boolean anticipateCarrot(Node AI, Image AIImage, Node carrot) {
        return anticipateCarrotMovement(carrot) < (AI.getLayoutX() + AIImage.getWidth());
    }


    /**
     * @param AI             - the node which represents the carrot
     * @param AIImage        - the image which is the AI
     * @param obstacleObject - the node which represents the obstacle object
     * @return - determines if a carrot can be grabbed and if not, when to jump
     */
    public boolean think(Node AI, Image AIImage, Node obstacleObject) {
        while (predict(AI, obstacleObject)) {
            if (anticipateCarrot(AI, AIImage, obstacleObject)) {
                return true;
            }
        }
        return predict(AI, obstacleObject);
    }


    /**
     * Increments the score when the character eats the carrot
     *
     * @param scoreLabel is the score and score label
     */
    public void incrementScore(Label scoreLabel) {
        //Increment score
        sec_counter++;
        if (sec_counter == 10) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            sec_counter = 0;
        }
    }

    /**
     * Gets the score of the game
     *
     * @return the score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Starts the player jumping when a key is pressed
     */
    public void initiatePlayerJump() {
        if (!isGoingUp && !isGoingDown) isGoingUp = true;

    }

    /**
     * Makes the player jump when a key is pressed
     *
     * @param playerObject is the player object
     */
    public void playerJump(Node playerObject) {
        if (isGoingUp && playerObject.getTranslateY() > MAX_JUMP_HEIGHT) {
            playerObject.setTranslateY(playerObject.getTranslateY() + UP_SPEED);
            if (playerObject.getTranslateY() <= MAX_JUMP_HEIGHT) {
                isGoingUp = false;
                isGoingDown = true;
            }
        }
        if (isGoingDown && playerObject.getTranslateY() < 0) {
            playerObject.setTranslateY(playerObject.getTranslateY() + DOWN_SPEED);
            if (playerObject.getTranslateY() >= 0) {
                isGoingDown = false;
            }
        }
    }

    /**
     * Controls the movement of each obstacle, generating a random obstacle each pass across the screen.
     *
     * @param obstacle - an obstacle object
     */
    public void moveObstacle(ImageView obstacle) {
        if (obstacle.getTranslateX() < -750.0) {
            obstacle.setTranslateX(0);
            String[] obstacle_list = new String[]{"rock.png", "bush.png", "tumbleweed.png"};
            Random random = new Random();
            int index = random.nextInt(obstacle_list.length);
            Image newImage = new Image(obstacle_list[index]);
            obstacle.setImage(newImage);
        } else {
            obstacle.setTranslateX(obstacle.getTranslateX() + OB_SPEED - (0.002 * score));
        }
    }

    /**
     * Controls the movement of the food in the game from the right of the screen to the left.
     *
     * @param food - food object represented by the carrot
     */
    public void moveFood(Node food) {
        if (food.getTranslateX() < -1400.0) {
            food.setTranslateX(0);
        } else {
            food.setTranslateX(food.getTranslateX() + FOOD_SPEED);
        }
    }


}
