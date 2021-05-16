import Body.Rate;
import Body.State;
import Body.Vector3d;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.LinkedList;

public class SolverTest {

    private final State probe = new State(15000, new Vector3d(0, 0, 0), new Vector3d(100, 0, 0));
    private final Rate zeroRateAcceleration;
    private final Rate RateAcceleration;
    {
        LinkedList<Vector3d> zeroAcceleration = new LinkedList<>();
        zeroAcceleration.add(new Vector3d(0, 0,0));
        zeroRateAcceleration = new Rate(zeroAcceleration);

        LinkedList<Vector3d> Acceleration = new LinkedList<>();
        Acceleration.add(new Vector3d(5, 0,0));
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
    @Disabled("Later")
    @DisplayName("Velocity-Verlet")
    class VelocityVerlet{

        @Test
        @DisplayName("First step without acceleration")
        public void firstStepWithoutAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
        }

        @Test
        @DisplayName("5 steps without acceleration")
        public void fiveStepsWithoutAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
        }

        @Test
        @DisplayName("First step with acceleration")
        public void firstStepWithAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
        }

        @Test
        @DisplayName("5 steps with acceleration")
        public void fiveStepsWithAcceleration(){
            //New_HALF_Velocity(i + 1/2) = old_velocity(i) + a(i) * step/2
            //New_Position(i+1) = old_position(i) + New_HALF_Velocity(i + 1/2) * step
            //New_Velocity = New_HALF_Velocity(i + 1/2) + a(i+1) * step/2
        }
    }

    @Nested
    @Disabled("Not implemented yet")
    @DisplayName("Stormer-Verlet")
    class StormerVerlet{

        @Test
        @DisplayName("First step without acceleration")
        public void firstStepWithoutAcceleration(){
            //First Step with another solver
            //New_position = old_position*2 - previous_position + acceleration * step^2
            //New_Velocity = old_velocity + acceleration * step
        }

        @Test
        @DisplayName("5 steps without acceleration")
        public void fiveStepsWithoutAcceleration(){
            //First Step with another solver
            //New_position = old_position*2 - previous_position + acceleration * step^2
            //New_Velocity = old_velocity + acceleration * step
        }

        @Test
        @DisplayName("First step with acceleration")
        public void firstStepWithAcceleration(){
            //First Step with another solver
            //New_position = old_position*2 - previous_position + acceleration * step^2
            //New_Velocity = old_velocity + acceleration * step
        }

        @Test
        @DisplayName("5 steps with acceleration")
        public void fiveStepsWithAcceleration() {
                //First Step with another solver
                //New_position = old_position*2 - previous_position + acceleration * step^2
                //New_Velocity = old_velocity + acceleration * step
        }
    }
}
