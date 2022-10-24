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

        HauntedTimer.initTimer(60);
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
                    System.out.println("exit or quit will allow you to leave the game before the time is up\n");
                }
                case "look" -> System.out.println(this.getRoomReference());
                case "north" -> System.out.println(move("north") ? "Successfully moved\n" : "Failed to move\n");
                case "east" -> System.out.println(move("east") ? "Successfully moved\n" : "Failed to move\n");
                case "south" -> System.out.println(move("south") ? "Successfully moved\n" : "Failed to move\n");
                case "west" -> System.out.println(move("west") ? "Successfully moved\n" : "Failed to move\n");
                case "shake" -> {
                    Item item = getRoomReference().getItem(inputSplit[1]);
                    if(!(item == null)){
                        //Checking to see if an items action is supported
                        if(item.supportsAction(Item.ItemActions.valueOf("SHAKE"))){
                            String shrieks = haunt(item, "SHAKE");
                            System.out.println("Successfully shook " + inputSplit[1]);
                            System.out.println(shrieks);
                        } else {
                            System.out.println(inputSplit[1] + " could not be shaken.\n");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room\n");
                    }
                }
                case "possess" -> {
                    Item item = getRoomReference().getItem(inputSplit[1]);
                    if(!(item == null)){
                        //Checking to see if an items action is supported
                        if(item.supportsAction(Item.ItemActions.valueOf("POSSESS"))){
                            String shrieks = haunt(item, "POSSESS");
                            System.out.println("Successfully possessed " + inputSplit[1]);
                            System.out.println(shrieks);
                        } else {
                            System.out.println(inputSplit[1] + " could not be possessed.\n");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room\n");
                    }
                }
                case "throw" -> {
                    Item item = getRoomReference().getItem(inputSplit[1]);
                    if(!(item == null)){
                        //Checking to see if an items action is supported
                        if(item.supportsAction(Item.ItemActions.valueOf("THROW"))){
                            String shrieks = haunt(item, "THROW");
                            System.out.println("Successfully threw " + inputSplit[1]);
                            System.out.println(shrieks);
                        } else {
                            System.out.println(inputSplit[1] + " could not be thrown.\n");
                        }
                    }else{
                        System.out.println(inputSplit[1] + " is not in this room\n");
                    }
                }
                case "exit", "quit" -> play = false;
                default -> System.out.println("Please enter an actual command like help\n");
            }

            if(Room.getNumNPCsInHouse() == 0) {
                System.out.println("Congrats YOU Won");
                System.exit(0);
            }

            //Keeps the time from being displayed after exiting the game
            if(play) {
                System.out.println("You have " + HauntedTimer.getTime() + " seconds left!");
            }
        }

        //Player chose to exit
        System.exit(0);
    }

    /**
     * Attempts to haunt a specified item using a specified action. Calls the item supportsAction method and sends a
     * haunt notification if the item action was supported.
     * @param item item used for haunting purposes.
     * @param action The action desired to be done to the item.
     * @return Returns a boolean value based upon whether the action was done to the item or not.
     */
    private String haunt(Item item, String action){
            String shrieks = this.getRoomReference()
                                    .hauntNotification(item.getItemName(), action);
            return shrieks;
    }

    /**
     * Attempts to move the player with a specified cardinal direction.
     * @param direction The direction the player wishes to move.
     * @return A boolean value based upon whether the player successfully moved or not.
     */
    public Boolean move(String direction){
        if(this.getRoomReference().roomConnected(direction)){
            Room temp;
            if(direction.equalsIgnoreCase("north")){
                temp = this.getRoomReference()
                                .getNorthRoom();
            } else if(direction.equalsIgnoreCase("east")){
                temp = this.getRoomReference()
                                .getEastRoom();
            } else if(direction.equalsIgnoreCase("south")){
                temp = this.getRoomReference()
                                .getSouthRoom();
            } else{
                temp = this.getRoomReference()
                                .getWestRoom();
            }
            this.getRoomReference()
                    .playerLeaveRoom();
            temp.addPlayer(this);
            return true;
        }else{
            return false;
        }
    }


}
