package Run.CalculationForProbe;

import Body.SpaceCrafts.State;
import Body.Vector.Vector3d;
import Interfaces.Vector3dInterface;
import ODESolver.ProbeSimulator;

import java.util.Arrays;

/**
 * Newton Raphson class calculating the initial velocity
 * We use Newton Raphson multivariable formula
 * Vk+1 = Vk - Dg(Vk)^-1 * g(Vk)
 * Dg(Vk) is the jacobian matrix (first order derivative of a vector)
 */
public class NewtonRaphson {

    public static void main(String[] args) {
        NewtonRaphson newtonRaphson = new NewtonRaphson();
        double[][] g = {{3,0,2},{2,0,-2},{0,1,1}};
        Vector3d vec = new Vector3d(5.16,3.26,8.79);
        System.out.println(vec.mulMatrix(g));
        System.out.println(Arrays.deepToString(newtonRaphson.findInverse3by3(g)));
       // newtonRaphson.minDist(new Vector3d(21744.742783895097,-31956.466390002974,-1113.868596091232));
    }


    public Vector3d initialVel(Vector3dInterface target, double radius, double distance,
                               Vector3dInterface initialVelocity, Vector3dInterface startPosition){
        double h= 1;
        Vector3d v = (Vector3d) initialVelocity;
        double dist = minDist((Vector3d) initialVelocity,true).getX();
        Vector3d velocityVector = minDist((Vector3d) initialVelocity,false);
        Vector3d newV= new Vector3d(0,0,0);
        while(dist>1000){
            System.out.println("Start");
            double[][] jac = jacobianDerivative(v,100000);
            System.out.println(Arrays.deepToString(jac));
            jac =findInverse3by3(jac);
            newV = (Vector3d) v.sub(velocityVector.mulMatrix(jac));
            v = new Vector3d(newV.getX(),newV.getY(),newV.getZ());
            System.out.println(dist + " "+v.toString());
            dist = minDist((Vector3d) newV,true).getX();
            velocityVector = minDist((Vector3d) newV,false);

        }
        return newV;
    }

    public double[][] jacobianDerivative(Vector3d v,double h){
        // f'(x) =  (f(x+h)-f(x-h))/2h
        double[][] g = new double[3][3];
        double y = v.getY();
        double z = v.getZ();
        double fPrime;
        Vector3d firstDist;
        Vector3d secondDist;
        double sub;
        for(int i=0;i<3;i++){
            for(int j= 0;j<3;j++){
                if(j==0){
                    firstDist = minDist(new Vector3d(v.getX()+h,v.getY(),v.getZ()),false);
                    secondDist = minDist(new Vector3d(v.getX()-h,v.getY(),v.getZ()),false);
                }else if(j==1){
                    firstDist = minDist(new Vector3d(v.getX(),v.getY()+h,v.getZ()),false);
                    secondDist = minDist(new Vector3d(v.getX(),v.getY()-h,v.getZ()),false);
                }else {
                    firstDist = minDist(new Vector3d(v.getX(),v.getY(),v.getZ()+h),false);
                    secondDist = minDist(new Vector3d(v.getX(),v.getY(),v.getZ()-h),false);
                }
                if(i==0){
                    sub = firstDist.getX()-secondDist.getX();
                }else if(i==1){
                    sub = firstDist.getY()-secondDist.getY();
                }else {
                    sub = firstDist.getZ()-secondDist.getZ();
                }
                g[i][j] = sub/(2*h);
            }
        }
        return g;
    }
    public Vector3d minDist(Vector3d v,boolean flag){
        ProbeSimulator simulator = new ProbeSimulator();
        double finalTime = 31557600;
        double stepSize  = 600;
        Vector3d minVector = new Vector3d(0,0,0);
        Vector3d pos = new Vector3d(4301000.0, -4692000.0, -276000.0);
        Vector3d[] trajectory = (Vector3d[])simulator.trajectory(pos, v, finalTime, stepSize);
        State[] trajectoryOfAll = simulator.getTrajectory();
        double min = Double.MAX_VALUE;
        for(int i =trajectory.length-1;i<trajectory.length;i++){
            if((trajectory[i].dist(trajectoryOfAll[i].celestialBody.get(8).getPosition())-2575.5e3)<min){
                min = (trajectory[i].dist(trajectoryOfAll[i].celestialBody.get(8).getPosition())-2575.5e3);
                minVector = (Vector3d) trajectoryOfAll[i].celestialBody.get(11).getVelocity();
            }

        }

        if(flag){
            minVector.setX(min);
            minVector.setY(min);
            minVector.setZ(min);
        }


        return minVector;
    }

    /**
     * https://www.mathsisfun.com/algebra/matrix-inverse-minors-cofactors-adjugate.html
     * Step 1: calculating the Matrix of Minors,
     * Step 2: then turn that into the Matrix of Cofactors,
     * Step 3: then the Adjugate, and
     * Step 4: multiply that by 1/Determinant.
     * @return
     */
    public double[][] findInverse3by3(double[][] g){
        double[][] inv = new double[3][3];
        double det=0;
        for(int i = 0;i<3;i++){
            for(int j =0;j<3;j++){
                int nbr =0;
                double firstDiagonal = 0;
                double secondDiagonal = 0;
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        if(k!=i && l!=j){
                            if(nbr ==0){
                                firstDiagonal = g[k][l];
                            }else if(nbr==1){
                                secondDiagonal = g[k][l];
                            }else if(nbr==2){
                                secondDiagonal = secondDiagonal*g[k][l];
                            }else if(nbr ==3){
                                firstDiagonal = firstDiagonal*g[k][l];
                            }
                            nbr++;
                        }
                    }
                }
                if(i==0){
                    if(j==0){
                        det += g[i][j] * (firstDiagonal-secondDiagonal);
                    }else if(j==1){
                        det -= g[i][j] * (firstDiagonal-secondDiagonal);
                    }else{
                        det += g[i][j] * (firstDiagonal-secondDiagonal);
                    }
                }
                inv[j][i]=firstDiagonal-secondDiagonal;
            }
        }
        for(int x=0;x<3;x++){
            for(int y=0;y<3;y++){
                if(x!=y && Math.abs(x-y)<2){
                    inv[x][y] = inv[x][y]*-1;
                }
            }
        }
        for(int a=0;a<3;a++){
            for(int b=0;b<3;b++){
                if(a!=b){
                    double toSave = inv[a][b];
                    inv[a][b] = inv[b][a];
                    inv[b][a] = toSave;
                }
            }
        }
        for(int c= 0;c<3;c++){
            for(int d= 0;d<3;d++){
                inv[c][d]= inv[c][d]* (1/det);
                inv[c][d] = Math.round(inv[c][d] * 1E14) / 1E14;
            }
        }
        return inv;
    }
}
