package flowershop.entities;

import java.util.Objects;

public class Flower extends Product{

    private String color;

    public Flower() {
        super();
    }

    public Flower(String name, double price, int stock, String color) {
        super(name, price, stock);
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {

        return color;
    }

    public void setColor(String color) {

        this.color = color;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//           return true;
//        }
//        if (!(o instanceof Flower)){
//           return false;
//        }
//        if (!super.equals(o)){
//           return false;
//        }
//        Flower flower = (Flower) o;
//        return Objects.equals(color, flower.color);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(super.hashCode(), color);
//    }

    @Override
    public String toString() {
    return String.format("Nombre: %s, Color: %s, Precio: %.2f, Stock: %d",
            getName(), getColor(), getPrice(), getStock());
    }
}
