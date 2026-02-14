package jewlry;

import utils.ValidationUtils;

public class Ring extends Jewelry {
    // Ring size (European standard, 10-30)
    private int size;

    public Ring(int jewelryId, String name, String material, double weight, double price, int stock, Category category, int size) {
        super(jewelryId, name, material, weight, price, stock, JewelryType.RING, category);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 10 || size > 30) {
            throw new IllegalArgumentException("Size must be between 1 and 30.");
        }
            this.size = size;
    }


    @Override
    public String toCSV() {
        return getJewelryId() + ", RING, " +
                getName() + ", " +
                getMaterial() + ", " +
                getWeight() + ", " +
                getSize() + ", " +
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
        setSize(Integer.parseInt(parts[5]));
        setPrice(Double.parseDouble(parts[6]));
        setStock(Integer.parseInt(parts[7]));
    }
}
