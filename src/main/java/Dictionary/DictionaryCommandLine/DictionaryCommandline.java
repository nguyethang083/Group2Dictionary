package Dictionary.DictionaryCommandLine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import Dictionary.Game.Quiz;

public class DictionaryCommandline {
    public static void showAllWords() {
        System.out.println(DataBase.readFromDatabase());
    }

    public ArrayList<String> dictionarySearcher(Dictionary inputDictionary) {
        System.out.println("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        int prefixLen = prefix.length();
        ArrayList<String> searchingResult = new ArrayList<String>();
        for (Word word : inputDictionary.wordArray) {
            String currentTarget = word.getWordTarget();
            if (currentTarget.length() < prefixLen) continue;
            if (prefix.equals(currentTarget.substring(0, prefixLen))) {
                searchingResult.add(currentTarget);
            }
        }
        return searchingResult;
    }

    public void gameplay(Dictionary inputDictionary) {
        int score = 0;
        for (int i = 0; i < 5; i++) {
            System.out.print((i+1) + ". ");
            Quiz quiz = new Quiz();
            score = quiz.controlQuiz(inputDictionary, score);
        }
        System.out.println("End game. Your score is: " + score + "/5");
    }

    public void dictionaryBasic(DictionaryManagement insertDict, Dictionary inputDict) {
        //insertDict.insertFromCommandline(inputDict);
        insertDict.insertFromFile2();
        showAllWords();
        insertDict.dictionaryLookup();
        insertDict.deleteWord();
        showAllWords();
        /*insertDict.updateWord(inputDict);
        showAllWords();*/
        insertDict.dictionaryExportToFile();
        ArrayList<String> prefixSearch = dictionarySearcher(inputDict);
        System.out.println("Result:");
        for (String result : prefixSearch) {
            System.out.println(result);
        }
        gameplay(inputDict);
    }

    public static void main(String[] args) {
        DataBase.connect();
        Dictionary DictTest = new Dictionary();
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement, DictTest);
    }
}