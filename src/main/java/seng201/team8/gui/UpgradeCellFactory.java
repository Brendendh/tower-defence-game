package seng201.team8.gui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import seng201.team8.models.Rarity;
import seng201.team8.models.Upgrade;
import javafx.scene.control.Label;

public class UpgradeCellFactory implements Callback<ListView<Upgrade>, ListCell<Upgrade>> {
    @Override
    public ListCell<Upgrade> call(ListView<Upgrade> param){
        return new ListCell<>() {
            @Override
            public void updateItem(Upgrade upgrade, boolean empty) {
                super.updateItem(upgrade, empty);
                //if there is no upgrade, don't display anything
                if (empty || upgrade == null) {
                    setGraphic(null);
                }
                else {
                    //Creates a new HBox to store elements to be displayed
                    HBox hBox = new HBox(5);
                    //Add inner VBox to hold upgrade info
                    VBox vBox = new VBox(5);
                    Label upgradeNameLabel = new Label(upgrade.getEffect().getEffectName());
                    Font font = Font.font("Arial", FontWeight.BOLD, 20);
                    upgradeNameLabel.setFont(font);
                    upgradeNameLabel.setTextFill(upgrade.getRarity().getRarityTextColor());
                    // Add effect description and selling price to the VBox
                    vBox.getChildren().addAll(
                            upgradeNameLabel,
                            new Label((upgrade.toString())),
                            new Label(String.format("Sells for: %s", upgrade.getSellingPrice()))
                    );
                    //Add the image and the VBox to the HBox
                    //TODO : ADD IMAGES TO UPGRADE SO THIS ACTUALLY DISPLAYS IMAGES
                    hBox.getChildren().addAll(vBox);
                    setGraphic(hBox);
                }
            }
        };
    }

}
