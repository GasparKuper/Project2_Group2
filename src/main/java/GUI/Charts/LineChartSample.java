package GUI.Charts;

import Body.Planets.Data;
import Body.Planets.PlanetBody;
import Body.SpaceCrafts.Lander;
import Body.SpaceCrafts.State;
import Body.Vector.Vector2d;
import Body.Vector.Vector3d;
import Controller.CloseLoopController.CloseLoopController;
import Controller.CloseLoopController.PhaseLandingClose;
import Interfaces.ODESolverInterface;
import Interfaces.Vector3dInterface;
import ODESolver.Function.ODEFunction;
import ODESolver.ODESolver;
import GUI.RunGui.Run;
import Run.MissionProbe;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;

import static Constant.Constant.*;

/**
 * Line chart representation of the trajectory
 */
public class LineChartSample extends Application {

    private LineChart<Number,Number> lineChart;

    private int[] series = new int[10];

    /**
     * LINE CHART, we can compare coordinates from Nasa with our coordinates
     * @param stage GUI
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart of Vector");
        //Icon
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Image/Logo.jpg")));
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Meters");
        xAxis.setLabel("Days");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        SOLVER = 3;
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);


        VBox vbox = new VBox(createMenuBar(stage), lineChart);
        VBox.setVgrow(lineChart, Priority.ALWAYS);
        Scene scene  = new Scene(vbox,800,600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  trajectory Data of the trajectories of the all planets and the probe
     */
    private State[] trajectory = trajectory();
    private final State[] trajectoryProbeToTitan = new MissionProbe().trajectoryProbeCalculationToTitan();
    private final State[] trajectoryProbeOrbitTitan = new MissionProbe().trajectoryProbeCalculationOrbitTitan(trajectoryProbeToTitan[trajectoryProbeToTitan.length-1]);
    private final State[] trajectoryProbeToEarth = new MissionProbe().trajectoryProbeCalculationToEarth(trajectoryProbeOrbitTitan[trajectoryProbeOrbitTitan.length-1]);
    private final ArrayList<Lander> trajectoryLanderCloseLoop = getTrajectoryCloseLoop();
    private ArrayList<Lander> trajectoryLanderCloseLoopWind = getTrajectoryCloseLoopWind();
    private final ArrayList<Lander> trajectoryLanderWind = getTrajectoryWind();

    /**
     * Create MenuBar in the GUI
     * @param stage GUI
     * @return MenuBars
     */
    private MenuBar createMenuBar(Stage stage){

        MenuBar menuBar = new MenuBar();

        Menu solver = new Menu("Solvers");
        MenuItem eulerSymplectic_solver = new MenuItem("Symplectic Euler");
        MenuItem eulerImplicit_solver = new MenuItem("Implicit Euler");
        MenuItem verletVelocity_solver = new MenuItem("Velocity-Verlet");
        MenuItem runge_solver = new MenuItem("4th-Runge-Kutta");

        //Symplectic Euler
        eulerSymplectic_solver.setOnAction(e -> {
            update("Days", "Meters");
            SOLVER = 1;
            this.trajectory = trajectory();
        });

        //Implicit Euler
        eulerImplicit_solver.setOnAction(e -> {
            update("Days", "Meters");
            SOLVER = 2;
            this.trajectory = trajectory();
        });

        //Velocity-Verlet
        verletVelocity_solver.setOnAction(e -> {
            update("Days", "Meters");
            SOLVER = 3;
            this.trajectory = trajectory();
        });

        //4th-Runge-Kutta
        runge_solver.setOnAction(e -> {
            update("Days", "Meters");
            SOLVER = 4;
            this.trajectory = trajectory();
        });

        solver.getItems().add(eulerSymplectic_solver);
        solver.getItems().add(eulerImplicit_solver);
        solver.getItems().add(verletVelocity_solver);
        solver.getItems().add(runge_solver);

        //EXIT BUTTON
        Menu exit = new Menu("EXIT");
        MenuItem exit1 = new MenuItem("EXIT");
        exit1.setOnAction(e -> {
            Run run = new Run();
            SOLVER = 3;
            try {
                stage.close();
                run.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        exit.getItems().add(exit1);

        //LIST of the planets
        Menu sun = new Menu("Sun");
        Menu mercury = new Menu("Mercury");
        Menu venus = new Menu("Venus");
        Menu earth= new Menu("Earth");
        Menu moon = new Menu("Moon");
        Menu mars = new Menu("Mars");
        Menu jupiter = new Menu("Jupiter");
        Menu saturn = new Menu("Saturn");
        Menu titan = new Menu("Titan");
        Menu uranus = new Menu("Uranus");
        Menu neptune = new Menu("Neptune");
        Menu probe = new Menu("Probe");
        Menu lander = new Menu("Lander");

        //SUN BAR
        MenuItem sunV = new MenuItem("Sun Velocity");
        MenuItem sunP = new MenuItem("Sun Position");
        sunV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Sun", 0, trajectory, false));
        });
        sunP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Sun", 0, trajectory, true));
        });
        sun.getItems().add(sunP);
        sun.getItems().add(sunV);

