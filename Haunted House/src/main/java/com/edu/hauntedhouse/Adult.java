package com.edu.hauntedhouse;

import java.util.ArrayList;
import java.util.Random;

public class Adult extends NPC{

    /**
     * Creates an instance of the adult class and sends the name and description to the npc constructor.
     * @param adultName Adult's name.
     * @param adultDesc Adult's description.
     */
    public Adult(String adultName, String adultDesc){
        super(adultName, adultDesc);
    }
}