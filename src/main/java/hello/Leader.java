package hello;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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

    public boolean parseAndAdd(String p) {
        String[] args = p.split("\\s+");
        if (args.length <= 1 || args.length % 2 == 0) {
            return false;
        }
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
        return true;
    }

    private void writeToFile() {
        try {
            FileWriter w = new FileWriter("van.txt", true);
            for (Map.Entry gameElem : gameData.entrySet()) {
                String gameName = (String)gameElem.getKey();
                Map<String, Integer> players = (HashMap<String, Integer>)gameElem.getValue();
                for (Map.Entry playerElem : players.entrySet()) {
                    String player = (String)playerElem.getKey();
                    int score = (int)playerElem.getValue();
                    int lineExists = fileContainsPlayerAndGame(player, gameName);
                    if (lineExists > 0) {
                        Path path = Paths.get("van.txt");
                        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
                        lines.set(lineExists - 1, gameName + " " + player + " " + Integer.toString(score) + "\n");
                        Files.write(path, lines, StandardCharsets.UTF_8);
                    } else {
                        w.write(gameName + " " + player + " " + Integer.toString(score) + "\n");
                    }
                }
            }
            w.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private int fileContainsPlayerAndGame(String player, String game) {
        try {
            FileReader fr = new FileReader("van.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int lineNo = 0;
            while(line != null) {
                lineNo++;
                if (line.contains(player) && line.contains(game)) {
                    return lineNo;
                }
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return -1;
    }

    public String getLeaderBoard() {
        writeToFile();
        try {
            FileReader fr = new FileReader("van.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String res = "";
            ArrayList<String> rows = new ArrayList<String>();
            while(line != null) {
                rows.add(line);
                line = br.readLine();
            }
            Collections.sort(rows, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    // provide your comparison logic here
                    int o1Score = Integer.parseInt(o1.split("\\s+")[2]);
                    int o2Score = Integer.parseInt(o2.split("\\s+")[2]);
                    return o1Score - o2Score;
                }
            });
            for (int i = 0; i < rows.size(); i++) {
                res += (rows.get(i) + "\n");
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

