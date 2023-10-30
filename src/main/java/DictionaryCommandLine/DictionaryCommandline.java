package DictionaryCommandLine;

import Game.Quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {
    public void showAllWords(Dictionary showWord) {
        System.out.println("No\t | English         | Vietnamese");
        for (int i = 0; i < showWord.list_word.size(); i++) {
            String wordTarget = showWord.list_word.get(i).getWord_target();
            String wordExplain = showWord.list_word.get(i).getWord_explain();
            System.out.printf("%d\t | %-15s | %s\n",i + 1 ,wordTarget ,wordExplain);
        }
    }

    public ArrayList<String> dictionarySearcher(Dictionary inputDictionary) {
        System.out.println("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        int prefixLen = prefix.length();
        ArrayList<String> searchingResult = new ArrayList<String>();
        for (Word word : inputDictionary.list_word) {
            String currentTarget = word.getWord_target();
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
        insertDict.insertFromFile(inputDict);
        showAllWords(inputDict);
        int index = insertDict.dictionaryLookup(inputDict);
        insertDict.removeWord(inputDict);
        showAllWords(inputDict);
        insertDict.addWord(inputDict);
        showAllWords(inputDict);
        insertDict.dictionaryExportToFile(inputDict);
        ArrayList<String> prefixSearch = dictionarySearcher(inputDict);
        System.out.println("Result:");
        for (String result : prefixSearch) {
            System.out.println(result);
        }
        gameplay(inputDict);
    }

    public static void main(String[] args) {
        Dictionary DictTest = new Dictionary();
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement, DictTest);
    }
}
