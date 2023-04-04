import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class NameDateParse {
    private String str, gender;
    private int fullYears = -1;
    private String[] fullNameArr;

    void setGender() {
        String secondName = fullNameArr[2];
        try {
            String lastSymbols = secondName.substring(secondName.length() - 2);
            if (lastSymbols.equals("на") || lastSymbols.equals("зы")) {
                gender = "Ж";
            } else if (lastSymbols.equals("ич") || lastSymbols.equals("лы")) {
                gender = "М";
            } else {
                gender = "Невозможно определить пол";
            }
        } catch (StringIndexOutOfBoundsException ex) {
            throw new StringIndexOutOfBoundsException(ex.getMessage());
        }
    }

    void setAge() {
        try {
            DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthDate = LocalDate.parse(fullNameArr[3], europeanDateFormatter);
            LocalDate currentDate = LocalDate.now();
            if (birthDate != null) {
                fullYears = Period.between(birthDate, currentDate).getYears();
            }
            if (fullYears < 0) {
                throw new IllegalArgumentException("Date of birth must be in the past!");
            }
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    String wordYear(int age) {
        String old = "";
        boolean isExclusion = (age % 100 >= 11) && (age % 100 <= 14);
        int ageLastNumber = age % 10;

        if (ageLastNumber == 1)
            old = "год";
        else if (ageLastNumber == 0 || ageLastNumber >= 5 && ageLastNumber <= 9)
            old = "лет";
        else if (ageLastNumber >= 2 && ageLastNumber <= 4)
            old = "года";
        if (isExclusion)
            old = "лет";
        return old;
    }

    void firstLettersToUpperCase() {
        for (int i = 0; i < fullNameArr.length - 1; i++) {
            fullNameArr[i] = fullNameArr[i].substring(0, 1).toUpperCase() + fullNameArr[i].substring(1);
        }
    }

    void input() {
        Scanner in = new Scanner(System.in);

        while (str == null || fullNameArr.length != 4) {
            System.out.println("Input fullName and date of birth");
            str = in.nextLine();
            fullNameArr = str.split(" ");
            if (fullNameArr.length != 4) {
                System.out.println("Incorrect. Try again");
            }
        }
    }

    void print() {
        try {
            firstLettersToUpperCase();
            setGender();
            setAge();
            String wordYear = wordYear(fullYears);
            System.out.println(fullNameArr[0] + ' ' + fullNameArr[1].charAt(0)
                    + '.' + fullNameArr[2].charAt(0) + ". " + gender + " " + fullYears
                    + " " + wordYear);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Incorrect fullName!" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Incorrect date!" + ex.getMessage());
        }
    }
}
