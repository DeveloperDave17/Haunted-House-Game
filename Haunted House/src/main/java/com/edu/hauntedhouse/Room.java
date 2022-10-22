package com.edu.hauntedhouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A Room contains a name, description, npcs, number of npcs , player, adjacent room references, and items.
 */
public class Room {

    private final String roomName;
    private final String roomDesc;
    private NPC[] npcs = new NPC[5];
    private ArrayList<NPC> shrieklist = new ArrayList<>();
    private Player player;
    private final Room[] ROOMS = new Room[4];
    private final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
    private int numNPCs = 0;
    private static int numNPCsInHouse = 0;
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Creates a Room Object with a room name and description
     * @param roomName A room's name.
     * @param roomDesc A room's Description.
     */
    public Room(String roomName, String roomDesc) {
        this.roomName = roomName;
        this.roomDesc = roomDesc;
    }

    /**
     * Adds an npc (adult or child) to the room and increments numNPCs (Representing our number of npcs in the room).
     * @param npc (adult or child)
     */
    public void addNPC(NPC npc) {
        if(numNPCs < 5) {
            npcs[numNPCs++] = npc;
            npc.setRoomReference(this);
            Room.numNPCsInHouse++;
        }
        else {System.out.println("There can't be more than 5 NPCs in a room");}
    }

    /**
     * Adds a player to the room.
     * @param player A reference to the player object.
     */
    public void addPlayer(Player player){
        this.player = player;
        player.setRoomReference(this);
    }

    @Override
    public String toString(){
        ArrayList<String> descs = new ArrayList<>();
        if(ROOMS[NORTH] != null){descs.add("\n" + ROOMS[NORTH].getRoomName() + " to the North");}
        if(ROOMS[SOUTH] != null){descs.add("\n" + ROOMS[SOUTH].getRoomName() + " to the South");}
        if(ROOMS[EAST] != null){descs.add("\n" + ROOMS[EAST].getRoomName() + " to the East");}
        if(ROOMS[WEST] != null){descs.add("\n" + ROOMS[WEST].getRoomName() + " to the West");}
        for(int i = 0; i < numNPCs; i++){descs.add("\n" + npcs[i].toString());}
        if(player != null){descs.add("\n" + player.toString());}
        items.forEach(item -> descs.add("\n" + item.toString()));
        String desc = descs.toString().replace("[", "").replace("]", "");
        return roomName + " : " + roomDesc + desc + "\n";
    }


    public void addNorthRoom(Room northRoom){ROOMS[NORTH] = northRoom;}
    public void addSouthRoom(Room southRoom){ROOMS[SOUTH] = southRoom;}
    public void addEastRoom(Room eastRoom){ROOMS[EAST] = eastRoom;}
    public void addWestRoom(Room westRoom){ROOMS[WEST] = westRoom;}
    public Room getNorthRoom(){return ROOMS[NORTH];}
    public Room getSouthRoom(){return ROOMS[SOUTH];}
    public Room getEastRoom(){return ROOMS[EAST];}
    public Room getWestRoom(){return ROOMS[WEST];}

    /**
     * Checks if a room for a specified direction exists.
     * @param direction Cardinal direction (North, South, West, East).
     * @return A boolean value based upon whether a room is connected in the desired direction.
     */
    public boolean roomConnected(String direction){
        if(direction.equalsIgnoreCase("north")){
            return ROOMS[NORTH] != null;
        } else if(direction.equalsIgnoreCase("east")){
            return ROOMS[EAST] != null;
        } else if(direction.equalsIgnoreCase("south")){
            return ROOMS[SOUTH] != null;
        } else{
            return ROOMS[WEST] != null;
        }
    }


    /**
     * Using a specified item and associated action all npcs within the room are sent "haunt notifications" by calling
     * each npcs scareNPC method and creating a random scare score for each. Calls the breakItem method for an item
     * if it is thrown.
     * @param itemName Name of item used for haunting.
     * @param action Action to be done to the item.
     */
    public void hauntNotification(String itemName, String action){
        Random random = new Random();
        //Ensuring all NPCs are scared (Prevents NPCs leaving rooms from changing the size of the iterated array)
        ArrayList<NPC> temp = new ArrayList<>(Arrays.asList(npcs).subList(0, numNPCs));

        if(action.equals("SHAKE")){
            for(NPC npc:temp){
                npc.scareNPC(5 + 10*random.nextDouble());
            }
        }
        if(action.equals("POSSESS")){
            for(NPC npc:temp){
                npc.scareNPC(10 + 15*random.nextDouble());
            }
        }
        if(action.equals("THROW")){
            for(NPC npc:temp){
                npc.scareNPC(20 + 20*random.nextDouble());
            }
            for(Item item: items){
                if(item.getItemName().equalsIgnoreCase(itemName)){
                    item.breakItem();
                }
            }
        }
    }

    /**
     * Gets rid of the associated player reference attached to the room when the player leaves.
     */
    public void playerLeaveRoom(){
        player = null;
    }

    /**
     * Removes a specified npc from the room.
     * @param npc The npc who is to be removed.
     */
    public void npcLeaveRoom(NPC npc){
        int i;
        //finding the index of the npc within the npcs array
        for(i = 0; i < numNPCs; i++){
            if(npcs[i] == npc){
                break;
            }
        }
        if(i < numNPCs - 1){
            //moving all the npcs up 1 in the array and ensuring a duplicate won't exist
            for(int idx = i; idx < numNPCs; idx++){
                npcs[idx] = npcs[idx+1];
                npcs[idx+1] = null;
            }
        } else{
            npcs[i] = null;
        }
        numNPCs--;
        numNPCsInHouse--;
    }

    /**
     * Adds an item to the items arraylist.
     * @param item The reference to a specific item object.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Gets an item reference from the room based upon a specified index.
     * @param index The specified index of an array list containing items.
     * @return The desired item reference.
     */
    public Item getItem(int index){
        return items.get(index);
    }

    /**
     * Gets an item reference from the room based upon a specified item name.
     * @param itemName The name of the item which a reference is desired for.
     * @return The item reference.
     */
    public Item getItem(String itemName){
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Gets room name.
     * @return Current room's name
     */
    public String getRoomName(){
        return roomName;
    }

    public Player getPlayer(){
        return player;
    }
    //public boolean itemInRoom() will check to see if an item is in the room
    public boolean itemInRoom(String itemName){
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all broken items from the room when called.
     */
    public void removeBrokenItems(){
        items.removeIf(Item::isItemBroken);
    }

    /**
     * Adds a npc to the list of npcs that are going to shriek (shrieklist).
     * @param npc The npc who is going to shriek.
     */
    public void addShrieker(NPC npc){
        shrieklist.add(npc);
    }

    /**
     * Checks the shriek list for all npcs that are going to shriek, determines how they are going to shriek, creates a
     * string consisting of all their shrieks, calls a method to clear the shriek list, and returns a string
     * consisting of all their shrieks.
     * @return A string consisting off all the shrieking npcs shrieks.
     */
    public String shriek(){
        StringBuilder result = new StringBuilder();
        for(NPC npc: shrieklist){
            if(npc.getScarednessLevel() < 50){
                result.append(npc.getName()).append(" shrieks\n");
            } else if (npc.getScarednessLevel() < 100){
                result.append(npc.getName()).append(" shrieks, shrieks, and shrieks\n");
            } else{
                result.append(npc.getName()).append(" lets out this profound shriek, rattling everyone's ears\n");
            }
        }

        shrieklist.clear();

        return result.toString();
    }

    public static int getNumNPCsInHouse(){
        return numNPCsInHouse;
    }
}