package flowershop.entities;

public class Decoration extends Product {

   private Material material;

    public Decoration() {
        super();
    }

    public Decoration(String name, double price, int stock, Material material) {
        super(name, price, stock);
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//           return true;
//        }
//        if (!(o instanceof Decoration)){
//           return false;
//        }
//        if (!super.equals(o)){
//           return false;
//        }
//        Decoration decoration = (Decoration) o;
//        return Objects.equals(material, decoration.material);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), material);
//    }

    @Override
    public String toString() {
        return String.format("Nombre: %s, Material: %s, Precio: %.2f, Stock: %d",
                getName(), getMaterial(), getPrice(), getStock());
    }
}
