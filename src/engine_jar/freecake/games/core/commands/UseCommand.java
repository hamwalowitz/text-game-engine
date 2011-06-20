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

package freecake.games.core.commands;

import freecake.games.core.AbstractGame;
import freecake.games.core.Command;
import freecake.games.core.model.Item;
import java.util.Arrays;

/*
 * UseCommand - use an item on another item.
 */
public class UseCommand extends AbstractCommand {

    @Override
    public String[] getRecognizedCommands() {
        String commands[] = { "use","apply","give","put","place" };
        return commands;
    }
    
    @Override
    public boolean execute(Command command) {
        if (command.getArg(0) == null) {
            System.out.println("Huh?");
            return false;
        }

        String[] acceptedPrepositions = {"on", "to"};

        //find preposition
        int prepIdx = -1;

        for (String s : command.getArgs()) {
            prepIdx++;
            if (Arrays.asList(acceptedPrepositions).contains(s)) {
                break;
            }
        }

        if (prepIdx <= 0) {
            if (command.getArgsString().isEmpty()) {
                System.out.println("Huh?");
                return false;                
            } else {
                System.out.println("What do you want to use it on?");
                return false;
            }
        }

        String toUse = "";
        String usedOn = "";
        for (int s2=0;s2<prepIdx;s2++) {
            if (s2 != 0) {
             toUse += " ";
            }
            toUse += command.getArg(s2);
        }
        for (int s3=prepIdx+1;s3<command.getArgs().length;s3++) {
            if (s3 != prepIdx+1) {
             usedOn += " ";
            }
            usedOn += command.getArg(s3);
        }

        if (usedOn.isEmpty()) {
            System.out.println("What do you want to use it on?");
            return false;
        }
        Item item = AbstractGame.instance.getInventoryItem(toUse);
        Item item2 = AbstractGame.instance.getInventoryItem(usedOn);
        
        if (item2 == null) {
            item2 = AbstractGame.instance.getCurrentRoom().getItem(usedOn);
        }
        AbstractGame.instance.combineItems(item2, item);
        return false;
    }

}
