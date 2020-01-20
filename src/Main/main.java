/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author kortneyfield
 */
public class main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Part part1 = new InHouse(1, "Refridgerator", 22.2, 2, 100, 0, 12);
        Part part2 = new Outsourced(2, "Sink", 112.2, 24, 100, 0, "Ace Hardware");
        Part part3 = new InHouse(3, "Queen Bed", 5.2, 60, 100, 0, 13);
        Part part4 = new InHouse(4, "Dressor Drawer", 5.2, 60, 100, 0, 14);
        Part part5 = new InHouse(5, "Couch", 5.2, 60, 100, 0, 15);
        Part part6 = new InHouse(6, "TV", 5.2, 60, 100, 0, 16);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        
        Product product1 = new Product(1, "Kitchen", 22.2, 2, 100, 0);
        Product product2 = new Product(2, "Bedroom", 112.2, 24, 100, 0);
        Product product3 = new Product(3, "Living Room", 5.2, 60, 100, 0);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        
        launch(args);
    }
    
}
