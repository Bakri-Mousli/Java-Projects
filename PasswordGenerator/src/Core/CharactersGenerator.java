package Core;

public class CharactersGenerator {
    public static String generateDigits() {
        StringBuilder digits = new StringBuilder();
        for (int i = 48; i <= 57; i++)
            digits.append((char) i);
        return digits.toString();
    }
    public static String generateLowercaseLetters() {
        StringBuilder lowercaseLetters = new StringBuilder();
        for (int i = 97; i <= 122; i++)
            lowercaseLetters.append((char) i);
        return lowercaseLetters.toString();
    }
    public static String generateUppercaseLetters() {
        StringBuilder uppercaseLetters = new StringBuilder();
        for (int i = 65; i <= 90; i++)
            uppercaseLetters.append((char) i);
        return uppercaseLetters.toString();
    }
    public static String generateSpecialCharacters() {
        StringBuilder specialCharacters = new StringBuilder();

        // Loop for ASCII range 33 to 126
        for (int i = 33; i <= 126; i++) {
            // Skip ASCII characters 48 to 57 (digits)
            // Skip ASCII characters 65 to 90 (uppercase letters)
            // Skip ASCII characters 97 to 122 (lowercase letters)
            // Skip ASCII characters 123 to 126 (brackets and tilde)
            if ((i >= 48 && i <= 57) || (i >= 65 && i <= 90) || (i >= 97 && i <= 122))
                continue;
            specialCharacters.append((char) i);
        }

        return specialCharacters.toString();
    }


}
