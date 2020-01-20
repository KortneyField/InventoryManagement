/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class AddProductController implements Initializable {

    @FXML
    private Button SaveButton;

    @FXML
    private Button DeleteProductButton;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField SearchText;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField MaxText;

    @FXML
    private TextField MinText;

    @FXML
    private TextField IDText;

    @FXML
    private TextField NameText;

    @FXML
    private TextField InventoryText;

    @FXML
    private TextField PriceText;

    @FXML
    private TextField CompanyNameText;
    
    @FXML
    void OnActionAddProduct(ActionEvent event) {

    }

    @FXML
    void OnActionDeleteProduct(ActionEvent event) {

    }

    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) {

    }

    @FXML
    void OnActionSaveProduct(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
}
