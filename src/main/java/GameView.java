/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2021
 * Instructor: Prof. Chris Dancy
 *
 * Name: Mohamed Bakr, Dylan LoPresti, Cole Hausman, Ryan Mosenkis
 * Section: 1:50PM section
 * Date: 5/13/21
 * Time: 6:30 PM
 *
 * Project: csci205SP21FinalProject
 * Class: GameView
 *
 * Description: This is the view of the game. It is the background and the set up of both the menu, and the game
 * itself. The view holds our characters, obstacles, and environment, but also has the background for our menu.
 *
 * ****************************************
 */


import javafx.geometry.Insets;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class GameView {
    private static final double W = 600, H = 400;
    private static final double GAP_SIZE = 20;
    private static final double MENU_FONT_SIZE = 16;
    private Image playerImage;
    public ImageView playerObject;
    private Image obstacleImage;
    private ImageView obstacle;
    private String[] obstacle_list;
    private Image foodImage;
    private ImageView food;
    private String imageChoice = "bison.png";
    private Label scoreLabel;
    private Label scoreTextLabel;
    private Media background_music;
    private MediaPlayer mediaPlayer;
    private static boolean isBot = false;


    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private ArrayList<Button> buttonList = new ArrayList<>();
    private Button helpButton;
    private Label helpText;
    private HBox hBox;
    private GridPane menu;
    private CheckBox playAsAi;
    public Group gameEnvironment;


    /**
     * Basic constructor to build the game view
     */
    public GameView() {
        menu = createMenu();

        createObstacle();
        createFood();
        createScoreLabel();

        if (!isBot) {
            createPlayerObject(imageChoice);
        } else {
            createAIObject();
        }

        createBackgroundMusic();

        setUpGameEnvironment();


    }

    /**
     * Creates the background music for the game
     */
    private void createBackgroundMusic() {
        background_music = new Media(Paths.get("src", "main", "resources", "background_music.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(background_music);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.2);
    }

    /**
     * Creates the game environment and background
     */
    private void setUpGameEnvironment() {
        gameEnvironment = new Group(playerObject);
        gameEnvironment.getChildren().add(obstacle);
        gameEnvironment.getChildren().add(food);
        gameEnvironment.getChildren().add(scoreLabel);
        gameEnvironment.getChildren().add(scoreTextLabel);
    }

    /**
     * Creates the player object by reading in an image URL and creating an ImageView object
     *
     * @param imageUrl the string of the url of the image being used
     */
    public void createPlayerObject(String imageUrl) {
        //Player
        playerImage = new Image(imageUrl);
        playerObject = new ImageView(playerImage);
        playerObject.setLayoutX(W / 8);
        playerObject.setLayoutY(H * 0.7);
    }

    /**
     * Creates the AI player object similar to how the player is created
     */
    private void createAIObject() {
        //AI
        playerImage = new Image(imageChoice);
        playerObject = new ImageView(playerImage);
        playerObject.setLayoutX(W / 8);
        playerObject.setLayoutY(H * 0.7);
    }

    /**
     * Creates the score label that is displayed during the game so the user can view their score
     */
    private void createScoreLabel() {
        //Create a score label
        scoreTextLabel = new Label("SCORE:");
        scoreTextLabel.setFont(new Font("Arial", 24));
        scoreTextLabel.setTranslateY(35);
        scoreTextLabel.setTranslateX(380);
        scoreLabel = new Label("0");
        scoreLabel.setFont(new Font("Arial", 24));
        scoreLabel.setTranslateY(35);
        scoreLabel.setTranslateX(500);
    }

    /**
     * Creates the obstacle object for the game play by selecting a random image for the obstacle
     */
    private void createObstacle() {
        //Obstacles part
        obstacle_list = new String[]{"rock.png", "bush.png", "tumbleweed.png"};
        Random random = new Random();
        int index = random.nextInt(obstacle_list.length);
        obstacleImage = new Image(obstacle_list[index]);
        obstacle = new ImageView(obstacleImage);
        obstacle.setLayoutX(W);
        obstacle.setLayoutY(H * 0.87);
    }

    /**
     * Creates the food object for the character to eat as a carrot
     */
    private void createFood() {
        //Obstacles part
        foodImage = new Image("carrot.png");
        food = new ImageView(foodImage);
        food.setLayoutX(W * 1.5);
        food.setLayoutY(H * 0.6);
    }

    /**
     * Creates the tone of the character jumping
     */
    public void makeJumpTone() {
        Media jump_tone = new Media(Paths.get("src", "main", "resources", "jump.wav").toUri().toString());
        MediaPlayer mediaPlayer2 = new MediaPlayer(jump_tone);
        mediaPlayer2.setAutoPlay(true);

    }

    /**
     * Creates the noise made when the character successfully eats a carrot
     */
    public void makeEatingTone() {
        Media eating_tone = new Media(Paths.get("src", "main", "resources", "eating_tone.wav").toUri().toString());
        MediaPlayer mediaPlayer3 = new MediaPlayer(eating_tone);
        mediaPlayer3.setAutoPlay(true);

    }


    /**
     * Gets the HBox object used for the Help instructions
     *
     * @return the hbox used for the help instructions
     */
    public HBox gethBox() {
        return hBox;
    }


    /**
     * Creates the menu for the game to select a character
     *
     * @return the menu of the game
     */
    private GridPane createMenu() {
        setUpGridPane();

        Label instructions = new Label("Select your character");
        instructions.setFont(Font.font("Verdana", MENU_FONT_SIZE));

        btn1 = createCharacterButton("bison.png");
        menu.setConstraints(btn1, 0, 1);

        btn2 = createCharacterButton("horse.png");
        menu.setConstraints(btn2, 2, 1);


        btn3 = createCharacterButton("dog.png");
        menu.setConstraints(btn3, 0, 2);

        btn4 = createCharacterButton("cat.png");
        menu.setConstraints(btn4, 2, 2);

        menu.getChildren().addAll(instructions, btn1, btn2, btn3, btn4);

        helpButton = new Button("HELP");
        menu.setConstraints(helpButton, 2, 4);
        menu.getChildren().add(helpButton);

        playAsAi = new CheckBox("Check to play as AI");
        menu.setConstraints(playAsAi, 0, 4);
        menu.getChildren().add(playAsAi);

        helpText = new Label("The goal of this game is to jump over the obstacle as many times \n" +
                "as possible. The keys to make your character jump are 'w', 'up arrow', and 'space'." + "\n" +
                "The game will end once you hit the obstacle.\n \nClick anywhere to return back to the menu. \n \n" +
                "El objetivo de este juego es saltar el obst\u00E1culo tantas veces como sea posible.\n" +
                "Los botonoes para hacer que tu personaje salte son 'w', 'flecha hacia arriba' y 'barra espaciadora.\n" +
                "El jugego terminar\u00E1 una vez que golpees el obst\u00E1culo. \n \nHaga clic en cualquier lugar para " +
                "volver al men\u00FA.");
        hBox = new HBox(helpText);
        return menu;
    }

    /**
     * Sets up the GridPane for the menu to be used
     */
    private void setUpGridPane() {
        menu = new GridPane();
        menu.setPadding(new Insets(30, 30, 50, 50));
        menu.setVgap(GAP_SIZE);
        menu.setHgap(GAP_SIZE);
    }

    /**
     * Creates a character button and adds the button to a list
     *
     * @param imageName the string name of the image
     * @return the character button
     */
    private Button createCharacterButton(String imageName) {
        ImageView character = new ImageView(new Image(imageName));
        Button button = new Button();
        button.setGraphic(character);
        buttonList.add(button);
        return button;
    }

    /**
     * Gets the list of the character buttons
     *
     * @return the array list of buttons
     */
    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    /**
     * Gets the help button
     *
     * @return the help button
     */
    public Button getHelpButton() {
        return helpButton;
    }

    /**
     * Gets the view and environment for the menu
     *
     * @return the menu view
     */
    public GridPane getMenuView() {
        return menu;
    }


    /**
     * Gets the score label from the game
     *
     * @return the score label
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }


    /**
     * Gets the image of the game character
     *
     * @return the character image
     */
    public Image getPlayerImage() {
        return playerImage;
    }

    /**
     * Gets the image of the obstacle from the game
     *
     * @return the obstacle image
     */
    public Image getObstacleImage() {
        return obstacleImage;
    }

    /**
     * Gets the width constant
     *
     * @return the constant width
     */
    public static double getW() {
        return W;
    }

    /**
     * Gets the height constant
     *
     * @return the height constant
     */
    public static double getH() {
        return H;
    }

    /**
     * Gets the background and setup of the game from the game view
     *
     * @return the game's setup
     */
    public Group getGameEnvironment() {
        return gameEnvironment;
    }


    /**
     * Gets the character's ImageView object
     *
     * @return the character's object
     */
    public ImageView getPlayerObject() {
        return playerObject;
    }

    /**
     * Gets the obstacle's ImageView object
     *
     * @return the obstacle's object
     */
    public ImageView getObstacle() {
        return obstacle;
    }

    /**
     * Gets the food's ImageView object
     *
     * @return the food's object
     */
    public ImageView getFood() {
        return food;
    }

    /**
     * Gets the image of the food from the game
     *
     * @return the food's image
     */
    public Image getFoodImage() {
        return foodImage;
    }


    /**
     * Get's the boolean value for whether or not the AI is turned on
     *
     * @return the boolean value for if the bot is turned on
     */
    public static boolean getIsBot() {
        return isBot;
    }

    /**
     * Gets the AI checkbox to ask user if they want to play as AI or not
     *
     * @return the AI checkbox
     */
    public CheckBox getPlayAsAi() {
        return playAsAi;
    }

    /**
     * Sets the boolean value for isBot
     *
     * @param isBot is the AI character being used
     */
    public static void setIsBot(boolean isBot) {
        GameView.isBot = isBot;
    }


}
