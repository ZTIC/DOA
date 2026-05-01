package customers;

import utils.Persistable;
import utils.ValidationUtils;

public class Customer implements Persistable {
    private int customerId;
    private String name;
    private String nif;
    private String email;
    private String phone;
    private String address;

    public Customer(int customerId, String name, String nif, String email, String phone, String address) {
        ValidationUtils.validateId(customerId);
        ValidationUtils.validateName(name);
        ValidationUtils.validateNif(nif);
        ValidationUtils.validateEmail(email);
        ValidationUtils.validatePhone(phone);

        this.customerId = customerId;
        this.name = name;
        this.nif = nif;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateName(name);
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        ValidationUtils.validateNif(nif);
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        ValidationUtils.validateEmail(email);
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        ValidationUtils.validatePhone(phone);
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullCustomerData() {
        return getCustomerId() + " " + getName() + " " + getEmail() + " " + getPhone() + " " + getAddress() + " " + getNif();
    }

    @Override
    public String toCSV() {
        return String.format(
                "%d,SALESPERSON,%s,%s,%s,%s,%s",
                getCustomerId(),
                getName(),
                getNif(),
                getEmail(),
                getPhone(),
                getAddress()
        );
    }

    @Override
    public void fromCSV(String csvLine) {

    }

    @Override
    public String toString() {
        return getFullCustomerData();
    }
}
