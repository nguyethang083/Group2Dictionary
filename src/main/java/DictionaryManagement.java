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
}
