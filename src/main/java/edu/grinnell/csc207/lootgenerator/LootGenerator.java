package edu.grinnell.csc207.lootgenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/large";

    // This method combines both pickMonster and getTreasureClass : the monster is the "key" and the
    // treasureClass is the "value" in a pair. Choose a random monster pair from an arrayList of size hashSize.
    public Pair<String, String> pickMonster(ArrayList<Pair<String, String>> hashes) {
        int randomIndex = (int) (Math.random() * hashes.size());
        Pair<String, String> monsterInfo = hashes.get(randomIndex);
        String monsterName = monsterInfo.getKey();
        String lootTable = monsterInfo.getValue();
        System.out.println("Fighting a " + monsterName + "...");
        System.out.println("You have slain " + monsterName + "!");
        System.out.println(monsterName + " dropped:");
        return new Pair<>(monsterName, lootTable);
    }

    // recursive function to pick loot from the loot table. params: tree, current
    // key. returns the base item (as a string)
    public String pickLoot(TreeMap<String, String[]> lootTableHashes, String curKey) {
        if (!lootTableHashes.containsKey(curKey)) {
            return curKey;
        } else {
            String[] possibleKeyStrings = lootTableHashes.get(curKey);
            int nextKeyIndex = (int) (Math.random() * possibleKeyStrings.length); // Should be 3.
            String nextKey = possibleKeyStrings[nextKeyIndex];
            return pickLoot(lootTableHashes, nextKey);
        }
    }

    public static int pickDefenseStat(TreeMap<String, Pair<Integer, Integer>> statTree, String itemName) {
        if (statTree.containsKey(itemName)) {
            Pair<Integer, Integer> statRange = statTree.get(itemName);
            int minac = statRange.getKey();
            int maxac = statRange.getValue();
            return (int) (Math.random() * (maxac - minac + 1)) + minac;
        } else {
            System.out.println("Error: item not found in the armor.txt file!");
            return 0; // Default value if item not found
        }
    }

    public static Pair<String, String> generateAffix(ArrayList<Pair<String, String[]>> affixes) {
        boolean hasAffix;
        Pair<String, String> affixPair = null;
        int generates = (int) (Math.random() * 2);
        if (generates == 0) {
            hasAffix = false;
        } else {
            hasAffix = true;
        }
        if(hasAffix) {
            int randomIndex = (int) (Math.random() * affixes.size());
            Pair<String, String[]> thisAffix = affixes.get(randomIndex);
            String affixName = thisAffix.getKey();
            String[] affixInfoArray = thisAffix.getValue();
            String affixType = affixInfoArray[0];
            int minStat = Integer.parseInt(affixInfoArray[1]);
            int maxStat = Integer.parseInt(affixInfoArray[2]);

            int affixStat = (int) (Math.random() * (maxStat - minStat + 1)) + minStat;
            affixPair = new Pair<>(affixName, affixType + " " + affixStat);
        }
        return affixPair;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        System.out.println("This program kills monsters and generates loot!");
        MonsterInfoGenerator<Integer, Pair<String, String>> m = new MonsterInfoGenerator<>(DATA_SET + "/monstats.txt");
        m.setMapPairs();
        m.getWords();
        boolean running = true;
        System.out.println("Press 'y' to fight a monster, or type 'n' to quit. Then press 'enter'.");
        while (running) {
            System.out.println("\nFight [y/n]?");
            while (true) {
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();
                if (input.equalsIgnoreCase("n")) {
                    System.out.println("Thanks for playing! May RNGesus be ever in your favor.");
                    System.out.println("Exiting...");
                    s.close();
                    running = false;
                    break;
                } else if (input.equalsIgnoreCase("y")) {
                    break;
                } else {
                    System.out.println("Fight again [y/n]?");
                }
                s.close();
            }

            if (!running) {
                break;
            }
            System.out.println();
            ArrayList<Pair<String, String>> monsterInfoHashes = m.getWords();
            LootGenerator lootGen = new LootGenerator();
            Pair<String, String> monsterInfo = lootGen.pickMonster(monsterInfoHashes);
            // System.out.println("Loot table: " + monsterInfo.getValue());

            System.out.println();

            // Now we need to pick a loot table from the monster's loot table
            // Generate the tree from TreasureClassEx.txt.
            TreasureTreeGenerator ltg = new TreasureTreeGenerator(DATA_SET + "/TreasureClassEx.txt");
            TreeMap<String, String[]> lootTableHashes = ltg.getHashesTreeMap();
            String currentTreasureClass = monsterInfo.getValue();

            String lootPull = lootGen.pickLoot(lootTableHashes, currentTreasureClass);

            LootStatTreeGenerator statTree = new LootStatTreeGenerator(DATA_SET + "/armor.txt");
            TreeMap<String, Pair<Integer, Integer>> lootStatTreeMap = statTree.getHashesTreeMap();
            int statValue = pickDefenseStat(lootStatTreeMap, lootPull);

            ArrayList<Pair<String, String[]>> prefixes = new AffixListGenerator<>(DATA_SET + "/MagicPrefix.txt").getWords();

            ArrayList<Pair<String, String[]>> suffixes = new AffixListGenerator<>(DATA_SET + "/MagicSuffix.txt").getWords();

            Pair<String, String> prefixPair = generateAffix(prefixes);
            Pair<String, String> suffixPair = generateAffix(suffixes);
            String prefix = null;
            String prefixInfo = null;
            String suffix = null;
            String suffixInfo = null;

            if(prefixPair != null) {
                prefix = prefixPair.getKey();
                prefixInfo = prefixPair.getValue();
            }

            if(suffixPair != null) {
                suffix = suffixPair.getKey();
                suffixInfo = suffixPair.getValue();
            }
            if(prefix != null) {
                System.out.print(prefix + " ");
            }
            if(suffix == null) {
                suffix = "";
            }
            System.out.println(lootPull + " " + suffix);
            System.out.println("Defense: " + statValue);
            if(prefixInfo != null) {
                System.out.println(prefixInfo);
            }
            if(suffixInfo != null) {
                System.out.println(suffixInfo);
            }
        }
    }
}

/*
 * Steps:
 * 1: Randomly pick a monster to fight from monstats.txt - and print its name
 * 2: Randomly pick a loot table from the monster's loot table: which we grabbed
 * as the
 * third word or the fourth tab-seperated word in the monster's line in
 * monstats.txt
 * 3: Recursively pick loot from the loot table until we reach a base item: do
 * this with a
 * binary search?? on every first line of the loot table: the key gives us
 * access to three possible items,
 * which may be a base item or another loot table (hence a key).
 * These keys are notably comparable, so we can use binary search to find the
 * key in the loot table.
 * 
 * Note: the first word of each line in the TreasureClass.txt file is a loot
 * table.
 * Should we binary search over these?
 * 
 * For random loot generation: we can use an array.
 * For recursive searching: we can use a tree map.
 * 
 * Suffixes: randomly generate a suffix, affix, 
 */