import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String pin;
        String cellPhoneNumber = "01179236104";
        String birthDate = "2000-05-12";

        Scanner sc = new Scanner(System.in);
        pin = sc.next();

        // Check PIN criteria
        if (pin.length() != 4) {
            System.out.println("Wrong PIN length!");
        } else if (!isValidPin(pin, cellPhoneNumber, birthDate)) {
            System.out.println("The PIN is invalid!");
        } else {
            System.out.println("The PIN is valid.");
        }
    }

    private static boolean isValidPin(String pin, String phoneNumber, String dateOfBirthString) {
        // Check for sequential numbers
        if (hasSequentialNumbers(pin)) {
            return false;
        }

        // Check that PIN is not derived from the date of birth
        if (isDerivedFromDateOfBirth(pin, dateOfBirthString)) {
            return false;
        }

        // Check that PIN is not a subset of the phone number
        return !isSubset(pin, phoneNumber);
    }

    private static boolean hasSequentialNumbers(String pin) {
        // Define sets of sequential numbers
        Set<String> sequentialNumbers = new HashSet<>(Arrays.asList(
                "0123", "1234", "2345", "3456", "4567", "5678", "6789",
                "9876", "8765", "7654", "6543", "5432", "4321", "3210"
        ));

        // Check for sequential numbers
        return sequentialNumbers.contains(pin);
    }
    private static boolean isDerivedFromDateOfBirth(String pin, String dateOfBirthString) {
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DateTimeFormatter.ISO_LOCAL_DATE);

        // Check birthdate containing zeros
        Set<String> datesWithoutZeros = generateDatesWithoutZeros(dateOfBirth);
        for (String date : datesWithoutZeros) {
            if (pin.contains(date)) {
                return true;
            }
        }

        // Generate all possible combinations of the date of birth
        Set<String> dateOfBirthCombinations = generateAllCombinations(dateOfBirth);

        // Check if the PIN is derived from the date of birth
        return dateOfBirthCombinations.contains(pin);
    }
    private static Set<String> generateAllCombinations(LocalDate date) {
        Set<String> combinations = new HashSet<>();

        // Generate combinations using different date formats
        combinations.add(date.format(DateTimeFormatter.ofPattern("MMdd")));
        combinations.add(date.format(DateTimeFormatter.ofPattern("ddMM")));
        combinations.add(date.format(DateTimeFormatter.ofPattern("yydd")));
        combinations.add(date.format(DateTimeFormatter.ofPattern("ddyy")));
        combinations.add(date.format(DateTimeFormatter.ofPattern("MMyy")));
        combinations.add(date.format(DateTimeFormatter.ofPattern("yyyy")));

        return combinations;
    }
    private static boolean isSubset(String pin, String str) {
        return str.contains(pin);
    }

    private static Set<String> generateDatesWithoutZeros(LocalDate dateOfBirth) {
        int day = dateOfBirth.getDayOfMonth();
        int month = dateOfBirth.getMonthValue();
        int year = dateOfBirth.getYear() % 100;
        Set<String> datesWithoutZeros = new HashSet<>();
        datesWithoutZeros.add(day + String.valueOf(month));
        datesWithoutZeros.add(month + String.valueOf(day));
        datesWithoutZeros.add(year + String.valueOf(day));
        datesWithoutZeros.add(day + String.valueOf(year));
        datesWithoutZeros.add(month + String.valueOf(year));
        datesWithoutZeros.add(year + String.valueOf(month));
        return datesWithoutZeros;
    }
}