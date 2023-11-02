package Dictionary.DictionaryCommandLine;

import Dictionary.models.EngWord;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static Dictionary.DatabaseConn.WordDAO;

public class DictionaryManagement {
    public void removeWord() throws SQLException {
        System.out.print("Enter the word that you want to remove: ");
        Scanner sc = new Scanner(System.in);
        String removeWord = sc.nextLine();

        if (!WordDAO.deleteWordByString(removeWord))
        {
            System.out.println("This word doesn't exist in the dictionary!");
        }
    }

    public void addWord() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the word that you want to add: \n");
        System.out.print("word: ");
        String word = sc.nextLine();
        System.out.print("meaning: ");
        String meaning = sc.nextLine();
        EngWord newWord = new EngWord(word, meaning, null);

        if(WordDAO.addWord(newWord)) System.out.println("thanh cong oi\n");
    }

    /*public void modifyWord()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the word that you want to modify meaning: \n");
        System.out.print("word: ");
        String word = sc.nextLine();
        System.out.print("meaning/type/pronunciation: ");
        String choose = sc.nextLine();
        System.out.print("write what you want to modify to ...: ");
        String modify = sc.nextLine();

        if(WordsDao.modifyWord(word, choose, modify, "English")) System.out.println("thanh cong oi\n");
    }*/

    public void dictionarySearcher() throws SQLException {
        System.out.print("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        List<EngWord> searchingResult = WordDAO.containWordByString(prefix);
        System.out.println("Result:");
        System.out.println("No\t | English         | Meaning");
        for (int i = 0; i < ((List<?>) searchingResult).size(); i++) {
            String word = searchingResult.get(i).getWord();
            String meaning = searchingResult.get(i).getMeaning();
            System.out.printf("%d\t | %-15s | %s\n",i + 1 ,word ,meaning);
        }
    }

    /*public void dictionarySearcher2() {
        System.out.print("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        ArrayList<String> searchingResult = WordDAO.queryListWordByString(prefix);
        System.out.println("Result:");
        System.out.println("No\t | English         | Meaning");
        for (int i = 0; i < searchingResult.size(); i++) {
            String word = searchingResult.get(i);
            System.out.printf("%-15s\n",i + 1 ,word);
        }
    }*/

    public void lookup() throws SQLException {
        System.out.print("Look up: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        EngWord searchingResult = WordDAO.queryListWordByString(word).get(0);
        if (searchingResult == null) return;
        System.out.println("Result:");
        System.out.println("1, Word: " + searchingResult.getWord());
        System.out.println("2, Type: " + searchingResult.getType());
        System.out.println("3, Pronunciation: " + searchingResult.getPronunciation());
        System.out.println("4, Meaning: " + searchingResult.getMeaning());
        System.out.println("5, Example: " + searchingResult.getExample());
        VoiceFunction.playVoice(searchingResult.getWord());
        VoiceFunction.playVoice(searchingResult.getMeaning());
        VoiceFunction.playVoice(searchingResult.getExample());
    }
}