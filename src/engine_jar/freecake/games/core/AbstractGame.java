/** * Copyright (c) 2011, Jason Gardner * All rights reserved. *  * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: *  * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/package freecake.games.core;import freecake.games.core.model.Direction;import freecake.games.core.model.Room;import freecake.games.core.commands.*;import freecake.games.core.model.Item;import java.util.ArrayList;import java.util.Arrays;import java.util.List;/* * AbstractGame - this is the base class for Text-based games. */public abstract class AbstractGame {    protected Parser parser;    protected Room currentRoom;    private ArrayList<AbstractCommand> executors = new ArrayList<AbstractCommand>();    protected ArrayList<Item> inventory = new ArrayList<Item>();    public static AbstractGame instance;    public AbstractGame() {            }    public void init() {        //very important to call this!        instance = this;        createRooms();        createItems();        addBaseCommands();        parser = new Parser(this);    }    public void createRooms() {        //override me    }    public void createItems() {        //override me    }    public List<AbstractCommand> getCommandExecutors() {        return executors;    }        public void addBaseCommands() {        executors.add(new NavigateCommand());        executors.add(new DropCommand());        executors.add(new ExamineCommand());        executors.add(new InventoryCommand());        executors.add(new TakeCommand());        executors.add(new ReadCommand());        executors.add(new UseCommand());        executors.add(new HelpCommand());    }    public void addCommand(AbstractCommand command) {        executors.add(command);    }    public void addToInventory(Item item) {        if (!inventory.contains(item)) {            item.setDropped(false);            inventory.add(item);            currentRoom.removeItem(item);            System.out.println("The " + item.getName() + " was added to your inventory");        }    }    public Item getInventoryItem(String itemName) {        for (Item item : inventory) {            if (item.keywordMatch(itemName)) {                return item;            }        }        return null;    }    public boolean removeInventoryItem(Item item, boolean permanent) {        if (item.canDrop()) {            if (!permanent) {                item.setDropped(true);                currentRoom.addItem(item);            }            inventory.remove(item);            return true;        } else {            return false;        }    }    public void play() {                    printWelcome();        parser.start();    }    public void printWelcome() {        //override if necc        System.out.println("Type 'help' if you need help.");        printCurrentRoom(true);    }    public boolean processCommand(Command command) {        for (AbstractCommand executor : executors) {            if (Arrays.asList(executor.getRecognizedCommands()).contains(command.getCommandWord())) {                return executor.execute(command);            }        }        System.out.println("I don't know what you mean...");        return false;    }    public void printHelp() {        System.out.println("The available commands are:");        String allcommands = "";        for (AbstractCommand executor : executors) {            for (String indCmd : executor.getRecognizedCommands()) {                allcommands += indCmd + "  ";            }        }        System.out.println(allcommands);    }    protected void clearScreen() {        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {            String ANSI_CSI = "\u001b[";            System.out.print(ANSI_CSI + 2 + "J");            System.out.flush();            System.out.print(ANSI_CSI + "1;1H");            System.out.flush();        }    }    public void printInverntory() {        String allitems = "";        for (Item item : inventory) {            allitems += "\n" + item.getName();        }        if (allitems.length() > 0) {            System.out.println("Your inventory:" + allitems);        } else {            System.out.println("Your inventory is empty");        }    }    public void combineItems(Item item, Item inventoryItem) {        //override    }    public void goInDirection(Direction direction) {              // Try to leave current room.        Room nextRoom = currentRoom.nextRoom(direction);        if (nextRoom == null) {            System.out.println("You can't go that way!");        } else {            currentRoom = nextRoom;            printCurrentRoom(false);        }    }    public Room getCurrentRoom() {        return currentRoom;    }    public void printCurrentRoom(boolean full) {        String longDesc = currentRoom.longDescription(full);        if (longDesc != null) {            System.out.println(currentRoom.shortDescription() + "\n" + longDesc);        } else {            System.out.println(currentRoom.shortDescription());        }    }}