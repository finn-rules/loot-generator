package edu.grinnell.csc207.lootgenerator;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TreasureTreeGenerator class.
 */
public class TreasureTreeGenerator {
    private final Scanner scanner;
    private HashMap<String, String[]> lootTableHashes;

     /**
      * Constructor for the TreasureTreeGenerator, which contains a HashMap and a
      * scanner.
      * 
      * @param fileName : a string representing the name of the file
      * @throws IOException
      * @throws InterruptedException
      * @throws FileNotFoundException
      */
    public TreasureTreeGenerator(String fileName) 
          throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.lootTableHashes = new HashMap<>();
        setHashes();
    }

     /**
      * setHashes: using the scanner on the file of interest, put each "line" into
      * the HashMap field.
      */
    public void setHashes() {
        while (this.scanner.hasNextLine()) {
            String fullTreasureInfo = this.scanner.nextLine();
            String[] monsterTArray = fullTreasureInfo.split("\t");
            String[] items = {monsterTArray[1], monsterTArray[2], monsterTArray[3]};
            this.lootTableHashes.put(monsterTArray[0], items);
        }
    }

     /**
      * getHashesTreeMap : convert the hash field into a tree map of hashes.
      * 
      * @return a TreeMap with a STRING as the key and a 3-length array as the value
      *         (which may or may not be keys)
      */
    public TreeMap<String, String[]> getHashesTreeMap() {
        return new TreeMap<>(this.lootTableHashes);
    }
}
