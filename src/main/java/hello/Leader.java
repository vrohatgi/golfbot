package hello;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.Test;


/*
 Leader contains information
 */
public class Leader {
    // map of game names to players and scores for each game
    public Map<String, Map<String, Integer>> gameData = new HashMap<String, Map<String, Integer>>();


    public Leader() {
        try {
            File f = new File("van.txt");
            boolean exists = f.exists();
            if (exists) {
                return;
            }
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void parseAndAdd(String p) {
        String[] args = p.split("\\s+");
        String name = args[0];
        Map<String, Integer> playerData;
        if (gameData.containsKey(name)) {
            // existing map of player names to scores
            playerData = gameData.get(name);
            for (int i = 1; i < args.length; i += 2) {
                String player = args[i];
                int newScore = Integer.parseInt(args[i+1]);
                int prevScore = 0;
                if (playerData.containsKey(player)) {
                    prevScore = playerData.get(player);
                }
                playerData.put(player, newScore+prevScore);
            }
        } else {
            // new map of player names to scores
            playerData = new HashMap<String, Integer>();
            for (int i = 1; i < args.length; i += 2) {
                playerData.put(args[i], Integer.parseInt(args[i + 1]));
            }
        }
        gameData.put(name, playerData);
        try {
            FileWriter w = new FileWriter("van.txt", true);
            for (Map.Entry gameElem : gameData.entrySet()) {
                String gameName = (String)gameElem.getKey();
                Map<String, Integer> players = (HashMap<String, Integer>)gameElem.getValue();
                for (Map.Entry playerElem : players.entrySet()) {
                    String player = (String)playerElem.getKey();
                    int score = (int)playerElem.getValue();
                    w.write(gameName + " " + player + " " + Integer.toString(score) + "\n");
                }
            }
            w.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getLeaderBoard() {
        try {
            FileReader fr = new FileReader("gamebot/van.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String res = "";
            while(line != null) {
                System.out.println(line);
                res += line;
                line = br.readLine();
            }
            br.close();
            return res;
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "failed";
        }
    }
}
