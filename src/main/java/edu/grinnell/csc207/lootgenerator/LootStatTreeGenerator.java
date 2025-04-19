package edu.grinnell.csc207.lootgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The LootStatTreeGenerator class.
 */
public class LootStatTreeGenerator {
    private final Scanner scanner;
    private HashMap<String, Pair<Integer, Integer>> lootStatHashes;

     /**
      * LootStatTreeGenerator : constructor for the LootStatTree.
      * 
      * @param fileName : the name of the file of interest to be scanned.
      * @throws IOException
      * @throws InterruptedException
      * @throws FileNotFoundException
      */
    public LootStatTreeGenerator(String fileName)
               throws IOException, InterruptedException, FileNotFoundException {
        this.scanner = new Scanner(new File(fileName));
        this.lootStatHashes = new HashMap<>();
        setHashes();
    }

     /**
      * setHashes: set the hashmap by scanning the file, inserting the name as a key
      * and a pair of the bounds as integers.
      */
    public void setHashes() {
        while (this.scanner.hasNextLine()) {
            String fullLineStatInfo = this.scanner.nextLine();
            String[] lootStatArray = fullLineStatInfo.split("\t");
            this.lootStatHashes.put(lootStatArray[0],
                       new Pair<>(Integer.parseInt(lootStatArray[1]), 
                       Integer.parseInt(lootStatArray[2])));
        }
    }

     /**
      * getHashesTreeMap : converts the HashMap into a TreeMap for ease of searching.
      * 
      * @return a TreeMap consisting of the loot information
      */
    public TreeMap<String, Pair<Integer, Integer>> getHashesTreeMap() {
        return new TreeMap<String, Pair<Integer, Integer>>(this.lootStatHashes);
    }
}
