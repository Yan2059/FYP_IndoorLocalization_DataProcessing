import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class errorAnalysis {
    private static dataPoint checkPoint; // the unknown point
    private static ArrayList<dataPoint> pointList = new ArrayList<>();
    static position pos;
    static double error[] = new double [11];
    static double WeightError[] = new double [11];
    static int wrongFloor[] = new int [11];
    static int n = 0;
    static File testFolder = new File("./test_23F_no13");//the testing point folder path
    static File dbFile = new File("./db_no13.csv");//file path of the database csv file

    static private boolean sameAsLastDataPoint(position newPosition){
        if (pointList.size() == 0) return false;
        return pointList.get(pointList.size()-1).location.compareTo(newPosition);
    }

    static void readCSV() throws IOException {

        InputStreamReader reader = new InputStreamReader(new FileInputStream(dbFile));
        BufferedReader br = new BufferedReader(reader);
        br.readLine();
        int lineNum = 0;

        //to compare whether they are in same point
        //point to store when reading
        dataPoint currentPoint = new dataPoint();
        String line = br.readLine();
        String lastFile="";

        while (line != null) {
            lineNum++;
            if (lineNum >= 1) {
                String splitLine[] = line.split(",");
                double x= Double.parseDouble(splitLine[0]);
                double y= Double.parseDouble(splitLine[1]);
                double z= Double.parseDouble(splitLine[2]);
                String newName = splitLine[5];
                position newPosition = new position(x,y,z);

                if(!lastFile.equals(newName) && lineNum!=1){
                    currentPoint = new dataPoint(newPosition);
                    lastFile = newName;
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
        File[] folder = testFolder.listFiles();
        int misMatch=0;//counter of number of mismatched floor
        int numberOfPrediction=0;//counter of total number of compare
        for(File file: folder){
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

            for(int k=1; k<11;k++){
                double centroidX = 0, centroidY = 0, centroidZ = 0;
                double WcentroidX = 0, WcentroidY = 0, WcentroidZ = 0;
                double denom=0;
                pointList.sort(Comparator.comparingInt(p -> p.distance(checkPoint)));

                for(int i = 0; i < k; i++){
                    double dist=0;
                    //handling small distance
                    dist = pointList.get(i).distance(checkPoint)/10000;
                    if(dist<1){
                        dist=1;
                    }
                    centroidX += pointList.get(i).location.x;
                    centroidY += pointList.get(i).location.y;
                    centroidZ += pointList.get(i).location.z;
                    WcentroidX += pointList.get(i).location.x/dist;
                    WcentroidY += pointList.get(i).location.y/dist;
                    WcentroidZ += pointList.get(i).location.z/dist;
                    denom+=1/dist;
                }

                centroidX/=k;
                centroidY/=k;
                centroidZ/=k;
                centroidZ=Math.round(centroidZ);
                WcentroidX/=denom;
                WcentroidY/=denom;
                WcentroidZ/=denom;
                WcentroidZ=Math.round(WcentroidZ);
                System.out.println("k= "+k);
                System.out.println("============= Labeled information ==============");
                System.out.println("X coordinate: "+x);
                System.out.println("Y coordinate: "+y);
                System.out.println("Z coordinate: "+z);
                System.out.println("============= Predicted information (KNN)==============");
                System.out.println("X coordinate: "+centroidX);
                System.out.println("Y coordinate: "+centroidY);
                System.out.println("Z coordinate: "+centroidZ);
                position predict = new position(centroidX,centroidY,centroidZ);
                System.out.println("Error: "+predict.distance(predict,pos));
                System.out.println("============= Predicted information (WKNN)==============");
                System.out.println("X coordinate: "+WcentroidX);
                System.out.println("Y coordinate: "+WcentroidY);
                System.out.println("Z coordinate: "+WcentroidZ);
                position Wpredict = new position(WcentroidX,WcentroidY,WcentroidZ);
                System.out.println("Error(weighted): "+Wpredict.distance(Wpredict,pos));
                if(centroidZ!=pos.z&&k==1){
                    System.out.println("FLOOR MISMATCHED!");
                    misMatch++;
                    wrongFloor[k]++;
                }
                error[k]+=predict.distance(predict,pos);
                WeightError[k]+=Wpredict.distance(Wpredict,pos);
                numberOfPrediction++;
            }
            System.out.println("///////////////Next prediction/////////////////");
            n++;
        }
        for(int i=1;i<error.length;i++){
            double avg = error[i]/n;
            System.out.println("Mean error distance of k= "+(i)+" : "+avg);
        }
        System.out.println();
        for(int i=1;i<error.length;i++){
            double Wavg = WeightError[i]/n;
            System.out.println("Mean error distance (weighted) of k= "+(i)+" : "+Wavg);
        }
        for(int i=1;i<wrongFloor.length;i++){
            System.out.println("Number of mismatched floor in k= "+(i)+" : "+wrongFloor[i]);
        }
    System.out.println(misMatch+" of prediction have floor mismatched, out of "+numberOfPrediction+" predictions");
    }

}
