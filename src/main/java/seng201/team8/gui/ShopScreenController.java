package seng201.team8.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import seng201.team8.exceptions.BuyingNullError;
import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.exceptions.NotEnoughCurrencyException;
import seng201.team8.exceptions.SellingNullError;
import seng201.team8.models.*;
import seng201.team8.services.GameGUIManager;
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.ShopManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class ShopScreenController {

    @FXML
    private Button buyButton, refreshButton, returnButton, sellButton;

    @FXML
    private Button firstTowerOnSale, secondTowerOnSale, thirdTowerOnSale;

    @FXML
    private Button firstUpgradeOnSale, secondUpgradeOnSale, thirdUpgradeOnSale;

    @FXML
    private Button mainTower1, mainTower2, mainTower3, mainTower4, mainTower5;

    @FXML
    private Button reserveTower1, reserveTower2, reserveTower3, reserveTower4, reserveTower5;

    @FXML
    private Label moneyAmountDisplay, pointAmountDisplay;

    @FXML
    private ListView<Upgrade> upgradesListView;

    private final InventoryManager inventoryManager;
    private final GameManager gameManager;
    private List<Button> towerButtons;
    private List<Button> shopButtons;
    private ShopManager shopManager;

    private int selectedShopItemIndex;

    private Item[] itemsOnSale;

    private String selectedInventoryItemType;
    private int selectedInventoryItemIndex;

    @FXML
    void buySelectedItem(ActionEvent event) throws NoSpaceException, NotEnoughCurrencyException {
        if (selectedShopItemIndex == -1){
            errorPopUp("Why are you trying to buy nothing?");
        }
        else{
            if (selectedShopItemIndex < 3 && selectedShopItemIndex >= 0){
                buyTower(selectedShopItemIndex);
                selectedShopItemIndex = -1;
            } else if (selectedShopItemIndex >= 3 && selectedShopItemIndex < 6) {
                buyUpgrade(selectedShopItemIndex-3);
                selectedShopItemIndex = -1;
            }
        }
    }

    private void buyUpgrade(int upgradeIndex) throws NotEnoughCurrencyException {
        try{
            shopManager.buyUpgrade(upgradeIndex);
        }
        catch (NotEnoughCurrencyException notEnoughCurrencyException){
            errorPopUp("Not enough points to purchase upgrade!");
        } catch (BuyingNullError e) {
            errorPopUp(e.getMessage());
        }
        updateUpgradeViewList();
        updateShopDisplay();
        updatePlayerCurrencyLabels();
    }

    private void buyTower(int towerIndex){
        try{
            shopManager.buyTower(towerIndex);
        }
        catch (NoSpaceException | NotEnoughCurrencyException | BuyingNullError exception){
            errorPopUp(exception.getMessage());
        }
        updateMainTowers();
        updateReserveTowers();
        updateShopDisplay();
        updatePlayerCurrencyLabels();
    }

    private void errorPopUp(String errorMessage){
        Dialog<?> errorPane = new Dialog<>();
        errorPane.setContentText(errorMessage);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        errorPane.show();
    }

    @FXML
    void refreshShop(ActionEvent event) throws NotEnoughCurrencyException {
        try{
            shopManager.refresh();
            updateShopDisplay();
            updatePlayerCurrencyLabels();
        }
        catch (NotEnoughCurrencyException e){
            errorPopUp(e.getMessage());
        }
    }

    @FXML
    void returnToGameMenu(ActionEvent event) {
        gameManager.getGameGUIManager().launchScreen("Game Menu");
    }

    @FXML
    void sellSelectedItem(ActionEvent event) {
        try{
            if (Objects.equals(selectedInventoryItemType, "Tower")){
                sellTower(selectedInventoryItemIndex);
            }
            else if (Objects.equals(selectedInventoryItemType, "Upgrade")) {
                sellUpgrade(selectedInventoryItemIndex);
            }
        } catch (SellingNullError e) {
            errorPopUp(e.getMessage());
        }
    }

    private void sellUpgrade(int upgradeIndex) throws SellingNullError{
        shopManager.sellUpgrade(upgradeIndex);
        updatePlayerCurrencyLabels();
        updateUpgradeViewList();
    }
    private void sellTower(int towerIndex) throws SellingNullError {
        if (towerIndex < 5 && towerIndex >= 0){
            shopManager.sellMainTower(towerIndex);
        } else if (towerIndex >= 5 && towerIndex < 10) {
            shopManager.sellReserveTower(towerIndex-5);
        }
        updatePlayerCurrencyLabels();
        updateMainTowers();
        updateReserveTowers();
    }


    public ShopScreenController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.inventoryManager = gameManager.getInventoryManager();
    }

    private void updateMainTowers(){
        Tower[] mainTowers = inventoryManager.getInventoryData().getMainTowers();
        for (int i = 0; i < mainTowers.length; i++){
            if (mainTowers[i] != null){
                towerButtons.get(i).setText(mainTowers[i].getName() +"\n" + "Lvl: "+ mainTowers[i].getLevel()+"" +
                        "\n"+ "Sells for: "+mainTowers[i].getSellingPrice()+" money");
                if (mainTowers[i].isBroken()){
                    towerButtons.get(i).setText("DESTROYED");
                }
                setStyleToRarity(towerButtons.get(i), mainTowers[i].getRarity());
            }
            else{
                towerButtons.get(i).setText("Empty");
                towerButtons.get(i).setTextFill(Color.color(0,0,0));
                towerButtons.get(i).setStyle("");
            }
        }
    }

    private void updateReserveTowers(){
        Tower[] reserveTowers = inventoryManager.getInventoryData().getReserveTowers();
        for (int i = 0; i < reserveTowers.length; i++){
            if (reserveTowers[i] != null){
                towerButtons.get(i+5).setText(reserveTowers[i].getName()+ "\n" + "Lvl: " + reserveTowers[i].getLevel()+
                        "\n"+ "Sells for: "+reserveTowers[i].getSellingPrice()+" money");
                if (reserveTowers[i].isBroken()){
                    towerButtons.get(i+5).setText("DESTROYED");
                }
                setStyleToRarity(towerButtons.get(i+5),reserveTowers[i].getRarity());
            }
            else{
                towerButtons.get(i+5).setText("Empty");
                towerButtons.get(i+5).setTextFill(Color.color(0,0,0));
                towerButtons.get(i+5).setStyle("");
            }
        }
    }
    private void updateUpgradeViewList(){
        upgradesListView.setItems(FXCollections.observableArrayList(inventoryManager.getInventoryData().getUpgrades()));
    }

    private void updatePlayerCurrencyLabels(){
        moneyAmountDisplay.setText(""+gameManager.getGameData().getMoney());
        pointAmountDisplay.setText(""+gameManager.getGameData().getPoint());
    }
    private void setTowerSimpleDescription(Button button, Tower tower){
        button.setText(tower.getName()+ "\n" + tower.getBuyingPrice() + " money");
        setStyleToRarity(button,tower.getRarity());
    }

    private void setUpgradeSimpleDescription(Button button, Upgrade upgrade){
        button.setText(upgrade.getEffect().getEffectName()+"\n"+upgrade.getBuyingPrice()+" points");
        setStyleToRarity(button,upgrade.getRarity());
    }

    private void setTowerDetailedDescription(Button button, Tower tower){
        button.setText(tower.getName()+ "\n" + "Produces: "+tower.getTowerStats().getResourceAmount()+" "+tower.getTowerStats().getResourceType()
                +"\n"+"Cooldown: "+tower.getTowerStats().getCooldown()+"\n"+ tower.getBuyingPrice() + " money");
        setStyleToRarity(button,tower.getRarity());
    }
    private void updateShopDisplay(){
        Tower[] towersSold = shopManager.getTowersSold();
        Upgrade[] upgradesSold = shopManager.getUpgradesSold();
        for (int i = 0; i < towersSold.length; i++){
            itemsOnSale[i] = towersSold[i];
            if (towersSold[i] != null){
                shopButtons.get(i).setTextFill(Color.color(0,0,0));
                setTowerSimpleDescription(shopButtons.get(i),towersSold[i]);
            }
            else{
                shopButtons.get(i).setText("Sold");
                shopButtons.get(i).setTextFill(Color.color(1,0,0));
                shopButtons.get(i).setStyle("");
            }
        }
        for (int i = 0; i < upgradesSold.length; i++){
            itemsOnSale[i+3] = upgradesSold[i];
            if (upgradesSold[i] != null){
                shopButtons.get(i+3).setTextFill(Color.color(0,0,0));
                setUpgradeSimpleDescription(shopButtons.get(i+3), upgradesSold[i]);
            }
            else{
                shopButtons.get(i+3).setText("Sold");
                shopButtons.get(i+3).setTextFill(Color.color(1,0,0));
                shopButtons.get(i+3).setStyle("");
            }
        }
    }

    private void setStyleToRarity(Button button, Rarity rarity){
        button.setStyle("-fx-border-color: "+rarity.getRarityTextColor()+"; -fx-border-radius: 5; -fx-border-width: 5");
    }

    private void updateTowerButtonsOnActionEvent() {
        for (int i = 0; i < towerButtons.size(); i++){
            int finalI = i;
            towerButtons.get(i).setOnAction(event ->{
                selectedInventoryItemType = "Tower";
                selectedInventoryItemIndex = finalI;
                updateMainTowers();
                updateReserveTowers();
                towerButtons.get(finalI).setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            });
        }
    }

    private void updateShopButtonOnActionEvent() {
        for (int i = 0; i < shopButtons.size(); i++) {
            int finalI = i;
            shopButtons.get(i).setOnAction(event -> {
                selectedShopItemIndex = finalI;
                updateShopDisplay();
                shopButtons.get(finalI).setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
            });
            //make the towers sold show detailed descriptions when mouse hovers over button
            if (finalI < 3){
                shopButtons.get(i).setOnMouseEntered(event -> {
                    setTowerDetailedDescription(shopButtons.get(finalI), (Tower) itemsOnSale[finalI]);
                });
                shopButtons.get(i).setOnMouseExited(event -> {
                    setTowerSimpleDescription(shopButtons.get(finalI), (Tower) itemsOnSale[finalI]);
                    if (finalI == selectedShopItemIndex){
                        shopButtons.get(finalI).setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
                    }
                });
            }
        }
    }


    public void initialize(){
        itemsOnSale = new Item[6];
        //Displays the upgrades in the upgrade list view
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        //Makes the player only able to select one upgrade an a time
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //sets the list view to update the selected upgrade index
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                selectedInventoryItemIndex = newIndex.intValue();
                selectedInventoryItemType = "Upgrade";
            }
        });
        updateUpgradeViewList();
        //groups buttons together into their respective lists
        shopButtons = List.of(firstTowerOnSale,secondTowerOnSale,thirdTowerOnSale,firstUpgradeOnSale,secondUpgradeOnSale,thirdUpgradeOnSale);
        towerButtons = List.of(mainTower1,mainTower2,mainTower3,mainTower4,mainTower5,reserveTower1,reserveTower2,reserveTower3,reserveTower4,reserveTower5);
        //sets the on action event of the inventory tower buttons to change the selected inven item type and index
        updateTowerButtonsOnActionEvent();
        //displays the player's main towers
        updateMainTowers();
        //displays the player's reserve towers
        updateReserveTowers();
        //displays the player currencies
        updatePlayerCurrencyLabels();
        //creates new shopManager
        shopManager = new ShopManager(this.gameManager);
        //update the shop display
        updateShopDisplay();
        //set the on action event of the shop buttons
        updateShopButtonOnActionEvent();
        selectedShopItemIndex = -1;
    }
}
