package flowershop.entities;

public class Tree extends Product {

    private double height;

    public Tree() {
        super();
    }

    public Tree(String name, double price, int stock, double height) {
        super(name, price, stock);
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s, Altura: %.2f, Precio: %.2f, Stock: %d",
                getName(), getHeight(), getPrice(), getStock());
    }

    @Override
    public String toStringAlt() {
        return String.format("Nombre: %s, Altura: %s, Precio: %.2f",
                getName(), getHeight(), getPrice());
    }
}
