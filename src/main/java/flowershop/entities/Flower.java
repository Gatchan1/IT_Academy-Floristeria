package flowershop.entities;

public class Flower extends Product {
    private String color;

    public Flower(String name, double price, int stock, String color) {
        super(name, price, stock);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("Nombre: %s, Color: %s, Precio: %.2f, Stock: %d",
                getName(), getColor(), getPrice(), getStock());
    }

    @Override
    public String toStringAlt() {
        return String.format("Nombre: %s, Color: %s, Precio: %.2f",
                getName(), getColor(), getPrice());
    }
}
