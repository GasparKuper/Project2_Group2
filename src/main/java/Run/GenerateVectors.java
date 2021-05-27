package Run;

import Body.Vector3d;

public class GenerateVectors {
    public GenerateVectors(){
        //Do nothing
    }

    public static void main(String[] args) {
        GenerateVectors generateVectors = new GenerateVectors();
        generateVectors.generate(new Vector3d(1.0805958821390627E12,-1.0131426222635747E12,-2.5264354228682713E10),150000);
    }
    public Vector3d generate(Vector3d aim, double distance) {
        //2675500
        //2875500
        //2775500
        double radius = 2575500;
        double optimalDist = radius + distance;
     //   System.out.println(optimalDist);
        final Vector3d posWithoutChange =new Vector3d(aim.getX(), aim.getY(), aim.getZ());
        Vector3d lastPos =new Vector3d(aim.getX(), aim.getY(), aim.getZ());
        Vector3d lastPosProbe =new Vector3d(aim.getX(), aim.getY(), aim.getZ());
        boolean end = false;
        double step = 26486450;
        Vector3d previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector3d previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        while(!end){
            if(lastPos.dist(lastPosProbe)==optimalDist || step<1E-15){
                if(lastPos.dist(lastPosProbe)==optimalDist ){
                }
                end = true;
               // System.out.println("Final pos = "+lastPosProbe.toString());
               // System.out.println(posWithoutChange.dist(lastPosProbe)-radius);
            }
            else if(lastPos.dist(lastPosProbe)<optimalDist){
                lastPosProbe.setX(lastPosProbe.getX()+step);
                lastPosProbe.setY(lastPosProbe.getY()+step);
                lastPosProbe.setZ(lastPosProbe.getZ()+step);
            }else if(lastPos.dist(lastPosProbe)>optimalDist){
                lastPosProbe.setX(lastPosProbe.getX()-step);
                lastPosProbe.setY(lastPosProbe.getY()-step);
                lastPosProbe.setZ(lastPosProbe.getZ()-step);
            }
            previousThree = previousTwo;
            previousTwo = previousOne;
            previousOne = lastPosProbe;
            if ((lastPosProbe.dist(previousThree) == 0)) {
                float l = 2;
                step = step / l;
                previousOne = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousTwo = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                previousThree = new Vector3d(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            }
        }
        return lastPosProbe;
    }
}
