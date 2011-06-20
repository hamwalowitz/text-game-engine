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
import freecake.games.core.model.Direction;

/*
 * NavigateCommand - Move to an adjacent room
 */
public class NavigateCommand extends AbstractCommand {

    @Override
    public String[] getRecognizedCommands() {
        String commands[] = { "w","s","e","n","sw","se","nw","ne" };
        return commands;
    }
    
    @Override
    public boolean execute(Command command) {
        Direction direction;
        if (command.getCommandWord().equals("n")) {
            direction = Direction.north;
        } else if (command.getCommandWord().equals("e")) {
            direction = Direction.east;
        } else if (command.getCommandWord().equals("s")) {
            direction = Direction.south;
        } else if (command.getCommandWord().equals("w")) {
            direction = Direction.west;
        } else if (command.getCommandWord().equals("se")) {
            direction = Direction.southeast;
        } else if (command.getCommandWord().equals("sw")) {
            direction = Direction.southwest;
        } else if (command.getCommandWord().equals("ne")) {
            direction = Direction.northeast;
        } else if (command.getCommandWord().equals("nw")) {
            direction = Direction.northwest;
        } else {
            direction = null;
        }
        AbstractGame.instance.goInDirection(direction);
        return false;
    }

}
