package com.gildedrose;

class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String CONJURED = "Conjured Mana Cake";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item currentItem = items[i];
            if (isNormalItem(currentItem))
                decreaseQuality(currentItem);
            if (isConjuredItem(currentItem))
                decreaseQuality(currentItem);
            else {
                increaseQuality(currentItem);
                handleBackstagePassQuality(currentItem);
            }

            decreaseSellIn(currentItem);

            if (currentItem.isPastSellIn()) {
                handleItemQualityPastSellIn(currentItem);
            }
        }
    }

    private void handleItemQualityPastSellIn(Item currentItem) {
        if (isNormalItem(currentItem))
            decreaseQuality(currentItem);
        else
            increaseQuality(currentItem);

        if (itemIsBackstagePass(currentItem))
            currentItem.quality = 0;
    }

    private boolean itemIsAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }

    private boolean isConjuredItem(Item item) {
        return item.name.equals(CONJURED);
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    private boolean isNormalItem(Item item) {
        return !itemIsAgedBrie(item) && !itemIsBackstagePass(item) && !itemIsSulfuras(item);
    }

    private boolean itemIsBackstagePass(Item item) {
        return item.name.equals(BACKSTAGE_PASSES);
    }

    private void decreaseSellIn(Item item) {
        if (!itemIsSulfuras(item)) {
            item.sellIn--;
        }
    }

    private void handleBackstagePassQuality(Item item) {
        if (itemIsBackstagePass(item)) {
            if (item.sellIn < 11) {
                increaseQuality(item);
            }

            if (item.sellIn < 6) {
                increaseQuality(item);
            }
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    private boolean itemIsSulfuras(Item item) {
        return item.name.equals(SULFURAS_HAND_OF_RAGNAROS);
    }

}