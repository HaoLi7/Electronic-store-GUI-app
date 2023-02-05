package view;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.ElectronicStore;
import model.Product;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This class is the view of electronic store GUI
 */
public class ElectronicStoreView extends Pane {
    private ListView<Product> stockList;
    private ListView<String> cartList;
    private ListView<Product> popularList;
    private Button resetButton;
    private Button addButton;
    private Button removeButton;
    private Button completeButton;
    private Label label3;
    private TextField salesField;
    private TextField revenueField;
    private TextField aveField;

    public ListView<Product> getStockList() { return stockList; }
    public ListView<String> getCartList() { return cartList; }
    public Button getResetButton() { return resetButton; }
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getCompleteButton() { return completeButton; }

    public ElectronicStoreView() {
        Label label1 = new Label("Store Summary:");
        label1.relocate(65, 20);
        Label label2 = new Label("Store Stock:");
        label2.relocate(330, 20);
        label3 = new Label("Current Cart($0.00):");
        label3.relocate(590, 20);
        Label label4 = new Label("# Sales:");
        label4.relocate(50, 47);
        Label label5 = new Label("Revenue:");
        label5.relocate(50, 82);
        Label label6 = new Label("$ / Sale:");
        label6.relocate(50, 117);
        Label label7 = new Label("Most Popular Items:");
        label7.relocate(55, 160);

        salesField = new TextField("0");
        salesField.relocate(110, 40);
        salesField.setPrefSize(100, 30);
        revenueField = new TextField("0.00");
        revenueField.relocate(110, 75);
        revenueField.setPrefSize(100, 30);
        aveField = new TextField("N/A");
        aveField.relocate(110, 110);
        aveField.setPrefSize(100, 30);

        popularList = new ListView<>();
        popularList.relocate(20, 180);
        popularList.setPrefSize(200, 150);
        stockList = new ListView<>();
        stockList.relocate(240, 40);
        stockList.setPrefSize(260, 290);
        cartList = new ListView<>();
        cartList.relocate(520, 40);
        cartList.setPrefSize(260, 290);

        resetButton = new Button("Reset Store");
        resetButton.relocate(55, 340);
        resetButton.setPrefSize(130, 50);
        addButton = new Button("Add to Cart");
        addButton.relocate(305, 340);
        addButton.setPrefSize(130, 50);
        addButton.setDisable(true);
        removeButton = new Button("Remove from Cart");
        removeButton.relocate(520, 340);
        removeButton.setPrefSize(130, 50);
        removeButton.setDisable(true);
        completeButton = new Button("Complete Sale");
        completeButton.relocate(650, 340);
        completeButton.setPrefSize(130, 50);
        completeButton.setDisable(true);

        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, salesField, revenueField, aveField,
                stockList, cartList, popularList, resetButton, addButton, removeButton, completeButton);

        setPrefSize(800, 400);
    }

    /**
     * Update the stockList
     * @param model the updated model of view
     */
    public void stockListUpdate(ElectronicStore model) {
        ArrayList<Product> sList = new ArrayList<Product>();
        Product[] products = model.getStock();
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null) {
                if (products[i].getStockQuantity() > 0)
                    sList.add(products[i]);
            } else
                break;
        }
        stockList.setItems(FXCollections.observableArrayList(sList));
    }

    /**
     * Update the most popular items ListView
     * @param model the updated model of view
     */
    public void popularListUpdate(ElectronicStore model) {
        //Use TreeSet to produce an ordered list, the most popular item comes first
        TreeSet<Product> orderedList = new TreeSet<Product>();
        Product[] products = model.getStock();
        for (Product p: products) {
            if (p != null)
                orderedList.add(p);
            else
                break;
        }
        ArrayList<Product> pList = new ArrayList<Product>();
        int addCount = 0; //Use a count number to ensure there are only three items
        for (Product p: orderedList) {
            pList.add(p);
            addCount++;
            if (addCount == 3)
                break;
        }
        popularList.setItems(FXCollections.observableArrayList(pList));
    }

    /**
     * Update the cartList
     * @param model the updated model of view
     */
    public void cartListUpdate(ElectronicStore model) {
        ArrayList<String> cList = new ArrayList<String>();
        ArrayList<Product> productInCart = model.getCart();
        for (Product p: productInCart)
            cList.add(p.getQuantityInCart() + " x " + p.toString());
        cartList.setItems(FXCollections.observableArrayList(cList));
    }

    /**
     * Update all buttons with certain conditions
     */
    public void buttonUpdate() {
        if (stockList.getSelectionModel().getSelectedIndex() >= 0)
            addButton.setDisable(false);
        else
            addButton.setDisable(true);

        if (cartList.getItems().size() != 0)
            completeButton.setDisable(false);
        else
            completeButton.setDisable(true);

        if (cartList.getSelectionModel().getSelectedIndex() >= 0)
            removeButton.setDisable(false);
        else
            removeButton.setDisable(true);
    }

    /**
     * Update the total price of products in the cart showing beside ‘Current Cart’
     * @param model the updated model of view
     */
    public void priceUpdate(ElectronicStore model) {
        label3.setText("Current Cart($" + String.format("%.2f",model.calculateCart()) + "):");
    }

    /**
     * Update all TextField
     * @param model the updated model of view
     */
    public void textFieldUpdate(ElectronicStore model) {
        if (model.getNumSales() > 0) {
            salesField.setText("" + model.getNumSales());
            revenueField.setText("" + model.getRevenue());
            aveField.setText("" + (model.getRevenue() / model.getNumSales()));
        }
    }

    /**
     * Reset the whole view
     * @param model the updated model of view
     */
    public void reset(ElectronicStore model) {
        stockListUpdate(model);
        popularListUpdate(model);
        cartListUpdate(model);
        priceUpdate(model);
        buttonUpdate();
        salesField.setText("0");
        revenueField.setText("0.00");
        aveField.setText("N/A");
    }
}
