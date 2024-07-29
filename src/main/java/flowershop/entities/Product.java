package flowershop.entities;

public abstract class Product{

    protected String id;
    protected String name;
    protected double price;
    protected int stock;

    public Product(){
    }

    public Product(String name, double price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {

        this.stock = stock;
    }

    public abstract String toStringAlt();
}
