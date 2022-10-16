package com.edu.hauntedhouse;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList<Room> rooms;
        String fileName;
        SAXParserFactory spf = SAXParserFactory.newInstance();
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

        try {
            System.out.print("What is the file name? ");
            fileName = sc.nextLine();
            InputStream input = new FileInputStream(fileName);
            SAXParser saxParser = spf.newSAXParser();
            RoomXMLParser sxp = new RoomXMLParser();
            saxParser.parse(input, sxp);
            rooms = sxp.getRooms();
        } catch (ParserConfigurationException | SAXException |
                 IOException e) {
            throw new RuntimeException(e);
        }

        for (Room room : rooms) {
            if (room.getPlayer() != null) {
                room.getPlayer().Play(sc);
                break;
            }
        }
    }
}
