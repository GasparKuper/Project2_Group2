package NWGravity;

public class Vector3D implements Vector3dInterface {

    private double x, y, z;

    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public double getX() {
        return 0;
    }

    @Override
    public void setX(double x) {

    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setY(double y) {

    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public void setZ(double z) {

    }

    @Override
    public Vector3dInterface add(Vector3dInterface other) {
        return null;
    }

    @Override
    public Vector3dInterface sub(Vector3dInterface other) {
        return null;
    }

    @Override
    public Vector3dInterface mul(double scalar) {
        return null;
    }

    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        return null;
    }

    @Override
    public double norm() {
        return 0;
    }

    @Override
    public double dist(Vector3dInterface other) {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
