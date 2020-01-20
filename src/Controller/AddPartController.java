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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kortneyfield
 */
public class AddPartController implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private Button SaveButton;

    @FXML
    private RadioButton InHouseRBtn;

    @FXML
    private RadioButton OutsourcedRBtn;

    @FXML
    private TextField MaxText;

    @FXML
    private TextField MinText;

    @FXML
    private TextField IdText;

    @FXML
    private TextField NameText;

    @FXML
    private TextField InventoryText;

    @FXML
    private TextField PriceText;

    @FXML
    private TextField CompanyNameText;

    @FXML
    void OnActionDisplayMainMeu(ActionEvent event) {

    }

    @FXML
    void OnActionSavePart(ActionEvent event) {

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
