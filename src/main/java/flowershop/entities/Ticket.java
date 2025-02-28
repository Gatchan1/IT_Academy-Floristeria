package flowershop.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ticket {

    private String id;
    private HashMap<Product, Integer> saleProducts;
    private LocalDate saleDate;
    private double saleTotal;

    public Ticket(){
    }

    public Ticket(HashMap<Product, Integer> saleProducts, double saleTotal){
        this.saleProducts = saleProducts;
        this.saleTotal = saleTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<Product, Integer> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(HashMap<Product, Integer> saleProducts) {
        this.saleProducts = saleProducts;
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {

        this.saleDate = saleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(ticket.saleTotal, saleTotal) == 0 &&
                Objects.equals(saleProducts, ticket.saleProducts) &&
                Objects.equals(saleDate, ticket.saleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleProducts, saleDate, saleTotal);
    }

    @Override
    public String toString() {
        StringBuilder productsString = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : saleProducts.entrySet()) {
            if (entry.getKey() == null) {
                productsString.append("Producto desconocido descatalogado. Precio: ??, Cantidad: ??\n");
            } else {
                productsString.append(entry.getKey().toString())
                        .append(", Cantidad: ")
                        .append(entry.getValue())
                        .append("\n");
            }
        }
        if (!productsString.isEmpty()) {
            productsString.setLength(productsString.length() - 1);
        }
        return "Ticket ID:" + id +
                "\n Productos: \n" + productsString + " " +
                "\n Fecha de venta: " + saleDate +
                "\n Importe total: " + saleTotal +
                "\n";
    }

    public String toStringAlt() {
        StringBuilder productsString = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : saleProducts.entrySet()) {
            productsString.append(entry.getKey().toStringAlt())
                    .append(", Cantidad: ")
                    .append(entry.getValue())
                    .append("\n");
        }
        if (!productsString.isEmpty()) {
            productsString.setLength(productsString.length() - 1);
        }
        return "Ticket:" +
                "\n Productos: \n" + productsString + " " +
                "\n Fecha de venta: " + LocalDate.now() +
                "\n Importe total: " + saleTotal +
                "\n";
    }
}
