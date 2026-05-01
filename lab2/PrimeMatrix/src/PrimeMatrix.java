import java.io.*;
import java.util.Scanner;



public class PrimeMatrix {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("******** CHECKING PRIME NUMBERS *********");
        System.out.println("---------------------------------------------");

        System.out.println("Enter the number: ");
        int num = scanner.nextInt();

        if (isPrime(num)) {
            System.out.println(num + " is a prime number!");
        } else {
            System.out.println(num + " is not a prime number!");
        }

        System.out.println("******** GENERATING PRIME MATRIX *********");
        System.out.println("---------------------------------------------");
        System.out.println("Enter the number of rows of the matrix: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number columns of the matrix: ");
        int columns = scanner.nextInt();
        generatePrimeMatrix(rows, columns);
        try {
            fileBasedPrimeMatrix("matrix_config.txt");
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        scanner.close();

    }

    // Prime checker
    public static boolean isPrime(int number) {
        // Handle edge cases: numbers less than 2 are not prime
        if (number < 2) {
            return false;
        }

        // 2 is the only even prime number
        if (number == 2) {
            return true;
        }

        // Even numbers greater than 2 are not prime
        if (number % 2 == 0) {
            return false;
        }

        // Here we are checking odd divisors up to square root of number
        // If number has a divisor greater than sqrt(number), it must also have a
        // corresponding divisor less than sqrt(n) - Theory
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Method responsible for generate Prime Matrix
    public static int[][] generatePrimeMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        int currentNumber = 2;
        int primesFound = 0;
        int totalPrimesNeeded = rows * cols;

        // Find enough prime numbers to fill the matrix
        while (primesFound < totalPrimesNeeded) {
            if (isPrime(currentNumber)) {
                int row = primesFound / cols;
                int col = primesFound % cols;
                matrix[row][col] = currentNumber;
                primesFound++;
            }
            currentNumber++;
        }

        return matrix;
    }

    public static void fileBasedPrimeMatrix(String filename) throws FileNotFoundException {
        System.out.println("******** GENERATING PRIME MATRIX FROM A CONFIGURATION FILE *********");
        System.out.println("---------------------------------------------------------------------");

        int rows;
        int cols;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String firstLine = bufferedReader.readLine();
            String secondLine = bufferedReader.readLine();
            if (firstLine == null || secondLine == null) {
                throw new IOException("The file contains null lines!");
            }
            rows = Integer.parseInt(firstLine.trim());
            cols = Integer.parseInt(secondLine.trim());

            System.out.printf("Read from file: %d, rows, %d, cols%n", rows, cols);

            int[][] matrix = generatePrimeMatrix(rows, cols);
            System.out.println("Generated Prime Matrix: ");
            displayPrimeMatrix(matrix);

            // Save to file
            saveMatrixToFile(matrix, "prime_matrix.txt");
            System.out.println("\nMatrix saved to file : prime_matrix.txt");

        } catch (IOException e) {
            throw new NumberFormatException("Invalid Format in file!");
        }
    }

    private static void saveMatrixToFile(int[][] matrix, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            int rows = matrix.length;
            int cols = matrix[0].length;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.write(String.valueOf(matrix[i][j]));
                    if (j < cols - 1) {
                        writer.write(" ");
                    }
                }
                writer.write("\n");
            }
        }

    }


    public static void displayPrimeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // Display the matrix
        System.out.println("Prime Matrix " + rows + " x " + cols + ": ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j]);
                if (j < cols - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


}