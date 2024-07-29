package flowershop.entities;

public class Decoration extends Product {
    private Material material;

    public Decoration(String name, double price, int stock, Material material) {
        super(name, price, stock);
        this.material = material;
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

    @Override
    public String toString() {
        return String.format("Nombre: %s, Material: %s, Precio: %.2f, Stock: %d",
                getName(), getMaterial(), getPrice(), getStock());
    }

    @Override
    public String toStringAlt() {
        return String.format("Nombre: %s, Material: %s, Precio: %.2f",
                getName(), getMaterial(), getPrice());
    }
}
