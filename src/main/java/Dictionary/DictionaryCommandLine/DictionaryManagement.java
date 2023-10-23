package Dictionary.DictionaryCommandLine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.*;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionaryManagement {
    private FileInputStream fileInputStream = null;
    private BufferedReader bufferedReader = null;
    private FileOutputStream fileOutputStream = null;
    private BufferedWriter bufferedWriter = null;

    private Scanner sc = new Scanner(System.in);
    private Dictionary mainDictionary = new Dictionary();

    public void insertFromCommandline() {
        try {
            int newNumOfWords;

            //input
            System.out.print("Nhập số lượng từ: ");
            newNumOfWords = sc.nextInt();
            sc.nextLine(); //tránh nhập kí tự xuống dòng

            //insert new words
            String target;
            String explain;
            for (int i = 0; i < newNumOfWords; i++) {
                System.out.print("Enter word " + (i + 1) + ": ");
                target = sc.nextLine();

                if (DataBase.checkRepeatedWord(target)) {
                    System.out.println("ERROR: This word has already existed!");
                } else {
                    System.out.print("Meaning: ");
                    explain = sc.nextLine();

                    DataBase.insertToDatabase(target, explain);
                    System.out.println("Word is added!");
                }
            }
        } catch (InputMismatchException ex) {
            Logger.getLogger(DictionaryManagement.class.getName())
                    .log(Level.SEVERE, "ERROR: Please type a number!", ex);
        }
    }

    public void insertFromFile() {
        try {
            String url = "src/main/resources/data.txt";
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder currentWord = new StringBuilder();
            String target = "";
            String explain = "";
            int data = bufferedReader.read();
            char c;

            while (data != -1) {
                c = (char) data;
                //input có dạng "target\texplain"
                //Đọc đến kí tự \t thì lưu lại target word
                if (c == '\t') {
                    //check từ đã tồn tại
                    if (DataBase.checkRepeatedWord(currentWord.toString())) {
                        bufferedReader.readLine(); //nếu từ đã tồn tại thì chuyển dòng tiếp theo
                        System.out.println("\"" + currentWord.toString()
                                + "\" has not been added because it's already existed");
                    } else {
                        target = currentWord.toString();  //nếu từ chưa tồn tại thì lưu lại vào target
                    }
                    currentWord = new StringBuilder(); //tạo mới lại "currentWord" để lưu từ mới
                } else if (c == '\n') {
                    explain = currentWord.toString();
                    currentWord = new StringBuilder();
                    DataBase.insertToDatabase(target, explain); //insert vào database
                } else {
                    currentWord.append(c);   //nếu kí tự tiếp theo ko phải \t hoặc \n thì đọc tiếp
                }
                data = bufferedReader.read();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", ex);
        } catch (IOException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FAILED TO READ FILE!", ex);
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (NullPointerException | IOException ex) {
                Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void insertFromFile2() {
        try {
            String url = "src/main/resources/data.txt";
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String target = bufferedReader.readLine();
            String explain = bufferedReader.readLine();

            while (target != null && explain != null) {
                if (!DataBase.checkRepeatedWord(target)) {
                    DataBase.insertToDatabase(target, explain); //nếu từ chưa tồn tại thì insert vào database
                }
                target = bufferedReader.readLine();
                explain = bufferedReader.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", ex);
        } catch (IOException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FAILED TO READ FILE!", ex);
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (NullPointerException | IOException ex) {
                Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void insertFromFile3() {
        try {
            String url = "src/main/resources/data2.txt";
            fileInputStream = new FileInputStream(url);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder currentWord = new StringBuilder();
            int data = bufferedReader.read();
            char c;
            String target = bufferedReader.readLine();
            String explain = bufferedReader.readLine();

            while (data != -1) {
                c = (char) data;

                if (c == '@') {
                    currentWord = new StringBuilder(); //reset StringBuilder

                    if (!DataBase.checkRepeatedWord(target)) {
                        DataBase.insertToDatabase(target, explain); //nếu từ chưa tồn tại thì insert vào database
                    }

                    target = bufferedReader.readLine();
                }

                if (c == '*' || c == '-' || c == '=') {
                    currentWord.append(bufferedReader.readLine()).append("\n");
                }
                data = bufferedReader.read();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FILE NOT FOUND!", ex);
        } catch (IOException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FAILED TO READ FILE!", ex);
        } finally {
            try {
                bufferedReader.close();
                fileInputStream.close();
            } catch (NullPointerException | IOException ex) {
                Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void deleteWord() {
        String target;
        System.out.print("Enter a word: ");
        target = sc.nextLine();

        if (!DataBase.checkRepeatedWord(target)) {
            System.out.println("ERROR: This word doesn't exist in dictionary!");
            return;
        }

        DataBase.deleteFromDatabase(target);
        System.out.println("Word is deleted from dictionary!");
    }

    public void updateWord() {
        String oldTarget;
        String newTarget;
        String explain;
        System.out.print("Enter a word: ");
        oldTarget = sc.nextLine();

        if (!DataBase.checkRepeatedWord(oldTarget)) {
            System.out.println("ERROR: This word doesn't exist in dictionary!");
            return;
        }

        System.out.print("Enter new version of the word: ");
        newTarget = sc.nextLine();
        System.out.print("Enter new meaning of the word: ");
        explain = sc.nextLine();

        DataBase.updateDatabase(newTarget, explain, oldTarget);
        System.out.println("Word is updated!");
    }

    public void dictionaryLookup() {
        String target;
        System.out.print("Enter a word: ");
        target = sc.nextLine();

        if (!DataBase.checkRepeatedWord(target)) {
            System.out.println("ERROR: This word doesn't exist in dictionary!");
            return;
        }

        System.out.println(DataBase.lookup(target));
    }

    public void dictionaryExportToFile() {
        try {
            String url = "src/main/resources/output.txt";
            fileOutputStream = new FileOutputStream(url);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            //Check empty
            if (DataBase.checkEmptyDatabase()) {
                throw new IllegalArgumentException();
            }

            bufferedWriter.write(DataBase.readFromDatabase());
            System.out.println("Exported successfully!");
            bufferedWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, "FAILED TO WRITE FILE!", ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DictionaryManagement.class.getName()).log(Level.INFO, "Dictionary is empty!", ex);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileOutputStream.close();
            } catch (NullPointerException | IOException ex) {
                Logger.getLogger(DictionaryManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Dictionary getMainDictionary() {
        return mainDictionary;
    }

    public static void main(String[] args) {
        DictionaryManagement manager = new DictionaryManagement();
        manager.insertFromFile2();
//        manager.insertFromCommandline();
//        manager.deleteWord();
//        manager.updateWord();
//        manager.dictionaryLookup();
//        manager.dictionaryExportToFile();
//        DictionaryCommandLine.showAllWords();
    }

    /*public void addWord(Dictionary inputDict)
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
    }*/
}
