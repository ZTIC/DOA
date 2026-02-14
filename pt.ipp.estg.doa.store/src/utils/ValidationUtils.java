package utils;

import java.time.LocalDate;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Invalid ID");
    }

    // ---------- Name ----------
    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required and cannot be empty.");
        }
    }
    // Validate NIF
    public static void validateNif(String nif) {
        if (nif == null || nif.isEmpty() || !nif.matches("\\d{3}-\\d{3}-\\d{3}")) {
            throw new IllegalArgumentException("Invalid NIF Format!");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.isEmpty() || !email.matches("^(.+)@(.+)$")) {
            throw new IllegalArgumentException("Invalid Email Address!");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || phone.length() != 13) {
            throw new IllegalArgumentException("Invalid Phone Number!");
        }
    }

    // ---------- Hire Date ----------
    public static void validateHireDate(LocalDate hireDate) {
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date is required.");
        }
        if (hireDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Hire date cannot be in the future.");
        }
    }

    // ---------- Salary ----------
    public static void validateSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than zero.");
        }
    }

    public static void requireNonBlank(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required and cannot be empty.");
        }
    }

    public static void requirePositive(double weight, String description) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }

    }
}
