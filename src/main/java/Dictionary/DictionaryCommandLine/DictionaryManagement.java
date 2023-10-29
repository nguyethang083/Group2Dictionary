package Dictionary.DictionaryCommandLine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
}