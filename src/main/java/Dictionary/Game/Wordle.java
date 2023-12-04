package Dictionary.Game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    private String answer;
    private static final List<String> wordleWord = new ArrayList<>();

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

    private static final int wordledbSize;

    static {
        wordledbSize = wordleWord.size();
    }

    public Wordle() {

    }

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
        for (String key : keyboardRow) {
            if (key.equalsIgnoreCase(letter)) {
                return true;
            }
        }
        return false;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
