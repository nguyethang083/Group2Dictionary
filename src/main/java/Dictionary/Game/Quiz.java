package Dictionary.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Dictionary.DictionaryCommandLine.Dictionary;

public class Quiz {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);

    private String question = "";
    private ArrayList<String> choice = new ArrayList<>();
    private int answer = -1;

    public void initQuiz(Dictionary inputDictionary) {
        int vocabRange = inputDictionary.wordArray.size();
        ArrayList<Integer> wordChoice = new ArrayList<>();
        while (wordChoice.size() < 4) {
            int random = (int) (Math.random() * vocabRange);
            while (wordChoice.contains(random)) {
                random = (int) (Math.random() * vocabRange);
            }
            wordChoice.add(random);
        }
        answer = (int) (Math.random() * 4);
        question = inputDictionary.wordArray.get(wordChoice.get(answer)).getWordTarget();
        for (int i : wordChoice) {
            choice.add(inputDictionary.wordArray.get(i).getWordExplain());
        }
    }

    public boolean validAnswer(String inputAnswer) {
        inputAnswer = inputAnswer.trim().toLowerCase();
        return inputAnswer.equals("a") || inputAnswer.equals("b")
                || inputAnswer.equals("c") || inputAnswer.equals("d");
    }

    public int controlQuiz(Dictionary inputDictionary, Integer score) {
        initQuiz(inputDictionary);
        System.out.println("What is the meaning of: " + question);
        for (int i = 0; i < 4; i++) {
            System.out.printf("[%c] %s\n", 'A' + i, choice.get(i));
        }
        System.out.println("Choose the answer A|B|C|D: ");
        Scanner sc = new Scanner(System.in);
        String inputAnswer = sc.nextLine();
        while (!validAnswer(inputAnswer)) {
            System.out.println("Your input is invalid. Try again: ");
            inputAnswer = sc.nextLine();
        }
        inputAnswer = inputAnswer.trim().toLowerCase();
        if (inputAnswer.equals(Character.toString('a' + answer))) {
            System.out.println("Correct!");
            score += 1;
        } else {
            System.out.printf("Incorrect! The answer is [%c] %s\n", 'A' + answer, choice.get(answer));
        }
        return score;
    }
}
