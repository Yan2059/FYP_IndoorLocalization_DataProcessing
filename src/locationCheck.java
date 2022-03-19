import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class locationCheck {
    private static dataPoint checkPoint; // the unknown point
    private static ArrayList<dataPoint> pointList = new ArrayList<>();

    static void readPoint() throws IOException {
        File filename = new File("./point.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        int lineNum = 0;

        checkPoint = new dataPoint();

        while (line != null) {
            lineNum++;
            if (lineNum >= 1) {
                String[] record = line.split(" ");
                checkPoint.addWifi(record[1], record[2]);
            }
            line = br.readLine();
        }
        checkPoint.sortByWifiID();
        br.close();
    }

    static private boolean sameAsLastDataPoint(position newPosition){
        if (pointList.size() == 0) return false;
        return pointList.get(pointList.size()-1).location.compareTo(newPosition);
    }

    static void readCSV() throws IOException {
        File filename = new File("./data.csv");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        br.readLine();
        int lineNum = 0;

        //to compare whether they are in same point
        //point to store when reading
        dataPoint currentPoint = new dataPoint();
        String line = br.readLine();

        while (line != null) {
            lineNum++;
            if (lineNum >= 1) {
                String splitLine[] = line.split(",");
                double x= Double.parseDouble(splitLine[0]);
                double y= Double.parseDouble(splitLine[1]);
                double z= Double.parseDouble(splitLine[2]);

                position newPosition = new position(x,y,z);

                if(!sameAsLastDataPoint(newPosition) && lineNum!=1){
                    currentPoint = new dataPoint(newPosition);
                    pointList.add(currentPoint);
                }
                currentPoint.addWifi(splitLine[3], splitLine[4]);
            }
            line = br.readLine();
        }
        br.close();

        for (dataPoint dp : pointList){
            dp.sortByWifiID();
            System.out.println("================================================================================");
            System.out.println(dp);
        }
    }

    public static void main(String[] args) throws IOException {
        readPoint();
        readCSV();
        System.out.println("Stored data read");

        int k = 1;
        double centroidX = 0, centroidY = 0, centroidZ = 0;
        pointList.sort(Comparator.comparingInt(p -> p.similarity(checkPoint)));

        for(int i = 0; i < k; i++){
            centroidX += pointList.get(i).location.x;
            centroidY += pointList.get(i).location.y;
            centroidZ += pointList.get(i).location.z;
        }

        centroidX/=k;
        centroidY/=k;
        centroidZ/=k;
        System.out.println("X coordinate: "+centroidX);
        System.out.println("Y coordinate: "+centroidY);
        System.out.println("Z coordinate: "+centroidZ);

    }

}
