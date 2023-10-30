package Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import DictionaryCommandLine.Dictionary;

public class Quiz {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    private String question;
    private ArrayList<String> choice = new ArrayList<>();
    private int answer = -1;
    private String correctAnswer;
    private String inputAnswer;
    private int score;
    private int numberofQuestion;
    private int correctAnswernum;

    public String endQuiz() {
        return "Your score is: " + score;
    }

    public String generateQuestion() {
        return "What is the meaning of: " + question;
    }

    public void initQuiz(Dictionary inputDictionary) {
        int vocabRange = inputDictionary.list_word.size();
        if (!choice.isEmpty()) choice.clear();
        ArrayList<Integer> wordChoice = new ArrayList<>();
        while (wordChoice.size() < 4) {
            int random = (int) (Math.random() * vocabRange);
            while (wordChoice.contains(random)) {
                random = (int) (Math.random() * vocabRange);
            }
            wordChoice.add(random);
        }
        answer = (int) (Math.random() * 4);
        question = inputDictionary.list_word.get(wordChoice.get(answer)).getWord_target();
        int random = (int) (Math.random() * 4);
        correctAnswernum = random;
        question = inputDictionary.list_word.get(wordChoice.get(random)).getWord_target();
        for (int i : wordChoice) {
            choice.add(inputDictionary.list_word.get(i).getWord_explain());
            if (i == wordChoice.get(random)) correctAnswer = inputDictionary.list_word.get(i).getWord_explain();
        }
    }

    public boolean validAnswer(String inputAnswer) {
        inputAnswer = inputAnswer.trim().toLowerCase();
        return inputAnswer.equals("a") || inputAnswer.equals("b")
                || inputAnswer.equals("c") || inputAnswer.equals("d");
    }

    public boolean checkAnswer() {
        return inputAnswer.equals(correctAnswer);
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
            if (inputAnswer.equals(Character.toString('a' + correctAnswernum))) {
                System.out.println("Correct!");
                score += 1;
            } else {
                System.out.printf("Incorrect! The answer is [%c] %s\n", 'A' + answer, choice.get(answer));
                System.out.printf("Incorrect! The answer is [%c] %s\n", 'A' + correctAnswernum, choice.get(correctAnswernum));
            }
        }
        return  score;
    }

    public void increaseScore () {
        score++;
    }


    public void increaseNumberofQuestion () {
        numberofQuestion++;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoice() {
        return choice;
    }

    public void setChoice(ArrayList<String> choice) {
        this.choice = choice;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }
}