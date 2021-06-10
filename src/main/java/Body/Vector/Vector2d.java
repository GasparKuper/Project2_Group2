package Body.Vector;

import Interfaces.Vector3dInterface;

public class Vector2d {

    private double x;
    private double y;

    /**
     * Constructor of vector3d class
     * @param x is the x coordinates of the vector
     * @param y is the y coordinates of the vector
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(){
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
     * Addition of two vectors
     * @param other is the vector to add
     * @return the result of the addition
     */
    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.getX(), this.y + other.getY());
    }

    /**
     * Substraction of two vectors
     * @param other is the vector to substract
     * @return the result of the substraction
     */
    public Vector2d sub(Vector2d other) {
        return new Vector2d(this.x - other.getX(), this.y - other.getY());
    }

    /**
     * Multiplication of a vector with scalar
     * @param scalar The double used for the multiplication
     * @return the result of the multiplication
     */
    public Vector2d mul(double scalar) {
        return new Vector2d(this.x*scalar, this.y*scalar);
    }

    /**
     * multiplicate vector other by scalar and add it to this vector
     * @param scalar double used in the multiplication step
     * @param other the vector used in the multiplication step
     * @return the result of the operation
     */
    public Vector2d addMul(double scalar, Vector2d other) {
        return this.add(other.mul(scalar));
    }

    /**
     * @return the Euclidean norm of a vector
     */
    public double norm(){
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }


    /**
     * @return the Euclidean distance between two vectors
     */
    public double dist(Vector2d other){
        return Math.sqrt(Math.pow((this.getX()-other.getX()),2)+Math.pow((this.getY()-other.getY()),2));
    }

    /**
     * @return The vector in a string format
     */
    public String toString() {
        return ("(" + this.x + "," + this.y + ")");
    }
}
