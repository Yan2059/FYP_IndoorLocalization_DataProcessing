import java.util.ArrayList;

public class dataPoint{
    position location;
    ArrayList<Wifi> wifi = new ArrayList<>();

    dataPoint(position pos){
        location = new position(pos.x,pos.y,pos.z);
    }

    //by default if position is not provided, assume 0 0 0
    dataPoint(){
        location = new position(0,0,0);
    }

    void setPoint(double x, double y, double z){
        location = new position(x,y,z);
    }

    void setPoint(position pos){
        location = new position(pos.x,pos.y,pos.z);
    }

    void addWifi(String id, String strength){
        wifi.add(new Wifi(id, strength));
    }

    void sortByWifiID(){
        this.wifi.sort((a, b) -> {
            return a.getID().compareTo(b.getID());
        });
    }

    private int compareBYID(int thisIndex, int thatIndex, dataPoint thatDP){
        if (this.wifi.size() <= thisIndex){
            // this reach the end but that doesn't
            // meaning: that wifi is sth this doesn't have
            return 1;
        } else if (thatDP.wifi.size() <= thatIndex){
            return -1;
        }

        return this.wifi.get(thisIndex).getID().compareTo(thatDP.wifi.get(thatIndex).getID());
    }

    private int distance(int thisIndex, int thatIndex, dataPoint thatDP){
        int diff = thatDP.wifi.get(thatIndex).getStrength() - this.wifi.get(thisIndex).getStrength();
        return diff * diff;
    }

    // calculate similarity of two Wifi RSS list
    // for faster comparison, omitting the sqrt
    // for wifi signal that both point doesn't have: (x-y)^2 = (0-0)^2
    int similarity(dataPoint thatDP){
        int thisDPIndex = 0, thatDPIndex = 0;
        int similaritySqSum = 0;
        int Strength;
        for (; thisDPIndex < this.wifi.size() || thatDPIndex < thatDP.wifi.size();){
            int compare = compareBYID(thisDPIndex, thatDPIndex, thatDP);
            if (compare == 0){
                // both point are the same
                similaritySqSum += distance(thisDPIndex, thatDPIndex, thatDP);
                thisDPIndex++;
                thatDPIndex++;
            } else if (compare < 0){
                // this wifiID < that wifiID
                // meaning: this wifi is sth that doesn't have
                Strength = this.wifi.get(thisDPIndex).getStrength();
                similaritySqSum += Strength*Strength;
                thisDPIndex++;
            } else {
                // this wifiID > that wifiID
                // meaning: that wifi is sth this doesn't have
                Strength = thatDP.wifi.get(thatDPIndex).getStrength();
                similaritySqSum += Strength*Strength;
                thatDPIndex++;
            }
        }
        System.out.println(similaritySqSum);
        System.out.println("Coordinate: "+location.x+" "+location.y+" "+location.z);
        return similaritySqSum;
    }

    @Override
    public String toString(){
        String result = "";
        result += "Location: ";
        result += location.toString();
        for(Wifi w : wifi){
            result += "\n\twifi: ";
            result += w.toString();
        }
        return result;
    }
}
