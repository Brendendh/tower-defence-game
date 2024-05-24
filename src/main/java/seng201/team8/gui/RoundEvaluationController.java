package seng201.team8.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import seng201.team8.models.Round;
import seng201.team8.models.Tower;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEvaluationService;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller class for RoundEvaluationController.
 * <p></p>
 * Responsible for the communication between the user input and the RoundEvaluationController.
 */
public class RoundEvaluationController {


    @FXML
    private GridPane mainLayout;
    @FXML
    private GridPane infoLayout;
    @FXML
    private Label roundCounterLabel;

    /**
     * The {@link GridPane} object which will display the cart animation.
     */
    private GridPane gamePane;

    /**
     * The {@link GridPane} object which will display the towers and tower productions.
     */
    private GridPane towerPane;

    /**
     * The {@link GameManager} object for accessing the information and
     * launching new screens.
     */
    private final GameManager gameManager;

    /**
     * The {@link Round} which contains the information needed to evaluate.
     */
    private final Round round;

    /**
     * The {@link RoundEvaluationService} which contains the functionality of
     * the round evaluation.
     */
    private final RoundEvaluationService roundEvaluationService;

    /**
     * The {@link List} of {@link Label}s which describes the cart's state.
     */
    private List<Label> cartLabels;

    /**
     * The {@link List} of {@link Label}s which describes if a tower has produced.
     */
    private List<Label> resourceLabels;

    /**
     * The {@link Integer} number of columns the game table will have.
     */
    private int numTableCols;
    /**
     * The {@link Integer} number of rows the game table will have.
     */
    private int numTableRows;

    /**
     * The {@link Integer} number of rows the tower table will have.
     */
    private int numTowerRows;

    /**
     * The constructor for {@link RoundEvaluationController}.
     * <p></p>
     * Takes in a {@link GameManager}, creates a new RoundEvaluationService and
     * stores it in the RoundEvaluationController.
     * @param gameManager {@link RoundEvaluationController}
     */
    public RoundEvaluationController(GameManager gameManager) {
        this.gameManager = gameManager;
        round = gameManager.getRound();
        this.roundEvaluationService = new RoundEvaluationService(gameManager);
    }

    /**
     * Runs when the {@link RoundEvaluationController} is initialized. Initializes the
     * game pane, tower pane, {@link Label}s and links a style sheet.
     */
    public void initialize(){
        initializeGamePane();
        initializeTowerPane();
        cartLabels = new ArrayList<>();
        resourceLabels = new ArrayList<>();

        createTableConstraints();
        createGameTable();
        createCartLabels();
        updateGameTable();

        createTowerConstraints();
        createTowerTable();
        createTowerImageViews();
        createResourceLabels();

        mainLayout.getStylesheets().add("/css/RoundEvaluationService.css");

        mainLayout.add(gamePane, 1, 0);
        infoLayout.add(towerPane, 0, 0);

        playAnimation();
    }

    /**
     * Initializes the game pane with a black border on the top right.
     */
    private void initializeGamePane() {
        gamePane = new GridPane();
        gamePane.getStyleClass().add("grid");
        gamePane.prefWidthProperty().bind(mainLayout.widthProperty());
    }

    /**
     * Initializes the tower pane with a black border on the top right.
     */
    private void initializeTowerPane() {
        towerPane = new GridPane();
        towerPane.getStyleClass().add("grid");
        towerPane.prefWidthProperty().bind(infoLayout.widthProperty());
    }

    /**
     * Plays the animation with the resource and cart keyframes. Stops until
     * either a {@link seng201.team8.models.Cart} reached the end or all the carts get filled.
     */
    private void playAnimation() {
        Timeline timeline = new Timeline();

        KeyFrame resourceKeyFrame = getResourceKeyFrame(timeline);
        KeyFrame cartKeyFrame = getCartKeyFrame(timeline);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(resourceKeyFrame);
        timeline.getKeyFrames().add(cartKeyFrame);
        timeline.play();
    }

