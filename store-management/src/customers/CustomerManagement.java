package customers;

import utils.CSVUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerManagement {
    private int nextId = 1;
    public final Map<Integer, Customer> customers = new HashMap<>();

    // ADD Customer
    public Customer addCustomer(String name, String nif, String email, String phone, String address) {
        ensureUniqueNif(nif);
        Customer customer = new Customer(
                nextId++,
                name,
                nif,
                email,
                phone,
                address
        );
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    public void save(String filename) {
        List<String> lines = customers.values()
                .stream()
                .map(Customer::toCSV)
                .toList();
        CSVUtil.writeCSV(filename, lines);
    }

    // Helpers
    private void ensureUniqueNif(String nif) {
        boolean exists = customers.values().stream()
                .anyMatch(c -> c.getNif().equals(nif));

        if (exists) {
            throw new IllegalArgumentException("NIF already exists.");
        }
    }
}
