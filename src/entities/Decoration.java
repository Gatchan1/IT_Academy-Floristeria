public class Decoration {

    private int idDecoration;
    private String material;

    public Decoration(){
    }

    public Decoration(int id, String name, float price, int stock, String material) {
        super(id, name, price, stock)
        this.material = material;
    }

    public int getIdDecoration() {
        return idDecoration;
    }

    public void setIdDecoration(int idDecoration) {
        this.idDecoration = idDecoration;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
