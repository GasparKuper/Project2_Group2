import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SimulationMethodTest {


    private double day = 24*60*60;
    private double year = 365.25*day;
    private double[] timeArray = createTimeArray(year, day);

    private double[] createTimeArray(double tf, double h){
        double tsLength = tf/h;
        if(tf % h > 0.0)
            tsLength++;

        double [] ts = new double[(int) tsLength];

        for(int x = 1; x < ts.length; x++){
            ts[x-1] = h * x;
        }

        ts[ts.length-1] = tf;

        return ts;
    }


    @Nested
    @DisplayName("Time array test")
    class TimeArray{

        @Test
        @DisplayName("Test length")
        public void testLength(){
            assertEquals(timeArray.length, 366);
        }

        @Test
        @DisplayName("Test first day")
        public void testFirstDay(){
            assertEquals(timeArray[0], 86400);
        }

        @Test
        @DisplayName("Test last day")
        public void testLastDay(){
            assertEquals(timeArray[timeArray.length-1], 31557600);
        }
    }

    @Nested
    @DisplayName("Step size test")
    class StepSize {

        @Test
        @DisplayName("Step size")
        public void testStepSize(){
            assertEquals(timeArray[0], 86400);
        }

        @Test
        @DisplayName("Step size in the last day")
        public void testLastDayStepSize(){
            int i = timeArray.length-1;
            assertEquals(timeArray[i] - timeArray[i-1], 21600);
        }
    }
}
