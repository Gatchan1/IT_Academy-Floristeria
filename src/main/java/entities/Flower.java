package entities;

public class Flower extends Product{

    private int idFlower;
    private String color;

    public Flower(){
        super();
    }

    public Flower(String name, double price, int stock, String color) {
        super(name, price, stock);
        this.color = color;
    }

    public int getIdFlower() {
        return idFlower;
    }

    public void setIdFlower(int idFlower) {
        this.idFlower = idFlower;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // hash&equals
}
