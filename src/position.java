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
        double xScale=1.2;
        double yScale=1.2;
        double floorHeight=5;

        double power = Math.pow(p1.x*xScale-p2.x*xScale,2)+Math.pow(p1.y*yScale-p2.y*yScale,2)+Math.pow(p1.z*floorHeight-p2.z*floorHeight,2);
        dist = Math.sqrt(power);
        return dist;
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
