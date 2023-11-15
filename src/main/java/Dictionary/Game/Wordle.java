package Dictionary.Game;

import Dictionary.Entities.AllWord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.SQLException;
import java.util.Scanner;

import static Dictionary.DatabaseConn.WordDAO;

public class Wordle {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    private String answer;
    private int numberofGuess = 0;
    private static List<String> wordleWord = new ArrayList<>();
    static {
        try {
            File fileName = new File("src/main/resources/valid-wordle-words.txt");
            Scanner input = new Scanner(fileName);
            while (input.hasNextLine()) {
                String word = input.nextLine();
                wordleWord.add(word);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static int wordledbSize;
    static {
        wordledbSize = wordleWord.size();
    }

    public Wordle() {

    };

    public void generateRandomWord() {
        int random = (int) (Math.random() * wordledbSize);
        answer = wordleWord.get(random);
    }

    public boolean valid(String guess) {
        int low = 0, high = wordledbSize - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = guess.compareTo(wordleWord.get(mid));
            if (comparison == 0) return true;
            if (comparison > 0) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

    public boolean contains(String[] keyboardRow, String letter) {
        for (String key : keyboardRow)
        {
            if (key.equalsIgnoreCase(letter)) {
                return true;
            }
        }
        return false;
    }

    public String letterStatus(String inputLetter, int currentIndex) {
        String status = "";
        if (String.valueOf(answer.charAt(currentIndex - 1)).toLowerCase().equals(inputLetter.toLowerCase())) {
            status = "correct-letter";
        } else if (answer.contains(inputLetter)) {
            status = "present-letter";
        } else {
            status = "wrong-letter";
        }
        return status;
    }

    public String getAnswer() {
        return answer;
    }

    public int getNumberofGuess() {
        return numberofGuess;
    }

    public void setNumberofGuess(int numberofGuess) {
        this.numberofGuess = numberofGuess;
    }

    public void increaseNumberofGuess() {
        this.numberofGuess++;
    }
}
