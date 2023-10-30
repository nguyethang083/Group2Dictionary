package DictionaryCommandLine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryManagement {
    public void insertFromCommandline(Dictionary inputDict) {
        Scanner sc = new Scanner(System.in);
        int numWord = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < numWord; i++) {
            Word inputFromCmd = new Word();
            String wordInput = sc.nextLine();
            String meaningInput = sc.nextLine();
            inputFromCmd.setWord_target(wordInput);
            inputFromCmd.setWord_explain(meaningInput);
            inputDict.list_word.add(inputFromCmd);
        }
    }

    public void insertFromFile(Dictionary inputDict) {
        try {
            File dataFile = new File("src/main/resources/data.txt");
            Scanner sc = new Scanner(dataFile);
            while (sc.hasNextLine()) {
                Word inputFromFile = new Word();
                String wordInput = sc.next();
                String meaningInput = sc.nextLine();
                inputFromFile.setWord_target(wordInput);
                inputFromFile.setWord_explain(meaningInput.trim());
                inputDict.list_word.add(inputFromFile);
            }

        } catch(IOException E) {
            E.printStackTrace();
        }
    }

    public int dictionaryLookup(Dictionary inputDict) {
        System.out.print("Enter a word: ");
        Scanner sc = new Scanner(System.in);
        int index = 0;
        String target = sc.nextLine();
        for (Word word : inputDict.list_word) {
            if (target.equals(word.getWord_target())) {
                System.out.println("Meaning: " + word.getWord_explain());
                return index ;
            }
            index++;
        }
        System.out.println("This word doesn't exist in dictionary!");
        return -1;
    }

    public void removeWord(Dictionary inputDict)
    {
        System.out.print("Enter the word that you want to remove: ");
        Scanner sc = new Scanner(System.in);
        String removeWord = sc.nextLine();

        int removeIndex = 0;
        for (Word word : inputDict.list_word) {
            if (removeWord.equals(word.getWord_target())) {
                inputDict.list_word.remove(removeIndex);
                break;
            }
            removeIndex++;
        }

        if (removeIndex == inputDict.list_word.size())
        {
            System.out.println("This word doesn't exist in the dictionary!");
        }
    }

    public void addWord(Dictionary inputDict)
    {
        System.out.print("Enter the word that you want to add: ");
        Scanner sc = new Scanner(System.in);
        String addWordTarget = sc.nextLine();

        boolean wordExisted = false;
        for (Word word : inputDict.list_word) {
            if (addWordTarget.equals(word.getWord_target())) {
                wordExisted = true;
                break;
            }
        }

        if (wordExisted)
        {
            System.out.println("This word already exist in the dictionary!");
        } else {
            System.out.print("Enter the meaning: ");
            String addWordMeaning = sc.nextLine();
            Word addWord = new Word();
            addWord.setWord_target(addWordTarget);
            addWord.setWord_explain(addWordMeaning);
            inputDict.list_word.add(addWord);
        }
    }

    public void dictionaryExportToFile(Dictionary outputDict) {
        try
        {
            FileWriter fileWriter =
                    new FileWriter("src/main/resources/data.txt");

            BufferedWriter fout =
                    new BufferedWriter(fileWriter);

            for (Word word : outputDict.list_word)
            {
                fout.append(word.getWord_target()+"\t"+word.getWord_explain()+"\n");
            }

            fout.close();
        }
        catch (IOException E)
        {
            E.printStackTrace();
        }
        System.out.println("The dictionary has been exported.");
    }
}
