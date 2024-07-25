package entities;

import java.time.LocalDate;
import java.util.HashMap;

public class Ticket<T> {

    private int idTicket;
    private HashMap<T, Integer> saleProducts;
    private LocalDate saleDate;
    private double saleTotal;

    public Ticket(){
        this.saleDate = LocalDate.now();
    }

    public Ticket(HashMap<T, Integer> saleProducts, double saleTotal){
        this.saleProducts = saleProducts;
        this.saleTotal = saleTotal;
        this.saleDate = LocalDate.now();
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public HashMap<T, Integer> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(HashMap<T, Integer> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = LocalDate.now;
    }

    //hash&equals
}
