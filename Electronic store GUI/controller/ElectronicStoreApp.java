package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ElectronicStore;
import view.ElectronicStoreView;

/**
 * This class is the controller of electronic store GUI
 */
public class ElectronicStoreApp extends Application {
    private ElectronicStore model;

    public ElectronicStoreApp() { model = ElectronicStore.createStore(); }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        ElectronicStoreView view = new ElectronicStoreView();

        //Update all ListViews
        view.stockListUpdate(model);
        view.popularListUpdate(model);
        view.cartListUpdate(model);

        /**
         * Handler for updating add button when user clicks items at stockList
         */
        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.buttonUpdate();
            }
        });

        /**
         * Handler for adding product to the cart
         */
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.addToCart(view.getStockList().getSelectionModel().getSelectedItem());
                //Update stockList and cartList to provide new information to user
                view.stockListUpdate(model);
                view.cartListUpdate(model);
                //Update the total price showing beside ‘Current Cart’
                view.priceUpdate(model);
                //Update the button to ensure add button to be disabled when no item is selected in stockList,
                //and to ensure complete sale button to be enabled
                view.buttonUpdate();
            }
        });

        /**
         * Handler for updating remove button when user click items at cartList
         */
        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.buttonUpdate();
            }
        });

        /**
         * Handler for removing item in cartList when user clicks the remove button
         */
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.removeFromCart(model.getCart().get(view.getCartList().getSelectionModel().getSelectedIndex()));
                view.stockListUpdate(model);
                view.cartListUpdate(model);
                view.priceUpdate(model);
                view.buttonUpdate();
            }
        });

        /**
         * Handler for simulating the checking out process when user clicks the complete sale button
         */
        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.sellProducts();
                view.textFieldUpdate(model);
                view.cartListUpdate(model);
                view.priceUpdate(model);
                view.popularListUpdate(model);
                view.buttonUpdate();
            }
        });

        /**
         * Handler for resetting the whole view
         */
        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Reset the model to the original electronic store
                model = ElectronicStore.createStore();
                view.reset(model);
            }
        });

        aPane.getChildren().add(view);

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }
}
