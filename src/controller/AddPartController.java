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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class AddPartController implements Initializable {
    
    Stage stage; 
    Parent scene;
    
    @FXML
    private RadioButton partInHouseRdBtn;

    @FXML
    private ToggleGroup PartSource;

    @FXML
    private RadioButton partOutSourcedRdBtn;

    @FXML
    private TextField partIdText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partInventoryText;

    @FXML
    private TextField partPriceText;

    

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;
    
    @FXML
    private Button BackButton;
    
    @FXML
    private Button SaveButton;

    @FXML
    private Label label;
    
     @FXML
    private Label sourceLabel;
     
    @FXML
    private TextField sourceText;

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
    void OnActionSavePart(ActionEvent event) throws IOException {
        try 
        {
            int lastPartIndex = Inventory.getAllParts().size() - 1; // convert to index
            int lastPartId = Inventory.getAllParts().get(lastPartIndex).getId();
            int id = lastPartId + 1;
            String name = partNameText.getText();
            double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInventoryText.getText());
            int min = Integer.parseInt(partMaxText.getText());
            int max = Integer.parseInt(partMinText.getText());
            boolean partSource; 
            int partMin = this.getTxtMin();
            int partMax = this.getTxtMax();
        
            if (partInHouseRdBtn.isSelected())
                partSource = true;
            else
                partSource = false; 
            
            if (partSource) {
                int machineID = 5; 
                Inventory.addPart(new InHouse(id, name, price, stock, max, min, machineID));
            }
            else {
                 String companyName = sourceText.getText();
                 Inventory.addPart(new Outsourced(id, name, price, stock, max, min, companyName));
            }
            
            if(partMin > partMax){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Min/Max Inventory Error");
            errorAlert.setHeaderText("Min Count is greater than Max Count");
            errorAlert.setContentText("Min Part Count MUST be LESS than Max Part Count");
          

            Optional<ButtonType> response = errorAlert.showAndWait();
                    if(response.get() == ButtonType.OK){
                       // do nothing
                    }                
            }
            
            
            
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow(); 
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        
        }
        catch (NumberFormatException e) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Information Entry");
            alert.setContentText("You will no be able to save without correct information");
            alert.showAndWait(); 
        }
         
}

    public int getTxtMax() {
        return Integer.parseInt(partMaxText.getText());
        }

    public int getTxtMin() {
        return Integer.parseInt(partMinText.getText());
    }
    
     @FXML
    void onActionInHouse(ActionEvent event) {
       sourceLabel.setText("Machine ID");
       sourceText.setPromptText("Mach ID"); 
       sourceText.setText("");
    }

    @FXML
    void onActionOutsourced(ActionEvent event) {
        sourceLabel.setText("Company Name");
        sourceText.setPromptText("Comp Name");
        sourceText.setText("");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partInHouseRdBtn.setSelected(true);
        sourceLabel.setText("Machine ID: ");
        sourceText.setPromptText("Mach ID");
    }    
    
}
