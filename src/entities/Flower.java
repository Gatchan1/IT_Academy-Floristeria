public class Flower extends Product{

    private int idFlower;
    private String color;

    public Flower(){
    }

    public Flower(int id, String name, float price, int stock, String color) {
        super(id, name, price, stock)
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
}
