/**
 * Program plays the RADEGAST's game
 * @author Salih Erdem Kocak, Student ID: 2022400324
 * @since Date: 10.05.2023
 * */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class TerrainWaterFounder {

    public static int maxValueOfTerrain(int[][] terrain){ // function that returns maximum height in a terrain
        int maxValue = 0;
        for (int i = 0; i < terrain.length; i++){
            for (int j = 0; j < terrain[0].length; j++){
                if (terrain[i][j] > maxValue){
                    maxValue = terrain[i][j];
                }
            }
        }
        return maxValue;
    }

    public static boolean isWater(int[][][] terrain3d, int i, int j, int k){ // function that determines wheter a given coordinate has water
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1} }; // right, left, down, up
        int m = terrain3d.length;
        int n = terrain3d[0].length;

        if(i == 0 || i == m-1 || j == 0 || j == n-1){
            return false;
        }

        boolean[][] visited = new boolean[m][n];
        ArrayList<int[]> queue = new ArrayList<>();
        queue.add(new int[]{i, j});
        visited[i][j] = true;
        int currentIndex = 0;

        while (currentIndex < queue.size()) {
            int[] current = queue.get(currentIndex);
            int x = current[0];
            int y = current[1];
            currentIndex++;

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    if (terrain3d[newX][newY][k] == 0 && !visited[newX][newY]) {
                        if (newX == 0 || newX == m - 1 || newY == 0 || newY == n - 1) {
                            return false; // found a path to the outside
                        }
                        queue.add(new int[]{newX, newY});
                        visited[newX][newY] = true;
                    }
                }
            }
        }
        return true; // no path to the outside found
    }

    public static double calculateScore(int[][] terrain){  // function that calculates the score and printing the lakes
        int maxValue = maxValueOfTerrain(terrain);
        int[][][] terrain3d = new int[terrain.length][terrain[0].length][maxValue];
        for (int i = 0; i < terrain.length; i++){
            for (int j = 0; j < terrain[0].length; j++){
                for(int k = 0; k < terrain[i][j]; k++){
                    terrain3d[i][j][k] = 1;
                }
            }
        }
        for (int i = 0; i < terrain.length; i++){
            for (int j = 0; j < terrain[0].length; j++){
                for(int k = 0; k < terrain3d[0][0].length; k++){
                    if (terrain3d[i][j][k] == 0){
                        if (isWater(terrain3d, i, j, k)){
                            terrain3d[i][j][k] = -1;
                        }
                    }
                }
            }
        }

        String[][] terrainWithLakes = terrainLakePrinter(terrain3d, terrain);
        String[] bigNotation = generateNotationArrayBig();

        double count = 0;
        for (String index : bigNotation){
            double temp = 0;
            for (int i = 0; i < terrainWithLakes.length; i++){
                for (int j = 0; j < terrainWithLakes[0].length; j++){
                    if (terrainWithLakes[i][j].equals(index)){
                        for (int k = 0; k < terrain3d[0][0].length; k++){
                            if(terrain3d[i][j][k] == -1){
                                temp += 1;
                            }
                        }
                    }
                }
            }
            count += Math.sqrt(temp);
        }




        return count;
    }

    public static String[][] terrainLakePrinter(int[][][] terrain3d, int[][] terrain){ // function that returns the terrain with lakes and also prints it

        for (int i = 0; i < terrain3d.length; i++){
            for (int j = 0; j < terrain3d[0].length; j++){
                for (int k = 0; k < terrain3d[0][0].length; k++){
                    if (terrain3d[i][j][k] == -1){
                        terrain[i][j] = -1;
                    }
                }
            }
        }

        String[][] terrainStr = new String[terrain.length][terrain[0].length];

        for(int i = 0; i < terrain.length; i++){
            for (int j = 0; j < terrain[0].length; j++){
                terrainStr[i][j] = Integer.toString(terrain[i][j]);
            }
        }

        String[] bigNotation = generateNotationArrayBig();
        int count = 0;
        for(int i = 0; i < terrainStr.length; i++){
            for(int j = 0; j < terrainStr[0].length; j++){
                if (terrainStr[i][j].equals("-1")){
                    terrainStr[i][j] = bigNotation[count];
                    lakeBuilder(terrainStr, i , j);
                    count += 1;
                }
            }
        }

        terrainPrinterForStringArray(terrainStr);
        return terrainStr;

    }

    public static void lakeBuilder(String[][] terrainStr, int i, int j){ // function that construct the lakes
        String initialValue = terrainStr[i][j];

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int newX = i + dir[0];
            int newY = j + dir[1];

            if (newX >= 0 && newX < terrainStr.length && newY >= 0 && newY < terrainStr[newX].length && terrainStr[newX][newY].equals("-1")) {
                terrainStr[newX][newY] = initialValue;
                lakeBuilder(terrainStr, newX, newY);
            }
        }
    }

    public static void terrainPrinter(int[][] terrain){ // function that prints the terrain when the terrain consists of Integers
        int n = terrain.length;
        int m = terrain[0].length;
        for(int i = 0; i < n; i++){
            if (i > 9) {
                System.out.print(" " + i);
            } else {
                System.out.print("  " + i);
            }
            for(int j = 0; j < m; j++){
                if (terrain[i][j] > 9) {
                    System.out.print(" " + terrain[i][j]);
                } else {
                    System.out.print("  " + terrain[i][j]);
                }

            }
            System.out.println("");
        }
        String[] newArray = generateNotationArray(m);
        for (int i = 0; i < newArray.length; i++){
            if (newArray[i].length() == 1) {
                System.out.print("  " + newArray[i]);
            } else {
                System.out.print(" " + newArray[i]);
            }
        }
        System.out.println("");
    }

    public static void terrainPrinterForStringArray(String[][] terrain){ // function that prints the terrain when the terrain consists of Strings
        int n = terrain.length;
        int m = terrain[0].length;
        for(int i = 0; i < n; i++){
            if (i > 9) {
                System.out.print(" " + i);
            } else {
                System.out.print("  " + i);
            }
            for(int j = 0; j < m; j++){
                if (terrain[i][j].length() > 1) {
                    System.out.print(" " + terrain[i][j]);
                } else {
                    System.out.print("  " + terrain[i][j]);
                }

            }
            System.out.println("");
        }
        String[] newArray = generateNotationArray(m);
        for (int i = 0; i < newArray.length; i++){
            if (newArray[i].length() == 1) {
                System.out.print("  " + newArray[i]);
            } else {
                System.out.print(" " + newArray[i]);
            }
        }
        System.out.println("");
    }

    public static String[] generateNotationArray(int number){ // function creating notation array for a given number
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String[] returningArray = new String[number + 1];
        returningArray[0] = " ";
        for (int i = 0; i < number; i++){
            if (i / 26 < 1){
                returningArray[i+1] = String.valueOf(alphabet[i%26]);
            } else {
                int divident = i / 26;
                int modulo = i % 26;
                String str1 = String.valueOf(alphabet[divident - 1]);
                String str2 = String.valueOf(alphabet[modulo]);
                returningArray[i+1] = str1 + str2;
            }
        }
        return returningArray;
    }

    public static String[] generateNotationArrayBig(){ // function creating notation array for upper chars
        int number = 26*27;
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String[] returningArray = new String[number];

        for (int i = 0; i < number; i++){
            if (i / 26 < 1){
                returningArray[i] = String.valueOf(alphabet[i%26]);
            } else {
                int divident = i / 26;
                int modulo = i % 26;
                String str1 = String.valueOf(alphabet[divident - 1]);
                String str2 = String.valueOf(alphabet[modulo]);
                returningArray[i] = str1 + str2;
            }
        }
        return returningArray;
    }

    public static String[] splitString(String input) { // function that splits the input string into the parts
        StringBuilder letters = new StringBuilder();
        StringBuilder digits = new StringBuilder();
        StringBuilder elseGuys = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (Character.isLetter(c)) {
                letters.append(c);
            } else if (Character.isDigit(c)) {
                digits.append(c);
            } else {
                elseGuys.append(c);
            }
        }

        return new String[] {letters.toString(), digits.toString(), elseGuys.toString()};
    }

    public static int findIndex(String[] arr, String target) { // function that finds index of a given element in an array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) { // main function
        File file = new File("input.txt");
        try {
            Scanner scanner = new Scanner(file);
            Scanner input = new Scanner(System.in);
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] terrain = new int[n][m];
            String empty = scanner.nextLine();
            String[] notationArray = generateNotationArray(m);
            for (int i = 0; i < n; i++){
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                int[] parts2 = new int[parts.length];
                for(int j = 0; j < m; j++){
                    parts2[j] = Integer.parseInt(parts[j]);
                }
                terrain[i] = parts2;
            }




            for (int k = 0; k < 10; k++){
                terrainPrinter(terrain);
                if(k>0){
                    System.out.println("---------------");
                }

                while (true) {
                    System.out.print("Add stone " + (k+1) + " / 10 to coordinate:");
                    String userInput = input.nextLine();
                    String[] userInputString = splitString(userInput);
                    String letters = userInputString[0];
                    int digits = 0;
                    try {
                        digits = Integer.parseInt(userInputString[1]);

                    } catch (NumberFormatException e) {
                        digits = -5;
                    }

                    int intLetters = findIndex(notationArray, letters) - 1;
                    String elseGuys = userInputString[2];

                    if(digits < n && digits >= 0 && intLetters < m && intLetters >= 0 && elseGuys.length() == 0){
                        terrain[digits][intLetters] += 1;
                        break;
                    }
                    System.out.println("Not a valid step!");

                }

            }
            for(int i = 0; i < n; i++){
                if (i > 9) {
                    System.out.print(" " + i);
                } else {
                    System.out.print("  " + i);
                }
                for(int j = 0; j < m; j++){
                    if (terrain[i][j] > 9) {
                        System.out.print(" " + terrain[i][j]);
                    } else {
                        System.out.print("  " + terrain[i][j]);
                    }

                }
                System.out.println("");
            }
            String[] newArray = generateNotationArray(m);
            for (int i = 0; i < newArray.length; i++){
                if (newArray[i].length() == 1) {
                    System.out.print("  " + newArray[i]);
                } else {
                    System.out.print(" " + newArray[i]);
                }
            }
            System.out.println();
            System.out.println("---------------");
            double score = calculateScore(terrain);
            System.out.printf("Final Score: %.2f", score);

        } catch (FileNotFoundException e) {
        System.out.println("File not found.");
        }

    }
}
