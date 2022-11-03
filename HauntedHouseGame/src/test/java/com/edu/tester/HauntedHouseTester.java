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
        item2.addItemAction(Item.ItemActions.valueOf("POSSESS"));
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

        //Testing to make sure the previous room didn't hold onto the player reference after they left
        assertNull(previousRoom.getPlayer());

        //Testing to make sure the room reference stored in the player doesn't match the previous room they were in
        assertNotEquals(player.getRoomReference(), previousRoom);
    }

    @Test
    @DisplayName("Player is added to room")
    public void testPlayerIsInRoom(){
        beforeEach();
        Room previousRoom = player.getRoomReference();
        player.move("East");

        //Testing to make sure the players room reference matches the room they moved to
        assertEquals(player.getRoomReference(), previousRoom.getEastRoom());

        //Testing to make sure the room moved to has a matching player reference to the that of the player.
        assertEquals(previousRoom.getEastRoom().getPlayer(), player);
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
        //Checking for an Adult npc
        Room previous = adult.getRoomReference();
        adult.move();
        assertNull(previous.getNPC(adult.getName()));

        //Checking for a Child npc
        Room previousForChild = child.getRoomReference();
        child.move();
        assertNull(previousForChild.getNPC(child.getName()));
    }

    @Test
    @DisplayName("NPC is added to new room, checks adult npc then child npc")
    public void testNPCJoinsRoom(){
        beforeEach();
        //Testing for an Adult npc
        adult.move();
        assertEquals(adult.getRoomReference(),room2);

        //Testing for a Child npc
        child.move();
        assertEquals(child.getRoomReference(),room2);
    }

    @Test
    @DisplayName("NPC returns to original room, checks cases of adult and child")
    public void testNPCReturns(){
        beforeEach();

        //Testing for an Adult NPC
        adult.move();
        adult.move();
        assertEquals(adult.getRoomReference(), room1);

        //Testing for a Child NPC
        child.move();
        child.move();
        assertEquals(child.getRoomReference(), room1);
    }

    @Test
    @DisplayName("Testing the NPC array to make sure its in an expected state before movement")
    public void testNPCArrayBeforeMovement(){
        beforeEach();
        //Checking both npcs
        assertEquals(room1.getNPCIndex(adult), 0);
        assertEquals(room1.getNPCIndex(child), 1);
    }

    @Test
    @DisplayName("Testing the NPC array to make sure its in an expected state after movement")
    public void testNPCArrayAfterMovement(){
        beforeEach();

        adult.move();
        //Checking both npcs in the first room
        assertEquals(room1.getNPCIndex(adult), -1);
        assertEquals(room1.getNPCIndex(child), 0);

        //Ensuring the npc was added to the right spot in the second room
        assertEquals(room2.getNPCIndex(adult), 0);
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

        //Adult Case
        beforeEach();
        room2.hauntNotification(item2.getItemName(), "THROW");
        adult.move();
        assertNull(room2.getItem(item2.getItemName()));

        //Child Case
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
        //Checking to make sure every action can't be done
        assertFalse(item2.supportsAction(Item.ItemActions.valueOf("THROW")));
        assertFalse(item2.supportsAction(Item.ItemActions.valueOf("SHAKE")));
        assertFalse(item2.supportsAction(Item.ItemActions.valueOf("POSSESS")));
    }

}
