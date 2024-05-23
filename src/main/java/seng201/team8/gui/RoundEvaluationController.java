package seng201.team8.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
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

public class RoundEvaluationController {


    @FXML
    private GridPane mainLayout;
    @FXML
    private GridPane infoLayout;
    @FXML
    private Label roundCounterLabel;

    private GridPane gamePane;
    private GridPane towerPane;

    private final GameManager gameManager;
    private final Round round;
    private final RoundEvaluationService roundEvaluationService;

    private List<Label> cartLabels;
    private List<Label> resourceLabels;

    private int numTableCols;
    private int numTableRows;

    private int numTowerRows;

    public RoundEvaluationController(GameManager gameManager) {
        this.gameManager = gameManager;
        round = gameManager.getRound();
        this.roundEvaluationService = new RoundEvaluationService(gameManager);
    }

    public void initialize(){
        initializeGamePane();
        initializeTowerPane();
        cartLabels = new ArrayList<>();
        resourceLabels = new ArrayList<>();

        createTableConstraints();
        createGameTable();
        createCartLabels();

        createTowerConstraints();
        createTowerTable();
        createTowerImageViews();
        createResourceLabels();

        mainLayout.getStylesheets().add("/css/GridBorders.css");

        mainLayout.add(gamePane, 1, 0);
        infoLayout.add(towerPane, 0, 0);

        playAnimation();
    }

    private void initializeGamePane() {
        gamePane = new GridPane();
        gamePane.getStyleClass().add("grid");
        gamePane.prefWidthProperty().bind(mainLayout.widthProperty());
    }

    private void initializeTowerPane() {
        towerPane = new GridPane();
        towerPane.getStyleClass().add("grid");
        towerPane.prefWidthProperty().bind(infoLayout.widthProperty());
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
                Duration.seconds(0.3),
                event -> {
                    if(roundEvaluationService.didCartReach()){
                        timeline.stop();
                        finishGame(false);
                    }
                    roundEvaluationService.incrementCounter();
                    roundCounterLabel.setText("Round: " + roundEvaluationService.getCounter());
                    roundEvaluationService.produceResources();
                    updateResourceTable();
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
                    updateResourceTableNull();
                }
        );
    }

    private void createResourceLabels() {
        for(int i = 0; i < gameManager.getInventoryManager().getInventoryData().getMainTowers().length; i++){
            //Change to toString
            Label tempLabel = new Label();
            resourceLabels.add(tempLabel);
            towerPane.add(tempLabel, 0, (i*2)+1);
        }
    }

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

    private void finishGame(boolean gameResult) {
        gameManager.setRoundWon(gameResult);
        gameManager.getGameGUIManager().launchScreen("Round End");
    }

    private void updateGamePane(){
        clearGamePane();
        createGameTable();
        updateGameTable();
    }


    private void clearGamePane(){
        gamePane.getChildren().clear();
    }

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

    private void updateResourceTableNull(){
        for (Label resourceLabel : resourceLabels) {
            resourceLabel.setText("");
        }
    }

    private void updateResourceTable(){
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

    private void createCartLabels(){
        for (int i = 0; i < round.getCartNumber(); i++){
            Label tempLabel = new Label(roundEvaluationService.getCarts()[i].toString());
            tempLabel.setWrapText(true);
            tempLabel.setPadding(new Insets(10, 30, 10, 10));
            tempLabel.setStyle("-fx-font: 8 arial;");
            cartLabels.add(tempLabel);
            ImageView cartImageView = new ImageView("/images/carts/"+ round.getCarts()[i].getResourceType().name().toLowerCase() +".png");
            cartImageView.setFitHeight(50);
            cartImageView.setFitWidth(50);
            gamePane.add(cartImageView, roundEvaluationService.getCarts()[i].getDistance(), i);
            gamePane.add(tempLabel, 0, i);
        }
    }

    private void createTowerImageViews(){
        for(int i = 0; i < gameManager.getInventoryManager().getInventoryData().getMainTowers().length; i++){
            Tower tower = gameManager.getInventoryManager().getInventoryData().getMainTowers()[i];
            if(tower != null) {
                ImageView towerImageView = new ImageView("/images/towers/"+tower.getTowerStats().getResourceType().name().toLowerCase() + ".jpg");
                towerImageView.setFitHeight(95);
                towerImageView.setFitWidth(300);
                towerImageView.setPreserveRatio(true);
                GridPane.setHalignment(towerImageView, HPos.CENTER);
                towerPane.add(towerImageView, 0, i*2);
            }
        }
    }

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

    private void createTowerTable() {
        for (int j = 0; j < numTowerRows; j++) {
            Pane pane = new Pane();
            pane.getStyleClass().add("cell");
            towerPane.add(pane, 0, j);
        }
    }
}
