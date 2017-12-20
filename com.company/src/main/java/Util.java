import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    private static Integer validateAndReturnInt(String value, Integer min, Integer max) {
        Integer intValue;
        try {
            intValue = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            System.out.println("Please, enter a number");
            return readAndValidateInt(min, max);
        }
        if ((max != null && intValue > max) || (min != null && intValue < min)) {
            System.out.println("Please, enter a number from " + min + " to " + max);
            return readAndValidateInt(min, max);
        }
        return intValue;
    }

    public static Integer readAndValidateInt(Integer min, Integer max) {
        String value;
        try {
            value = new BufferedReader(new InputStreamReader(System.in)).readLine();
            return validateAndReturnInt(value, min, max);
        } catch (IOException e) {
            System.out.println("Impossible to read from console");
            System.exit(1);
        }
        return null;
    }
}
