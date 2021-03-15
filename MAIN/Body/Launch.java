package MAIN.Body;

public class Launch {

    public static void main(String[] args) {
        Data data = new Data();

        PlanetBody[] planets = data.SolarSystem();

        Orbit orbit = new Orbit();

        Vector3d[] acceleration = (Vector3d[]) orbit.AccelerationVectorPlanet(planets);

        for (Vector3d vector3d : acceleration) {
            System.out.println(vector3d.toString());
        }
    }
}
