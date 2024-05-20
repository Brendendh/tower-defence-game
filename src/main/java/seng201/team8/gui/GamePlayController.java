package seng201.team8.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import seng201.team8.models.Round;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEvaluationService;

import java.util.ArrayList;
import java.util.List;

public class GamePlayController {

    @FXML
    private GridPane mainLayout;
    @FXML
    private GridPane resourcePane;

    private GridPane gamePane;

    private final GameManager gameManager;
    private final Round round;
    private final RoundEvaluationService roundEvaluationService;

    private List<Label> cartLabels;
    private List<Label> resourceLabels;

    private int numCols;
    private int numRows;

    public GamePlayController(GameManager gameManager) {
        this.gameManager = gameManager;
        round = gameManager.getRound();
        this.roundEvaluationService = new RoundEvaluationService(gameManager);
    }

    public void initialize(){
        gamePane = new GridPane();
        gamePane.getStyleClass().add("grid");
        gamePane.prefWidthProperty().bind(mainLayout.widthProperty());
        cartLabels = new ArrayList<>();
        resourceLabels = new ArrayList<>();

        createTableConstraints();
        createGameTable();

        createCartLabels();
        createResourceLabels();

        mainLayout.getStylesheets().add("/css/GridBorders.css");

        mainLayout.add(gamePane, 1, 0);

        playAnimation();
    }

    private void playAnimation() {
        Timeline timeline = new Timeline();

        KeyFrame resourceKeyFrame = getResourceKeyFrame(timeline);
        KeyFrame cartKeyFrame = getCartKeyFrame(timeline);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(resourceKeyFrame);
        timeline.getKeyFrames().add(cartKeyFrame);
        timeline.play();
    }

    private KeyFrame getResourceKeyFrame(Timeline timeline) {
        return new KeyFrame(
                Duration.seconds(0.1),
                event -> {
                    if(roundEvaluationService.didCartReach()){
                        timeline.stop();
                        finishGame(false);
                    }
                    roundEvaluationService.incrementCounter();
                    roundEvaluationService.produceResources();
                    updateResourcePane();
                }
        );
    }

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
                    updateResourcePane();
                }
        );
    }

    private void createResourceLabels() {
        for(int i = 0; i < gameManager.getDefaultResources().length; i++){
            //Change to toString
            Label tempLabel = new Label(gameManager.getDefaultResources()[i] + " : 0");
            resourceLabels.add(tempLabel);
            resourcePane.add(tempLabel, 0, i);
        }
    }

    private void createTableConstraints() {
        numCols = round.getDistanceAllowed();
        numRows = round.getCartNumber();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gamePane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            gamePane.getRowConstraints().add(rowConst);
        }
    }

    private void finishGame(boolean gameResult) {
        gameManager.setGameWon(gameResult);
        gameManager.getGameGUIManager().launchScreen("Round Evaluation");
    }

    private void updateGamePane(){
        clearGamePane();
        createGameTable();
        updateGameTable();
    }

    private void updateResourcePane(){
        clearResourcePane();
        updateResourceTable();
    }

    private void clearGamePane(){
        gamePane.getChildren().clear();
    }

    private void clearResourcePane(){
        resourcePane.getChildren().clear();
    }

    private void createGameTable() {
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cell");
                gamePane.add(pane, i, j);
            }
        }
    }

    private void updateGameTable(){
        for(int i = 0; i < round.getCartNumber(); i++){
            cartLabels.get(i).setText(roundEvaluationService.getCarts()[i].toString());
            gamePane.add(cartLabels.get(i), roundEvaluationService.getCarts()[i].getDistance() , i);
        }
    }

    private void updateResourceTable(){
        for(int i = 0; i < roundEvaluationService.getResourcesProduced().size(); i++){
            resourceLabels.get(i).setText(gameManager.getDefaultResources()[i] + " : " +roundEvaluationService.getResourcesProduced().get(gameManager.getDefaultResources()[i]).toString());
            resourcePane.add(resourceLabels.get(i), 0, i);
        }
    }

    private void createCartLabels(){
        for (int i = 0; i < round.getCartNumber(); i++){
            Label tempLabel = new Label("cart" + i);
            tempLabel.setWrapText(true);
            cartLabels.add(tempLabel);
            gamePane.add(tempLabel, 0, i);
        }
    }

}
