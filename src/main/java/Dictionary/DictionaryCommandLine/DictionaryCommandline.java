package Dictionary.DictionaryCommandLine;

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