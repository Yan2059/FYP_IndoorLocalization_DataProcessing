public class position {
    double x;
    double y;
    double z;

    position(double xValue, double yValue, double zValue){
        x = xValue;
        y = yValue;
        z = zValue;
    }

    public double distance (position p1, position p2){
        double dist;
        double power = Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2)+Math.pow(p1.z-p2.z,2);
        dist = Math.sqrt(power);
        return dist;
    }

    public position centroid(position[] array)  {
        double centroidX = 0, centroidY = 0, centroidZ = 0;
        for(int i=0; i<array.length; i++) {
            centroidX += array[i].x;
            centroidY += array[i].y;
            centroidZ += array[i].z;
        }
        return new position(centroidX / array.length, centroidY / array.length, centroidZ / array.length);
    }

    public boolean compareTo(position point){
        if(Double.compare(point.x,x)==0 && Double.compare(point.y,y)==0 && Double.compare(point.z,z)==0 ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return "x: "+ x + ", y: " + y + ", z :" + z;
    }
}
