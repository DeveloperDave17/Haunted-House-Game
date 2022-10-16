package com.edu.hauntedhouse;

public class Item {
    private final String name;
    private final String desc;
    private ItemActions[] actions = new ItemActions[3];
    private boolean itemBroken = false;

    /**
     * A group of CONSTANTS representing item actions.
     */
    public enum ItemActions{
        POSSESS,
        SHAKE,
        THROW
    }

    /**
     * Creates an item object.
     * @param name Item's name.
     * @param desc Item's description.
     */
    public Item(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    /**
     * Adds an item action to the actions array if adding the action is supported. Calls the
     * supportsAddingAction method to check if the action can be added.
     * @param action Item action.
     */
    public void addItemAction(ItemActions action){
        if(supportsAddingAction(action)){
            for(int i = 0; i < 3; i++){
                if(actions[i] == null){
                    actions[i] = action;
                    break;
                }
            }
        }
    }

    /**
     * Checks to see if an action can be done to an item.
     * @param action Requested item action.
     * @return Returns a boolean value based on whether an item action can be done (true) or not (false).
     */
    public boolean supportsAction(ItemActions action){
        if(itemBroken){
            return false;
        }
        for(int i = 0; i < 3; i++){
            if(actions[i] == action){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if an actions can be added to the action array. (No Duplicates)
     * @param action The specific action to be added.
     * @return Returns true if an item action does not already exist in the item action array.
     */
    private boolean supportsAddingAction(ItemActions action){
        for(int i = 0; i < 3; i++){
            if(actions[i] == action){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        return name + " : " + desc + (itemBroken ? " (broken)" : " (not broken)");}

    /**
     * breakItem updates the itemBroken value to true.
     */
    public void breakItem(){itemBroken = true;}

    /**
     * Checks if this item is broken.
     * @return A boolean value based upon whether the item is broken.
     */
    public boolean isItemBroken(){
        return itemBroken;
    }

    public String getItemName(){return name;}
}
