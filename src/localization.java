import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;


public class localization {
    private static dataPoint checkPoint; // the unknown point
    private static ArrayList<dataPoint> pointList = new ArrayList<>();
    private static ArrayList<Double> errorDist = new ArrayList<>();
    private static ArrayList<Double> WerrorDist = new ArrayList<>();
    static position pos;
    static double error[] = new double [11];
    static double WeightError[] = new double [11];

    static void readPoint() throws IOException {
        File filename = new File("./testB.csv");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);

        int lineNum = 0;
        double x =  Double.parseDouble(br.readLine());
        double y =  Double.parseDouble(br.readLine());
        double z =  Double.parseDouble(br.readLine());
        pos = new position(x,y,z);
        String line = br.readLine();
        checkPoint = new dataPoint();


        while (line != null) {
            lineNum++;
            if (lineNum >= 1) {
                String[] record = line.split(",");
                checkPoint.addWifi(record[0], record[1]);
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
        File filename = new File("./2F_data.csv");
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
        }
    }

    public static void main(String[] args) throws IOException {
        readCSV();
        File file = new File("./testC.csv");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            int lineNum = 0;
            double x =  Double.parseDouble(br.readLine());
            double y =  Double.parseDouble(br.readLine());
            double z =  Double.parseDouble(br.readLine());
            pos = new position(x,y,z);
            String line = br.readLine();
            checkPoint = new dataPoint();

            while (line != null) {
                lineNum++;
                if (lineNum >= 1) {
                    String[] record = line.split(",");
                    checkPoint.addWifi(record[0], record[1]);
                }
                line = br.readLine();
            }
            checkPoint.sortByWifiID();
            br.close();
            int k=2;
                double centroidX = 0, centroidY = 0, centroidZ = 0;
                double WcentroidX = 0, WcentroidY = 0, WcentroidZ = 0;
                double denom=0;
                pointList.sort(Comparator.comparingInt(p -> p.distance(checkPoint)));

                for(int i = 0; i < k; i++){
                    double dist = pointList.get(i).distance(checkPoint)/10000;
                    centroidX += pointList.get(i).location.x;
                    centroidY += pointList.get(i).location.y;
                    centroidZ += pointList.get(i).location.z;
                    WcentroidX += pointList.get(i).location.x/dist;
                    WcentroidY += pointList.get(i).location.y/dist;
                    WcentroidZ += pointList.get(i).location.z/dist;
                    denom+=1/dist;
                    System.out.println(dist);
                }

                centroidX/=k;
                centroidY/=k;
                centroidZ/=k;
                WcentroidX/=denom;
                WcentroidY/=denom;
                WcentroidZ/=denom;
                System.out.println("k= "+k);
                System.out.println("X coordinate: "+centroidX);
                System.out.println("Y coordinate: "+centroidY);
                System.out.println("Z coordinate: "+centroidZ);
                System.out.println("k(weighted)= "+k);
                System.out.println("X coordinate(weighted): "+WcentroidX);
                System.out.println("Y coordinate(weighted): "+WcentroidY);
                System.out.println("Z coordinate(weighted): "+WcentroidZ);
                position predict = new position(centroidX,centroidY,centroidZ);
                position Wpredict = new position(WcentroidX,WcentroidY,WcentroidZ);
                System.out.println("Error: "+predict.distance(predict,pos)*1.2);
                System.out.println("Error(weighted): "+Wpredict.distance(Wpredict,pos)*1.2);

    }

}
