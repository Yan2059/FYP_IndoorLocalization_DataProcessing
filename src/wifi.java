class Wifi implements Comparable<Wifi>{
    private String id;
    private Integer strength;

    public Wifi(String id, String strength){
        this.id = id;
        this.strength = Integer.parseInt(strength);
    }

    @Override
    public int compareTo(Wifi o){
        return strength.compareTo(o.strength);
    }

    @Override
    public String toString() {
        return id + " " + strength;
    }
    public String getId(){
        return id;
    }
    public Integer getStrength(){
        return strength;
    }
}