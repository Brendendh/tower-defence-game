package seng201.team8.gui;

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
import seng201.team8.services.GameManager;
import seng201.team8.services.InventoryManager;
import seng201.team8.services.ShopManager;

import java.util.List;
import java.util.Objects;

/**
 * The controller class responsible for handling the communication between the GUI elements of the Shop screen and
 * the {@link ShopManager}.
 */
public class ShopScreenController {
    /**
     * The different shop {@link Button}
     */
    @FXML
    private Button buyButton, refreshButton, returnButton, sellButton;
    /**
     * The {@link Button} that represent the different {@link Tower} on sale
     */
    @FXML
    private Button firstTowerOnSale, secondTowerOnSale, thirdTowerOnSale;
    /**
     * The {@link Button} that represent the different {@link Upgrade} on sale
     */
    @FXML
    private Button firstUpgradeOnSale, secondUpgradeOnSale, thirdUpgradeOnSale;
    /**
     * The {@link Button} that represents the player's main {@link Tower}
     */
    @FXML
    private Button mainTower1, mainTower2, mainTower3, mainTower4, mainTower5;
    /**
     * The {@link Button} that represents the player's reserve {@link Tower}
     */
    @FXML
    private Button reserveTower1, reserveTower2, reserveTower3, reserveTower4, reserveTower5;
    /**
     * The {@link Label}s that display the player's current money and points respectively.
     */
    @FXML
    private Label moneyAmountDisplay, pointAmountDisplay;
    /**
     * The {@link ListView} that displays the players owned {@link Upgrade}
     */
    @FXML
    private ListView<Upgrade> upgradesListView;
    /**
     * The game {@link InventoryManager}, obtained from the {@link GameManager}
     */
    private final InventoryManager inventoryManager;
    /**
     * The game's {@link GameManager}
     */
    private final GameManager gameManager;
    /**
     * A {@link List} of the player {@link Tower} buttons, index 0-4 are for main towers and index 5-9 are for reserve
     * towers.
     */
    private List<Button> towerButtons;
    /**
     * A {@link List} of the different {@link Item}s buttons on sale in the shop, index 0-2 are for sold {@link Tower}
     * and index 3-5 are for sold {@link Upgrade}
     */
    private List<Button> shopButtons;
    /**
     * The Shop's {@link ShopManager}
     */
    private ShopManager shopManager;
    /**
     * The selected shop {@link Item} index, is set to -1 at the start when no items are selected
     */
    private int selectedShopItemIndex;
    /**
     * An Array of {@link Item} that are on sale, index 0-2 are for sold {@link Tower}and index 3-5 are for sold
     * {@link Upgrade}.
     */
    private Item[] itemsOnSale;
    /**
     * A {@link String} to let the controller know which item type is currently being selected by the player.
     * @see ShopScreenController#sellSelectedItem(ActionEvent)
     */
    private String selectedInventoryItemType;
    /**
     * The selected inventory {@link Item} index.
     */
    private int selectedInventoryItemIndex;

