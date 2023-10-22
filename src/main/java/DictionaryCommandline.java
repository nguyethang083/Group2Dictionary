public class DictionaryCommandline {
    public void showAllWords(Dictionary showWord) {
        System.out.println("No\t | English\t | Vietnamese");
        for (int i = 0; i < showWord.list_word.size(); i++) {
            String wordTarget = showWord.list_word.get(i).getWord_target();
            String wordExplain = showWord.list_word.get(i).getWord_explain();
            System.out.println(i + 1 + "\t | " + wordTarget + "\t | " + wordExplain);
        }
    }
    public void dictionaryBasic(DictionaryManagement insertDict, Dictionary inputDict) {
        insertDict.insertFromCommandline(inputDict);
        showAllWords(inputDict);
    }

    public static void main(String[] args) {
        Dictionary DictTest = new Dictionary();
        DictionaryManagement DictManagement = new DictionaryManagement();
        DictionaryCommandline DictCmD = new DictionaryCommandline();
        DictCmD.dictionaryBasic(DictManagement, DictTest);
    }
}
