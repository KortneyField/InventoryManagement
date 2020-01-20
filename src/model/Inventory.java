/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kortneyfield
 */
public class Inventory{
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart (Part part) {
        allParts.add(part); 
    }
    
    public static void addProduct (Product product) {
        allProducts.add(product); 
    }
    
    public static ObservableList<Part> getAllParts () {
        return allParts; 
    }
    
    public static ObservableList<Product> getAllProducts () {
        return allProducts; 
    }
    
    public static ObservableList<Part> lookUpPart (String name) {
        ObservableList<Part> listPart = FXCollections.observableArrayList();
        for(Part part : Inventory.getAllParts()){
            String pName = part.getName().toLowerCase();
            if(pName.contains(name.toLowerCase()))
                 listPart.add(part);
        }
        return listPart; 
    }
    
    public static Part lookUpPart(int id) {
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == id)
                return part; 
        }
        return null;
    }
    
    public static ObservableList<Product> lookUpProduct (String name) {
        ObservableList<Product> listProduct = FXCollections.observableArrayList();
        for(Product product : Inventory.getAllProducts()){
            String pName = product.getName().toLowerCase();
            if(pName.contains(name.toLowerCase()))
                 listProduct.add(product);
        }
        return listProduct; 
    }
    
    public static Product lookUpProduct(int id) {
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == id)
                return product; 
        }
        return null;
    }
    
    
    
    public static void updatePart(int index, Part selectedPart){
        Integer partIndex = index;
        allParts.set(partIndex, selectedPart);           
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        Integer productIndex = index;
        allProducts.set(productIndex, selectedProduct);   
    }
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart); 
    }
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);      
    }
}
