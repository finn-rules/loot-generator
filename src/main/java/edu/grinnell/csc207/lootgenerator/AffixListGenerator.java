package edu.grinnell.csc207.lootgenerator;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The AffixListGenerator class.
 */
public class AffixListGenerator<K> {
    private final Scanner scanner;
    private ArrayList<Pair<String, String[]>> affixInfoArray;

    /**
     * AffixListGenerator : constructor for our data structure supporting possible
     * affixes.
     * 
     * @param fileName : the name of the file we're interested in Scanning
     * @throws IOException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public AffixListGenerator(String fileName)
            throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.affixInfoArray = new ArrayList<>();
        setMapPairs();
    }

    /**
     * setMapPairs : takes in lines from the file of interest and converts them into
     * pairs, then adds them to our ArrayList to be randomly selected.
     */
    public void setMapPairs() {
        while (this.scanner.hasNextLine()) {
            String fullMonsterInfo = this.scanner.nextLine();
            String[] monsterInfoArray = fullMonsterInfo.split("\t");
            String[] affixInfo = {monsterInfoArray[1], monsterInfoArray[2], monsterInfoArray[3]};
            Pair<String, String[]> relevantMonsterInfo = new Pair<>(monsterInfoArray[0], affixInfo);
            this.affixInfoArray.add(relevantMonsterInfo);
        }
    }

    /**
     * getWords : returns the ArrayList of possible prefix/suffixes.
     * 
     * @return the ArrayList of pairs of possible prefix/suffixes.
     */
    public ArrayList<Pair<String, String[]>> getWords() {
        return this.affixInfoArray;
    }
}
