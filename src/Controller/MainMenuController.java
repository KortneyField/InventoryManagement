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
 *
 * @author kortneyfield
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Button ExitButton;

    @FXML
    private Label label;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button ModifyProductButton;

    @FXML
    private Button DeleteProductButton;

    @FXML
    private Button AddPartButton;

    @FXML
    private Button ModifyPartButton;

    @FXML
    private Button DeletePartButton;

    @FXML
    private Button SearchPartButton;

    @FXML
    private TextField SearchPartText;

    @FXML
    private Button SearchProductButton;

    @FXML
    private TextField SearchProductText;
    
    
    @FXML
    void OnActionAddPart(ActionEvent event) {
        System.out.println("Add Part Was Pressed!");
    }

    @FXML
    void OnActionAddProduct(ActionEvent event) {
       System.out.println("Add Product Was Pressed!");
    }

    @FXML
    void OnActionDeletePart(ActionEvent event) {
        System.out.println("Delete Part Was Pressed!");
    }

    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete Product Was Pressed!");
    }

    @FXML
    void OnActionExitProgram(ActionEvent event) {
        System.out.println("Exit Was Pressed!");
    }

    @FXML
    void OnActionModifyPart(ActionEvent event) {
        System.out.println("Modify Part Was Pressed!");
    }

    @FXML
    void OnActionModifyProduct(ActionEvent event) {
        System.out.println("Modify Product Was Pressed!");
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
