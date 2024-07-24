package entities;

public class Ticket {

    private int idTicket;
    private HashMap<T, int> saleProducts;
    private double saleTotal;

    public Ticket(){
    }

    public Ticket(HashMap<T, int> saleProducts, double saleTotal){
        this.saleProducts = saleProducts;
        this.saleTotal = saleTotal;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public HashMap<T, int> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(HashMap<T, int> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }
}
