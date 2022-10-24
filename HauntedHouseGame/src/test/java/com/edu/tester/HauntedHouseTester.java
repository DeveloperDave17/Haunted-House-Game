package com.edu.tester;

import com.edu.hauntedhouse.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HauntedHouseTester {

    private Player player;

    private Room room1,room2;
    private Item item1, item2;
    private Adult adult;
    private Child child;


    public void beforeEach(){
        room1 = new Room("Starting Room", "Welcome to the starting test zone.");
        room2 = new Room("East Room", "The room to the east of the starting room.");
        room1.addEastRoom(room2);
        room2.addWestRoom(room1);

        item1 = new Item("Sofa", "One of those big chair things.");
        item1.addItemAction(Item.ItemActions.valueOf("SHAKE"));
        item1.addItemAction(Item.ItemActions.valueOf("POSSESS"));
        room1.addItem(item1);

        item2 = new Item("Mug", "A cup");
        item2.addItemAction(Item.ItemActions.valueOf("SHAKE"));
        item2.addItemAction(Item.ItemActions.valueOf("THROW"));
        room2.addItem(item2);

        adult = new Adult("Dan", "A cool guy");
        room1.addNPC(adult);

        child = new Child("Jacob", "A child");
        room1.addNPC(child);

        player = new Player("Dave", "The ultimate creator of this games world");
        room1.addPlayer(player);
    }

    @Test
    @DisplayName("Player truly leaves room")
    public void testPlayerLeavesRoom(){
        beforeEach();
        Room previousRoom = player.getRoomReference();
        player.move("East");
        assertNull(previousRoom.getPlayer());
    }

    @Test
    @DisplayName("Player is added to room")
    public void testPlayerIsInRoom(){
        beforeEach();
        Room previousRoom = player.getRoomReference();
        player.move("East");
        assertEquals(player.getRoomReference(), previousRoom.getEastRoom());
    }

    @Test
    @DisplayName("Player goes back to starting room and leaves previous room")
    public void testPlayerReturnsToRoomAndLeavesRoom(){
        beforeEach();
        player.move("East");
        Room previousRoom = player.getRoomReference();
        player.move("West");
        assertNull(previousRoom.getPlayer());
    }

    @Test
    @DisplayName("Player goes back to starting room and is added back")
    public void testPlayerReturnsToRoom(){
        beforeEach();
        Room startingRoom = player.getRoomReference();
        player.move("East");
        player.move("West");
        assertEquals(player.getRoomReference(), startingRoom);
    }

    @Test
    @DisplayName("NPC leaves previous room, checks if the adult leaves the room and then if the child leaves the room")
    public void testNPCleavesRoom(){
        beforeEach();
        Room previous = adult.getRoomReference();
        adult.move();
        assertNull(previous.getNPC(adult.getName()));

        Room previousForChild = child.getRoomReference();
        child.move();
        assertNull(previousForChild.getNPC(child.getName()));
    }

    @Test
    @DisplayName("NPC is added to new room, checks adult npc then child npc")
    public void testNPCJoinsRoom(){
        beforeEach();
        adult.move();
        assertEquals(adult.getRoomReference(),room2);

        child.move();
        assertEquals(child.getRoomReference(),room2);
    }

    @Test
    @DisplayName("NPC returns to original room, checks cases of adult and child")
    public void testNPCReturns(){
        beforeEach();
        adult.move();
        adult.move();
        assertEquals(adult.getRoomReference(), room1);

        child.move();
        child.move();
        assertEquals(child.getRoomReference(), room1);
    }

    @Test
    @DisplayName("Checks if throwing an item breaks it")
    public void testBreakItem(){
        beforeEach();
        room1.hauntNotification(item1.getItemName(), "THROW");
        assertTrue(item1.isItemBroken());
    }

    @Test
    @DisplayName("Cleans Broken item, checks adult case then child case")
    public void testCleansBrokenItem(){
        beforeEach();
        room2.hauntNotification(item2.getItemName(), "THROW");
        adult.move();
        assertNull(room2.getItem(item2.getItemName()));

        beforeEach();
        room2.hauntNotification(item2.getItemName(), "THROW");
        child.move();
        assertNull(room2.getItem(item2.getItemName()));
    }

    @Test
    @DisplayName("Tests if a broken item can be acted upon")
    public void testBrokenItem(){
        beforeEach();
        room2.hauntNotification(item2.getItemName(), "THROW");
        assertFalse(item2.supportsAction(Item.ItemActions.valueOf("THROW")));
    }

}
