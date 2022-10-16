package com.edu.hauntedhouse;

import java.util.Locale;
import java.util.Scanner;

public class Player extends Character{

    /**
     * Creates a player object and sends the name and description to the character's constructor.
     * @param name Name of the player.
     * @param desc Description of the player.
     */
    public Player(String name, String desc){
        super(name,desc);
    }

    public void Play(Scanner s){

        boolean play = true;

        while(play){

            System.out.print("Enter a command: ");
            String input = s.nextLine().toLowerCase();
            String[] inputSplit = input.split(":");

            switch (inputSplit[0]) {
                case "help" -> {
                    System.out.println("You asked for some help? Well here's all the commands.");
                    System.out.println("help - produces this list of commands.");
                    System.out.println("look - lets you know all the information about the room you are in.");
                    System.out.println("north - leaves this room and enters the north room if it exists.");
                    System.out.println("east - leaves this room and enters the east room if it exists.");
                    System.out.println("south - leaves this room and enters the south room if it exists.");
                    System.out.println("west - leaves this room and enters the west room if it exists.");
                    System.out.println("shake:item - shakes an item");
                    System.out.println("possess:item - possesses an item");
                    System.out.println("throw:item - throws an item");
                    System.out.println("exit or quit will allow you to leave the game before the time is up");
                }
                case "look" -> System.out.println(this.getRoomReference());
                case "north" -> System.out.println(move("north") ? "Successfully moved" : "Failed to move");
                case "east" -> System.out.println(move("east") ? "Successfully moved" : "Failed to move");
                case "south" -> System.out.println(move("south") ? "Successfully moved" : "Failed to move");
                case "west" -> System.out.println(move("west") ? "Successfully moved" : "Failed to move");
                case "shake" -> {
                    boolean[] results = haunt(inputSplit[1], "SHAKE");
                    if(results[0]){
                        if(results[1]){
                            System.out.println("Successfully shook " + inputSplit[1]);
                            System.out.println(this.getRoomReference().shriek());
                        } else {
                            System.out.println(inputSplit[1] + " could not be shaken.");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room");
                    }
                }
                case "possess" -> {
                    boolean[] results = haunt(inputSplit[1], "POSSESS");
                    if(results[0]){
                        if(results[1]){
                            System.out.println("Successfully possessed " + inputSplit[1]);
                            System.out.println(this.getRoomReference().shriek());
                        } else {
                            System.out.println(inputSplit[1] + " could not be possessed.");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room");
                    }
                }
                case "throw" -> {
                    boolean[] results = haunt(inputSplit[1], "THROW");
                    if(results[0]){
                        if(results[1]){
                            System.out.println("Successfully threw the " + inputSplit[1]);
                            System.out.println(this.getRoomReference().shriek());
                        }else {
                            System.out.println(inputSplit[1] + " could not be thrown.");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room.");
                    }
                }
                case "exit", "quit" -> play = false;
                default -> System.out.println("Please enter an actual command like help");
            }
        }
    }

    //public void haunt(Item item, ItemActions action)  will haunt an item within a room sending out a haunting
    //                                                  notification to everyone in the room. Calls itemInRoom()
    //                                                  to check if an item is in the room then calls
    //                                                  item.supportsAction() before doing the action of
    //                                                  hauntNotification()
    private boolean[] haunt(String itemName, String action){
        if(this.getRoomReference().itemInRoom(itemName)){
            Item item = this.getRoomReference().getItem(itemName);
            if(item.supportsAction(Item.ItemActions.valueOf(action))){
                this.getRoomReference().hauntNotification(itemName, action);
                boolean[] success = {true, true};
                return success;
            }
            boolean[] actionNotSupported = {true, false};
            return actionNotSupported;
        }
        boolean [] itemNotFound = {false, false};
        return itemNotFound;
    }

    //public void move(Room room1, Room room2)     will allow the player to move from one room to a connected room
    //                                             by checking if the player can move to the specified room
    //                                             using Room methods connectedRoom() then playerLeaveRoom() then
    //                                             addPlayer()

    public Boolean move(String direction){
        if(this.getRoomReference().roomConnected(direction)){
            Room temp;
            if(direction.equalsIgnoreCase("north")){
                temp = this.getRoomReference().getNorthRoom();
            } else if(direction.equalsIgnoreCase("east")){
                temp = this.getRoomReference().getEastRoom();
            } else if(direction.equalsIgnoreCase("south")){
                temp = this.getRoomReference().getSouthRoom();
            } else{
                temp = this.getRoomReference().getWestRoom();
            }
            this.getRoomReference().playerLeaveRoom();
            temp.addPlayer(this);
            return true;
        }else{
            return false;
        }
    }


}
