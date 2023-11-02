package Dictionary.DictionaryCommandLine;

import java.util.ArrayList;
import java.util.Scanner;

import Dictionary.models.Dao.WordsDao;
import Dictionary.models.Entity.Word;

public class DictionaryManagement {
    public void removeWord()
    {
        System.out.print("Enter the word that you want to remove: ");
        Scanner sc = new Scanner(System.in);
        String removeWord = sc.nextLine();

        if (!WordsDao.deleteWord(removeWord, "English"))
        {
            System.out.println("This word doesn't exist in the dictionary!");
        }
    }

    public void addWord()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the word that you want to add: \n");
        System.out.print("word: ");
        String word = sc.nextLine();
        System.out.print("meaning: ");
        String meaning = sc.nextLine();
        Word newWord = new Word(word, null, null, meaning);

        if(WordsDao.addWord(newWord, "English")) System.out.println("thanh cong oi\n");
    }

    public void modifyWord()
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
    }

    public void dictionarySearcher() {
        System.out.print("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        ArrayList<Word> searchingResult = WordsDao.queryWord(prefix, "English");
        System.out.println("Result:");
        System.out.println("No\t | English         | Meaning");
        for (int i = 0; i < searchingResult.size(); i++) {
            String word = searchingResult.get(i).getWord();
            String meaning = searchingResult.get(i).getMeaning();
            System.out.printf("%d\t | %-15s | %s\n",i + 1 ,word ,meaning);
        }
    }

    public void lookup() {
        System.out.print("Look up: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        Word searchingResult = WordsDao.queryWord(prefix, "English").get(0);
        System.out.println("Result:");
        System.out.println("1, Word: " + searchingResult.getWord());
        System.out.println("2, Type: " + searchingResult.getType());
        System.out.println("3, Pronunciation: " + searchingResult.getPronunciation());
        System.out.println("4, Meaning: " + searchingResult.getMeaning());
    }
}