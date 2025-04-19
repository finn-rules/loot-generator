package edu.grinnell.csc207.lootgenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * The LootGenerator class.
 */
public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/large";

    // This method combines both pickMonster and getTreasureClass : the monster is
    // the "key" and the
    // treasureClass is the "value" in a pair. Choose a random monster pair from an
    // arrayList of size hashSize.
    /**
     * pickMonster : choose a random monster to fight from the monstats file (which
     * is now an ArrayList) and return the information of a single monster.
     * 
     * @param hashes the ArrayList of all monsters from a single file (represented
     *               as pairs)
     * @return a single monster pair.
     */
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
    /**
     * pickLoot : a recursive function which searches a TreeMap to see if a
     * TreasureClass "key" is contained in the TreeMap. If not, we know it is a
     * valid treasure pull, and we return it. If the key is contained, we randomly
     * select another potential key from the current Key's value, and recurse.
     * 
     * @param lootTableHashes a TreeMap of all possible Treasure combos.
     * @param curKey          the current TreasureClass to be searched for
     * @return the ultimate goal is to return an "item" which is not a treasureclass
     *         (so not a key)
     */
    public String pickLoot(TreeMap<String, String[]> lootTableHashes, String curKey) {
        if (!lootTableHashes.containsKey(curKey)) {
            return curKey;
        } else {
            String[] possibleKeyStrings = lootTableHashes.get(curKey);
            int nextKeyIndex = (int) (Math.random() * possibleKeyStrings.length);
            String nextKey = possibleKeyStrings[nextKeyIndex];
            return pickLoot(lootTableHashes, nextKey);
        }
    }

    /**
     * pickDefenseStat : using a TreeMap of possible items, generate the stats of a
     * single item using randomness.
     * We will appeal to the fact that we have pairs of maxes and min defense stats
     * stored for each item, and return a random value between these bounds.
     * 
     * @param statTree a TreeMap where key are the items and "defense bounds" are
     *                 the values.
     * @param k the item name or "key" we're looking for in the TreeMap
     * @return a randomly generated defense stat in the bounds for our item.
     */
    public static int pickDefenseStat(TreeMap<String, Pair<Integer, Integer>> statTree, String k) {
        if (statTree.containsKey(k)) {
            Pair<Integer, Integer> statRange = statTree.get(k);
            int minac = statRange.getKey();
            int maxac = statRange.getValue();
            return (int) (Math.random() * (maxac - minac + 1)) + minac;
        } else {
            System.out.println("Error: item not found in the armor.txt file!");
            return 0; // Default value if item not found (shouldn't happen)
        }
    }

    /**
     * generateAffix : decides whether a prefix/suffix should be generated for the
     * item! If one is generated,
     * then we return a pair that is actually meaningful (aka a pair of the affix
     * itself and extra buffs).
     * 
     * @param affixes an ArrayList of possible affixes (which we randomly choose, if
     *                needed)
     * @return either a valid affix or an empty pair.
     */
    public static Pair<String, String> generateAffix(ArrayList<Pair<String, String[]>> affixes) {
        boolean hasAffix;
        Pair<String, String> affixPair = null;
        int generates = (int) (Math.random() * 2);
        if (generates == 0) {
            hasAffix = false;
        } else {
            hasAffix = true;
        }
        if (hasAffix) {
            int randomIndex = (int) (Math.random() * affixes.size());
            Pair<String, String[]> thisAffix = affixes.get(randomIndex);
            String affixName = thisAffix.getKey();
            String[] affixInfoArray = thisAffix.getValue();
            String affixType = affixInfoArray[0];
            int minStat = Integer.parseInt(affixInfoArray[1]);
            int maxStat = Integer.parseInt(affixInfoArray[2]);

            // Randomly generate a stat in the bounds, and set the pair to the actual affix
            // + affix info
            int affixStat = (int) (Math.random() * (maxStat - minStat + 1)) + minStat;
            affixPair = new Pair<>(affixName, affixType + " " + affixStat);
        }
        return affixPair;
    }

    /**
     * main : the entry point of our program. Contains a loop where the user is
     * prompted. Decomposes our problem into the necessary classes and methods when
     * applicable.
     * 
     * @param args : the command line arguments (not applicable)
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) 
        throws FileNotFoundException, IOException, InterruptedException {
        System.out.println("This program kills monsters and generates loot!");
        MonsterInfoGenerator<Integer, Pair<String, String>> m = 
            new MonsterInfoGenerator<>(DATA_SET + "/monstats.txt");
        m.setMapPairs();
        m.getWords();
        boolean running = true;
        System.out.println("Press 'y' to fight a monster / type 'n' to quit. Then press 'enter'.");
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
                    input = s.nextLine();
                    System.out.println("Fight again [y/n]?");
                    continue;
                }
            }

            if (!running) {
                break;
            }
            System.out.println();
            ArrayList<Pair<String, String>> monsterInfoHashes = m.getWords();
            LootGenerator lootGen = new LootGenerator();
            Pair<String, String> monsterInfo = lootGen.pickMonster(monsterInfoHashes);

            System.out.println();

            // Now we need to pick a loot table from the monster's loot table
            // Generate the tree from TreasureClassEx.txt.
            TreasureTreeGenerator ltg = 
                new TreasureTreeGenerator(DATA_SET + "/TreasureClassEx.txt");
            TreeMap<String, String[]> lootTableHashes = ltg.getHashesTreeMap();
            String currentTreasureClass = monsterInfo.getValue();

            String lootPull = lootGen.pickLoot(lootTableHashes, currentTreasureClass);

            LootStatTreeGenerator statTree = new LootStatTreeGenerator(DATA_SET + "/armor.txt");
            TreeMap<String, Pair<Integer, Integer>> lootStatTreeMap = statTree.getHashesTreeMap();
            int statValue = pickDefenseStat(lootStatTreeMap, lootPull);

            ArrayList<Pair<String, String[]>> prefixes = 
                new AffixListGenerator<>(DATA_SET + "/MagicPrefix.txt").getWords();

            ArrayList<Pair<String, String[]>> suffixes = 
                new AffixListGenerator<>(DATA_SET + "/MagicSuffix.txt").getWords();

            Pair<String, String> prefixPair = generateAffix(prefixes);
            Pair<String, String> suffixPair = generateAffix(suffixes);
            String prefix = null;
            String prefixInfo = null;
            String suffix = null;
            String suffixInfo = null;

            if (prefixPair != null) {
                prefix = prefixPair.getKey();
                prefixInfo = prefixPair.getValue();
            }

            if (suffixPair != null) {
                suffix = suffixPair.getKey();
                suffixInfo = suffixPair.getValue();
            }
            if (prefix != null) {
                System.out.print(prefix + " ");
            }
            if (suffix == null) {
                suffix = "";
            }
            System.out.println(lootPull + " " + suffix);
            System.out.println("Defense: " + statValue);
            if (prefixInfo != null) {
                System.out.println(prefixInfo);
            }
            if (suffixInfo != null) {
                System.out.println(suffixInfo);
            }
        }
    }
}
