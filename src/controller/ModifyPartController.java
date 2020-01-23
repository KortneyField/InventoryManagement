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
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class ModifyPartController implements Initializable {
    Stage stage; 
    Parent scene;
    
    @FXML
    private Button BackButton;

    @FXML
    private Button SaveButton;
    
    @FXML
    private RadioButton inHouseRdButton;
    
    @FXML
    private RadioButton outsourcedRdButton;
    
    @FXML
    private ToggleGroup PartSource;

    @FXML
    private TextField partIdText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partStockText;

    @FXML
    private TextField partPriceText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

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
            int id = Integer.parseInt(partIdText.getText()); 
            String name = partNameText.getText();
            double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partStockText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            boolean partSource; 
            
            if(min > max){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Min/Max Inventory Error");
            errorAlert.setHeaderText("Min Count is greater than Max Count");
            errorAlert.setContentText("Min Part Count MUST be LESS than Max Part Count");
            errorAlert.showAndWait();
            return; 
            }
            
            Inventory.deletePart(Inventory.lookUpPart(id));
            
            if (inHouseRdButton.isSelected())
                partSource = true;
            else
                partSource = false; //inhouse

            if (partSource){
                //get mach id from text field
                int machineID = Integer.parseInt(sourceText.getText());
                //add part to inventory list
                Inventory.addPart(new InHouse(id, name, price, stock, max, min, machineID));
            }
            else { //outsourced
                //get comp name from text field
                String compName = sourceText.getText();  
                Inventory.addPart(new Outsourced(id, name, price, stock, max, min, compName));
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
    
    //To fill in the textboxes from the table on main menu. 
     void setPartToModify(Part part) {
            System.out.print(part.getId());
                partIdText.setText(String.valueOf(part.getId()));
                partNameText.setText(part.getName());
                partPriceText.setText(String.valueOf(part.getPrice()));
                partMaxText.setText(String.valueOf(part.getMax()));
                partMinText.setText(String.valueOf(part.getMin()));
                partStockText.setText(String.valueOf(part.getStock()));

            //Going between buttons of inHouse and Outsoruced. 
            if (part instanceof InHouse) {
                inHouseRdButton.setSelected(true);
                sourceLabel.setText("Machine ID");
                sourceText.setPromptText("Mach ID");
                InHouse inhouse = (InHouse)part;
                sourceText.setText(Integer.toString(inhouse.getMechineId()));            
                }
            else { //outsourced
                outsourcedRdButton.setSelected(true);
                sourceLabel.setText("Company Name");
                sourceText.setPromptText("Comp Name");
                Outsourced outsource = (Outsourced) part; 
                sourceText.setText(outsource.getCompanyName());
                }
        }
     
    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

   
    
}
