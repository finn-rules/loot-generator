package edu.grinnell.csc207.lootgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class LootStatTreeGenerator {
    private final Scanner scanner;
     private HashMap<String, Pair<Integer, Integer>> lootStatHashes;

     public LootStatTreeGenerator(String fileName) throws IOException, InterruptedException, FileNotFoundException {
          this.scanner = new Scanner(new File(fileName));
          this.lootStatHashes = new HashMap<>();
          setHashes();
     }

     public void setHashes() {
          while (this.scanner.hasNextLine()) {
               String fullLineStatInfo = this.scanner.nextLine();
               String[] lootStatArray = fullLineStatInfo.split("\t");
               this.lootStatHashes.put(lootStatArray[0], new Pair<>(Integer.parseInt(lootStatArray[1]), Integer.parseInt(lootStatArray[2])));
          }
     }

     public TreeMap<String, Pair<Integer, Integer>> getHashesTreeMap() {
          return new TreeMap<String, Pair<Integer, Integer>>(this.lootStatHashes);
     }
}

