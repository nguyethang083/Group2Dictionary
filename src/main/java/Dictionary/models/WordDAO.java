package Dictionary.models;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO extends BaseDaoImpl<EngWord, Long> {
    public WordDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EngWord.class);
    }

    public long queryIdByWord(String word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word).queryForFirst().getId();
    }

    public boolean updatePronunciation(String word, String pronunciation) throws SQLException {
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (engWord != null) {
                if (engWord.getPronunciation().isEmpty())
                    engWord.setPronunciation(pronunciation);
                else if (!pronunciation.isEmpty())
                    engWord.setPronunciation(engWord.getPronunciation() + "\n" + pronunciation);
                this.update(engWord);
            } else {
                EngWord newEngWord = new EngWord();
                newEngWord.setWord(word);
                newEngWord.setPronunciation(pronunciation);
                this.create(newEngWord);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertPronunciation");
            return false;
        }
        return true;
    }

    public boolean updateMeaning(String word, String meaning) throws SQLException {
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (engWord != null) {
                if (engWord.getMeaning().isEmpty()) {
                    engWord.setMeaning(meaning);
                } else if (!meaning.isEmpty())
                    engWord.setMeaning(meaning);
                this.update(engWord);
            } else {
                EngWord newEngWord = new EngWord();
                newEngWord.setWord(word);
                newEngWord.setMeaning(meaning);
                this.create(newEngWord);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertMeaning");
            return false;
        }
        return true;
    }

    public boolean updateExample(String word, String example) throws SQLException {
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (engWord != null) {
                if (engWord.getExample().isEmpty()) {
                    engWord.setExample(example);
                } else if (!example.isEmpty())
                    engWord.setExample(example);
                this.update(engWord);
            } else {
                EngWord newEngWord = new EngWord();
                newEngWord.setWord(word);
                newEngWord.setExample(example);
                this.create(newEngWord);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertExample");
            return false;
        }
        return true;
    }

    public boolean updateSynonym(String word, String synonym) throws SQLException {
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (engWord != null) {
                if (engWord.getSynonym().isEmpty()) {
                    engWord.setSynonym(synonym);
                } else if (!synonym.isEmpty())
                    engWord.setSynonym(engWord.getSynonym() + "\n" + synonym);
                this.update(engWord);
            } else {
                EngWord newEngWord = new EngWord();
                newEngWord.setWord(word);
                newEngWord.setSynonym(synonym);
                this.create(newEngWord);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertSynonym");
            return false;
        }
        return true;
    }

    public boolean updateType(String word, String type) throws SQLException {
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", word).queryForFirst();
            if (engWord != null) {
                if (engWord.getType().isEmpty()) {
                    engWord.setType(type);
                } else if (!type.isEmpty())
                    engWord.setType(type);
                this.update(engWord);
            } else {
                EngWord newEngWord = new EngWord();
                newEngWord.setWord(word);
                newEngWord.setType(type);
                this.create(newEngWord);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage() + " insertType");
            return false;
        }
        return true;
    }

    // co the tra ra null
    public EngWord queryWordByString(String word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word).queryForFirst();
    }

    public EngWord queryWordByEngWord(EngWord word) throws SQLException {
        return this.queryBuilder().where().eq("Word", word.getWord()).queryForFirst();
    }

    public boolean addWord(EngWord x) throws SQLException {
        if (x.getWord().isEmpty() || x.getMeaning().isEmpty()) {
            return false;
        }
        try {
            EngWord engWord = this.queryBuilder().where().eq("Word", x.getWord()).queryForFirst();
            if (engWord != null && !engWord.getWord().isEmpty()) {
                engWord.setMeaning(x.getMeaning());
                engWord.setType(x.getType());
                engWord.setPronunciation(x.getPronunciation());
                engWord.setAntonyms(x.getAntonyms());
                engWord.setSynonym(x.getSynonym());
                engWord.setExample(x.getExample());
                this.update(engWord);
            } else {
                this.create(x);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " insertWord");
            return false;
        }
        return true;
    }

    public boolean whetherExist(String x) throws SQLException {
        EngWord engWord = this.queryBuilder().where().eq("Word", x).queryForFirst();
        return engWord == null;
    }

    public boolean whetherExist(EngWord engWord) throws SQLException {
        EngWord engWord1 = this.queryBuilder().where().eq("Word", engWord.getWord()).queryForFirst();
        return engWord1 == null;
    }

    public boolean deleteWordByString(String word) throws SQLException {
        try {
            Where<EngWord, Long> english = this.queryBuilder().where().eq("Word", word);
            for (EngWord x : english.query()) {
                this.delete(x);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteWord " + word);
            return false;
        }
    }

    public boolean deleteWordByEngWord(EngWord engWord) throws SQLException {
        try {
            Where<EngWord, Long> english1 = this.queryBuilder().where().eq("Word", engWord.getWord());
            for (EngWord x : english1.query()) {
                this.delete(x);
            }
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " deleteWord " + engWord.getWord());
            return false;
        }
    }

    public List<EngWord> queryListWordByString(String word) throws SQLException {

        Where<EngWord, Long> english = this.queryBuilder().where().eq("Word", word);
        return new ArrayList<>(english.query());
    }

    public List<EngWord> queryListWordByEngWord(EngWord engWord) {
        try {
            Where<EngWord, Long> english1 = this.queryBuilder().where().eq("Word", engWord.getWord());
            return new ArrayList<>(english1.query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EngWord> containWordByString(String word) throws SQLException {
        Where<EngWord, Long> english = this.queryBuilder().where().like("Word", word + "%");
        return new ArrayList<>(english.query());
    }

    public List<EngWord> containWordByEngWord(EngWord engWord) throws SQLException {
        Where<EngWord, Long> english1 = this.queryBuilder().where().like("Word", engWord.getWord() + "%");
        return new ArrayList<>(english1.query());
    }

    public void changeType(String Word) throws SQLException {
        var english = this.queryBuilder().where().eq("Word", Word).queryForFirst();
        if (english != null) {
            String type = english.getType();
            if (type.contains("n.")) {
                type = type.replace("n.", "noun");
            }
            if (type.contains("v.")) {
                type = type.replace("v.", "verb");
            }
            if(type.contains("t.")){
                type = type.replace("t.", "transitive");
            }
            if (type.contains("Imp.")) {
                type = type.replace("Imp", "Imperative");
            }
            if (type.contains("a.")) {
                type = type.replace("a.", "adjective");
            }
            if (type.contains("adv.")) {
                type = type.replace("adv.", "adverb");
            }
            if (type.contains("pr.")) {
                type = type.replace("pr.", "pronoun");
            }
            if(type.contains("i.")){
                type = type.replace("i.", "intransitive");
            }
            if(type.contains("pl.")){
                type = type.replace("pl.", "plural");
            }
            if (type.contains("p.")) {
                type = type.replace("p.", "preposition");
            }
            if (type.contains("conj.")) {
                type = type.replace("conj.", "conjunction");
            }
            if (type.contains("interj")) {
                type = type.replace("interj.", "interjection");
            }
            if(type.contains("&")){
                type = type.replace("&", " and ");
            }
            if(type.contains("vb.")){
                type = type.replace("vb.", "verbNoun");
            }
            english.setType(type);
            this.update(english);
        }
    }
    public List<EngWord> getAllWords() throws SQLException {
        return this.queryForAll();
    }

    public String renderDefinition(EngWord engWord) {
        if (engWord == null) {
            return "";
        }

        StringBuilder definitionBuilder = new StringBuilder();

        if(!engWord.getWord().isEmpty()){
            definitionBuilder.append("Word: ").append(engWord.getWord()).append("\n").append("\n");
        }

        if (!engWord.getType().isEmpty()) {
            definitionBuilder.append("Part of Speech: ").append(engWord.getType()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Part of Speech: ").append("No part of speech found").append("\n").append("\n");
        }
        if (!engWord.getMeaning().isEmpty()) {
            definitionBuilder.append("Definition: ").append(engWord.getMeaning()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Definition: ").append("No definition found").append("\n").append("\n");
        }

        if(!engWord.getPronunciation().isEmpty()){
            definitionBuilder.append("Pronunciation: ").append(engWord.getPronunciation()).append("\n").append("\n");
        }
        else {
            definitionBuilder.append("Pronunciation: ").append("No pronunciation found").append("\n").append("\n");
        }

        if (!engWord.getSynonym().isEmpty()) {
            definitionBuilder.append("Synonym: ").append(engWord.getSynonym()).append("\n").append("\n");
        }else {
            definitionBuilder.append("Synonym: ").append("No synonym found").append("\n").append("\n");
        }
        if (!engWord.getAntonyms().isEmpty()) {
            definitionBuilder.append("Antonym: ").append(engWord.getAntonyms()).append("\n").append("\n");
        }else {
            definitionBuilder.append("Antonym: ").append("No antonym found").append("\n").append("\n");
        }
        if (!engWord.getExample().isEmpty()) {
            definitionBuilder.append("Example: ").append(engWord.getExample()).append("\n").append("\n");
        } else {
            definitionBuilder.append("Example: ").append("No example found").append("\n").append("\n");
        }
        return definitionBuilder.toString().trim();
    }

   public boolean sortedWord() throws SQLException {
       var x = this.queryBuilder();
       try {
           x.orderBy("Word", true);
       } catch (Exception e) {
           System.err.println(e.getMessage() + " sortedWord");
           return false;
       }
       return true;
   }
}