    /**
     * Creates a {@link KeyFrame} for the first phase of a turn, generating
     * resources. However, if a cart reached the end, the animation stops, and
     * the player is defeated.
     * @param timeline {@link Timeline}
     * @return {@link KeyFrame}
     */
    private KeyFrame getResourceKeyFrame(Timeline timeline) {
        return new KeyFrame(
                Duration.seconds(0.3),
                event -> {
                    if(roundEvaluationService.didCartReach()){
                        timeline.stop();
                        finishGame(false);
                    }
                    roundEvaluationService.incrementCounter();
                    roundCounterLabel.setText("Round: " + roundEvaluationService.getCounter());
                    roundEvaluationService.produceResources();
                    updateResourceLabels();
                }
        );
    }

    /**
     * Creates a {@link KeyFrame} for the second phase of a turn, filling and
     * advancing carts. However, if all the carts are filled up,
     * the animation stops, and the player gains a victory.
     * @param timeline {@link Timeline}
     * @return {@link KeyFrame}
     */
    private KeyFrame getCartKeyFrame(Timeline timeline) {
        return new KeyFrame(
                Duration.seconds(0.5),
                event -> {
                    roundEvaluationService.fillCarts();
                    if (roundEvaluationService.areAllCartsFull()) {
                        timeline.stop();
                        finishGame(true);
                    }
                    roundEvaluationService.advanceCarts();
                    updateGamePane();
                    updateResourceTableNull();
                }
        );
    }

    /**
     * Creates {@link seng201.team8.models.Resource} {@link Label}s to be used in the tower pane.
     */
    private void createResourceLabels() {
        for(int i = 0; i < gameManager.getInventoryManager().getInventoryData().getMainTowers().length; i++){
            Label tempLabel = new Label();
            resourceLabels.add(tempLabel);
            towerPane.add(tempLabel, 0, (i*2)+1);
        }
    }

