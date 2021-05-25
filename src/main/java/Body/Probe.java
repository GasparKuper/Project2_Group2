package Body;

public class Probe extends PlanetBody{
    private double fuel;

    public Probe(double mass, Vector3d p0, Vector3d v0, double fuel) {
        super(mass, p0, v0);
        this.fuel = fuel;
    }

    public double getFuel() {
        return fuel;
    }

    private static double exhaustSpeed = 1.0;

    //max force 30 MN

    public void activateThruster(double consume, Vector3d direction){
        //v= v+(vex)ln(m0/m)

        if(this.fuel >= consume){
            Vector3d temp = (Vector3d) this.velocity.add(direction.mul(exhaustSpeed*Math.log(this.getM()+(this.getFuel()-consume)/(this.getM()+this.fuel))));
            this.fuel = this.fuel-consume;
            this.velocity.add(temp);
        }else if(this.fuel > 0){
            consume=this.fuel;
            Vector3d temp = (Vector3d) this.velocity.add(direction.mul(exhaustSpeed*Math.log(this.getM()+(this.getFuel()-consume)/(this.getM()+this.fuel))));
            this.fuel = this.fuel-consume;
            this.velocity.add(temp);
        }
    }
}
