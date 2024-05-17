package seng201.team8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import seng201.team8.models.Cart;
import seng201.team8.models.Round;
import seng201.team8.services.GameManager;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class GamePlayController {

    @FXML
    private GridPane mainLayout;

    private GridPane gamePane;

    private GameManager gameManager;
    private Round round;

    private List<Label> cartLabels;

    public GamePlayController(GameManager gameManager) {
        this.gameManager = gameManager;
        round = gameManager.getRound();
    }

    public void initialize(){
        gamePane = new GridPane();
        gamePane.prefWidthProperty().bind(mainLayout.widthProperty());
        cartLabels = new ArrayList<Label>();
        gamePane.setGridLinesVisible(true);

        final int numCols = round.getDistanceAllowed();
        final int numRows = round.getCartNumber();
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


        for (int i = 0; i < round.getCartNumber(); i++){
            Label tempLabel = new Label("cart" + i);
            cartLabels.add(tempLabel);
            gamePane.add(tempLabel, 0, i);
        }
        mainLayout.add(gamePane, 1, 0);

    }
}
