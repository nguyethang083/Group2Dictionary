package Dictionary.Entities;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dictionary.DatabaseConn.WordDAO;

public class WordDAO extends BaseDaoImpl<EngWord, Long> {
    public WordDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EngWord.class);
    }

    // lỗi null pointer cần sửa lại!!!
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
            String addWord = x.getWord().substring(0, 1).toUpperCase() + x.getWord().substring(1).toLowerCase();
            EngWord engWord = this.queryBuilder().where().eq("Word", addWord).queryForFirst();
            if (engWord != null && !engWord.getWord().isEmpty()) {
//                engWord.setMeaning(x.getMeaning());
////                engWord.setType(x.getType());
////                engWord.setPronunciation(x.getPronunciation());
////                engWord.setAntonyms(x.getAntonyms());
////                engWord.setSynonym(x.getSynonym());
////                engWord.setExample(x.getExample());
////                this.update(engWord);
                return false;
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
            System.err.println(e.getMessage() + " deleteTuple " + word);
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
            System.err.println(e.getMessage() + " deleteTuple " + engWord.getWord());
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

    public static void main(String[] args) throws SQLException {
        EngWord ew1 = new EngWord("vivacious", "adjective", "energetic and enthusiastic", "/ˈviːvɪʃəs/", "She was a vivacious young woman.", "jovial", "somber");
        EngWord ew2 = new EngWord("prudent", "adjective", "marked by or showing prudence; cautious and careful", "/ˈpruːdənt/", "The investor decided to be prudent with his funds.", "astute", "impulsive");
        EngWord ew3 = new EngWord("equivocal", "adjective", "open to more than one interpretation; uncertain", "/ˌɛkɪˈvɑːklɪk/", "Her remarks were equivocal and caused confusion.", "unequivocal", "definite");
        EngWord ew4 = new EngWord("convivial", "adjective", "likely to promote good fellowship; genial", "/kənˈviːviəl/", "The convivial atmosphere of the party was appreciated by all.", "somber", "formal");
        EngWord ew5 = new EngWord("conflate", "verb", "to merge (elements, concepts, etc.) into one without discrimination", "/ˈkɑːnfluːɪt/", "He often conflated different historical periods in his research.", "discriminate", "differentiate");
        EngWord ew6 = new EngWord("renaissance", "noun", "a period in history marked by the revival of learning and the arts and by a general rebirth of interest in the classical ideas", "/ˌrenəˈissanceɪŋ/", "The 16th century marked the beginning of the Italian Renaissance.", "Dark Ages", "golden age");
        EngWord ew7 = new EngWord("impervious", "adjective", "unable to be penetrated, influenced, or affected; impassable", "/ɪmˈpɜːrəs/", "The material is impervious to water.", "permeable", "susceptible");
        EngWord ew8 = new EngWord("abnegate", "verb", "to give up something, especially one's self-interest", "/əbˈniːɡeɪt/", "She abnegated her personal needs for the sake of her children.", "preserve", "relinquish");
        EngWord ew9 = new EngWord("luminosity", "noun", "the property of a material or light to emit light or to reflect or transmit light", "/luːmɪˈnɒsɪti/", "The lamp's luminosity made it the ideal choice for the project.", "brightness", "darkness");
        EngWord ew10 = new EngWord("recrimination", "noun", "mutual criticism and blame, often used in a legal context", "/rɪˈkrɪmɪneɪʃən/", "The courtroom battle led to a series of bitter recriminations.", "confrontation", "cooperation");
        EngWord ew11 = new EngWord("abstain", "verb", "to choose not to consume a particular substance, such as alcohol, for a specific period of time", "/əbˈstɪn/", "He abstained from alcohol to maintain his fitness level.", "consume", "avoid");
        EngWord ew12 = new EngWord("lucid", "adjective", "bright and easily seen", "/ˈluːsɪd/", "The moonlit sky was breathtakingly lucid.", "opaque", "obscure");
        EngWord ew13 = new EngWord("lugubrious", "adjective", "having or expressing a melancholy and gloomy atmosphere", "/ˈlʌɡbriəs/", "The mournful song had a lugubrious tone.", "cheerful", "jovial");
        EngWord ew14 = new EngWord("aplomb", "noun", "calm and self-possessedness, especially in difficult situations", "/ˈæplɒm/", "The expert spoke with aplomb during the press conference.", "poise", "composure");
        EngWord ew15 = new EngWord("bucolic", "adjective", "describing idyllical, rural scenes, landscapes, or lifestyles", "/ˈbʊkəlɪk/", "The painter created a bucolic landscape of rolling hills and serene cows.", "urban", "suburban");
        EngWord ew16 = new EngWord("parochial", "adjective", "limited in scope or outlook, or considering only the interests of a small or narrow-minded group", "/ˈpærəSHɪəl/", "Their views were parochial and shortsighted.", "broad", "global");
        EngWord ew17 = new EngWord("raconteur", "noun", "a person skilled in the art of narrating amusing anecdotes or stories", "/ˌrækɒnˈteɪr/", "He was known as a master raconteur and always had his audience in stitches.", "storyteller", "author");
        EngWord ew18 = new EngWord("taciturn", "adjective", "disposed to or characterized by silence or reticence", "/ˈtæsɪtɜːrn/", "He was a taciturn man and seldom shared his thoughts.", "silent", "chatty");
        EngWord ew19 = new EngWord("rhapsodize", "verb", "to speak or write in a way that is highly enthusiastic or emotional about something", "/ˈræpsəˌdɪzeɪ/", "The critics rhapsodized about the performer's outstanding performance.", "criticize", "evaluate");
        EngWord ew20 = new EngWord("penurious", "adjective", "lacking in money or means; miserly", "/ˈpenjʊərɪs/", "He was penurious and could not afford the latest smartphone.", "affluent", "wealthy");
        EngWord ew21 = new EngWord("corpulent", "adjective", "fat or plump", "/ˈkɔːrpjələnt/", "The corpulent man could hardly fit through the doorway.", "slender", "skinny");

        EngWord ew22 = new EngWord("crass", "adjective", "extremely insensitive or tactless", "/kræs/", "His crass remark offended everyone in the room.", "sensitive", "polite");

        EngWord ew23 = new EngWord("covetous", "adjective", "greedy and desirous of possessions", "/ˈkʌvɪtəs/", "The covetous man could not resist the shiny gold object.", "unselfish", "generous");

        EngWord ew24 = new EngWord("debilitate", "verb", "to weaken or make ineffective", "/dɪˈbɪlɪteɪt/", "The debilitating disease slowly debilitated her body.", "strengthen", "energize");

        EngWord ew25 = new EngWord("debonair", "adjective", "distinguished, dignified, and stylish", "/dəˈbɔːneɪər/", "The debonair man exuded a charming air.", "unpretentious", "casual");

        EngWord ew26 = new EngWord("decorum", "noun", "proper behavior or demeanor in social situations", "/ˈdɛkərəm/", "It is important to maintain decorum during formal occasions.", "courtesy", "good manners");

        EngWord ew27 = new EngWord("deride", "verb", "to ridicule or make fun of someone or something", "/dɪˈraɪd/", "He did not have the courage to deride her.", "respect", "admire");

        EngWord ew28 = new EngWord("discombobulate", "verb", "to confuse or upset someone", "/ˌdɪskəmˈbɔblət/", "The unexpected news discombobulated the group.", "comfort", "assure");

        EngWord ew29 = new EngWord("dissemble", "verb", "to conceal one's true feelings or intentions", "/ˈdɪsəmbl/", "She could not dissemble her dislike for the person.", "reveal", "expose");

        EngWord ew30 = new EngWord("disingenuous", "adjective", "not genuine or sincere", "/dɪˈsɪndʒənjuːs/", "Her disingenuous apology did not convince anyone.", "authentic", "genuine");

        EngWord ew31 = new EngWord("disparage", "verb", "to criticize or belittle someone or something", "/dɪˈspærɪdʒ/", "He should not disparage the person who tried to help him.", "compliment", "praise");

        EngWord ew32 = new EngWord("dolorous", "adjective", "extremely sad or sorrowful", "/ˈdɑːljərəs/", "The dolorous look on her face conveyed her heartache.", "somber", "gloomy");

        EngWord ew33 = new EngWord("droll", "adjective", "comically dull or uninteresting", "/drɔl/", "His droll sense of humor did not amuse many people.", "humorous", "funny");

        EngWord ew34 = new EngWord("ebullient", "adjective", "excessively and boisterously joyful", "/ɪˈbjuːlɪənt/", "The ebullient partygoer could not contain his excitement.", "jovial", "merry");

        EngWord ew35 = new EngWord("efficacy", "noun", "the ability to produce desired results or achieve something", "/ɪˈfɪkəsi/", "The new medicine showed promising efficacy in treating the disease.", "effectiveness", "potency");
        // create list to hold the english words
        List<EngWord> engWords = new ArrayList<>();
        engWords.add(ew1);
        engWords.add(ew2);
        engWords.add(ew3);
        engWords.add(ew4);
        engWords.add(ew5);
        engWords.add(ew6);
        engWords.add(ew7);
        engWords.add(ew8);
        engWords.add(ew9);
        engWords.add(ew10);
        engWords.add(ew11);
        engWords.add(ew12);
        engWords.add(ew13);
        engWords.add(ew14);
        engWords.add(ew15);
        engWords.add(ew16);
        engWords.add(ew17);
        engWords.add(ew18);
        engWords.add(ew19);
        engWords.add(ew20);
        engWords.add(ew21);
        engWords.add(ew22);
        engWords.add(ew23);
        engWords.add(ew24);
        engWords.add(ew25);
        engWords.add(ew26);
        engWords.add(ew27);
        engWords.add(ew28);
        engWords.add(ew29);
        engWords.add(ew30);
        engWords.add(ew31);
        engWords.add(ew32);
        engWords.add(ew33);
        engWords.add(ew34);
        engWords.add(ew35);


        // use for loop to print all english words
        for (EngWord ew : engWords) {
            WordDAO.addWord(ew);
            System.out.println("add " + ew.getWord());
        }
    }

}
