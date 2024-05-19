package seng201.team8.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import seng201.team8.models.Cart;
import seng201.team8.models.Resource;
import seng201.team8.models.Round;
import seng201.team8.services.GameManager;
import seng201.team8.services.RoundEvaluationService;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class GamePlayController {

    @FXML
    private GridPane mainLayout;
    @FXML
    private GridPane resourcePane;

    private GridPane gamePane;

    private GameManager gameManager;
    private Round round;
    private RoundEvaluationService roundEvaluationService;

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
        gamePane.prefWidthProperty().bind(mainLayout.widthProperty());
        cartLabels = new ArrayList<Label>();
        resourceLabels = new ArrayList<Label>();
        gamePane.setGridLinesVisible(true);

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
        createTable();
        gamePane.getStyleClass().add("grid");

        for (int i = 0; i < round.getCartNumber(); i++){
            Label tempLabel = new Label("cart" + i);
            tempLabel.setWrapText(true);
            cartLabels.add(tempLabel);
            gamePane.add(tempLabel, 0, i);
        }
        for(int i = 0; i < gameManager.getDefaultResources().length; i++){
            //Change to toString
            Label tempLabel = new Label(gameManager.getDefaultResources()[i] + " : 0");
            resourceLabels.add(tempLabel);
            resourcePane.add(tempLabel, 0, i);
        }

        mainLayout.getStylesheets().add("/css/GridBorders.css");

        mainLayout.add(gamePane, 1, 0);

        Timeline timeline = new Timeline();
        KeyFrame resourceKeyFrame = new KeyFrame(
                Duration.seconds(0.1),
                event -> {
                    if(roundEvaluationService.didCartReach()){
                        timeline.stop();
                        finishGame(false);
                    }
                    roundEvaluationService.incrementCounter();
                    roundEvaluationService.produceResources();
                    clearResourcePane();
                    updateResourceTable();
                }
        );
        KeyFrame cartKeyFrame = new KeyFrame(
                Duration.seconds(0.5),
                event -> {
                    roundEvaluationService.fillCarts();
                    if (roundEvaluationService.areAllCartsFull()) {
                        timeline.stop();
                        finishGame(true);
                    }
                    roundEvaluationService.advanceCarts();
                    clearGamePane();
                    updateTable();
                    clearResourcePane();
                    updateResourceTable();
                }
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(resourceKeyFrame);
        timeline.getKeyFrames().add(cartKeyFrame);
        timeline.play();
    }

    private void checkGameCondition() {

    }

    private void finishGame(boolean gameResult) {
        gameManager.setGameWon(gameResult);
        gameManager.getGameGUIManager().launchScreen("Round Evaluation");
    }

    private void clearGamePane(){
        gamePane.getChildren().clear();
        createTable();
    }

    private void clearResourcePane(){
        resourcePane.getChildren().clear();
    }

    private void createTable() {
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cell");
                gamePane.add(pane, i, j);
            }
        }
    }

    private void updateTable(){
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

}
