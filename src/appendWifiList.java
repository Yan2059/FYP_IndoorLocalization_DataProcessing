import java.util.ArrayList;
import java.io.*;
import java.util.Collections;


class appendWifiList {

    private static ArrayList<Wifi> wifiList = new ArrayList<>();
    private static double x,y,z;

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
                    if(Integer.parseInt(record[1])>-80)
                    wifiList.add(newWifi);
                }
                line = br.readLine();
            }
            br.close();
    }

    public static void main(String[] args) throws IOException{
        File filename = new File("./input");
        File[] folder = filename.listFiles();
        for(File dPfile: folder){
            readData(dPfile);
            Collections.sort(wifiList, Collections.reverseOrder());
            for(int i = 0; i < wifiList.size()&&i<10; i++){
                System.out.println(wifiList.get(i));
            }
            File file =new File("./2F_data_80.csv");
            StringBuilder sb = new StringBuilder();
            if(!file.exists()){
                file.createNewFile();
                sb.append("x coordinate");
                sb.append(',');
                sb.append("y coordinate");
                sb.append(',');
                sb.append("z coordinate");
                sb.append(',');
                sb.append("Wifi ID");
                sb.append(',');
                sb.append("Wifi RSS");
                sb.append('\n');
            }
            for(int i=0;i<wifiList.size()&&i<10;i++){
                sb.append(x);
                sb.append(',');
                sb.append(y);
                sb.append(',');
                sb.append(z);
                sb.append(',');
                sb.append(wifiList.get(i).getId());
                sb.append(',');
                sb.append(wifiList.get(i).getStrength());
                sb.append('\n');
            }
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            //Closing BufferedWriter Stream
            bw.close();
            wifiList.clear();
            System.out.println("Data successfully appended at the end of file");
            System.out.println(sb.toString().getBytes());
        }


    }
}



