public class Person {
    private String name;
    private String dateOfBirth;
    private Address address;
    private String phoneNumber;

    public Person(String name, String dateOfBirth, Address address, String phoneNumber) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void relocate(Address newAddress) {
        this.address = newAddress;
    }

    @Override
    public String toString() {
        return "Person: " +
                "name= " + name +
                ", dateOfBirth= " + dateOfBirth +
                ", address= " + address +
                ", phoneNumber= " + phoneNumber;
    }
}
