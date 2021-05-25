package Run;

import ODESolver.ProbeSimulator;

import java.text.DecimalFormat;

import static Constant.Constant.*;

public class Experiments extends Thread{

    /**
     * Gives you experiments with each solver
     */
    public void Solver() {
        ProbeSimulator simulator = new ProbeSimulator();

        double day = 24*60*60;
        double year = 365.25*day;
        double[] array = {day, 12*60*60, 60*60, 30*60, 10*60, 60};

        double[] earth1day = {-1.467017349489421E+08*1000, -3.113778902611655E+07*1000, 8.350045664343983E+03*1000};
        double[] earth1year = {-1.475975941838746E+08*1000, -2.884406357806718E+07*1000, 2.039237780630402E+04*1000};

        DecimalFormat df = new DecimalFormat("#.########");

        for (int i = 0; i < 4; i++) {
            SOLVER = i+1;
            double best = Double.MAX_VALUE;
            double worst = Double.MIN_VALUE;
            double stepBest = 0;
            double stepWorst = 0;
            if (SOLVER == 1)
                System.out.println("SYMPLECTIC EULER SOLVER");
            else if (SOLVER == 2)
                System.out.println("IMPLICIT EULER SOLVER");
            else if (SOLVER == 3)
                System.out.println("VELOCITY-VERLET SOLVER");
            else if (SOLVER == 4)
                System.out.println("RUNGE-KUTTA SOLVER");

            double theBestTime = 1L;
            double theWorstTime = 1L;
            for (int j = 0; j < 6; j++) {
                long startTime = System.currentTimeMillis();

                //Change the time year or day
                simulator.trajectory(STARTPOS, VELOCITIES[SOLVER - 1], year , array[j]); //CA
                long endTime = System.currentTimeMillis();
                double time = (double) endTime - startTime;


                Body.State[] trajectoryOfAll = simulator.getTrajectory();

                //CHANGE ARRAY *CA, it depends on which time, you want check
                //earth1year = 1 year 6 hours (NASA Coordinates)
                //earth1day = 1 day (NASA Coordinates)
                double tmpX = trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getX() -
                        earth1year[0]; //CA

                double tmpY = trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getY() -
                        earth1year[1]; //CA

                double tmpZ = trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getZ() -
                        earth1year[2]; //CA


                //Calculate errors |p*-p| / |p|
                double errorX = Math.abs(tmpX)/
                        Math.abs(earth1year[0]); //CA

                double errorY = Math.abs(tmpY)/
                        Math.abs(earth1year[1]); //CA

                double errorZ = Math.abs(tmpZ)/
                        Math.abs(earth1year[2]); //CA


                double average = (errorX + errorY + errorZ)/3;
                System.out.println("Step size = " + array[j]);

                //Outputs solvers coordinates
//                System.out.println(trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getX());
//                System.out.println(trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getX());
//                System.out.println(trajectoryOfAll[trajectoryOfAll.length - 1].celestialBody.get(3).position.getZ());

                //Outputs errors
                System.out.println("Average = " + df.format(average*100.0)+ "%");
                System.out.println("x_error = " + df.format(errorX*100.0) + "%");
                System.out.println("y_error = " + df.format(errorY*100.0)+ "%");
                System.out.println("z_error = " + df.format(errorZ*100.0)+ "%");
                System.out.println();

                //The best calculation
                if(average < best) {
                    best = average;
                    stepBest = array[j];
                    theBestTime = time;
                }

                //The worst calculation
                if(average > worst) {
                    worst = average;
                    stepWorst = array[j];
                    theWorstTime = time;
                }
            }
            //Outputs the best and the worst calculation
            System.out.println("==============================================");
            System.out.println("The best step = " + stepBest + " average = " + df.format(best*100.0) + "%" + " time = " + theBestTime + "ms");
            System.out.println("The worst step = " + stepWorst + " average = " + df.format(worst*100.0) + "%" + " time = "  + theWorstTime + "ms");
            System.out.println("==============================================");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Experiments run = new Experiments();
        run.Solver();
    }
}
