# Project2_Group10
   ## The capabilities of our program
   ### Representation of the solar system (GUI)  <br />
   To represent the solar system, we use coordinates that we get using solvers and then scale them to allow a better visualization on the screen.
   
   ### Calculate the trajectory of the probe and planets<br />
   We have two options:
   1) You can start the GUI and run solvers with our parameters.
   2) You can start the console and run the program with your parameters.
   <br />
   For calculation, we can use 4 solvers: <br /> <br />
   - Implicit Euler <br />
   - Symplectic Euler <br />
   - Velocity-Verlet (Leapfrog-Verlet implementation) <br />
   - 4th order Runge-Kutta <br />
   
   ### Calculate the initial velocity of the probe <br />
   Our program calculates the optimal initial velocity to reach Titan.
   
   ### Compare coordinates with NASA coordinates <br />
   We can compare the coordinates of the probe and planets calculated by our program with the ones from NASA horizons. Then, the program can represent this comparison using  line charts.
   
   
  ## Instruction on how to use our program
  
  ### JUnit testing
   To test our program, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br />
   ***gradle test*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/gradleTest.png)** <br />
   If first command does not give the output, try this: <br />
   ***gradle test --rerun-tasks*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/gradleTestSec.png)**
   
  ### Graphic User Interface
   To run our GUI, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br />
   ***gradle run*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/gradleRun.png)**
  
  ### Calculate the trajectory of the probe with your parameters 
   To Calculate the trajectory of the probe with your parameters, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br />
   ***gradle runSolver*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/runSolver.png)**
  
  ### Calculate the initial velocity of the probe 
   To Calculate the initial velocity of the probe, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br />
   ***gradle runVelocity*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/runVelocity.png)**
   
   ### Experiments output
   To watch experiments with solvers, run this command on the terminal inside the main directory (*You need to see "src" folder*): <br />
   ***gradle runExperiments*** <br />
   **![Example](https://github.com/GasparKuper/Project2_Group2/tree/main/readmeImages/runExperiments.png)**
   

  
  ## Experiments with solvers and brute force
