/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// thing to do still" 
//  - select delete error message. 
//  - save modify changes in modify part and product. 
//  - select part and place in product's part list. 


package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 *
 * @author kortneyfield
 */
public class MainMenuController implements Initializable {
    
    
    
    @FXML
    private Label label;

    @FXML
    private Button ExitButton;

    @FXML
    private Button AddPartButton;

    @FXML
    private Button ModifyPartButton;

    @FXML
    private Button DeletePartButton;

    @FXML
    private TableView<Part> PartTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInventoryCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button ModifyProductButton;

    @FXML
    private Button DeleteProductButton;

    @FXML
    private TableView<Product> ProductTableView;

    @FXML
    private TableColumn<Product, Integer> ProductIdCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventorycol;

    @FXML
    private TableColumn<Product, Double> ProductPriceCol;
    
    @FXML
    private TextField searchPartText;
    
    @FXML
    private TextField searchProductText;
    
    

    @FXML
    void OnActionDisplayCreateAddPart(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow(); 
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show(); 
    }
    
    @FXML
    void OnActionDisplayCreateAddProduct(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow(); 
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show(); 
    }
    
    @FXML
    void OnActionDisplayModifyPart(ActionEvent event) throws IOException {
        
        Part part = PartTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error in Product Selection");
            errorAlert.setHeaderText("No Product Selected to modify");
            errorAlert.setContentText("You must click on and select a product to modify.");
            errorAlert.showAndWait();
            return; 
        }
     
        FXMLLoader loader = new FXMLLoader(); 
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load(); 
        
        ModifyPartController ADMController = loader.getController(); 
        ADMController.setPartToModify(part);
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot(); 
        stage.setScene(new Scene(scene)); 
        stage.show();
        
        
            
            
                   
            
        
    }
    
    @FXML
    void OnActionDisplayCreateModifyProduct(ActionEvent event) throws IOException {  
        
        try{
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("/view/ModifyProduct_1.fxml"));
            loader.load(); 
        
            ModifyProductController ADMController = loader.getController(); 
            ADMController.setProductToModify(ProductTableView.getSelectionModel().getSelectedItem());
        
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot(); 
            stage.setScene(new Scene(scene)); 
            stage.show();
            }
        catch (NullPointerException e) 
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error in Product Selection");
            errorAlert.setHeaderText("No Product Selected to modify");
            errorAlert.setContentText("You must click on and select a product to modify.");

            Optional<ButtonType> response = errorAlert.showAndWait();
                if(response.get() == ButtonType.OK){
                   // do nothing

            }    
            }
    }
        
    
    
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        System.out.println("You clicked delete part!");
        label.setText("You clicked delete part!");
       
            Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedPart.getName() + "?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Confirm Part Deletioon");
            confirmAlert.setHeaderText("Deleted items CANNOT be recovered!");
            
            Optional<ButtonType> response = confirmAlert.showAndWait();
            if(response.isPresent() && response.get() == ButtonType.YES){
                    Inventory.deletePart(selectedPart);
                    PartTableView.refresh();
            }else{
                confirmAlert.hide();
            } 
        
        
    }
    
    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        System.out.println("You clicked delete Product!");
        label.setText("You clicked delete Product!");
        
        Product selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedProduct.getName() + "?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Confirm Part Deletioon");
            confirmAlert.setHeaderText("Deleted items CANNOT be recovered!");
            
            Optional<ButtonType> response = confirmAlert.showAndWait();
            if(response.isPresent() && response.get() == ButtonType.YES){
                    Inventory.deleteProduct(selectedProduct);
                    ProductTableView.refresh();
            }else{
                confirmAlert.hide();
            } 
    }

    @FXML
    void onActionSearchPart(ActionEvent event) {
        String name = searchPartText.getText();
        ObservableList<Part> list = Inventory.lookUpPart(name);
        
        //if list is empty, look up by ID
        if (list.isEmpty()) {
            //Change text to a number
            try {
                int partId = Integer.parseInt(name);
                //look up parts
                Part partP = Inventory.lookUpPart(partId); 
                //if not null, add to list
                if (partP != null) {
                    list.add(partP);
                }
            } 
            catch (Exception e) {
                 
            } 
        }
        PartTableView.setItems(list);  
    }
 
    @FXML
    void onActionSearchProduct(ActionEvent event) {
        String name = searchProductText.getText();
        ObservableList<Product> list = Inventory.lookUpProduct(name);
        //if list is empty, look up by ID
        if (list.isEmpty()) {
            //Change text to a number
            try {
                int productId = Integer.parseInt(name);
                //look up parts
                Product productP = Inventory.lookUpProduct(productId); 
                //if not nuluctl, add to list
                if (productP != null) {
                    list.add(productP);
                }
            } 
            catch (Exception e) {
                 
            }    
        }
        ProductTableView.setItems(list);  
    }  
    
    public Part selectPart(int id) {
        for(Part part: Inventory.getAllParts()) {
            if(part.getId()==id)
                return part;
        }
        return null;
    }
    
    public Product selectProduct(int id) {
        for(Product product: Inventory.getAllProducts()) {
            if(product.getId()==id)
                return product;
        }
        return null;
    }
    
    @FXML
    void OnActionExitProgram(ActionEvent event) {
        System.exit(0); 
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        PartTableView.setItems(Inventory.getAllParts());
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
        ProductTableView.setItems(Inventory.getAllProducts());
            ProductIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            ProductInventorycol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
    
        
    }    
    
}
