package jewlry;

import utils.Persistable;
import utils.ValidationUtils;

public abstract class Jewelry implements Persistable {
    private final int jewelryId;
    private String name;
    private String material;
    private double weight;
    private double price;
    private int stock;
    private final JewelryType jewelryType;
    private final Category category;

    protected Jewelry(int jewelryId, String name, String material, double weight, double price, int stock, JewelryType jewelryType, Category category) {
        ValidationUtils.requireNonBlank(name, "Name");
        ValidationUtils.requirePositive(weight, "Weight");
        ValidationUtils.requirePositive(price, "Price");
        this.jewelryId = jewelryId;
        this.name = name;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stock = stock;
        this.jewelryType = jewelryType;
        this.category = category;
    }

    public int getJewelryId() {
        return jewelryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.requireNonBlank(name, "Name");
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        ValidationUtils.requireNonBlank(material, "Material");
        this.material = material;
    }

    public double getWeight() {
        ValidationUtils.requirePositive(weight, "Weight");
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        ValidationUtils.requirePositive(price, "Price");
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must be greater than zero.");
        }
        this.stock = stock;
    }

    public JewelryType getJewelryType() {
        return jewelryType;
    }

    public Category getCategory() {
        return category;
    }

    public void increaseStock(int amount) {
        ValidationUtils.requirePositive(amount, "Stock increase amount");
        stock += amount;
    }

    public void decreaseStock(int amount) {
        ValidationUtils.requirePositive(amount, "Stock decrease stock amount");
        if (stock <= amount) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        stock -= amount;
    }
}
