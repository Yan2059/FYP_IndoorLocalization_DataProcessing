import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class locationCheck {
    private static dataPoint checkPoint; //point input
    private static ArrayList<dataPoint> pointList = new ArrayList<>();
    static void readPoint() throws IOException {
        ArrayList<Wifi> wifiList = new ArrayList<>();
        ArrayList<position> posList = new ArrayList<>();
        File filename = new File("./point.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        double x =  Double.parseDouble(br.readLine());
        double y =  Double.parseDouble(br.readLine());
        double z =  Double.parseDouble(br.readLine());
        position pos = new position(x,y,z);
        String line = br.readLine();
        int lineNum = 0;

        while (line != null) {
            lineNum++;
            if (lineNum >= 1) {
                String[] record = line.split(" ");
                Wifi newWifi = new Wifi(record[1], record[2]);
                wifiList.add(newWifi);
                posList.add(pos);
            }
            line = br.readLine();
        }
        br.close();
        checkPoint = new dataPoint(pos,wifiList);
    }
    static void readCSV() throws IOException {
        Scanner sc = new Scanner(new File("./data.csv"));
        //parsing a CSV file into the constructor of Scanner class
        sc.useDelimiter(",");
        //setting comma as delimiter pattern
        while (sc.hasNext()) {
            System.out.print(sc.next());
        }
        sc.close();
    }
    /*
    public static void main(String[] args) throws IOException{
        compare checkpoint and pointList
                sort strongest 5
                calculate centroid

    }
    */

}