    /**
     * Buys the {@link Item} indexed at {@link ShopScreenController#selectedInventoryItemIndex i} when the
     * {@link ShopScreenController#buyButton} is pressed by the player.
     * <p></p>
     * If i is -1, that means no item is currently selected and {@link ShopScreenController#errorPopUp(String)} is called
     * to display the error.
     * <p></p>
     * If i is between 0 and 2 inclusively, it means the selected item type is a {@link Tower} and
     * {@link ShopScreenController#buyTower(int)} is called.
     * <p></p>
     * If i is between 3 and 6 inclusively, it means the selected item type is an {@link Upgrade} and
     * {@link ShopScreenController#buyUpgrade(int)} is called.
     * <p></p>
     * After every successful purchase, i is then set back to -1 to show that no item is currently selected.
     * @param event
     */
    @FXML
    void buySelectedItem(ActionEvent event) {
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

    /**
     * Buys the selected {@link Upgrade} indexed by the parameter upgradeIndex. Calls the
     * {@link ShopManager#buyUpgrade(int)} method and catches all the thrown exceptions by calling
     * {@link ShopScreenController#errorPopUp(String)} to display the error to the player.
     * <p></p>
     * After a successful purchase, updates the shop view by calling the respective update methods.
     * @param upgradeIndex the selected Upgrade's index
     * @see ShopScreenController#updateShopDisplay()
     * @see ShopScreenController#updateUpgradeViewList()
     * @see ShopScreenController#updatePlayerCurrencyLabels()
     */
    private void buyUpgrade(int upgradeIndex) {
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
    /**
     * Buys the selected {@link Tower} indexed by the parameter upgradeIndex. Calls the
     * {@link ShopManager#buyTower(int)} method and catches all the thrown exceptions by calling
     * {@link ShopScreenController#errorPopUp(String)} to display the error to the player.
     * <p></p>
     * After a successful purchase, updates the shop view by calling the respective update methods.
     * @param towerIndex the selected Tower's index
     * @throws NotEnoughCurrencyException
     * @see ShopScreenController#updateShopDisplay()
     * @see ShopScreenController#updateMainTowers()
     * @see ShopScreenController#updateReserveTowers()
     * @see ShopScreenController#updatePlayerCurrencyLabels()
     */
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

    /**
     * The main {@link Exception} handling method used to handle all thrown exceptions.
     * <p></p>
     * Creates a new {@link DialogPane} popup to display the error message to the player.
     * @param errorMessage the error message {@link String} to display
     */
    private void errorPopUp(String errorMessage){
        Dialog<?> errorPane = new Dialog<>();
        errorPane.setContentText(errorMessage);
        errorPane.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        errorPane.show();
    }

    /**
     * Calls the {@link ShopManager#refresh()} method when {@link ShopScreenController#refreshButton}
     * is pressed by the player.
     * <p></p>
     * Calls {@link ShopScreenController#errorPopUp(String)} to handle the potentially thrown
     * {@link NotEnoughCurrencyException} thrown by the refresh method.
     * @param event
     */
    @FXML
    void refreshShop(ActionEvent event){
        try{
            shopManager.refresh();
            updateShopDisplay();
            updatePlayerCurrencyLabels();
        }
        catch (NotEnoughCurrencyException e){
            errorPopUp(e.getMessage());
        }
    }

    /**
     * Switches the scene to "Game Menu" when the return button is pressed.
     * @param event
     */
    @FXML
    void returnToGameMenu(ActionEvent event) {
        gameManager.getGameGUIManager().launchScreen("Game Menu");
    }

    /**
     * Sells the selected inventory {@link Item} when {@link ShopScreenController#sellButton} is pressed by the player.
     * <p></p>
     * Calls {@link ShopScreenController#sellTower(int)} if {@link ShopScreenController#selectedInventoryItemType} is
     * {@link Tower} and {@link ShopScreenController#sellUpgrade(int)} if it's an {@link Upgrade} otherwise.
     * <p></p>
     * Calls {@link ShopScreenController#errorPopUp(String)} to handle the potentially thrown {@link SellingNullError}
     * @param event
     */
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

    /**
     * Passes on the parameter upgradeIndex to {@link ShopManager#sellUpgrade(int)} to sell the {@link Upgrade} at the
     * index.
     * <p></p>
     * Updates the shop view by calling the respective update methods after a successful sale.
     * @see ShopScreenController#updatePlayerCurrencyLabels()
     * @see ShopScreenController#updateUpgradeViewList()
     * @param upgradeIndex the index of the sold Upgrade
     * @throws SellingNullError
     */
    private void sellUpgrade(int upgradeIndex) throws SellingNullError{
        shopManager.sellUpgrade(upgradeIndex);
        updatePlayerCurrencyLabels();
        updateUpgradeViewList();
    }
    /**
     * Takes in a parameter {@link Integer towerIndex} and calls either {@link ShopManager#sellMainTower(int)} or
     * {@link ShopManager#sellReserveTower(int)} depending if towerIndex is less than 5.
     * <p></p>
     * Calls {@link ShopScreenController#errorPopUp(String errorMessage)} to handle the different exceptions thrown.
     * <p></p>
     * Updates the shop view by calling the respective update methods after a successful sale.
     * @see ShopScreenController#updatePlayerCurrencyLabels()
     * @see ShopScreenController#updateMainTowers()
     * @see ShopScreenController#updateReserveTowers()
     * @param towerIndex the index of the sold Tower
     * @throws SellingNullError
     */
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

    /**
     * The constructor for the ShopScreenController.
     * <p></p>
     * Takes in the game's {@link GameManager} and stores it. Then sets {@link ShopScreenController#inventoryManager} by
     * calling {@link GameManager#getInventoryManager()}.
     * @param gameManager the game's {@link GameManager}
     */
    public ShopScreenController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.inventoryManager = gameManager.getInventoryManager();
    }

    /**
     * Updates the display of the player's main {@link Tower}. Sets the border of the button to reflect the tower's
     * {@link Rarity} and displays the tower's name and selling price.
     * <p></p>
     * If the tower is destroyed or if it is an empty slot instead, it will display the appropriate message on the button.
     */
    private void updateMainTowers(){
        Tower[] mainTowers = inventoryManager.getInventoryData().getMainTowers();
        for (int i = 0; i < mainTowers.length; i++){
            if (mainTowers[i] != null){
                towerButtons.get(i).setText(mainTowers[i].getName() +"\n" + "Lvl: "+ mainTowers[i].getLevel()+
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
    /**
     * Updates the display of the player's reserve {@link Tower}. Sets the border of the button to reflect the tower's
     * {@link Rarity} and displays the tower's name and selling price.
     * <p></p>
     * If the tower is destroyed or if it is an empty slot instead, it will display the appropriate message on the button.
     */
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
    /**
     * Updates the display of {@link ShopScreenController#upgradesListView}.
     */
    private void updateUpgradeViewList(){
        upgradesListView.setItems(FXCollections.observableArrayList(inventoryManager.getInventoryData().getUpgrades()));
    }

    /**
     * Updates the display of the player's currencies.
     */
    private void updatePlayerCurrencyLabels(){
        moneyAmountDisplay.setText(""+gameManager.getGameData().getMoney());
        pointAmountDisplay.setText(""+gameManager.getGameData().getPoint());
    }

    /**
     * Sets the text on the shop {@link Button button} to display a simplified description of the respective button's
     * {@link Tower}.
     * @param button The shop button
     * @param tower The shop button respective tower
     */
    private void setTowerSimpleDescription(Button button, Tower tower){
        if (tower != null){
            button.setText(tower.getName()+ "\n" + tower.getBuyingPrice() + " money");
            setStyleToRarity(button,tower.getRarity());
        }
    }
    /**
     * Sets the text on the shop {@link Button button} to display a simplified description of the respective button's
     * {@link Upgrade}.
     * @param button The shop button
     * @param upgrade The shop button respective upgrade
     */
    private void setUpgradeSimpleDescription(Button button, Upgrade upgrade){
        button.setText(upgrade.getEffect().getEffectName()+"\n"+upgrade.getBuyingPrice()+" points");
        setStyleToRarity(button,upgrade.getRarity());
    }
    /**
     * Sets the text on the shop {@link Button button} to display a detailed description of the respective button's
     * {@link Tower}.
     * @param button The shop button
     * @param tower The shop button respective tower
     */
    private void setTowerDetailedDescription(Button button, Tower tower){
        if (tower != null){
            button.setText(tower.getName()+ "\n" + "Produces: "+tower.getTowerStats().getResourceAmount()+" "+tower.getTowerStats().getResourceType()
                    +"\n"+"Cooldown: "+tower.getTowerStats().getCooldown()+"\n"+ tower.getBuyingPrice() + " money");
            setStyleToRarity(button,tower.getRarity());
        }
    }

    /**
     * Updates all the displays of the shop {@link Button} to display their corresponding {@link Item} and their price.
     * <p></p>
     * If the item is sold, displays the text "Sold" in red instead.
     */
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

    /**
     * Sets the border of the button to the color of the {@link Rarity}
     * @param button the button to update the border
     * @param rarity the rarity of the button's item
     * @see Rarity#getRarityTextColor()
     */
    private void setStyleToRarity(Button button, Rarity rarity){
        button.setStyle("-fx-border-color: "+rarity.getRarityTextColor()+"; -fx-border-radius: 5; -fx-border-width: 5");
    }

    /**
     * Sets the player's {@link Tower} buttons on action event to update its visuals,
     * {@link ShopScreenController#selectedInventoryItemType}
     * and {@link ShopScreenController#selectedInventoryItemIndex} when clicked.
     * <p></p>
     * Also makes the buttons have a "toggled" pressed effect to make it easier for the player to see which button is
     * currently selected.
     * <p></p>
     * Called once during initialization.
     * @see ShopScreenController#updateMainTowers()
     * @see ShopScreenController#updateReserveTowers()
     * @see ShopScreenController#setStyleToRarity(Button, Rarity)
     */
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

    /**
     * Sets the shop {@link Button} to properly update the button visuals and {@link ShopScreenController#selectedShopItemIndex}
     * <p></p>
     * Sets the sold {@link Tower} buttons to display a detailed description when hovered over and revert back to
     * a simplified display when no longer hovered over.
     * <p></p>
     * Also makes the buttons have a "toggled" pressed effect to make it easier for the player to see which button is
     * currently selected.
     * <p></p>
     * Called once during initialization.
     * @see ShopScreenController#setTowerDetailedDescription(Button, Tower)
     * @see ShopScreenController#setTowerSimpleDescription(Button, Tower)
     * @see ShopScreenController#updateShopDisplay()
     */
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
                shopButtons.get(i).setOnMouseEntered(event -> setTowerDetailedDescription(shopButtons.get(finalI), (Tower) itemsOnSale[finalI]));
                shopButtons.get(i).setOnMouseExited(event -> {
                    setTowerSimpleDescription(shopButtons.get(finalI), (Tower) itemsOnSale[finalI]);
                    if (finalI == selectedShopItemIndex){
                        setStyleToRarity(shopButtons.get(finalI), itemsOnSale[selectedShopItemIndex].getRarity());
                        shopButtons.get(finalI).setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 5;");
                    }
                });
            }
        }
    }

    /**
     * Immediately sets the {@link ShopScreenController#upgradesListView} cell factory to {@link UpgradeCellFactory}
     * set the selection mode to single and makes sure the list view properly updates
     * {@link ShopScreenController#selectedInventoryItemIndex} and {@link ShopScreenController#selectedInventoryItemType}
     * and then calls {@link ShopScreenController#updateUpgradeViewList()}
     * <p></p>
     * Groups all the buttons into their respective lists and calls {@link ShopScreenController#updateShopButtonOnActionEvent()}
     * and {@link ShopScreenController#updateTowerButtonsOnActionEvent()}.
     * <p></p>
     * Creates a new instance of {@link ShopManager}.
     * <p></p>
     * Calls all update methods to update the visuals of the shop.
     * @see ShopScreenController#updateShopDisplay()
     * @see ShopScreenController#updateMainTowers()
     * @see ShopScreenController#updateReserveTowers()
     * @see ShopScreenController#updateUpgradeViewList()
     * @see ShopScreenController#updateShopDisplay()
     * @see ShopScreenController#updatePlayerCurrencyLabels()
     */
    public void initialize(){
        itemsOnSale = new Item[6];
        //Displays the upgrades in the upgrade list view
        upgradesListView.setCellFactory(new UpgradeCellFactory());
        //Makes the player only able to select one upgrade at a time
        upgradesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //sets the list view to update the selected upgrade index
        upgradesListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldIndex, newIndex) -> {
            selectedInventoryItemIndex = newIndex.intValue();
            selectedInventoryItemType = "Upgrade";
        });
        updateUpgradeViewList();
        //groups buttons together into their respective lists
        shopButtons = List.of(firstTowerOnSale,secondTowerOnSale,thirdTowerOnSale,firstUpgradeOnSale,secondUpgradeOnSale,thirdUpgradeOnSale);
        towerButtons = List.of(mainTower1,mainTower2,mainTower3,mainTower4,mainTower5,reserveTower1,reserveTower2,reserveTower3,reserveTower4,reserveTower5);
        //sets the on action event of the inventory tower buttons to change the selected inventory item type and index
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
