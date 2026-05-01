package jewlry;

import utils.ValidationUtils;

public class Necklace extends Jewelry {
    //  Length in centimeters
    private double length;

    public Necklace(int jewelryId, String name, String material, double weight, double price, int stock, Category category, double length) {
        super(jewelryId, name, material, weight, price, stock, JewelryType.NECKLACE, category);
        ValidationUtils.requirePositive(length, "Necklace length");
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        ValidationUtils.requirePositive(length, "Necklace Length");
        this.length = length;
    }

    @Override
    public String toCSV() {
        return getJewelryId() + ", NECKLACE, " +
                getName() + ", " +
                getMaterial() + ", " +
                getWeight() + ", " +
                getLength() + ", " +
                getPrice() + ", " +
                getStock() + ", " +
                getCategory();
    }

    @Override
    public void fromCSV(String csvLine) {

        String[] parts = csvLine.split(",\\S*");
        setName(parts[2]);
        setMaterial(parts[3]);
        setWeight(Double.parseDouble(parts[4]));
        setLength(Double.parseDouble(parts[5]));
        setPrice(Double.parseDouble(parts[6]));
        setStock(Integer.parseInt(parts[7]));
    }
}
