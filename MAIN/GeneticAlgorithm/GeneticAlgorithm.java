package MAIN.GeneticAlgorithm;

import MAIN.Body.State;
import MAIN.Body.Vector3d;
import MAIN.Interfaces.ODEFunctionInterface;
import MAIN.Interfaces.StateInterface;
import MAIN.Interfaces.Vector3dInterface;
import MAIN.ODESolver.ODEFunction;
import MAIN.ODESolver.ODESolver;

public class GeneticAlgorithm {

    private final Vector3dInterface targetPosition = new Vector3d(6.332873118527889e+11,  -1.357175556995868e+12, -2.134637041453660e+09);

    private final int POPSIZE = 1000;

    private final double INFINITY = Double.POSITIVE_INFINITY;

    private final double LEAP_YEAR = 3.162e+7;

    public GeneticAlgorithm(){

        Individual[] population = new Individual[POPSIZE];

        SurfacePosition position = new SurfacePosition();

        ODESolver simulator = new ODESolver();

        ODEFunctionInterface f = new ODEFunction();

        for (int i = 0; i < POPSIZE; i++) {


            double altitude = 0;
            double longitude = Math.random() * (180 + 180 + 1) - 180;
            double latitude = Math.random() * (90 + 90 + 1) - 90;
            StateInterface exp = new State(position.positionOnEarth(altitude, longitude, latitude), new Vector3d(0, 0, 0));


            //Step-size will be also random
            double stepSize = 1000;
            StateInterface[] solution = simulator.solve(f, exp, LEAP_YEAR, stepSize);

            int solTime = isFind((State[]) solution);

            if(solTime > 0) {
                System.out.println(exp.toString() + " Time = " + solTime * stepSize);
                population[i] = new Individual(exp, solTime * stepSize, longitude, latitude);
            } else {
                population[i] = new Individual(exp, LEAP_YEAR, longitude, latitude);
            }
        }

       HeapSort.sort(population);

        //TODO NOT DONE YET
    }

    public int isFind(State[] trajectory){

        double radiusTitan = 2575.5e3;

        for (int i = 0; i < trajectory.length; i++) {
            if(trajectory[i].position.dist(targetPosition) <= radiusTitan)
                return i;
        }

        return 0;
    }

    public void minTime(){

    }

    public static void main(String[] args) {
        GeneticAlgorithm g = new GeneticAlgorithm();
    }
}

