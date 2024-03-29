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

    private BinarySearchTree<String, Room> bst;


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

    public void beforeBinarySearchTests(){
        bst = new BinarySearchTree<>();
        Room temp1 = new Room("Living Room", "");
        Room temp2 = new Room("Den", "");
        Room temp3 = new Room("Arcade", "");
        Room temp4 = new Room("Dining Room", "");
        Room temp5 = new Room("Closet", "");
        Room temp6 = new Room("Restroom", "");
        Room temp7 = new Room("Opera Theatre", "");
        Room temp8 = new Room("Nathan's Room", "");
        Room temp9 = new Room("Piano Room", "");
        Room temp10 = new Room("Queen Bedroom", "");
        Room temp11 = new Room("Study Room", "");
        Room temp12 = new Room("Running Room", "");
        Room temp13 = new Room("Table Hangout", "");

        bst.add(temp1.getRoomName(),temp1);
        bst.add(temp2.getRoomName(),temp2);
        bst.add(temp3.getRoomName(),temp3);
        bst.add(temp4.getRoomName(),temp4);
        bst.add(temp5.getRoomName(),temp5);
        bst.add(temp6.getRoomName(),temp6);
        bst.add(temp7.getRoomName(),temp7);
        bst.add(temp8.getRoomName(),temp8);
        bst.add(temp9.getRoomName(),temp9);
        bst.add(temp10.getRoomName(),temp10);
        bst.add(temp11.getRoomName(), temp11);
        bst.add(temp12.getRoomName(), temp12);
        bst.add(temp13.getRoomName(), temp13);
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

    @Test
    @DisplayName("Insertion test")
    public void testInsert(){
        beforeBinarySearchTests();
        Room temp = new Room("Indoor Pool", "");
        bst.add(temp.getRoomName(), temp);
        assertTrue(bst.find(temp.getRoomName()));
    }

    @Test
    @DisplayName("Deletes Node on left side of Tree")
    public void testDeleteLeft(){
        beforeBinarySearchTests();
        assertTrue(bst.find("Den"));
        assertTrue(bst.remove("Den"));
        assertFalse(bst.find("Den"));
    }

    @Test
    @DisplayName("Deletes Node on the right side of Tree")
    public void testDeleteRight(){
        beforeBinarySearchTests();
        assertTrue(bst.find("Restroom"));
        assertTrue(bst.remove("Restroom"));
        assertFalse(bst.find("Restroom"));
    }

    @Test
    @DisplayName("Ensuring the values in the subtree are still there after a left removal")
    public void testLeftStillThere(){
        beforeBinarySearchTests();
        assertTrue(bst.find("Arcade"));
        assertTrue(bst.find("Dining Room"));
        assertTrue(bst.find("Closet"));

        assertTrue(bst.find("Den"));
        assertTrue(bst.remove("Den"));
        assertFalse(bst.find("Den"));

        assertTrue(bst.find("Arcade"));
        assertTrue(bst.find("Dining Room"));
        assertTrue(bst.find("Closet"));
    }

    @Test
    @DisplayName("Ensuring the values in the subtree are still there after a left removal")
    public void testRightStillThere(){
        beforeBinarySearchTests();
        assertTrue(bst.find("Opera Theatre"));
        assertTrue(bst.find("Nathan's Room"));
        assertTrue(bst.find("Piano Room"));
        assertTrue(bst.find("Queen Bedroom"));
        assertTrue(bst.find("Study Room"));
        assertTrue(bst.find("Running Room"));
        assertTrue(bst.find("Table Hangout"));

        assertTrue(bst.find("Restroom"));
        assertTrue(bst.remove("Restroom"));
        assertFalse(bst.find("Restroom"));

        assertTrue(bst.find("Opera Theatre"));
        assertTrue(bst.find("Nathan's Room"));
        assertTrue(bst.find("Piano Room"));
        assertTrue(bst.find("Queen Bedroom"));
        assertTrue(bst.find("Study Room"));
        assertTrue(bst.find("Running Room"));
        assertTrue(bst.find("Table Hangout"));
    }

    @Test
    @DisplayName("Deleting the most bottom right node")
    public void testRemoveBottomRight(){
        beforeBinarySearchTests();

        assertTrue(bst.find("Table Hangout"));
        assertTrue(bst.remove("Table Hangout"));
        assertFalse(bst.find("Table Hangout"));
    }

    @Test
    @DisplayName("Deletes Root")
    public void testRemoveRoot(){
        beforeBinarySearchTests();

        assertTrue(bst.find("Living Room"));
        assertTrue(bst.remove("Living Room"));
        assertFalse(bst.find("Living Room"));
    }

    @Test
    @DisplayName("Retains all values after deletion of Root")
    public void testRemoveRootRetention(){
        beforeBinarySearchTests();

        assertTrue(bst.find("Living Room"));
        assertTrue(bst.remove("Living Room"));
        assertFalse(bst.find("Living Room"));

        assertTrue(bst.find("Den"));
        assertTrue(bst.find("Arcade"));
        assertTrue(bst.find("Dining Room"));
        assertTrue(bst.find("Closet"));
        assertTrue(bst.find("Restroom"));
        assertTrue(bst.find("Opera Theatre"));
        assertTrue(bst.find("Nathan's Room"));
        assertTrue(bst.find("Piano Room"));
        assertTrue(bst.find("Queen Bedroom"));
        assertTrue(bst.find("Study Room"));
        assertTrue(bst.find("Running Room"));
        assertTrue(bst.find("Table Hangout"));
    }

    @Test
    @DisplayName("Iterable iterates through all elements")
    public void testIterable(){
        BinarySearchTree<Integer, Integer> bstIterable = new BinarySearchTree<>();
        int result = 0;
        bstIterable.add(5,5);
        result += 5;
        bstIterable.add(6,6);
        result += 6;
        bstIterable.add(2,2);
        result += 2;
        bstIterable.add(1,1);
        result += 1;
        bstIterable.add(3,3);
        result += 3;
        bstIterable.add(9,9);
        result += 9;
        bstIterable.add(7,7);
        result += 7;
        bstIterable.add(8,8);
        result += 8;

        int iterResult = 0;
        for(int b : bstIterable){
            iterResult += b;
        }
        assertEquals(iterResult, result);
    }

    @Test
    @DisplayName("Ensuring iteration within iteration is possible")
    public void testIterableInIterable(){
        BinarySearchTree<Integer, Integer> bstIterable = new BinarySearchTree<>();
        int result = 0;
        bstIterable.add(5,5);
        result += 5;
        bstIterable.add(6,6);
        result += 6;
        bstIterable.add(2,2);
        result += 2;
        bstIterable.add(1,1);
        result += 1;
        bstIterable.add(3,3);
        result += 3;
        bstIterable.add(9,9);
        result += 9;
        bstIterable.add(7,7);
        result += 7;
        bstIterable.add(8,8);
        result += 8;

        //Since the entire tree is going to be iterated through 8 times (8 nodes in the tree) and
        //Each node is also going to be added another 8 times this is the expected result
        //Could be simplified further to the sum of all nodes times 9.
        result = result * 8 + (1 * 8) + (2 * 8) + (3 * 8) + (5 * 8) + (6 * 8) + (7 * 8) + (8 * 8) + (9 * 8);

        int iterResult = 0;
        for(int a : bstIterable) {
            for (int b : bstIterable) {
                iterResult += a + b;
            }
        }
        assertEquals(iterResult, result);
    }
}
