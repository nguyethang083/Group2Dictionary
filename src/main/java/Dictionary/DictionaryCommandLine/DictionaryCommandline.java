package Dictionary.DictionaryCommandLine;

import java.sql.SQLException;

public class DictionaryCommandline {
    public void dictionaryBasic(DictionaryManagement insertDict) throws SQLException {
//        insertDict.removeWord();
//        insertDict.addWord();
//        //insertDict.modifyWord();
//        insertDict.dictionarySearcher();
//        //insertDict.dictionarySearcher2();
//        insertDict.lookup();
        insertDict.UserSearcher();
    }

    public static void main(String[] args) throws SQLException {
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement);
    }
}