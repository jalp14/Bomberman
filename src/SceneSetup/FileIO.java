/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SceneSetup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Allows to read and write game data from txt file
 * @author jalpd
 */
public class FileIO {

    private PrintWriter printWriter;
    private File saveFile;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private float volume;
    private float sfx;
    private String user;

    /**
     * Sets up the location of the file
     */
    public FileIO() {
        saveFile = new File("saveFile.txt");
    }

    /**
     *
     * @param username data to be stored in the file
     * @param volumeOffset data to be stored in the file
     * @param sfxOffset data to be stored in the file
     */
    public void saveToFile(String username, float volumeOffset, float sfxOffset) {

        if (username.isEmpty()) {
            username = "user";
        }
        try {
            fileWriter = new FileWriter(saveFile);
            fileWriter.write(username + "," + volumeOffset + "," + sfxOffset + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    /**
     * Reads data from file
     */
    public void readFromFile() {
        try {
            fileReader = new FileReader(saveFile);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String[] tokens = line.split(",");
            user = tokens[0];
            volume = Float.parseFloat(tokens[1]);
            sfx = Float.parseFloat(tokens[2]);
            System.out.println(user);
            System.out.println(volume);
            System.out.println(sfx);
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
    }

    /**
     * Encapsulation for private field
     * @return volume value from the file
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Encapsulation for private field
     * @return sfx value from the file
     */
    public float getSfx() {
        return sfx;
    }

    /**
     * Encapsulation for private field
     * @return user value from the file
     */
    public String getUser() {
        return user;
    }

}
