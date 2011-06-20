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
package freecake.games.example;

import freecake.games.core.model.Direction;
import freecake.games.core.model.Room;
import freecake.games.core.*;
import freecake.games.core.model.Item;
import freecake.games.core.model.ItemType;

/*
 * ExampleGame - this is a silly little game just to illustrate the capabilities
 * of the text game engine.  The story takes place in a fast food restaurant and
 * the goal is to leave the restaurant with your desired meal, even though the
 * staff has taken the day off.
 */
public class ExampleGame extends AbstractGame {

    public static void main(String[] args) {
        ExampleGame game = new ExampleGame();
        game.init();
        game.play();
    }

    //instance variables of every room in the game
    Room cashier,
         diningroom,
         bathroom,
         kitchen,
         managersroom,
         freezer,
         backalley
         ;

    //instance variables of every item in the game
    Item note,
         beefpatties,
         cookedpatties,
         cheese,
         ketchup,
         buns,
         spatula,
         grill
         ;

    public ExampleGame() {
        super();
    }

    @Override
    public void createRooms() {

        //create rooms (names and descriptions)
        cashier = new Room("Cashier area",
                           "You're standing in line near the cash registers.  Available directions are northwest, " +
                           "north, and south");

        diningroom = new Room("Dining room",
                           "You're in the dining area.  The cashier area is to the north");

        bathroom = new Room("Bathroom",
                           "You are in your bathroom.  The door is to the southeast.");

        kitchen = new Room("Kitchen",
                           "You are in the kitchen.  The cashier area is to the south, the manager's office is " +
                           "to the east, a walk-in freezer is to the west, and the back alley is the door to " +
                           "the north.");

        managersroom = new Room("Manager's office",
                           "This is the manager's office.  The door out of here is to the west.");

        freezer = new Room("Freezer",
                           "It's cold in the freezer.  You shouldn't stay long.  Return to the kitchen through the " +
                           "door to the east.");

        backalley = new Room("Back alley",
                           "You wound up in the alley behind the building.  You can re-enter the building to the south.");

        //add exits for each room.  You need to add both sides of an individual exit, unless of course
        //it is a one way door (like a magical door)
        cashier.addExit(Direction.northwest, bathroom);
        cashier.addExit(Direction.north, kitchen);
        cashier.addExit(Direction.south, diningroom);
        kitchen.addExit(Direction.south, cashier);
        kitchen.addExit(Direction.east, managersroom);
        kitchen.addExit(Direction.north, backalley);
        kitchen.addExit(Direction.west, freezer);
        bathroom.addExit(Direction.southeast, cashier);
        diningroom.addExit(Direction.north, cashier);
        managersroom.addExit(Direction.west, kitchen);
        freezer.addExit(Direction.east, kitchen);
        backalley.addExit(Direction.south, kitchen);

        //start game where?
        currentRoom = cashier;
    }

    @Override
    public void createItems() {

        //create items and add to the initial room
        note = new Item(ItemType.Literature, true, true, "small note",
                new String[] {"note", "small note"},
                "There's a small note on the counter",
                "Dear Customer,\n\nThe crew has taken the day off.  If you'd like to prepare your own lunch, " +
                "go for it!  Just try not to hurt yourself!");
        cashier.addItem(note);

        beefpatties = new Item(ItemType.Misc, true, false, "raw beef patties",
                 new String[] {"raw beef patties", "beef patties", "raw patties", "patties",
                               "beef", "hamburger patties"},
                 "Some raw beef patties are in a box in the corner",
                 "Grade 'A' Choice Beef Patties"
                 );
        freezer.addItem(beefpatties);

        cheese = new Item(ItemType.Misc, true, false, "cheese slices",
                new String[] {"cheese", "slices"},
                "Some cheese slices are in the fridge",
                "Single slices of American cheese");
        kitchen.addItem(cheese);

        ketchup = new Item(ItemType.Misc, true, false, "bottle of ketchup",
                new String[] {"bottle of ketchup", "bottle", "ketchup", "ketchup bottle"},
                "A squeeze bottle of ketchup is on one of the tables",
                "Half filled ketchup bottle");
        diningroom.addItem(ketchup);

        buns = new Item(ItemType.Misc, true, false, "hamburger buns",
                new String[] {"buns", "hamburger buns"},
                "A package of hamburger buns is sitting on a shelf",
                "An eight pack of hamburger buns");
        managersroom.addItem(buns);

        spatula = new Item(ItemType.Misc, true, false, "spatula",
                new String[] { "spatula" },
                "A spatula is leaning against the wall",
                "Your standard fast food spatula");
        backalley.addItem(spatula);

        grill = new Item(ItemType.Misc, false, false, "grill",
                new String[] {"grill"},
                "A grill is located along the east wall",
                "This grill should be adequate to cook a burger!");
        kitchen.addItem(grill);
 
    }

    @Override
    public void addToInventory(Item item) {
        super.addToInventory(item);
        //if there is any additional logic to run when an item is added to the
        //inventory, you can do it here.  Like below, where I'm checking to
        //see if the player has found all of the burger ingredients
        int counter = 0;
        for (Item i : inventory) {
            if (i == buns) {
                counter++;
            } else if (i == cookedpatties) {
                counter++;
            } else if (i == cheese) {
                counter++;
            } else if (i == ketchup) {
                counter++;
            }
        }
        if (counter == 4) {
            //Game over
            System.out.println("You now have all of the ingredients you need to assemble " +
                                   "the triple-cheesey burger!  After creating your culinary " +
                                   "masterpiece, you grab yourself a diet soda from the nearest " +
                                   "soda fountain and out the door you go!\n\nThe End");
            System.exit(0);
        }
    }

    private boolean pattiesCooking = false;

    @Override
    public void combineItems(Item appliedTo, Item toApply) {
        //here we evalute "use thing1 on thing2", which in this case, you can see
        //that it results in a new item being created
        if ((appliedTo == grill && toApply == beefpatties) ||
            (appliedTo == beefpatties && toApply == grill)) {
            removeInventoryItem(beefpatties, true);
            pattiesCooking = true;
            System.out.println("The patties are cooking nicely.  You should remove them soon, but be sure to " +
                               "use the proper tool.  The grill is very hot!");
        } else if ((appliedTo == grill || appliedTo == beefpatties) && toApply == spatula && pattiesCooking == true) {
           cookedpatties = new Item(ItemType.Misc, true, false, "beef patties",
                                    new String[] {"beef patties", "patties", "beef"},
                                    "",
                                    "Mmmmmm.  The beef patties smell good!");
           addToInventory(cookedpatties);
        } else {
            System.out.println("Those items can't be used in that fashion");
        }
    }

    @Override
    public void printWelcome() {
        //print the initial text to get the story started
        clearScreen();
        System.out.println("Welcome to the Example Game");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You have entered your favorite local fast food restaurant.  " +
                "You walk over the cashier to place an order, but the place is a ghost town.  " +
                "Refusing to leave without your triple cheesey-burger and diet soda, " +
                "you decide to look around and try to find out what happened to everybody.\n");
        printCurrentRoom(true);
    }

}
