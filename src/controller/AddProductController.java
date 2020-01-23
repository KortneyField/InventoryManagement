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
    private TableView<Part> PartAddTableView;

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

    ObservableList partsAddedList = FXCollections.observableArrayList();//gives an empty list
    
     
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
        int max = Integer.parseInt(productMaxText.getText());
        int min = Integer.parseInt(productMinText.getText());
        boolean productSource; 
        
        
        if(min > max){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Min/Max Inventory Error");
            errorAlert.setHeaderText("Min Count is greater than Max Count");
            errorAlert.setContentText("Min Part Count MUST be LESS than Max Part Count");
            errorAlert.showAndWait();
            return; 
            } 
        //break this up in mul lines.  
        Product newProduct = new Product(id, name, price, stock, max, min);
        Inventory.addProduct(newProduct);
        //take the parts in the added list and add to this is newProduct. 
        //ObservableList partsAddedList = Inventory.getAllParts();
        //newProduct.add(partsAddedList);
        
        
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
    void onActionDelete(ActionEvent event) {
        System.out.println("you pressed delete");
        Part selectedPartofProduct = PartAddTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPartofProduct == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error in Part Selection");
            errorAlert.setHeaderText("No Part Selected to modify");
            errorAlert.setContentText("You must click on and select a product to delete.");
            errorAlert.showAndWait();
            return;
            }
        
        
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedPartofProduct.getName() + "?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Confirm Part Deletioon");
            confirmAlert.setHeaderText("Deleted items CANNOT be recovered!");
            
            Optional<ButtonType> response = confirmAlert.showAndWait();
            if(response.isPresent() && response.get() == ButtonType.YES){
                    partsAddedList.remove(selectedPartofProduct);
                    //PartOfProductTableView.refresh();
            }else{
                confirmAlert.hide();
            } 
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
        
     Part selectedPart = PartTableMenuView.getSelectionModel().getSelectedItem();
            
            if (selectedPart == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error in Part Selection");
            errorAlert.setHeaderText("No Part Selected to add");
            errorAlert.setContentText("You must click on and select a product to add.");
            errorAlert.showAndWait();
            return;
            }
            
            partsAddedList.add(selectedPart);
            
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
        
        PartAddTableView.setItems(partsAddedList);
            PartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            PartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PartInvAddCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            PartPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
    }    
    
}
