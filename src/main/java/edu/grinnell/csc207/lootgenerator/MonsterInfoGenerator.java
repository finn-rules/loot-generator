package edu.grinnell.csc207.lootgenerator;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MonsterInfoGenerator<K, V> {
    private final Scanner scanner;
    private ArrayList<Pair<String, String>> monsterInfoArray;
    public MonsterInfoGenerator(String fileName) throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.monsterInfoArray = new ArrayList<>();
    }

    public void setMapPairs() {
        while (this.scanner.hasNextLine()) {
            String fullMonsterInfo = this.scanner.nextLine();
            String[] monsterInfoArray = fullMonsterInfo.split("\t");
            Pair<String, String> relevantMonsterInfo = new Pair<>(monsterInfoArray[0], monsterInfoArray[3]);
            this.monsterInfoArray.add(relevantMonsterInfo);
        }
    }

    public ArrayList<Pair<String, String>> getWords() {
        return this.monsterInfoArray;
    }
}
