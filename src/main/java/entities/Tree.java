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

    public void setHeight(double height) {
        this.height = height;
    }

    //hash&equals


    @Override
    public boolean equals(Object o) {
        if (this == o) {
           return true;
        }
        if (!(o instanceof Tree)){
           return false;
        }
        if (!super.equals(o)){
           return false;
        }
        Tree tree = (Tree) o;
        return Objects.equals(height, tree.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }



}
