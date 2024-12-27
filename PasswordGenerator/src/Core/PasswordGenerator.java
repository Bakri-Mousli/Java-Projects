package Core;

import java.util.Random;

public class PasswordGenerator {
    public static final String LOWERCASE_CHARACTERS = CharactersGenerator.generateLowercaseLetters();
    public static final String UPPERCASE_CHARACTERS = CharactersGenerator.generateUppercaseLetters();
    public static final String NUMBERS = CharactersGenerator.generateDigits();
    public static final String SPECIAL_SYMBOLS = CharactersGenerator.generateSpecialCharacters();

    // the random class allows us to generate a random number which will be used to randomly choose the characters
    private final Random random;

    // constructor
    public PasswordGenerator(){
        random = new Random();
    }
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                                   boolean includeNumbers, boolean includeSpecialSymbols){
        StringBuilder passwordBuilder = new StringBuilder();

        String validCharacters = "";
        if(includeUppercase) validCharacters += UPPERCASE_CHARACTERS;
        if(includeLowercase) validCharacters += LOWERCASE_CHARACTERS;
        if(includeNumbers) validCharacters += NUMBERS;
        if(includeSpecialSymbols) validCharacters += SPECIAL_SYMBOLS;



        for(int i = 0; i < length; i++){
            // generate a random index
            int randomIndex = random.nextInt(validCharacters.length());

            // get the char based on the random index
            char randomChar = validCharacters.charAt(randomIndex);

            // store char into password builder
            passwordBuilder.append(randomChar);
        }

        // return the result
        return passwordBuilder.toString();
    }
}








