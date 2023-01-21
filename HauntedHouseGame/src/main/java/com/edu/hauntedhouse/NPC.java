package com.edu.hauntedhouse;

import java.util.ArrayList;
import java.util.Random;

public class NPC extends Character{

    private double scarednessLevel = 0;

    private boolean movedRoom = false;

    /**
     * Sends the npcs name and description to the character class method for creation.
     * @param name Name of npc.
     * @param desc Name of description.
     */
    public NPC(String name, String desc){
        super(name,desc);
    }

    /**
     * Scares an NPC by increasing their scarednessLevel by the scareScore specified, checks to see if a scare level
     * is greater than or equal 100 then leaves the house if so. If not it checks whether a npc has a scarednessLevel
     * of greater than or equal to 50 then if true calls the move method and the associated rooms removeBrokenItems
     * method.
     * @param scareScore The amount the scarednessLevel is to increase by.
     */
    public String scareNPC(double scareScore){
        scarednessLevel += scareScore;

        if(scarednessLevel >= 100){
            this.getRoomReference().npcLeaveRoom(this);
            HauntedTimer.addTime(30);
            return getName() + " lets out this profound shriek, rattling everyone's ears and leaves the house\n";
        } else if(scarednessLevel >= 50 && !movedRoom){
            movedRoom = true;
            return getName() + " shrieks, shrieks, and shrieks " + move() + "\n";
        }
        return getName() + " shrieks\n";
    }

    /**
     * Moves the npc from their current room to a randomly connected room.
     */
    public String move(){
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

        this.getRoomReference().removeBrokenItems();

        return "and moves to the " + this.getRoomReference().getRoomName();
    }

    @Override
    public String toString(){
        String result = "";
        result += super.getName() +" (Scare Level = " + (int)scarednessLevel +")" + " : " + super.getDesc();
        return result;
    }

}
