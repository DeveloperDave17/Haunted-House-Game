package com.edu.hauntedhouse;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomXMLParser extends DefaultHandler{

    private ArrayList<Room> rooms = new ArrayList<>();
    private HashMap<String, Room> roomHashMap = new HashMap<>();

    private int roomCount = 0;
    private int roomItemCount;


    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes atts){

        if (qName.equals("room")){
            String roomName;
            String roomDesc;
            roomName = atts.getValue("name");
            roomDesc = atts.getValue("description");
            rooms.add(new Room(roomName,roomDesc));
            roomHashMap.put(roomName, rooms.get(roomCount));
            if(roomHashMap.containsKey(atts.getValue("north"))){
                roomHashMap.get(atts.getValue("north")).addSouthRoom(rooms.get(roomCount));
                rooms.get(roomCount)
                        .addNorthRoom(roomHashMap.get(atts.getValue("north")));
            }
            if(roomHashMap.containsKey(atts.getValue("south"))){
                roomHashMap.get(atts.getValue("south")).addNorthRoom(rooms.get(roomCount));
                rooms.get(roomCount)
                        .addSouthRoom(roomHashMap.get(atts.getValue("south")));
            }
            if(roomHashMap.containsKey(atts.getValue("east"))){
                roomHashMap.get(atts.getValue("east")).addWestRoom(rooms.get(roomCount));
                rooms.get(roomCount)
                        .addEastRoom(roomHashMap.get(atts.getValue("east")));
            }
            if(roomHashMap.containsKey(atts.getValue("west"))){
                roomHashMap.get(atts.getValue("west")).addEastRoom(rooms.get(roomCount));
                rooms.get(roomCount)
                        .addWestRoom(roomHashMap.get(atts.getValue("west")));
            }
            roomCount++; roomItemCount =0;
        }

        if(qName.equals("item")){
            String[] itemActions;
            rooms.get(roomCount-1).addItem(new Item(atts.getValue("name"), atts.getValue("description")));
            itemActions = atts.getValue("actions").split(",");
            for(String s: itemActions) {
                rooms.get(roomCount - 1)
                        .getItem(roomItemCount)
                        .addItemAction(Item.ItemActions.valueOf(s.toUpperCase()));
            }
            roomItemCount++;
        }

        if(qName.equals("adult")){
            rooms.get(roomCount-1)
                    .addNPC(new Adult(atts.getValue("name"), atts.getValue("description")));
        }

        if(qName.equals("child")){
            rooms.get(roomCount-1)
                    .addNPC(new Child(atts.getValue("name"), atts.getValue("description")));
        }

        if(qName.equals("player")){
            rooms.get(roomCount-1)
                    .addPlayer(new Player(atts.getValue("name"), atts.getValue("description")));
        }
    }

    public Player getPlayer() {
        Player player = null;
        for (Room room : rooms) {
            if (room.getPlayer() != null) {
                player = room.getPlayer();
            }
        }
        return player;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }
}