        //MERCURY BAR
        MenuItem mercuryV = new MenuItem("Mercury Velocity");
        MenuItem mercuryP = new MenuItem("Mercury Position");
        mercuryV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Mercury", 1, trajectory, false));
        });
        mercuryP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Mercury", 1, trajectory, true));
        });
        mercury.getItems().add(mercuryP);
        mercury.getItems().add(mercuryV);

        //VENUS BAR
        MenuItem venusV = new MenuItem("Venus Velocity");
        MenuItem venusP = new MenuItem("Venus Position");
        venusV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Venus", 2, trajectory, false));
        });
        venusP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Venus", 2, trajectory, true));
        });
        venus.getItems().add(venusP);
        venus.getItems().add(venusV);

        //EARTH BAR
        MenuItem earthV = new MenuItem("Earth Velocity");
        MenuItem earthP = new MenuItem("Earth Position");
        earthV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Earth", 3, trajectory, false));
        });
        earthP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Earth", 3, trajectory, true));
        });
        earth.getItems().add(earthP);
        earth.getItems().add(earthV);

        //MOON BAR
        MenuItem moonV = new MenuItem("Moon Velocity");
        MenuItem moonP = new MenuItem("Moon Position");
        moonV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Moon", 4, trajectory, false));
        });
        moonP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Moon", 4, trajectory, true));
        });
        moon.getItems().add(moonP);
        moon.getItems().add(moonV);

        //MARS BAR
        MenuItem marsV = new MenuItem("Mars Velocity");
        MenuItem marsP = new MenuItem("Mars Position");
        marsV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Mars", 5, trajectory, false));
        });
        marsP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Mars", 5, trajectory, true));
        });
        mars.getItems().add(marsP);
        mars.getItems().add(marsV);

        //JUPITER BAR
        MenuItem jupiterV = new MenuItem("Jupiter Velocity");
        MenuItem jupiterP = new MenuItem("Jupiter Position");
        jupiterV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Jupiter", 6, trajectory, false));
        });
        jupiterP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Jupiter", 6, trajectory, true));
        });
        jupiter.getItems().add(jupiterP);
        jupiter.getItems().add(jupiterV);

        //SATURN BAR
        MenuItem saturnV = new MenuItem("Saturn Velocity");
        MenuItem saturnP = new MenuItem("Saturn Position");
        saturnV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Saturn", 7, trajectory, false));
        });
        saturnP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Saturn", 7, trajectory, true));
        });
        saturn.getItems().add(saturnP);
        saturn.getItems().add(saturnV);

        //TITAN BAR
        MenuItem titanV = new MenuItem("Titan Velocity");
        MenuItem titanP = new MenuItem("Titan Position");
        titanV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Titan", 8, trajectory, false));
        });
        titanP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Titan", 8, trajectory, true));
        });
        titan.getItems().add(titanP);
        titan.getItems().add(titanV);

        //URANUS BAR
        MenuItem uranusV = new MenuItem("Uranus Velocity");
        MenuItem uranusP = new MenuItem("Uranus Position");
        uranusV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Uranus", 9, trajectory, false));
        });
        uranusP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Uranus", 9, trajectory, true));
        });
        uranus.getItems().add(uranusP);
        uranus.getItems().add(uranusV);

        //NEPTUNE BAR
        MenuItem neptuneV = new MenuItem("Neptune Velocity");
        MenuItem neptuneP = new MenuItem("Neptune Position");
        neptuneV.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeries("Vel_Neptune", 10, trajectory, false));
        });
        neptuneP.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeries("Pos_Neptune", 10, trajectory, true));
        });
        neptune.getItems().add(neptuneP);
        neptune.getItems().add(neptuneV);

        //PROBE BAR
        //To Titan
        Menu toTitan = new Menu("To Titan");
        MenuItem probeVToTitan = new MenuItem("Probe Velocity");
        MenuItem probePToTitan = new MenuItem("Probe Position");
        probeVToTitan.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeToTitan, false, 6*24));
        });
        probePToTitan.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeToTitan, true, 6*24));
        });
        toTitan.getItems().add(probePToTitan);
        toTitan.getItems().add(probeVToTitan);
        probe.getItems().add(toTitan);

        //Orbit Titan
        Menu orbitTitan = new Menu("Orbit Titan");
        MenuItem probeVOrbitTitan = new MenuItem("Probe Velocity");
        MenuItem probePOrbitTitan = new MenuItem("Probe Position");
        probeVOrbitTitan.setOnAction(e -> {
            update("1 point = 2 hours 24 minutes", "Meters per seconds");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeOrbitTitan, false, 6*24));
        });
        probePOrbitTitan.setOnAction(e -> {
            update("1 point = 2 hours 24 minutes", "Meters");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeOrbitTitan, true, 6*24));
        });
        orbitTitan.getItems().add(probePOrbitTitan);
        orbitTitan.getItems().add(probeVOrbitTitan);
        probe.getItems().add(orbitTitan);

        //To Earth
        Menu toEarth = new Menu("To Earth");
        MenuItem probeVToEarth = new MenuItem("Probe Velocity");
        MenuItem probePToEarth = new MenuItem("Probe Position");
        probeVToEarth.setOnAction(e -> {
            update("Days", "Meters per seconds");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeToEarth, false, 6*24));
        });
        probePToEarth.setOnAction(e -> {
            update("Days", "Meters");
            lineChart.getData().addAll(createSeriesProbe(trajectoryProbeToEarth, true, 6*24));
        });
        toEarth.getItems().add(probePToEarth);
        toEarth.getItems().add(probeVToEarth);
        probe.getItems().add(toEarth);

        //Landing Bar
        Menu closeLoop = new Menu("Close-Loop");
        Menu Wind = new Menu("Wind");
        Menu closeWind = new Menu("Wind Close");
        MenuItem WindP = new MenuItem("Position");
        MenuItem WindV = new MenuItem("Velocity");
        MenuItem closeWindP = new MenuItem("Position");
        MenuItem closeWindV = new MenuItem("Velocity");
        Menu closeVacuum = new Menu("Vacuum");
        MenuItem closeVacuumP = new MenuItem("Position");
        MenuItem closeVacuumV = new MenuItem("Velocity");
        MenuItem closeVacuumRD = new MenuItem("Angle Degree");
        MenuItem closeVacuumRV = new MenuItem("Angle Velocity");

        //Wind
        WindP.setOnAction(e -> {
            update("Seconds", "Meters");
            lineChart.getData().addAll(createSeriesLanderWind(trajectoryLanderWind, true, 10));
        });
        WindV.setOnAction(e -> {
            update("Seconds", "Meters per seconds");
            lineChart.getData().addAll(createSeriesLanderWind(trajectoryLanderWind, false, 10));
        });

        //Wind Close
        closeWindP.setOnAction(e -> {
            update("Seconds", "Meters");
            lineChart.getData().addAll(createSeriesLander(trajectoryLanderCloseLoopWind, true, 10));
        });
        closeWindV.setOnAction(e -> {
            update("Seconds", "Meters per seconds");
            lineChart.getData().addAll(createSeriesLander(trajectoryLanderCloseLoopWind, false, 10));
        });

        //Vacuum
        closeVacuumP.setOnAction(e -> {
            update("Seconds", "Meters");
            lineChart.getData().addAll(createSeriesLander(trajectoryLanderCloseLoop, true, 10));
        });
        closeVacuumV.setOnAction(e -> {
            update("Seconds", "Meters per seconds");
            lineChart.getData().addAll(createSeriesLander(trajectoryLanderCloseLoop, false, 10));
        });
        closeVacuumRD.setOnAction(e -> {
            update("Seconds", "Degree");
            lineChart.getData().addAll(createSeriesLanderRotation(trajectoryLanderCloseLoop, true, 10, "Degree"));
        });
        closeVacuumRV.setOnAction(e -> {
            update("Seconds", "Meters per second");
            lineChart.getData().addAll(createSeriesLanderRotation(trajectoryLanderCloseLoop, false, 10, "Angle Velocity"));
        });
        closeLoop.getItems().add(closeVacuum);
        closeLoop.getItems().add(closeWind);
        closeWind.getItems().add(closeWindP);
        closeWind.getItems().add(closeWindV);
        Wind.getItems().add(WindP);
        Wind.getItems().add(WindV);
        closeVacuum.getItems().add(closeVacuumP);
        closeVacuum.getItems().add(closeVacuumV);
        closeVacuum.getItems().add(closeVacuumRD);
        closeVacuum.getItems().add(closeVacuumRV);
        lander.getItems().addAll(closeLoop, Wind);

        //Adds of bars into a MenuBar
        menuBar.getMenus().addAll(sun, mercury, venus, earth, moon, mars,
                jupiter, saturn, titan, uranus, neptune, probe, lander, solver, exit);

        return menuBar;
    }

    private void update(String duration, String data){
        lineChart.getData().clear();
        lineChart.getXAxis().setLabel(duration);
        lineChart.getYAxis().setLabel(data);
    }

    /**
     *
     * @param planet Name of the planet
     * @param numberPlanet index of the planet
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = Velocity
     * @return array of the series for the line chart
     */
    private ArrayList<XYChart.Series<Number, Number>> createSeries(String planet, int numberPlanet, State[] trajectory, boolean flag){
        ArrayList<XYChart.Series<Number, Number>> arrayList = new ArrayList<>();

        FileReaderXYZ file = new FileReaderXYZ();

        double[] x = file.getCord("X_" + planet);
        double[] y = file.getCord("Y_" + planet);
        double[] z = file.getCord("Z_" + planet);


        final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("X Nasa");

        final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Y Nasa");

        final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("Z Nasa");


        final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        series4.setName("X");

        final XYChart.Series<Number, Number> series5 = new XYChart.Series<>();
        series5.setName("Y");

        final XYChart.Series<Number, Number> series6 = new XYChart.Series<>();
        series6.setName("Z");

        double M = 1000;
        for (int i = 0; i < x.length; i++) {
            int point = (i+1);
            series1.getData().add(new XYChart.Data<Number, Number>(point, x[i]*M));
            series2.getData().add(new XYChart.Data<Number, Number>(point, y[i]*M));
            series3.getData().add(new XYChart.Data<Number, Number>(point, z[i]*M));
            //Solver
            Vector3d body;
            if(flag) {
                body = trajectory[i].celestialBody.get(numberPlanet).getPosition();
            } else {
                body = trajectory[i].celestialBody.get(numberPlanet).getVelocity();
            }
            series4.getData().add(new XYChart.Data<Number, Number>(point, body.getX()));
            series5.getData().add(new XYChart.Data<Number, Number>(point, body.getY()));
            series6.getData().add(new XYChart.Data<Number, Number>(point, body.getZ()));
        }

        arrayList.add(series1);
        arrayList.add(series2);
        arrayList.add(series3);
        arrayList.add(series4);
        arrayList.add(series5);
        arrayList.add(series6);

        return arrayList;
    }

    /**
     * Calculate Trajectories
     * @return Trajectories of all planets
     */
    private State[] trajectory(){
        double day = 24*60*60;
        double year = 365.25*day;
        ODEFunction odeFunction = new ODEFunction();

        Data data = new Data();

        LinkedList<PlanetBody> solarSystem = data.getPlanets();

        Vector3dInterface probe_relative_position = new Vector3d(4301000.0,-4692000.0,-276000.0);
        Vector3dInterface probe_relative_velocity = new Vector3d(5123.76070022583,-19016.060829162598,-1210.176944732666);

        State launchPosition = new State(probe_relative_position, probe_relative_velocity, solarSystem, true);
        ODESolverInterface simulator = new ODESolver();

        return  (State[]) simulator.solve(odeFunction, launchPosition, year, day);
    }

    /**
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = Velocity
     * @return array of the series for the line chart
     */
    private ArrayList<XYChart.Series<Number, Number>> createSeriesProbe(State[] trajectory, boolean flag, int scaleData){
        ArrayList<XYChart.Series<Number, Number>> arrayList = new ArrayList<>();



        final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        series4.setName("X");

        final XYChart.Series<Number, Number> series5 = new XYChart.Series<>();
        series5.setName("Y");

        final XYChart.Series<Number, Number> series6 = new XYChart.Series<>();
        series6.setName("Z");

        int scaleOneDay = trajectory.length/(scaleData);
        for (int i = 0; i < scaleOneDay; i++) {
            int point = (i+1);
            //Solver
            Vector3d body;

            if(flag) {
                body = (Vector3d) trajectory[(scaleData)*i].position;
            } else {
                body = (Vector3d) trajectory[(scaleData)*i].velocity;
            }


            series4.getData().add(new XYChart.Data<Number, Number>(point, body.getX()));
            series5.getData().add(new XYChart.Data<Number, Number>(point, body.getY()));
            series6.getData().add(new XYChart.Data<Number, Number>(point, body.getZ()));
        }


        arrayList.add(series4);
        arrayList.add(series5);
        arrayList.add(series6);

        return arrayList;
    }

    /**
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = Velocity
     * @return array of the series for the line chart
     */
    private ArrayList<XYChart.Series<Number, Number>> createSeriesLander(ArrayList<Lander> trajectory, boolean flag, int scaleData){
        ArrayList<XYChart.Series<Number, Number>> arrayList = new ArrayList<>();


        final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        series4.setName("X");

        final XYChart.Series<Number, Number> series5 = new XYChart.Series<>();
        series5.setName("Y");

        int scaleOneDay = trajectory.size()/(scaleData);
        for (int i = 0; i < scaleOneDay; i++) {
            int point = (i+1);
            //Solver
            Vector2d body;

            if(flag) {
                body = trajectory.get((scaleData)*i).getPosition();
            } else {
                body = trajectory.get((scaleData)*i).getVelocity();
            }


            series4.getData().add(new XYChart.Data<Number, Number>(point, body.getX()));
            series5.getData().add(new XYChart.Data<Number, Number>(point, body.getY()));
        }


        arrayList.add(series4);
        arrayList.add(series5);

        return arrayList;
    }

    /**
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = Velocity
     * @return array of the series for the line chart
     */
    private ArrayList<XYChart.Series<Number, Number>> createSeriesLanderWind(ArrayList<Lander> trajectory, boolean flag, int scaleData){
        ArrayList<XYChart.Series<Number, Number>> arrayList = new ArrayList<>();


        XYChart.Series<Number, Number> seriesL;
        for (int i = 0; i < 9; i++) {
            seriesL= new XYChart.Series<>();
            int pointSec = 0;
            int scaleOneDay = series[i+1]/(scaleData);
            for (int j = series[i]/(scaleData); j < scaleOneDay; j++) {
                int point = (pointSec + 1);
                pointSec++;
                //Solver
                Vector2d body;

                if (flag) {
                    body = trajectory.get((scaleData) * j).getPosition();
                } else {
                    body = trajectory.get((scaleData) * j).getVelocity();
                }

                seriesL.setName("X");

                seriesL.getData().add(new XYChart.Data<Number, Number>(point, body.getX()));
            }
            arrayList.add(seriesL);
        }

        return arrayList;
    }

    /**
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = Velocity
     * @return array of the series for the line chart
     */
    private ArrayList<XYChart.Series<Number, Number>> createSeriesLanderRotation(ArrayList<Lander> trajectory, boolean flag, int scaleData, String data){
        ArrayList<XYChart.Series<Number, Number>> arrayList = new ArrayList<>();


        final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
        series4.setName(data);

        int scaleOneDay = trajectory.size()/(scaleData);
        for (int i = 0; i < scaleOneDay; i++) {
            int point = (i+1);
            //Solver
            double body;

            if(flag) {
                body = trajectory.get((scaleData)*i).getRotation();
            } else {
                body = trajectory.get((scaleData)*i).getRotationVelocity();
            }


            series4.getData().add(new XYChart.Data<Number, Number>(point, body));
        }


        arrayList.add(series4);

        return arrayList;
    }

    private ArrayList<Lander> getTrajectoryCloseLoop(){
        CloseLoopController mission = new CloseLoopController();
        return mission.land();
    }

    private ArrayList<Lander> getTrajectoryCloseLoopWind(){
        CloseLoopController mission = new CloseLoopController();
        WIND = true;
        ArrayList<Lander> result = mission.getSimulation();
        WIND = false;
        return result;
    }

    private ArrayList<Lander> getTrajectoryWind(){
        Lander lander = new Lander(new Vector2d(0, 220000), new Vector2d(0, 0), 6000, 0, 0, 0);
        WIND = true;
        series[0] = 0;
        ArrayList<Lander> result = new PhaseLandingClose().phaseLanding(lander, 0.1);;
        series[1] = result.size();
        for (int i = 0; i < 8; i++) {
            ArrayList<Lander> tmp = new PhaseLandingClose().phaseLanding(lander, 0.1);;
            series[i+2] = tmp.size() + series[i+1];
            result.addAll(tmp);
        }
        WIND = false;
        return result;
    }
}