package Dictionary.DictionaryCommandLine;

import java.util.ArrayList;
import java.util.Scanner;

import Dictionary.models.Dao.AllWord;
import Dictionary.models.Dao.WordsDao;
import Dictionary.models.Entity.Word;

public class DictionaryCommandline {
    public void dictionarySearcher() {
        System.out.print("Search: ");
        Scanner sc = new Scanner(System.in);
        String prefix = sc.nextLine();
        ArrayList<Word> searchingResult = WordsDao.queryWord(prefix, "English");
        System.out.println("Result:");
        System.out.println("No\t | English         | Vietnamese");
        for (int i = 0; i < searchingResult.size(); i++) {
            String word = searchingResult.get(i).getWord();
            String meaning = searchingResult.get(i).getMeaning();
            System.out.printf("%d\t | %-15s | %s\n",i + 1 ,word ,meaning);
        }
    }

    public void dictionaryBasic(DictionaryManagement insertDict) {
        insertDict.removeWord();
        insertDict.addWord();
        dictionarySearcher();
    }

    public static void main(String[] args) {
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement);
    }
}