package edu.grinnell.csc207.lootgenerator;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TreasureTreeGenerator {
     private final Scanner scanner;
     private HashMap<String, String[]> lootTableHashes;

     public TreasureTreeGenerator(String fileName) throws IOException, InterruptedException, FileNotFoundException {
          this.scanner = new Scanner(new File(fileName));
          this.lootTableHashes = new HashMap<>();
          setHashes();
     }

     public void setHashes() {
          while (this.scanner.hasNextLine()) {
               String fullTreasureInfo = this.scanner.nextLine();
               String[] monsterTreasureArray = fullTreasureInfo.split("\t");
               String[] items = { monsterTreasureArray[1], monsterTreasureArray[2], monsterTreasureArray[3] };
               this.lootTableHashes.put(monsterTreasureArray[0], items);
          }
     }

     public TreeMap<String, String[]> getHashesTreeMap() {
          return new TreeMap<>(this.lootTableHashes);
     }
}
