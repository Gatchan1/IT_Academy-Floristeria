public class Tree extends Product {

    private int idTree;
    private double height;

    public Tree(){
    }

    public Tree(int id, String name, float price, int stock, float height) {
        super(id, name, price, stock)
        this.height = height;
    }

    public int getIdTree() {
        return idTree;
    }

    public void setIdTree(int idTree) {
        this.idTree = idTree;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
