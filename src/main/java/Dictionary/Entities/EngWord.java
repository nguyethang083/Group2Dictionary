package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "English", daoClass = WordDAO.class)
public class EngWord {
    @DatabaseField(generatedId = true, index = true)
    private long Id;
    @DatabaseField(canBeNull = false, index = true)
    private String Word = "";
    @DatabaseField(canBeNull = true, index = true)
    private String Type = "";
    @DatabaseField(canBeNull = false, index = true)
    private String Meaning = "";

    @DatabaseField(canBeNull = true, index = true)
    private String Pronunciation = "";
    @DatabaseField(canBeNull = true, index = true)
    private String Example = "";

    @DatabaseField(canBeNull = true, index = true)
    private String Synonym = "";

    @DatabaseField(canBeNull = true, index = true)
    private String Antonyms = "";

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getPronunciation() {
        return Pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        Pronunciation = pronunciation;
    }

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }

    public String getSynonym() {
        return Synonym;
    }

    public void setSynonym(String synonym) {
        Synonym = synonym;
    }

    public String getAntonyms() {
        return Antonyms;
    }

    public void setAntonyms(String antonyms) {
        Antonyms = antonyms;
    }

    public EngWord() {
    }

    public EngWord(String word, String type, String meaning, String pronunciation, String example, String synonym, String antonyms) {
        Word = word;
        Type = type;
        Meaning = meaning;
        Pronunciation = pronunciation;
        Example = example;
        Synonym = synonym;
        Antonyms = antonyms;
    }

    public EngWord(String word, String Meaning, String pronunciation) {
        Word = word;
        this.Meaning = Meaning;
        Pronunciation = pronunciation;
    }

    public EngWord(String word, String Meaning) {
        Word = word;
        if (Meaning != null) {
            this.Meaning = Meaning;
        }
    }

    // englishDAO.create(new English("Hello", "Xin chao", "'he:llo'", "Noun", "Greeting,Hello, how are you?"));
    public EngWord(String word, String Meaning, String pronunciation, String type, String example) {
        Word = word;
        this.Meaning = Meaning;
        Pronunciation = pronunciation;
        Type = type;
        Example = example;
    }

    @Override
    public String toString() {
        return "The word is " + Word + " " + Meaning + " " + Pronunciation + " " + Type + " " + Example + " " + Synonym + " " + Antonyms + "\n";
    }
}