    /**
     * Creates the constraints of a table to be used in the game pane.
     * The number of columns will be the distance allowed + 1 and the
     * number of rows will be the number of carts.
     */
    private void createTableConstraints() {
        numTableCols = round.getDistanceAllowed()+1;
        numTableRows = round.getCartNumber();
        for (int i = 0; i < numTableCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numTableCols);
            gamePane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numTableRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numTableRows);
            gamePane.getRowConstraints().add(rowConst);
        }
    }

    /**
     * Stores the result of the round in the
     * {@link GameManager} and Launches to the "Round End" Screen.
     * @param gameResult {@link Boolean}
     */
    private void finishGame(boolean gameResult) {
        if (gameResult){
            gameManager.setRoundWon(gameResult);
            gameManager.getGameGUIManager().launchScreen("Round End");
        }
        else{
            gameManager.getGameGUIManager().launchScreen("Game Result");
        }

    }

    /**
     * Updates the game pane. This is done by clearing the pane, creating
     * a new table and updating the table.
     */
    private void updateGamePane(){
        clearGamePane();
        createGameTable();
        updateGameTable();
    }

    /**
     * Clears the game pane children elements.
     */
    private void clearGamePane(){
        gamePane.getChildren().clear();
    }

    /**
     * Creates the table to be used in the game pane.
     * The panes will be styled with black borders on the left and bottom.
     * The panes at the very end which represent the end line will be styled
     * with black borders on the left and bottom and the background will be red.
     */
    private void createGameTable() {
        for (int i = 0; i < numTableCols-1; i++) {
            for (int j = 0; j < numTableRows; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cell");
                gamePane.add(pane, i, j);
            }
        }
        for (int j = 0; j < numTableRows; j++) {
            Pane pane = new Pane();
            pane.getStyleClass().add("end-cell");
            gamePane.add(pane, numTableCols-1, j);
        }
    }

    /**
     * Updates the game table by creating new {@link ImageView}s of carts
     * and displaying the stats of each cart above the ImageView with {@link Label}.
     */
    private void updateGameTable(){
        for(int i = 0; i < round.getCartNumber(); i++){
            cartLabels.get(i).setText(roundEvaluationService.getCarts()[i].toString());
            ImageView cartImageView = new ImageView("/images/carts/"+ round.getCarts()[i].getResourceType().name().toLowerCase() +".png");
            cartImageView.setFitHeight(50);
            cartImageView.setFitWidth(50);
            gamePane.add(cartImageView, roundEvaluationService.getCarts()[i].getDistance(), i);
            gamePane.add(cartLabels.get(i), roundEvaluationService.getCarts()[i].getDistance() , i);
        }
    }

    /**
     * Displays "" for each resource {@link Label}.
     */
    private void updateResourceTableNull(){
        for (Label resourceLabel : resourceLabels) {
            resourceLabel.setText("");
        }
    }

    /**
     * Updates the resource Labels to show the amount resources
     * that a {@link Tower} has produced per turn.
     */
    private void updateResourceLabels(){
        boolean[] towerProduced = roundEvaluationService.getTowerProduced();
        for(int i = 0; i < resourceLabels.size(); i++){
            Tower tower = gameManager.getInventoryManager().getInventoryData().getMainTowers()[i];
            if(towerProduced[i]){
                resourceLabels.get(i).setText("+" + tower.getTowerStats().getResourceAmount());
            } else if(tower == null) {
                resourceLabels.get(i).setText("");
            } else  {
                resourceLabels.get(i).setText("");
            }
        }
    }

    /**
     * Creates the cart {@link Label}s.
     */
    private void createCartLabels(){
        for (int i = 0; i < round.getCartNumber(); i++){
            Label tempLabel = new Label(roundEvaluationService.getCarts()[i].toString());
            tempLabel.setWrapText(true);
            tempLabel.setPadding(new Insets(10, 30, 10, 10));
            tempLabel.setStyle("-fx-font: 8 arial;");
            cartLabels.add(tempLabel);
        }
    }

    /**
     * Creates the tower {@link ImageView}s in the tower pane.
     */
    private void createTowerImageViews(){
        for(int i = 0; i < gameManager.getInventoryManager().getInventoryData().getMainTowers().length; i++){
            Tower tower = gameManager.getInventoryManager().getInventoryData().getMainTowers()[i];
            if(tower != null) {
                ImageView towerImageView = getImageView(tower);
                GridPane.setHalignment(towerImageView, HPos.CENTER);
                towerPane.add(towerImageView, 0, i*2);
            }
        }
    }

    /**
     * Gets an {@link ImageView} for a tower.
     * If it is broken, a greyscale filter is applied over the ImageView.
     * @param tower {@link Tower}
     * @return {@link ImageView}
     */
    private static ImageView getImageView(Tower tower) {
        ImageView towerImageView = new ImageView("/images/towers/"+ tower.getTowerStats().getResourceType().name().toLowerCase() + ".jpg");
        if(tower.isBroken()) {
            ColorAdjust brokenFilter = new ColorAdjust();
            brokenFilter.setSaturation(-1);
            towerImageView.setEffect(brokenFilter);
        }
        towerImageView.setFitHeight(95);
        towerImageView.setFitWidth(300);
        towerImageView.setPreserveRatio(true);
        return towerImageView;
    }

    /**
     * Creates the constraints of a table to be used in the tower pane.
     * The number of columns will be 1 and the number of rows is
     * the length of main towers * 2.
     */
    private void createTowerConstraints(){
        numTowerRows = gameManager.getInventoryManager().getInventoryData().getMainTowers().length * 2;
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(100);
        colConst.setHalignment(HPos.CENTER);
        towerPane.getColumnConstraints().add(colConst);
        for (int i = 0; i < numTowerRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            if(i % 2 == 0){
                rowConst.setPercentHeight(15);
            } else {
                rowConst.setPercentHeight(5);
            }
            rowConst.setValignment(VPos.CENTER);
            towerPane.getRowConstraints().add(rowConst);
        }
    }

    /**
     * Creates the table to be used in the tower pane.
     * The panes will be styled with black borders on the left and bottom.
     */
    private void createTowerTable() {
        for (int j = 0; j < numTowerRows; j++) {
            Pane pane = new Pane();
            pane.getStyleClass().add("cell");
            towerPane.add(pane, 0, j);
        }
    }
}
