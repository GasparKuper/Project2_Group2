*#Project2_Group10*
   # The capabilities of our program
   ### 1. Representation of the solar system (GUI)  
   To represent the solar system, we use coordinates that we get using solvers and then scale them to allow a better visualization on the screen.<br />
         <br>**HOT-KEYS** for the solar system representation: <br /> 
   - W - Zoom in <br />
   - S - Zoom out <br />
   - D - Camera to the right <br />
   - A - Camera to the left <br />
   - R - Camera to the top <br />
   - F - Camera to the bottom <br />
   
   ### 2. Calculate the trajectory of the probe and planets
   We have two options:<br />
      <br>1) You can start the GUI and run solvers with our parameters. <br />
      2) You can start the console and run the program with your parameters <br />
   <br>For calculation, we can use 4 solvers: <br /> 
   - Implicit Euler <br />
   - Symplectic Euler <br />
   - Velocity-Verlet (Leapfrog-Verlet implementation) <br />
   - 4th order Runge-Kutta <br />
   
   ### 3. Calculate the initial velocity of the probe 
   Our program calculates the optimal initial velocity to reach Titan.
   
   ### 4. Compare coordinates with NASA coordinates
   We can compare the coordinates of the probe and planets calculated by our program with the ones from NASA horizons. Then, the program can represent this comparison using  line charts.
   
   
  # Instruction on how to use our program
  
  ### JUnit testing
   To test our program, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br /><br />
   ***gradle test*** <br /><br />
   ![](readmeImages/gradleTest.png) <br /> <br />
   If first command does not give the output, try this: <br /><br />
   ***gradle test --rerun-tasks*** <br /><br />
   ![](readmeImages/gradleTestSec.png) <br /> <br />
   
  ### Graphic User Interface
   To run our GUI, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br /><br />
   ***gradle run*** <br /><br />
   ![](readmeImages/gradleRun.png) <br /> <br />
  
  ### Calculate the trajectory of the probe with your parameters 
   To Calculate the trajectory of the probe with your parameters, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br /><br />
   ***gradle runSolver*** <br /><br />
   ![](readmeImages/runSolver.png) <br /> <br />
  
  ### Calculate the initial velocity of the probe 
   To Calculate the initial velocity of the probe, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br /><br />
   ***gradle runVelocity*** <br /><br />
   ![](readmeImages/runVelocity.png) <br /> <br />
   
   ### Experiments output
   To watch experiments with solvers, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br /><br />
   ***gradle runExperiments*** <br /><br />
   ![](readmeImages/runExperiments.png) <br /> <br />
   
   

  
  # Experiments with solvers and brute force
