// Salih Erdem Kocak
// 26.03.2023
// Firstly I handled the data in the main function. Then I took the inputs, and find the path between to inputs. Then I printed the path. Then I made the animation.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Font;

public class MetroIstanbul {

    public static void removeAsterisks(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace("*", "");
        }
    }

    public static String removeAsterisksStr(String str) {

        str = str.replace("*", "");
        return  str;

    }

    public static ArrayList<String> returnPathBetweenTwoStations(String stat1, String stat2, int lineCode, String[][] metros) {
        ArrayList<String> path = new ArrayList<>();
        int station1 = placeFinder(stat1, metros, lineCode);
        int station2 = placeFinder(stat2, metros, lineCode);
        if(station1 > station2) {
            int difference = station1 - station2;
            for(int i = 0; i <= difference; i++){
                path.add(metros[lineCode][station1 - i]);
            }

        }else{
            int difference = station2 - station1;
            for(int i = 0; i <= difference; i++) {
                path.add(metros[lineCode][station1 + i]);
            }
        }
        return path;
    }

    public static int lineFinder(String str, String[][] mtrs) {
        for (int i=0; i < mtrs.length; i++){
            for (int j=0; j < mtrs[i].length; j++){
                if (mtrs[i][j].equals(str)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static ArrayList<String> breakpointFinder(String str, String[][] breakpoints) {
        ArrayList<String> breakpointOfALine = new ArrayList<>();

        for (int i=0; i < breakpoints.length; i++) {
            for (int j = 0; j < breakpoints[i].length; j++) {
                if (breakpoints[i][j].equals(str)) {
                    breakpointOfALine.add(breakpoints[i][0]);
                }
            }
        }
        return breakpointOfALine;
    }



    public static int placeFinder(String str, String[][] metros, int line) {
        for (int i=0; i < metros[line].length; i++){
            if (metros[line][i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean hasCommonElement(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        for (Integer element : list1) {
            if (list2.contains(element)) {
                return true;
            }
        }
        return false;
    }

    public static Integer theCommonElement(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        for (Integer element : list1) {
            if (list2.contains(element)) {
                return element;
            }
        }
        return -1;
    }




    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> arr) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            int current = arr.get(i);
            boolean found = false;
            for (int j = 0; j < result.size(); j++) {
                if (current == result.get(j)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(current);
            }
        }
        return result;
    }

    public static ArrayList<String> removeDuplicatesStr(ArrayList<String> arr) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            String current = arr.get(i);
            boolean found = false;
            for (int j = 0; j < result.size(); j++) {
                if (current.equals(result.get(j))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(current);
            }
        }
        return result;
    }

    public static ArrayList<Integer> linesFinder(String str, String[][] metros) {
        ArrayList<Integer> returningList = new ArrayList<>();

        for (int i=0; i < metros.length; i++){
            for (int j=0; j < metros[i].length; j++){
                if (metros[i][j].equals(str)) {
                    returningList.add(i);
                }
            }
        }

        return returningList;
    }

    public static ArrayList<String> finalFunction(String station1, String station2, String[][] metros, String[][] breakpoints, String[][] metroNames){
        ArrayList<Integer> startLines = linesFinder(station1, metros);
        ArrayList<Integer> endLines = linesFinder(station2, metros);
        if(hasCommonElement(startLines, endLines)){
            int commonLine = theCommonElement(startLines, endLines);
            return returnPathBetweenTwoStations(station1, station2, commonLine, metros);
        }

        else {
            String theFastestBreakpoint = fastestBreakpointFinder(startLines, endLines, metros, breakpoints, metroNames);
            ArrayList<Integer> breakpointsLines = linesFinder(theFastestBreakpoint, metros);
            int commonLine2 = theCommonElement(startLines, breakpointsLines);
            ArrayList<String> path = returnPathBetweenTwoStations(station1, theFastestBreakpoint, commonLine2, metros);
            path.addAll(finalFunction(theFastestBreakpoint, station2, metros, breakpoints, metroNames));
            return path;

        }
    }

    public static String fastestBreakpointFinder(ArrayList<Integer> startLines, ArrayList<Integer> endLines, String[][] metros, String[][] breakpoints, String[][] metroNames){
        ArrayList<String> lineNames = new ArrayList<>();
        ArrayList<String> breakpointList1 = new ArrayList<>();
        ArrayList<Integer> breakpointOrders = new ArrayList<>();


        for (Integer element : startLines){
            lineNames.add(metroNames[element][0]);
        }

        for (String element : lineNames){
            breakpointList1.addAll(breakpointFinder(element, breakpoints));
        }

        ArrayList<String> breakpointList2 = removeDuplicatesStr(breakpointList1);

        for (String element : breakpointList2){
            breakpointOrders.add(orderFinder(element, endLines, metros, breakpoints, metroNames));
        }

        int index = findMinIndex(breakpointOrders);
        return breakpointList2.get(index);

    }

    public static int orderFinder(String breakpoint, ArrayList<Integer> endLines, String[][] metros, String[][] breakpoints, String[][] metroNames){


        ArrayList <Integer> breakpointLines = linesFinder(breakpoint, metros);
        if (hasCommonElement(breakpointLines, endLines)){
            return 0;
        }
        ArrayList <Integer> oneStep = OneStepMultiLine(breakpoint, metros, breakpoints, metroNames);
        ArrayList <Integer> twoStep = nextStepFinder(oneStep, metroNames, breakpoints);
        ArrayList <Integer> threeStep = nextStepFinder(twoStep, metroNames, breakpoints);
        ArrayList <Integer> fourStep = nextStepFinder(threeStep, metroNames, breakpoints);
        ArrayList <Integer> fiveStep = nextStepFinder(fourStep, metroNames, breakpoints);
        ArrayList <Integer> sixStep = nextStepFinder(fiveStep, metroNames, breakpoints);
        ArrayList <Integer> sevenStep = nextStepFinder(sixStep, metroNames, breakpoints);
        ArrayList <Integer> eightStep = nextStepFinder(sevenStep, metroNames, breakpoints);
        ArrayList <Integer> nineStep = nextStepFinder(eightStep, metroNames, breakpoints);


        if (hasCommonElement(oneStep, endLines)){
            return 1;
        } else if (hasCommonElement(twoStep, endLines)) {
            return 2;
        } else if (hasCommonElement(threeStep, endLines)) {
            return 3;
        } else if (hasCommonElement(fourStep, endLines)) {
            return 4;
        } else if (hasCommonElement(fiveStep, endLines)) {
            return 5;
        } else if (hasCommonElement(sixStep, endLines)) {
            return 6;
        } else if (hasCommonElement(sevenStep, endLines)) {
            return 7;
        } else if (hasCommonElement(eightStep, endLines)) {
            return 8;
        } else if (hasCommonElement(nineStep, endLines)){
            return 9;
        } else {
            return 10;
        }


    }

    public static ArrayList <Integer> OneStepMultiLine(String breakpoint, String[][] metros, String[][] breakpoints, String[][] metroNames){
        ArrayList<Integer> breakpointsLines = linesFinder(breakpoint, metros);
        ArrayList<Integer> breakpointsOneStep = new ArrayList<>();
        for (Integer element : breakpointsLines){
            breakpointsOneStep.addAll(oneStepFinder(element, metroNames, breakpoints));
        }
        return removeDuplicates(breakpointsOneStep);
    }



    public static ArrayList<Integer> oneStepFinder(int start, String[][] metroNames, String[][] breakpoints) {
        String nameOfTheStart = metroNames[start][0];
        ArrayList<String> breakpointsOfALine = breakpointFinder(nameOfTheStart, breakpoints);
        ArrayList<Integer> returningList = new ArrayList<>();
        for (String element : breakpointsOfALine){
            for (int i = 0; i < breakpoints.length; i++){
                if (element.equals(breakpoints[i][0])){
                    for (int j = 1; j < breakpoints[i].length; j ++){
                        if(!returningList.contains(metroPlaceFinder(breakpoints[i][j], metroNames))){
                            returningList.add(metroPlaceFinder(breakpoints[i][j], metroNames));
                        }
                    }
                }
            }
        }

        return returningList;
    }

    public static ArrayList<Integer> nextStepFinder(ArrayList<Integer> oneStep, String[][] metroNames, String[][] breakpoints) {
        ArrayList<Integer> returningList = new ArrayList<>();
        for(int i = 0; i < oneStep.size(); i++){
            int temp = oneStep.get(i);
            returningList.addAll(oneStepFinder(temp, metroNames, breakpoints));
        }
        return removeDuplicates(returningList);
    }


    public static int findMinIndex(ArrayList<Integer> list) {
        int minIndex = 0;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(minIndex)) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static int metroPlaceFinder(String str, String[][] metroNames) {
        for (int i=0; i < metroNames.length; i++){
            if (str.equals(metroNames[i][0])){
                return i;
            }
        }
        return -1;
    }

    public static String getCoord(String station, String[][] forDraw){
        for (int m = 0; m < forDraw.length; m ++){
            for (int n = 0; n < forDraw[m].length; n ++){
                if (station.equals(forDraw[m][n].replace("*", ""))){
                    return forDraw[m][n + 1];
                }
            }
        }
        return "";
    }

    public static void printMap(String[][] forDraw){



        StdDraw.picture(512, 241, "background.jpg");


        for(int i = 0; i < 10; i++){
            ArrayList<Integer> partsForDraw = new ArrayList<>();
            String penColor = forDraw[2 * i][1];
            String[] penColorArray = penColor.split(",");

            int r = Integer.parseInt(penColorArray[0]);
            int g = Integer.parseInt(penColorArray[1]);
            int b = Integer.parseInt(penColorArray[2]);
            StdDraw.setPenColor(r, g, b);



            for(int j = 0; j < forDraw[2 * i + 1].length / 2.0; j++){
                String coords = (forDraw[2 * i + 1][2 * j + 1]);
                String[] coordsAsArray = coords.split(",");
                int xCoord = Integer.parseInt(coordsAsArray[0]);
                int yCoord = Integer.parseInt(coordsAsArray[1]);
                partsForDraw.add(xCoord);
                partsForDraw.add(yCoord);
            }

            StdDraw.setPenRadius(0.012);
            for(int k = 0; k < partsForDraw.size() / 2.0 - 1; k++){
                int x0 = partsForDraw.get(2 * k);
                int y0 = partsForDraw.get(2 * k + 1);
                int x1 = partsForDraw.get(2 * k + 2);
                int y1 = partsForDraw.get(2 * k + 3);

                StdDraw.line(x0, y0, x1, y1);
            }
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.WHITE);
            for(int k = 0; k < partsForDraw.size() / 2.0; k++){
                int x0 = partsForDraw.get(2 * k);
                int y0 = partsForDraw.get(2 * k + 1);

                StdDraw.point(x0, y0);
            }

            StdDraw.setFont(new Font("Helvetica", Font.BOLD, 8));
            StdDraw.setPenColor(StdDraw.BLACK);
            for (int k = 0; k < forDraw[2 * i + 1].length / 2.0; k ++){
                String text = forDraw[2 * i + 1][2 * k];
                int x0 = partsForDraw.get(2 * k);
                int y0 = partsForDraw.get(2 * k + 1);
                if (text.contains("*")){
                    text = removeAsterisksStr(text);
                    StdDraw.textLeft(x0 - 5, y0 + 5, text);
                }

            }

        }
    }

    public static boolean isConnected(int start, int end, String [][] metroNames, String[][] breakpoints){
        ArrayList<Integer> oneStep = oneStepFinder(start, metroNames, breakpoints);
        ArrayList <Integer> twoStep = nextStepFinder(oneStep, metroNames, breakpoints);
        ArrayList <Integer> threeStep = nextStepFinder(twoStep, metroNames, breakpoints);
        ArrayList <Integer> fourStep = nextStepFinder(threeStep, metroNames, breakpoints);
        ArrayList <Integer> fiveStep = nextStepFinder(fourStep, metroNames, breakpoints);
        ArrayList <Integer> sixStep = nextStepFinder(fiveStep, metroNames, breakpoints);
        ArrayList <Integer> sevenStep = nextStepFinder(sixStep, metroNames, breakpoints);
        ArrayList <Integer> eightStep = nextStepFinder(sevenStep, metroNames, breakpoints);
        ArrayList <Integer> nineStep = nextStepFinder(eightStep, metroNames, breakpoints);
        ArrayList <Integer> tenStep = nextStepFinder(nineStep, metroNames, breakpoints);
        ArrayList <Integer> endList = new ArrayList<>();
        endList.add(end);
        if(hasCommonElement(tenStep, endList)){
            return true;
        }
        else{
            return false;
        }


    }



    public static void main(String[] args) {

        try {
            File file = new File("coordinates.txt");// Document Handling
            Scanner scanner = new Scanner(file);
            Scanner input = new java.util.Scanner(System.in);
            String[][] metros = new String[10][];
            String[][] metroNames = new String[10][10];
            String[][] breakpoints = new String[7][];
            String[][] forDraw = new String [20][];

            for (int i = 0; i < 10; i++) {
                String line = scanner.nextLine();
                String line2 = scanner.nextLine();
                String[] parts = line2.split(" ");
                String[] parts2 = new String[parts.length/2];
                for (int j = 0; j < (parts.length)/2; j++){
                    parts2[j] = parts[2*j];
                }

                removeAsterisks(parts2);
                metros[i] = parts2;

                String[] parts3 = line.split(" ");
                metroNames[i] = parts3;

                forDraw[2 * i] = line.split(" ");
                forDraw[2 * i + 1] = line2.split(" ");

            }

            for (int k = 0; k < 7; k++){
                String line3 = scanner.nextLine();
                String[] parts4 = line3.split(" ");
                breakpoints[k] = parts4;
            }


            String startStation = input.next(); // taking inputs
            String endStation = input.next();

            int startMetroLine = lineFinder(startStation, metros);
            int endMetroLine = lineFinder(endStation, metros);



            if (startMetroLine == -1 || endMetroLine == -1){   // first case
                System.out.println("The station names provided are not present in this map.");

            } else if (!isConnected(startMetroLine, endMetroLine, metroNames, breakpoints)) {   // second case
                System.out.println("These two stations are not connected");
            }else {   // third case
                ArrayList<String> finalRoute = removeDuplicatesStr(finalFunction(startStation, endStation, metros, breakpoints, metroNames));
                for(int d = 0; d < finalRoute.size(); d++){
                    System.out.println(finalRoute.get(d));
                }


                StdDraw.setCanvasSize(1024, 482);
                StdDraw.setScale(0, 1024);
                StdDraw.setYscale(0, 482);
                StdDraw.enableDoubleBuffering();

                int count = 0;
                while (count < finalRoute.size()){
                    printMap(forDraw);
                    for (int s = 0; s < count + 1; s++){
                        String currentStation = finalRoute.get(s);
                        String currentStationCoord = getCoord(currentStation, forDraw);
                        String[] currentStationCoordArray = currentStationCoord.split(",");
                        int x0 = Integer.parseInt(currentStationCoordArray[0]);
                        int y0 = Integer.parseInt(currentStationCoordArray[1]);
                        if (s < count){
                            StdDraw.setPenRadius(0.01);
                            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                            StdDraw.point(x0, y0);
                        }
                        else {
                            StdDraw.setPenRadius(0.02);
                            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                            StdDraw.point(x0, y0);
                        }

                    }

                    StdDraw.show();
                    StdDraw.pause(300);
                    count += 1;
                    StdDraw.clear(StdDraw.WHITE);

                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

    }
}

