/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author kortneyfield
 */
public class InHouse extends Part{
    private int mechineId; 

    public InHouse(int id, String name, double price, int stock, int max, int min, int mechineId) {
        super(id, name, price, stock, max, min);
        this.mechineId = mechineId;
    }

    

    public int getMechineId() {
        return mechineId;
    }

    public void setMechineId(int mechineId) {
        this.mechineId = mechineId;
    }
    
    
}
