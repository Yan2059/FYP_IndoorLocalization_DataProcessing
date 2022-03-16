import java.util.ArrayList;
import java.util.Collection;

public class dataPoint {
    position location;
    public static ArrayList<Wifi> wifiList = new ArrayList<>();
    dataPoint(position point, ArrayList<Wifi> list){
        location = point;
        wifiList = list;
    }
    //calculate similarity of two Wifi RSS list
    double similarity(dataPoint point){
        double sim;
        ArrayList<Wifi> unique = createUniqueList(point);
        ArrayList<Integer> vector1 = new ArrayList<Integer>();
        ArrayList<Integer> vector2 = new ArrayList<Integer>();
        for(int i=0;i<unique.size();i++){
            if(findById(wifiList, unique.get(i).getId()).getId().equals(wifiList.get(i).getId())){
                vector1.add(wifiList.get(i).getStrength());
            }
            else{
                vector1.add(0);
            }
            if(findById(point.wifiList, unique.get(i).getId()).getId().equals(point.wifiList.get(i).getId())){
                vector2.add(wifiList.get(i).getStrength());
            }
            else{
                vector2.add(0);
            }

        }
        sim = calculateDistance(vector1,vector2);
        return sim;
    }
    //find and add all unique Wifi of two datapoint
    ArrayList<Wifi> createUniqueList(dataPoint point){
        ArrayList<Wifi> wifiVector = new ArrayList<>(); //wifi vector storing unique wifi
        //for loop to scan through this wifi add new wifi
        for(int i=0;i<wifiList.size();i++){
            String tempId = wifiList.get(i).getId();
            if(point.wifiList.stream()
                    .anyMatch(p -> p.getId().equals(tempId))&& !(wifiVector.stream()
                    .anyMatch(p -> p.getId().equals(tempId)))){
                wifiVector.add(wifiList.get(i));
            }
        }
        //for loop to scan through the target and add new wifi
        for(int i=0;i<point.wifiList.size();i++){
            String tempId = point.wifiList.get(i).getId();
            if(wifiList.stream()
                    .anyMatch(p -> p.getId().equals(tempId))&& !(wifiVector.stream()
                    .anyMatch(p -> p.getId().equals(tempId)))){
                wifiVector.add(point.wifiList.get(i));
            }
        }
        return wifiVector;
    }
    public static Wifi findById(Collection<Wifi> list, String id) {
        return list.stream().filter(wifi -> id.equals(wifi.getId())).findFirst().orElse(null);
    }

    //Euclidean distance calculation of two Wifi RSS vector
    public static double calculateDistance(ArrayList<Integer> v1, ArrayList<Integer> v2)
    {
        double Sum = 0.0;
        for(int i=0;i<v1.size();i++) {
            Sum = Sum + Math.pow((v1.get(i)-v2.get(i)),2.0);
        }
        return Math.sqrt(Sum);
    }

}
