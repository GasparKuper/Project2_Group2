package Body;

import Interfaces.Vector3dInterface;
import java.lang.Math;

public class Vector3d implements Vector3dInterface{

    private double x;
    private double y;
    private double z;

    /**
     * Constructor of vector3d class
     * @param x is the x coordinates of the vector
     * @param y is the y coordinates of the vector
     * @param z is the z coordinates of the vector
     */
    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(){
    }

    /**
     *
     * @return The x coordinate of the vector
     */
    public double getX() {
        return this.x;
    }

    /**
     *
     * @param x is the double to set as the x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     *
     * @return The y coordinate of the vector
     */
    public double getY() {
        return this.y;
    }

    /**
     *
     * @param y is the double to set as the y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     *
     * @return The z coordinate of the vector
     */
    public double getZ() {
        return this.z;
    }

    /**
     *
     * @param z is the double to set as the z coordinate
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Addition of two vectors
     * @param other is the vector to add
     * @return the result of the addition
     */
    public Vector3dInterface add(Vector3dInterface other) {
        return new Vector3d(this.x + other.getX(), this.y + other.getY(), this.z + other.getZ());
    }

    /**
     * Substraction of two vectors
     * @param other is the vector to substract
     * @return the result of the substraction
     */
    public Vector3dInterface sub(Vector3dInterface other) {
        return new Vector3d(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
    }

    /**
     * Multiplication of a vector with scalar
     * @param scalar The double used for the multiplication
     * @return the result of the multiplication
     */
    public Vector3dInterface mul(double scalar) {
        return new Vector3d(this.x*scalar, this.y*scalar, this.z*scalar);
    }

    /**
     * multiplicate vector other by scalar and add it to this vector
     * @param scalar double used in the multiplication step
     * @param other the vector used in the multiplication step
     * @return the result of the operation
     */
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        return this.add(other.mul(scalar));
    }

    /**
     * @return the Euclidean norm of a vector
     */
    public double norm(){
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2));
    }


    /**
     * @return the Euclidean distance between two vectors
     */
    public double dist(Vector3dInterface other){
        return Math.sqrt(Math.pow((this.getX()-other.getX()),2)+Math.pow((this.getY()-other.getY()),2)+Math.pow((this.getZ()-other.getZ()),2));
    }

    /**
     * @return The vector in a string format
     */
    public String toString() {
        return ("(" + this.x + "," + this.y + "," + this.z + ")");
    }

    /**
     *
     * @return the vector
     */
    public Vector3d Normalize(){
        double magnitude = this.norm();
        return new Vector3d(this.getX()/magnitude, this.getY()/magnitude,this.getZ()/magnitude);
    }
}
