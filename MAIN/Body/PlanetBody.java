package MAIN.Body;

public class PlanetBody {

    private double m;

    private PointCoordinate point;

    private Vector3d vector;

    public PlanetBody(double m, PointCoordinate point, Vector3d vector){
        this.m = m;
        this.point = point;
        this.vector = vector;
    }

    public double getM() {
        return m;
    }

    public PointCoordinate getPoint() {
        return point;
    }

    public Vector3d getVector() {
        return vector;
    }

    public void setM(double m) {
        this.m = m;
    }

    public void setPoint(PointCoordinate point) {
        this.point = point;
    }

    public void setVector(Vector3d vector) {
        this.vector = vector;
    }

}
