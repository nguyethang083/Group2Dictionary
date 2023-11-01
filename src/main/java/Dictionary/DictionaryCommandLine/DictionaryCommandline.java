package Dictionary.DictionaryCommandLine;

import java.util.ArrayList;
import java.util.Scanner;

import Dictionary.models.Dao.AllWord;
import Dictionary.models.Dao.WordsDao;
import Dictionary.models.Entity.Word;

public class DictionaryCommandline {
    public void dictionaryBasic(DictionaryManagement insertDict) {
        insertDict.removeWord();
        insertDict.addWord();
        insertDict.modifyWord();
        insertDict.dictionarySearcher();
        insertDict.lookup();
    }

    public static void main(String[] args) {
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement);
    }
}