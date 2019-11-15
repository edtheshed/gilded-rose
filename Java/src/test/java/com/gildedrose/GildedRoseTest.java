package com.gildedrose;

import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GildedRoseTest {

    public static final int SEED = 100;
    public static final int MINIMUM = -50;
    public static final int MAXIMUM = 100;
    public static final int NUMBER_OF_ITEMS = 2000;
    public static final String TEST_OUTPUT_FILE = "./src/test/java/com/gildedrose/testOutput.txt";
    public static final String TEST_EXPECTED_FILE = "./src/test/java/com/gildedrose/testExpectedOutput.txt";
    private Random random;

    private String[] itemNames = {"+5 Dexterity Vest",
            "Aged Brie",
            "Elixir of the Mongoose",
            "Sulfuras, Hand of Ragnaros",
            "Backstage passes to a TAFKAL80ETC concert",
            "Conjured Mana Cake"};

    @Before
    public void setUp() throws Exception {
        random = new Random(SEED);
    }

    @Test
    public void test_all_random_items() throws IOException {
        Item[] items = createItems(NUMBER_OF_ITEMS);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        output(app.items);

        File outputFile = new File(TEST_OUTPUT_FILE);
        File expectedFile = new File(TEST_EXPECTED_FILE);

        assertTrue("The files differ!", FileUtils.contentEquals(expectedFile, outputFile));
    }

    private Item[] createItems(int numberOfItems){
        Item[] items = new Item[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            String itemName = getRandomItemName();
            if (itemName == "Sulfuras, Hand of Ragnaros")
                items[i] = new Item(itemName, getRandomSellIn(), 80);
            else
                items[i] = new Item(itemName, getRandomSellIn(), getRandomQuality());
        }

        return items;
    }

    private int getRandomQuality() {
        return randomNumberBetween(0, 50);
    }

    private int randomNumberBetween(int minimum, int maximum) {
        return minimum + random.nextInt(maximum);
    }

    private int getRandomSellIn() {
        return randomNumberBetween(MINIMUM, MAXIMUM);
    }

    private String getRandomItemName() {
        return itemNames[randomNumberBetween(0, itemNames.length)];
    }

    private void output(Item[] items) throws IOException {
        StringBuilder str = new StringBuilder();
        for (Item item : items) {
            str.append(item.toString() + '\n');
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_OUTPUT_FILE, false));
        writer.append(str);

        writer.close();
    }

}
