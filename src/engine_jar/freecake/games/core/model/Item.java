/**
 * Copyright (c) 2011, Jason Gardner
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package freecake.games.core.model;

import java.util.Arrays;

/*
 * Item - entity for an Item.
 */
public class Item {

    private String[] keywords;
    private String itemName;
    private String origDescription;
    private String itemDetails;
    private ItemType type;
    private boolean canInventory = false;
    private boolean canDrop = false;
    private boolean wasDropped = false;

    public Item(ItemType type, boolean canInventory, boolean canDrop,
                String itemName, String[] keywords, String origDescription,
                String itemDetails) {
        this.keywords = keywords;
        this.itemName = itemName;
        this.type = type;
        this.origDescription = origDescription;
        this.itemDetails = itemDetails;
        this.canInventory = canInventory;
        this.canDrop = canDrop;
    }

    public String getName() {
        return itemName;
    }

    public String getOriginalDescription() {
        return origDescription;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public ItemType getType() {
        return type;
    }

    public String examine() {
        return itemDetails;
    }

    public boolean canInventory() {
        return canInventory;
    }

    public boolean canDrop() {
        return canDrop;
    }

    public void setDropped(boolean dropped) {
        wasDropped = dropped;
    }

    public boolean wasDropped() {
        return wasDropped;
    }

    public boolean keywordMatch(String itemName) {
        return Arrays.asList(keywords).contains(itemName);
    }

}
