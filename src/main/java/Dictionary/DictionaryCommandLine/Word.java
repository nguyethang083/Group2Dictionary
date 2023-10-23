package Dictionary.DictionaryCommandLine;

public class Word {
    private String wordTarget;
    private String wordExplain;

    public Word() {

    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    @Override
    public String toString() {
        return wordTarget + "\t-\t" + wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
