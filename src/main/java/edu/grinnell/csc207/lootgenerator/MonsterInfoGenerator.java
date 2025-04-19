package edu.grinnell.csc207.lootgenerator;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class for MonsterInfoGenerator.
 */
public class MonsterInfoGenerator<K, V> {
    private final Scanner scanner;
    private ArrayList<Pair<String, String>> monsterInfoArray;

    /**
     * MonsterInfoGenerator : constructor for the class, creates a new scanner on
     * the
     * file and initializes the array field.
     * 
     * @param fileName : the name of the file of interest
     * @throws IOException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public MonsterInfoGenerator(String fileName)
            throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.monsterInfoArray = new ArrayList<>();
    }

    /**
     * setMapPairs : goes through the lines in a file, converts them to String[]s,
     * and inserts the relevant elements into a Pair, inserting this pair into the
     * array.
     */
    public void setMapPairs() {
        while (this.scanner.hasNextLine()) {
            String fullMonsterInfo = this.scanner.nextLine();
            String[] monsterInfoArray = fullMonsterInfo.split("\t");
            Pair<String, String> relevantMonsterInfo;
            relevantMonsterInfo = new Pair<>(monsterInfoArray[0], monsterInfoArray[3]);
            this.monsterInfoArray.add(relevantMonsterInfo);
        }
    }

    /**
     * getWords : returns the classes monsterInfoArray
     * 
     * @return the arrayList of Monster - TreasureClass pairs
     */
    public ArrayList<Pair<String, String>> getWords() {
        return this.monsterInfoArray;
    }
}
