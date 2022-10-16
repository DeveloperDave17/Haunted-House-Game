package com.edu.hauntedhouse;

import java.util.ArrayList;
import java.util.Random;

abstract class NPC extends Character{

    private double scarednessLevel = 0;

    /**
     * Sends the npcs name and description to the character class method for creation.
     * @param name Name of npc.
     * @param desc Name of description.
     */
    public NPC(String name, String desc){
        super(name,desc);
    }

    /*
    abstract void scareNPC(int scareScore){} will increase scaredness level by the amount the NPC was scared
     */
    public void scareNPC(double scareScore){
        scarednessLevel += scareScore;
        this.getRoomReference().addShrieker(this);
        if(scarednessLevel >= 100){
            this.getRoomReference().npcLeaveRoom(this);
        } else if(scarednessLevel >= 50){
            move();
            this.getRoomReference().removeBrokenItems();
        }
    }

    public void move(){
        Room temp = this.getRoomReference();
        ArrayList<Room> rooms = new ArrayList<>();
        Random random = new Random();

        if(temp.roomConnected("north")){
            rooms.add(temp.getNorthRoom());
        }
        if(temp.roomConnected("east")){
            rooms.add(temp.getEastRoom());
        }
        if(temp.roomConnected("west")){
            rooms.add(temp.getWestRoom());
        }
        if(temp.roomConnected("south")){
            rooms.add(temp.getSouthRoom());
        }
        if(rooms.size() > 1) {
            rooms.get(random.nextInt(rooms.size()))
                    .addNPC(this);
        } else{
            rooms.get(0).addNPC(this);
        }
        temp.npcLeaveRoom(this);
    }

    @Override
    public String toString(){
        String result = "";
        result += super.getName() +" (Scare Level = " + (int)scarednessLevel +")" + " : " + super.getDesc();
        return result;
    }

    public double getScarednessLevel(){
        return scarednessLevel;
    }

    //public void move(Room room1, Room room2)     will allow the NPC to move from one room to a connected room
    //                                             by checking if the NPC can move to the specified room
    //                                             using Room methods connectedRoom() then if true, npcLeaveRoom() then
    //                                             addNPC() then brokenItems() will be called then if true
    //                                             removeBrokenItems() will be called. Something to note is a random
    //                                             number generator will be used to determine which room the npc
    //                                             will move to unless the project specifies otherwise.
}
