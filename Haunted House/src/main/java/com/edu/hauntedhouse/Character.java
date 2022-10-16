package com.edu.hauntedhouse;

public class Character {
    private final String name;
    private final String desc;
    private Room roomReference;

    public Character(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    /**
     * Provides a way for a character to know which room they are in by storing a reference to the room.
     * @param room The room reference.
     */
    public void setRoomReference(Room room){
        roomReference = room;
    }

    public Room getRoomReference() {return roomReference;}

    @Override
    public String toString(){
        return name + " : " + desc;
    }

    public String getName(){return name;}

    public String getDesc(){return desc;}

}
