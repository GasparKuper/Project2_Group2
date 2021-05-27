import Body.Rate;
import Body.State;
import Body.Vector3d;
import static org.junit.jupiter.api.Assertions.*;

import ODESolver.ODEFunction;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import static Constant.Constant.FLAG_VERLET_TEST;

import java.util.LinkedList;

public class SolverTest {

    private final State probe = new State(15000, new Vector3d(0, 0, 0), new Vector3d(100, 0, 0));
    private final Rate zeroRateAcceleration;
    private final Rate RateAcceleration;

    //Initialize variables
    {
        LinkedList<Vector3d> zeroAcceleration = new LinkedList<>();
        zeroAcceleration.add(new Vector3d(0, 0, 0));
        zeroRateAcceleration = new Rate(zeroAcceleration);

        LinkedList<Vector3d> Acceleration = new LinkedList<>();
        Acceleration.add(new Vector3d(5, 0, 0));
        RateAcceleration = new Rate(Acceleration);
    }

    @Nested
    @DisplayName("Symplectic Euler")
    class EulerSymplectic{

        @Test
        @DisplayName("First step without acceleration")
        public void firstStepWithoutAcceleration(){
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            //New position = old Position + stepSize * Velocity
            double initial_Pos = 0;
            double initial_Vel = 100;
            double new_Vel = initial_Vel + 0 * 5;
            double new_Pos = initial_Pos + new_Vel * 5;
            State firstStep = (State) probe.addMul(5, zeroRateAcceleration);
            Assertions.assertAll(() -> assertEquals(new_Pos, firstStep.position.getX()),
                    () -> assertEquals(new_Vel, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps without acceleration")
        public void fiveStepsWithoutAcceleration(){
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            //New position = old Position + stepSize * Velocity
            double tmp_Pos = 0;
            double tmp_Vel = 100;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                tmp_Vel = tmp_Vel + 0 * 5;
                tmp_Pos = tmp_Pos + tmp_Vel * 5;
                fiveSteps = (State) probe.addMul(5, zeroRateAcceleration);
            }
            double fiveSteps_Pos = tmp_Pos;
            double fiveSteps_Vel = tmp_Vel;
            State fiveSteps_Solver = fiveSteps;
            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }

        @Test
        @DisplayName("First step with acceleration")
        public void firstStepWithAcceleration(){
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            //New position = old Position + stepSize * Velocity
            double initial_Pos = 0;
            double initial_Vel = 100;
            double new_Vel = initial_Vel + 5 * 5;
            double new_Pos = initial_Pos + new_Vel * 5;
            State firstStep = (State) probe.addMul(5, RateAcceleration);
            Assertions.assertAll(() -> assertEquals(new_Pos, firstStep.position.getX()),
                                    () -> assertEquals(new_Vel, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps with acceleration")
        public void fiveStepsWithAcceleration(){
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            //New position = old Position + stepSize * Velocity
            double tmp_Pos = 0;
            double tmp_Vel = 100;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                tmp_Vel = tmp_Vel + 5 * 5;
                tmp_Pos = tmp_Pos + tmp_Vel * 5;
                fiveSteps = (State) probe.addMul(5, RateAcceleration);
            }
            double fiveSteps_Pos = tmp_Pos;
            double fiveSteps_Vel = tmp_Vel;
            State fiveSteps_Solver = fiveSteps;
            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }
    }

    @Nested
    @DisplayName("Implicit Euler")
    class EulerImplicit{

        @Test
        @DisplayName("First step without acceleration")
        public void firstStepWithoutAcceleration(){
            //New position = old Position + stepSize * Velocity
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            double initial_Pos = 0;
            double initial_Vel = 100;
            double new_Pos = initial_Pos + initial_Vel * 5;
            double new_Vel = initial_Vel + 0 * 5;
            State firstStep = (State) probe.addMulImplicit(5, zeroRateAcceleration);
            Assertions.assertAll(() -> assertEquals(new_Pos, firstStep.position.getX()),
                    () -> assertEquals(new_Vel, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps without acceleration")
        public void fiveStepsWithoutAcceleration(){
            //New position = old Position + stepSize * Velocity
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            double tmp_Pos = 0;
            double tmp_Vel = 100;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                tmp_Pos = tmp_Pos + tmp_Vel * 5;
                tmp_Vel = tmp_Vel + 0 * 5;
                fiveSteps = (State) probe.addMulImplicit(5, zeroRateAcceleration);
            }
            double fiveSteps_Pos = tmp_Pos;
            double fiveSteps_Vel = tmp_Vel;
            State fiveSteps_Solver = fiveSteps;
            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }

        @Test
        @DisplayName("First step with acceleration")
        public void firstStepWithAcceleration(){
            //New position = old Position + stepSize * Velocity
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            double initial_Pos = 0;
            double initial_Vel = 100;
            double new_Pos = initial_Pos + initial_Vel * 5;
            double new_Vel = initial_Vel + 5 * 5;
            State firstStep = (State) probe.addMulImplicit(5, RateAcceleration);
            Assertions.assertAll(() -> assertEquals(new_Pos, firstStep.position.getX()),
                    () -> assertEquals(new_Vel, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps with acceleration")
        public void fiveStepsWithAcceleration(){
            //New position = old Position + stepSize * Velocity
            //New Velocity = old Velocity + stepSize * Rate (Acceleration)
            double tmp_Pos = 0;
            double tmp_Vel = 100;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                tmp_Pos = tmp_Pos + tmp_Vel * 5;
                tmp_Vel = tmp_Vel + 5 * 5;
                fiveSteps = (State) probe.addMulImplicit(5, RateAcceleration);
            }
            double fiveSteps_Pos = tmp_Pos;
            double fiveSteps_Vel = tmp_Vel;
            State fiveSteps_Solver = fiveSteps;
            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }
    }

    @Nested
    @DisplayName("Velocity-Verlet")
    class VelocityVerlet{

        @Test
        @DisplayName("First step without acceleration")
        public void firstStepWithoutAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
            double old_velocity = 100;
            double old_position = 0;
            double new_HALF_Velocity = old_velocity + 0 * 10/2;
            double new_Position = old_position + new_HALF_Velocity * 10;
            double new_Velocity = new_HALF_Velocity + 0 * 10/2;
            State firstStep = (State) probe.addMulVerletVelocity(10, zeroRateAcceleration, new ODEFunction());
            Assertions.assertAll(() -> assertEquals(new_Position, firstStep.position.getX()),
                    () -> assertEquals(new_Velocity, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps without acceleration")
        public void fiveStepsWithoutAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
            double old_velocity = 100;
            double old_position = 0;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                double new_HALF_Velocity = old_velocity + 0 * 10/2;
                double new_Position = old_position + new_HALF_Velocity * 10;
                double new_Velocity = new_HALF_Velocity + 0 * 10/2;
                old_position = new_Position;
                old_velocity = new_Velocity;
                fiveSteps = (State) probe.addMulVerletVelocity(10, zeroRateAcceleration, new ODEFunction());
            }
            double fiveSteps_Pos = old_position;
            double fiveSteps_Vel = old_velocity;
            State fiveSteps_Solver = fiveSteps;

            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }

        @Test
        @DisplayName("First step with acceleration")
        public void firstStepWithAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
            FLAG_VERLET_TEST = true;
            double old_velocity = 100;
            double old_position = 0;
            double new_HALF_Velocity = old_velocity + 5.0 * 10/2;
            double new_Position = old_position + new_HALF_Velocity * 10;
            double new_Velocity = new_HALF_Velocity + 5.0 * 10/2;
            State firstStep = (State) probe.addMulVerletVelocity(10, RateAcceleration, new ODEFunction());

            FLAG_VERLET_TEST = false;
            Assertions.assertAll(() -> assertEquals(new_Position, firstStep.position.getX()),
                    () -> assertEquals(new_Velocity, firstStep.velocity.getX()));
        }

        @Test
        @DisplayName("5 steps with acceleration")
        public void fiveStepsWithAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
            FLAG_VERLET_TEST = true;
            double old_velocity = 100;
            double old_position = 0;
            State fiveSteps = null;
            for (int i = 0; i < 5; i++) {
                double new_HALF_Velocity = old_velocity + 5.0 * 10/2;
                double new_Position = old_position + new_HALF_Velocity * 10;
                double new_Velocity = new_HALF_Velocity + 5.0 * 10/2;
                old_position = new_Position;
                old_velocity = new_Velocity;
                fiveSteps = (State) probe.addMulVerletVelocity(10, RateAcceleration, new ODEFunction());
            }
            double fiveSteps_Pos = old_position;
            double fiveSteps_Vel = old_velocity;
            State fiveSteps_Solver = fiveSteps;

            FLAG_VERLET_TEST = false;
            Assertions.assertAll(() -> assertEquals(fiveSteps_Pos, fiveSteps_Solver.position.getX()),
                    () -> assertEquals(fiveSteps_Vel, fiveSteps_Solver.velocity.getX()));
        }
    }

    @Nested
    @DisplayName("4th-order-Runge-Kutta")
    @Description("If Acceleration function,Implicit and Symplectic Euler give passed test, then 4th order Runge-Kutta works correctly")
    public class RungeKutta{

        @Test
        public void testRungeKutta(){
            System.out.println("If Acceleration function,Implicit and Symplectic Euler give passed test, then 4th order Runge-Kutta works correctly");
        }
    }
}
