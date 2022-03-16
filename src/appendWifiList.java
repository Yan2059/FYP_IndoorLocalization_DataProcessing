import java.util.ArrayList;
import java.io.*;
import java.util.Collections;


class appendWifiList {
    private static ArrayList<Wifi> wifiList = new ArrayList<>();
    private static ArrayList<position> posList = new ArrayList<>();

    static void readData() throws IOException{
        File filename = new File("./data.txt");
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
    }

    public static void main(String[] args) throws IOException{
        readData();
        Collections.sort(wifiList, Collections.reverseOrder());
        for(int i = 0; i < wifiList.size(); i++){
            System.out.println(wifiList.get(i));
        }
        File file =new File("./data.csv");
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
        for(int i=0;i<wifiList.size();i++){
            sb.append(posList.get(i).x);
            sb.append(',');
            sb.append(posList.get(i).y);
            sb.append(',');
            sb.append(posList.get(i).z);
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

        System.out.println("Data successfully appended at the end of file");
        System.out.println(sb.toString().getBytes());

    }
}



