package Dictionary.Entities;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "SavedWord", daoClass = SavedWordDAO.class)
public class SavedWord {
    @DatabaseField(canBeNull = false, index = true)
    private String User_id = "";
    @DatabaseField(canBeNull = false, index = true)
    private long English_id;

    public long getEnglish_id() {
        return English_id;
    }

    public String getUser_id() {
        return User_id;
    }
    /*public EngWord() {
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
    public EngWord(String word, String Meaning, String pronunciation){
        Word = word;
        this.Meaning = Meaning;
        Pronunciation = pronunciation;
    }
    public EngWord(String word, String Meaning){
        Word = word;
        if(Meaning != null) {
            this.Meaning = Meaning;
        }
    }
    // englishDAO.create(new English("Hello", "Xin chao", "'he:llo'", "Noun", "Greeting,Hello, how are you?"));
    public EngWord(String word, String Meaning, String pronunciation, String type, String example){
        Word = word;
        this.Meaning = Meaning;
        Pronunciation = pronunciation;
        Type = type;
        Example = example;
    }
    @Override
    public String toString(){
        return "The word is " + Word + " " + Meaning + " " + Pronunciation + " " + Type + " " + Example + " " + Synonym + " " + Antonyms + "\n";
    }*/
}