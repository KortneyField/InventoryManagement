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
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class ModifyProductController implements Initializable {
    
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
    private TextField productInvText;

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
    private TableColumn<?, ?> PartIdCol;

    @FXML
    private TableColumn<?, ?> PartNameCol;

    @FXML
    private TableColumn<?, ?> PartInvCol;

    @FXML
    private TableColumn<?, ?> PartPriceCol;

    @FXML
    private TableView<Part> PartOfProductTableView;

    @FXML
    private TableColumn<?, ?> partIdOfProductCol;

    @FXML
    private TableColumn<?, ?> partNameOfProductCol;

    @FXML
    private TableColumn<?, ?> partInvOfProductCol;

    @FXML
    private TableColumn<?, ?> partPriceOfProductCol;

    @FXML
    private TextField SearchText;
    
    ObservableList partsAddedList = FXCollections.observableArrayList();//gives an empty list
    
    void setProductToModify(Product product) {
        System.out.print(product.getId());
         
        productIdText.setText(Integer.toString(product.getId()));
        productNameText.setText(product.getName());
        productPriceText.setText(String.valueOf(product.getPrice()));
        productMaxText.setText(String.valueOf(product.getMax()));
        productMinText.setText(String.valueOf(product.getMin()));
        productInvText.setText(String.valueOf(product.getStock()));   
    }
    
    public Product selectProduct(int id) {
        for(Product product: Inventory.getAllProducts()) {
            if(product.getId()==id)
                return product;
        }
        return null;
    }
    
    @FXML
    void OnActionSearchPart(ActionEvent event) {
        String name = SearchText.getText();
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
    void OnActionSaveProduct(ActionEvent event) throws IOException {
        System.out.println("You clicked the save button");
        label.setText("Your work will be saved");
        
        try {
        int id = Integer.parseInt(productIdText.getText()); 
        String name = productNameText.getText();
        double price = Double.parseDouble(productPriceText.getText());
        int stock = Integer.parseInt(productInvText.getText());
        int min = Integer.parseInt(productMaxText.getText());
        int max = Integer.parseInt(productMinText.getText());
        boolean productSource; 

        Inventory.deleteProduct(Inventory.lookUpProduct(id));
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
    void OnActionCancel(ActionEvent event) throws IOException {
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?", ButtonType.YES, ButtonType.NO);
        confirmAlert.setTitle("Confirm Cancel");
        confirmAlert.setHeaderText("Changes will be lost!");
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
    void onActionAddPart(ActionEvent event) {
        System.out.println("You pressed add");
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
    
    @FXML
    void onActionDeletePart(ActionEvent event) {
        System.out.println("you pressed delete");
        Part selectedPartofProduct = PartOfProductTableView.getSelectionModel().getSelectedItem();
        
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList partList = Inventory.getAllParts();
        //productPartList.clear();
        
        PartTableMenuView.setItems(partList);
            PartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            PartInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
            
        PartOfProductTableView.setItems(partsAddedList);
            partIdOfProductCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
            partNameOfProductCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
            partInvOfProductCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
            partPriceOfProductCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }    

    

    
}
