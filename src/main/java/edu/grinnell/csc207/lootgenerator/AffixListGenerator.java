package edu.grinnell.csc207.lootgenerator;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// do i need both of these files? i could maybe just use one for affix?
public class AffixListGenerator<K> {
    private final Scanner scanner;
    private ArrayList<Pair<String, String[]>> affixInfoArray;
    public AffixListGenerator(String fileName) throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.affixInfoArray = new ArrayList<>();
        setMapPairs();
    }

    public void setMapPairs() {
        while (this.scanner.hasNextLine()) {
            String fullMonsterInfo = this.scanner.nextLine();
            String[] monsterInfoArray = fullMonsterInfo.split("\t");
            String[] affixInfo = { monsterInfoArray[1], monsterInfoArray[2], monsterInfoArray[3] };
            Pair<String, String[]> relevantMonsterInfo = new Pair<>(monsterInfoArray[0], affixInfo);
            this.affixInfoArray.add(relevantMonsterInfo);
        }
    }

    public ArrayList<Pair<String, String[]>> getWords() {
        return this.affixInfoArray;
    }
}
