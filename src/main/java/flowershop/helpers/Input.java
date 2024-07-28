package flowershop.helpers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ERROR_BYTE = "Error de formato. Introduce un dato de tipo byte";
    private static final String ERROR_INT = "Error de formato. Introduce un dato de tipo int";
    private static final String ERROR_FLOAT = "Error de formato. Introduce un dato de tipo float";
    private static final String ERROR_DOUBLE = "Error de formato. Introduce un dato de tipo double";
    private static final String ERROR_CHAR = "Solamente se acepta un carácter";
    private static final String ERROR_EMPTY_STRING = "No se puede introducir una cadena vacía";
    private static final String ERROR_YES_NO = "Respuesta no válida. Introduce 's' o 'n'.";

    public static byte readByte(String message) {
        byte inputValue = 0;
        boolean correctData = false;

        while (!correctData) {
            try {
                System.out.println(message);
                inputValue = scanner.nextByte();
                correctData = true;
            } catch (InputMismatchException e) {
                System.out.println(ERROR_BYTE);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return inputValue;
    }

    public static int readInt(String message) {
        int inputValue = 0;
        boolean correctData = false;

        while (!correctData) {
            try {
                System.out.print(message);
                inputValue = scanner.nextInt();
                correctData = true;
            } catch (InputMismatchException e) {
                System.out.println(ERROR_INT);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return inputValue;
    }

    public static int readIntInRange(String message, int min, int max) {
        int inputValue = 0;
        boolean correctData = false;

        while (!correctData) {
            try {
                System.out.print(message);
                inputValue = scanner.nextInt();

                if (inputValue >= min && inputValue <= max) {
                    correctData = true;
                } else {
                    System.out.print("Error: Introduce un valor entre " + min + " y " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println(ERROR_INT);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return inputValue;
    }

    public static float readFloat(String message) {
        float inputValue = 0.0f;
        boolean correctData = false;

        while (!correctData) {
            try {
                System.out.println(message);
                inputValue = scanner.nextFloat();
                correctData = true;
            } catch (InputMismatchException e) {
                System.out.println(ERROR_FLOAT);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return inputValue;
    }

    public static double readDouble(String message) {
        double inputValue = 0.0;
        boolean correctData = false;

        while (!correctData) {
            try {
                System.out.print(message);
                inputValue = scanner.nextDouble();
                correctData = true;
            } catch (InputMismatchException e) {
                System.out.println(ERROR_DOUBLE);
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return inputValue;
    }

    public static char readChar(String message) {
        String inputValue;
        boolean correctData = false;
        char inputChar = ' ';

        while (!correctData) {
            try {
                System.out.println(message);
                inputValue = scanner.nextLine();
                if (inputValue.length() != 1) {
                    throw new Exception(ERROR_CHAR);
                }
                inputChar = inputValue.charAt(0);
                correctData = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
        return inputChar;
    }

    public static String readString(String message) {
        String inputValue = "";
        boolean correctData = false;
        while (!correctData) {
            try {
                System.out.print(message);
                inputValue = scanner.nextLine();

                if (inputValue.isEmpty()) {
                    throw new Exception(ERROR_EMPTY_STRING);
                }
                correctData = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return inputValue;
    }

    public static boolean readYesNo(String message) {
        while (true) {
            try {
                System.out.println(message + " (s/n)");
                String inputValue = scanner.nextLine();
                if (inputValue.equalsIgnoreCase("s")) {
                    return true;
                } else if (inputValue.equalsIgnoreCase("n")) {
                    return false;
                } else {
                    throw new Exception(ERROR_YES_NO);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
