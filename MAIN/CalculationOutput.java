package MAIN;

import MAIN.Body.Vector3d;
import MAIN.ODESolver.ProbeSimulator;

public class CalculationOutput extends Thread{

    public static void main(String[] args) {
        ProbeSimulator simulator = new ProbeSimulator();

        //Array the trajectory of the probe
        Vector3d[] trajectoryOfProbe = (Vector3d[]) simulator.trajectory(
                new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
                new Vector3d(27978.003182957942, -62341.39349461967 ,-651.590970913659), 31556952, 360);


        MAIN.Body.State[] trajectoryOfAll = simulator.getTrajectory();


        System.out.println(trajectoryOfAll[trajectoryOfAll.length-1].celestialBody.get(3).position);
    }

    public void run() {
        ProbeSimulator simulator = new ProbeSimulator();

        //Array the trajectory of the probe
        simulator.trajectory(
                new Vector3d(-1.4718861838613153E11, -2.8615219147677864E10 ,8174296.311571818),
                new Vector3d(27978.003182957942, -62341.39349461967 ,-651.590970913659),
                31556952, 360);

        MAIN.Body.State[] trajectoryOfAll = simulator.getTrajectory();

        for (int i = 0; i < trajectoryOfAll.length; i++) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(trajectoryOfAll[i].celestialBody.get(11).getPosition());
            this.updatePosition(trajectoryOfAll[i]);
        }
    }

    public void updatePosition(MAIN.Body.State state) {
        UI f = new UI();
        for(int i =0;i<f.getOrbit().length;i++) {
                double y = (state.celestialBody.get(0).getPosition().getX() - state.celestialBody.get(i).getPosition().getX());
                double z = (state.celestialBody.get(0).getPosition().getZ() - state.celestialBody.get(i).getPosition().getZ());
                double x = (state.celestialBody.get(0).getPosition().getY() - state.celestialBody.get(i).getPosition().getY());
                f.getOrbit()[i].getShape().translateXProperty().set(x / 600000000);
                f.getOrbit()[i].getShape().translateZProperty().set(z / 600000000);
                f.getOrbit()[i].getShape().translateYProperty().set(y / 600000000);
            if(i == 4 || i == 8) {
                double yS = (state.celestialBody.get(0).getPosition().getX() - state.celestialBody.get(i-1).getPosition().getX());
                double zS = (state.celestialBody.get(0).getPosition().getZ() - state.celestialBody.get(i-1).getPosition().getZ());
                double xS = (state.celestialBody.get(0).getPosition().getY() - state.celestialBody.get(i-1).getPosition().getY());

                f.getOrbit()[i].getShape().translateXProperty().set((xS + 25.0*(x - xS)) / 600000000);
                f.getOrbit()[i].getShape().translateZProperty().set((zS + 25.0*(z - zS)) / 600000000);
                f.getOrbit()[i].getShape().translateYProperty().set((yS + 25.0*(y - yS)) / 600000000);
            }

        }
    }
}
