import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

class appendWifiList {

    private static ArrayList<Wifi> wifiList = new ArrayList<>();
    private static double x,y,z;
    static File dbFile =new File("./db_no13.csv"); //name of database file to be generate or append
    static File pointFolder = new File("./input_no13"); //name of folder storing the datapoint files

    static void readData(File file) throws IOException{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            x =  Double.parseDouble(br.readLine());
            y =  Double.parseDouble(br.readLine());
            z =  Double.parseDouble(br.readLine());
            String line = br.readLine();
            int lineNum = 0;

            while (line != null) {
                lineNum++;
                if (lineNum >= 1) {
                    String[] record = line.split(",");
                    Wifi newWifi = new Wifi(record[0], record[1]);
                    wifiList.add(newWifi);
                }
                line = br.readLine();
            }
            br.close();
    }

    public static void main(String[] args) throws IOException{
        File[] folder = pointFolder.listFiles();
        for(File dPfile: folder){
            String name = dPfile.getName();
            readData(dPfile);
            Collections.sort(wifiList, Collections.reverseOrder());
            StringBuilder sb = new StringBuilder();
            if(!dbFile.exists()){
                dbFile.createNewFile();
                sb.append("x coordinate");
                sb.append(',');
                sb.append("y coordinate");
                sb.append(',');
                sb.append("z coordinate");
                sb.append(',');
                sb.append("Wifi ID");
                sb.append(',');
                sb.append("Wifi RSS");
                sb.append(',');
                sb.append("File name");
                sb.append('\n');
            }
            for(int i=0;i<wifiList.size();i++){
                sb.append(x);
                sb.append(',');
                sb.append(y);
                sb.append(',');
                sb.append(z);
                sb.append(',');
                sb.append(wifiList.get(i).getID());
                sb.append(',');
                sb.append(wifiList.get(i).getStrength());
                sb.append(',');
                sb.append(name);
                sb.append('\n');
            }
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(dbFile,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            //Closing BufferedWriter Stream
            bw.close();
            wifiList.clear();
        }


    }
}



