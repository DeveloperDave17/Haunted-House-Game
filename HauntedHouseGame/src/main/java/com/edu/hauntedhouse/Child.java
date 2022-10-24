package com.edu.hauntedhouse;

public class Child extends NPC{

    /**
     * Creates a child object and sends the name and description to the npc constructor.
     * @param name Name of child.
     * @param desc Description of child.
     */
    public Child(String name, String desc){
        super(name, desc);
    }

    /**
     * Calls NPCs method for scaring the npc but with an increased scareScore of 1.5 times the original amount.
     * @param scareScore The amount the scarednessLevel is to increase by.
     */
    @Override
    public String scareNPC(double scareScore){
        return super.scareNPC(scareScore*1.5);
    }
}
