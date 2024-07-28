package entities;

public class Decoration extends Product {

   private int id;
   private Material material;

    public Decoration() {

        super();
    }

    public Decoration(String name, double price, int stock, Material material) {
        super(name, price, stock);
        this.material = material;
    }

    public int getId() {

        return idn;
    }

    public void setId(int id) {

        this.idDecoration = id;
    }

    public Material getMaterial() {

        return material;
    }

    public void setMaterial(Material material) {

        this.material = material;
    }

    public enum Material {
        WOOD,
        PLASTIC;
    }

    // hash&equals

    @Override
    public boolean equals(Object o) {
        if (this == o) {
           return true;
        }
        if (!(o instanceof Decoration)){
           return false;
        }
        if (!super.equals(o)){
           return false;
        }
        Decoration decoration = (Decoration) o;
        return Objects.equals(material, decoration.material);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), material);
    }

}
