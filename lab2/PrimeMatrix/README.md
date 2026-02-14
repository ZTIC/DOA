Exercise 2: Matrix and Prime Numbers
Goal

Generate a matrix populated with prime numbers and save it to a file based on dimensions read from an input file.
Task Description

Develop a Java program named PrimeMatrix.java that implements the following:

    Method 1: Prime Number Detection
        Create a method isPrime(int n) that returns true if a number is prime, false otherwise
        The method should handle edge cases (numbers less than 2)

    Method 2: Prime Matrix Generation
        Create a method generatePrimeMatrix(int rows, int cols) that:
            Calculates the first N prime numbers (where N = rows × cols)
            Arranges them in a matrix with the specified dimensions
            Returns the matrix as a 2D array

    Method 3: File-Based Matrix Creation
        Create a method that:
            Reads a file matrix_config.txt containing two integers: number of rows and number of columns
            Generates the prime matrix using the dimensions from the file
            Saves the resulting matrix to a new file prime_matrix.txt
            Each row of the matrix should be on a separate line with numbers separated by spaces

    Display Method
        Create a method to print the matrix to the console in a formatted manner

Input File Format (matrix_config.txt)

3
3

Expected Console Output

2 3 5
7 11 13
17 19 23

Expected File Output (prime_matrix.txt)

2 3 5
7 11 13
17 19 23
