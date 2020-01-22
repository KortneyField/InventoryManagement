/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static java.util.concurrent.ThreadLocalRandom.current;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class AddProductController implements Initializable {
    
    Stage stage; 
    Parent scene;
    
    @FXML
    private Button BackButton;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField productIdText;

    @FXML
    private TextField productNameText;

    @FXML
    private TextField productInventoryText;

    @FXML
    private TextField productPriceText;

    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

    @FXML
    private Label label;

    @FXML
    private TableView<Part> PartTableMenuView;

    @FXML
    private TableColumn<?, ?> PartIDMenuCol;

    @FXML
    private TableColumn<?, ?> PartNameMenuCol;

    @FXML
    private TableColumn<?, ?> PartInvMenuCol;

    @FXML
    private TableColumn<?, ?> PartPriceMenuCol;

    @FXML
    private TableView<?> PartAddTableView;

    @FXML
    private TableColumn<?, ?> PartIdAddCol;

    @FXML
    private TableColumn<?, ?> PartNameAddCol;

    @FXML
    private TableColumn<?, ?> PartInvAddCol;

    @FXML
    private TableColumn<?, ?> PartPriceAddCol;

    @FXML
    private TextField partSearchText;


    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Confirm Cancel");
        confirmAlert.setHeaderText("Changes will be lost! ");
        Optional<ButtonType> response = confirmAlert.showAndWait();
            if(response.isPresent() && response.get() == ButtonType.YES){
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }else{
                confirmAlert.hide();
            }
    }
    
    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException {
        System.out.println("You clicked the save button");
        label.setText("Your work will be saved");
        
        try {
        int lastProductIndex = Inventory.getAllProducts().size() - 1; // convert to index
        int lastProductId = Inventory.getAllProducts().get(lastProductIndex).getId();
        int id = lastProductId + 1; 
        String name = productNameText.getText();
        double price = Double.parseDouble(productPriceText.getText());
        int stock = Integer.parseInt(productInventoryText.getText());
        int min = Integer.parseInt(productMaxText.getText());
        int max = Integer.parseInt(productMinText.getText());
        boolean productSource; 

        Inventory.addProduct(new Product(id, name, price, stock, max, min));
        
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow(); 
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();  
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Information Entry");
            alert.setContentText("You will no be able to save without correct information");
            alert.showAndWait(); 
        }
        
    }
    
    @FXML
    void onActionAddPartToList(ActionEvent event) {

    }
    
     @FXML
    void onActionDeletePartFromList(ActionEvent event) {

    }
    public Product selectProduct(int id) {
        for(Product product: Inventory.getAllProducts()) {
            if(product.getId()==id)
                return product;
        }
        return null;
    }
    
    @FXML
    void onActionSearchPartListon(ActionEvent event) {
        String name = partSearchText.getText();
        ObservableList<Part> list = Inventory.lookUpPart(name);
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
        PartTableMenuView.setItems(list); 
        
    }
    
    @FXML
    void onActionAddPart(ActionEvent event) {
        System.out.println("you pressed add part to list button");
        
     /*   
        if (PartTableMenuView.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No part found");
            alert.setContentText("please select part");
            alert.showAndWait();
       }
       else{
            Part part = PartTableMenuView.getSelectionModel().getSelectedItem();            
            partsList = Inventory.addPart(part);  
            PartAddTableView.setItems(partsList);
       }
       */ 
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ObservableList partsList = Inventory.getAllParts();
        //productPartList.clear();
        
        PartTableMenuView.setItems(partsList);
            PartIDMenuCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            PartNameMenuCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PartInvMenuCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            PartPriceMenuCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
        
    }    
    
}
