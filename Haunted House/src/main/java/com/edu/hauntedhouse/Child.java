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

    /*
    @override
    public void scareNPC(int scareScore){} //Will multiply scare score by 1.5x before adding to scareLevel and check
                                             if the move() method should be called(if scarednessLevel > 50)
     */
    @Override
    public void scareNPC(double scareScore){
        super.scareNPC(scareScore*1.5);
    }
}
