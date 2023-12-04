package Dictionary.Game;

import Dictionary.Entities.AllWord;
import Dictionary.Entities.EngWord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Quiz {
    long seed = System.currentTimeMillis();
    Random random = new Random(seed);
    private String question;
    private ArrayList<String> choice = new ArrayList<>();
    private String correctAnswer;
    private String inputAnswer;
    private int score;
    private int numberofQuestion;

    public Quiz() throws SQLException {
    }

    public String endQuiz() {
        return "Your score is: " + score;
    }

    public String generateQuestion() {
        return "What is the word for: " + question;
    }

    public void initQuiz() {
        if (!choice.isEmpty()) choice.clear();
        ArrayList<Integer> wordChoice = new ArrayList<>();
        int answerIndex = (int) (Math.random() * 4);
        int i = 0;
        while (wordChoice.size() < 4) {
            int random = (int) (Math.random() * AllWord.dbSize);
            EngWord tmp = AllWord.allWord.get(random);
            while (wordChoice.contains(random) || !validChoice(tmp.getMeaning())) {
                random = (int) (Math.random() * AllWord.dbSize);
                tmp = AllWord.allWord.get(random);
            }
            wordChoice.add(random);
            choice.add(tmp.getWord().toLowerCase());
            if (i == answerIndex) {
                correctAnswer = tmp.getWord().toLowerCase();
                question = tmp.getMeaning();
            }
            i++;
        }
    }

    public boolean validChoice(String choice) {
        if (choice == null || choice.trim().isEmpty()) {
            return false;
        }
        String simpleChoice = choice.trim().toLowerCase();
        if (simpleChoice.startsWith("of") || simpleChoice.startsWith("alt") || simpleChoice.startsWith(" ")) {
            return false;
        }
        return choice.length() <= 100;
    }

    public boolean checkAnswer() {
        return inputAnswer.equals(correctAnswer);
    }


    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseNumberofQuestion() {
        numberofQuestion++;
    }

    public int getNumberofQuestion() {
        return numberofQuestion;
    }

    public void setNumberofQuestion(int numberofQuestion) {
        this.numberofQuestion = numberofQuestion;
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
