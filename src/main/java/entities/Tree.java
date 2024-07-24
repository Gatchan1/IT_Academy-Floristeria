package entities;

public class Tree extends Product {

    private int idTree;
    private double height;

    public Tree(){
        super();
    }

    public Tree(String name, double price, int stock, double height) {
        super(name, price, stock);
        this.height = height;
    }

    public int getIdTree() {
        return idTree;
    }

    public void setIdTree(int idTree) {
        this.idTree = idTree;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
