package entities;

public class Decoration extends Product {

   private int idDecoration;
   private Material material;

    public Decoration(){
    }

    public Decoration(String name, double price, int stock, Material material) {
        this.material = material;
    }

    public int getIdDecoration() {
        return idDecoration;
    }

    public void setIdDecoration(int idDecoration) {
        this.idDecoration = idDecoration;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public enum Material {
        FUSTA,
        PLASTIC;
    }


}
