package jewlry;

import utils.CSVUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JewelryManager {
    private final Map<Integer, Jewelry> jewelries = new HashMap<>();
    private int nextId = 1;

    // ADD Jewelry ---> Necklace
    public Necklace addNecklace(String name, String material, double weight,
                                double price, int stock, Category category, double length) {

        Necklace n = new Necklace(
                nextId++,
                name,
                material,
                weight,
                price,
                stock,
                category,
                length
        );
        jewelries.put(n.getJewelryId(), n);
        return n;
    }

    // ADD Jewelry ---> Erring
    public Ring addRing(String name, String material, double weight,
                        double price, int stock, Category category, int size) {

        Ring n = new Ring(
                nextId++,
                name,
                material,
                weight,
                price,
                stock,
                category,
                size
        );
        jewelries.put(n.getJewelryId(), n);
        return n;
    }

    // ADD Jewelry ---> Earring
    public Earring addEarring(String name, String material, double weight, double price, int stock, Category category, String claspType) {

        Earring e = new Earring(
                nextId++,
                name,
                material,
                weight,
                price,
                stock,
                category,
                claspType
        );
        jewelries.put(e.getJewelryId(), e);
        return e;
    }

    // CSV
    public void load(String filename) {
        List<String> lines = CSVUtil.readCSV(filename);
        for (String line : lines) {
            try {
                String[] parts = line.split(", ");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String type = parts[2];
                String material = parts[3];
                double weight = Double.parseDouble(parts[4]);
                double price = Double.parseDouble(parts[5]);
                int stock = Integer.parseInt(parts[6]);
                JewelryType jewelryType = JewelryType.valueOf(parts[7]);
                Category category = Category.valueOf(parts[8]);

                Jewelry jewelry;

                if ("NECKLACE".equals(type)) {
                    double length = Double.parseDouble(parts[9]);
                    jewelry = new Necklace(
                            id,
                            name,
                            material,
                            weight,
                            price,
                            stock,
                            category,
                            length
                    );
                } else if ("RING".equals(type)) {
                    int size = Integer.parseInt(parts[10]);

                    jewelry = new Ring(
                            id,
                            name,
                            material,
                            weight,
                            price,
                            stock,
                            category,
                            size
                    );
                } else {
                    String claspType = parts[11];
                    jewelry = new Earring(
                            id,
                            name,
                            material,
                            weight,
                            price,
                            stock,
                            category,
                            claspType
                    );
                }
                jewelries.put(id, jewelry);
                nextId = Math.max(nextId, id + 1);

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    // Saving data to CSV file
    public void save(String filename) {
        try {
            List<String> lines = jewelries.values()
                    .stream()
                    .map(Jewelry::toCSV)
                    .toList();
            CSVUtil.writeCSV(filename, lines);

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    // Find Jewelry by ID
    public Jewelry findJewelry(int jewelryId) {
        return jewelries.get(jewelryId);
    }

    // Find Jewelry by NAME
    public List<Jewelry> findByName(String name) {
        try {
            String search = name.toLowerCase();
            return jewelries.values().stream()
                    .filter(
                            jewelry -> jewelry.getName()
                                    .toLowerCase()
                                    .contains(search)
                    ).toList();
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // Find all Jewelries
    public List<Jewelry> findAll() {
        return new ArrayList<>(jewelries.values());
    }

    // Find Jewelry by CATEGORY
    public List<Jewelry> findByCategory(String category) {
        try {
            Category c = Category.valueOf(category.toUpperCase());
            return jewelries.values()
                    .stream()
                    .filter(
                            jewelry -> jewelry.getCategory() == c).toList();

        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // Find Jewelry by Type
    public List<Jewelry> findByType(String type) {

        try {
            JewelryType jType = JewelryType.valueOf(type.toUpperCase());
            return jewelries.values()
                    .stream()
                    .filter(
                            jewelry -> jewelry.getJewelryType() == jType
                    ).toList();
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // List jewelry by stock
    public List<Jewelry> findByJewelryInStock(int jewelryId) {
        try {
            return jewelries.values()
                    .stream()
                    .filter(
                            jewelry -> jewelry.getJewelryId() == jewelryId).toList();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    // TODO: UPDATE
    // UPDATE jewelry base fields
    public boolean updateJewelry(
            int jewelryId,
            String name,
            String material,
            Double weight,
            Double price,
            Integer stock
            ) {

        Jewelry jewelry = getExistingJewelry(jewelryId);

        if (name != null) jewelry.setName(name);
        if (material != null) jewelry.setMaterial(material);
        if (weight != null) jewelry.setWeight(weight);
        if (price != null) jewelry.setPrice(price);
        if (stock != null) jewelry.setStock(stock);
        return true;
    }

    public boolean updateNecklace(int id, Double length) {
        Jewelry jewelry = getExistingJewelry(id);
        if (!(jewelry instanceof Necklace necklace)) {
            throw new IllegalArgumentException("Jewelry with id " + id + " is not a Necklace");
        }
        if (length != null) necklace.setLength(length);
        return true;
    }

    public boolean updateRing(int id, Integer size) {
        Jewelry jewelry = getExistingJewelry(id);
        if (!(jewelry instanceof Ring ring)) {
            throw new IllegalArgumentException("Jewelry with id " + id + " is not a Ring");
        }
        if (size != null) ring.setSize(size);
        return true;
    }

    public boolean updateEarring(int id, String claspType) {
        Jewelry jewelry = getExistingJewelry(id);
        if (!(jewelry instanceof Earring earring)) {
            throw new IllegalArgumentException("Jewelry with id " + id + " is not a Earring");
        }
        if (claspType != null) earring.setClaspType(claspType);
        return true;
    }

    // DELETE
    public boolean remove(int nextId) {
        try {
            getExistingJewelry(nextId);
            jewelries.remove(nextId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get existing jewelry
    private Jewelry getExistingJewelry(int id) {
        Jewelry j = jewelries.get(id);
        if (j == null) {
            throw new IllegalArgumentException("Jewelry not found with id: " + id);
        }
        return j;
    }
}
