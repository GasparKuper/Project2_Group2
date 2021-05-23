package GUI.Charts;

import Body.Data;
import Body.PlanetBody;
import Body.State;
import Body.Vector3d;
import Interfaces.ODESolverInterface;
import Interfaces.Vector3dInterface;
import ODESolver.ODEFunction;
import ODESolver.ODESolver;
import GUI.RunGui.Run;
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
import static Constant.Constant.SOLVER;

import java.util.ArrayList;
import java.util.LinkedList;


public class LineChartSample extends Application {

    private LineChart<Number,Number> lineChart;

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
        xAxis.setLabel("Days");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        SOLVER = 3;
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);

        lineChart.setTitle("Vector 3D");

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
            lineChart.getData().clear();
            SOLVER = 1;
            this.trajectory = trajectory();
        });

        //Implicit Euler
        eulerImplicit_solver.setOnAction(e -> {
            lineChart.getData().clear();
            SOLVER = 2;
            this.trajectory = trajectory();
        });

        //Velocity-Verlet
        verletVelocity_solver.setOnAction(e -> {
            lineChart.getData().clear();
            SOLVER = 3;
            this.trajectory = trajectory();
        });

        //4th-Runge-Kutta
        runge_solver.setOnAction(e -> {
            lineChart.getData().clear();
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

        //SUN BAR
        MenuItem sunV = new MenuItem("Sun Velocity");
        MenuItem sunP = new MenuItem("Sun Position");
        sunV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Sun", 0, trajectory, false));
        });
        sunP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Sun", 0, trajectory, true));
        });
        sun.getItems().add(sunP);
        sun.getItems().add(sunV);

        //MERCURY BAR
        MenuItem mercuryV = new MenuItem("Mercury Velocity");
        MenuItem mercuryP = new MenuItem("Mercury Position");
        mercuryV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Mercury", 1, trajectory, false));
        });
        mercuryP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Mercury", 1, trajectory, true));
        });
        mercury.getItems().add(mercuryP);
        mercury.getItems().add(mercuryV);

        //VENUS BAR
        MenuItem venusV = new MenuItem("Venus Velocity");
        MenuItem venusP = new MenuItem("Venus Position");
        venusV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Venus", 2, trajectory, false));
        });
        venusP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Venus", 2, trajectory, true));
        });
        venus.getItems().add(venusP);
        venus.getItems().add(venusV);

        //EARTH BAR
        MenuItem earthV = new MenuItem("Earth Velocity");
        MenuItem earthP = new MenuItem("Earth Position");
        earthV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Earth", 3, trajectory, false));
        });
        earthP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Earth", 3, trajectory, true));
        });
        earth.getItems().add(earthP);
        earth.getItems().add(earthV);

        //MOON BAR
        MenuItem moonV = new MenuItem("Moon Velocity");
        MenuItem moonP = new MenuItem("Moon Position");
        moonV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Moon", 4, trajectory, false));
        });
        moonP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Moon", 4, trajectory, true));
        });
        moon.getItems().add(moonP);
        moon.getItems().add(moonV);

        //MARS BAR
        MenuItem marsV = new MenuItem("Mars Velocity");
        MenuItem marsP = new MenuItem("Mars Position");
        marsV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Mars", 5, trajectory, false));
        });
        marsP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Mars", 5, trajectory, true));
        });
        mars.getItems().add(marsP);
        mars.getItems().add(marsV);

        //JUPITER BAR
        MenuItem jupiterV = new MenuItem("Jupiter Velocity");
        MenuItem jupiterP = new MenuItem("Jupiter Position");
        jupiterV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Jupiter", 6, trajectory, false));
        });
        jupiterP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Jupiter", 6, trajectory, true));
        });
        jupiter.getItems().add(jupiterP);
        jupiter.getItems().add(jupiterV);

        //SATURN BAR
        MenuItem saturnV = new MenuItem("Saturn Velocity");
        MenuItem saturnP = new MenuItem("Saturn Position");
        saturnV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Saturn", 7, trajectory, false));
        });
        saturnP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Saturn", 7, trajectory, true));
        });
        saturn.getItems().add(saturnP);
        saturn.getItems().add(saturnV);

        //TITAN BAR
        MenuItem titanV = new MenuItem("Titan Velocity");
        MenuItem titanP = new MenuItem("Titan Position");
        titanV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Titan", 8, trajectory, false));
        });
        titanP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Titan", 8, trajectory, true));
        });
        titan.getItems().add(titanP);
        titan.getItems().add(titanV);

        //URANUS BAR
        MenuItem uranusV = new MenuItem("Uranus Velocity");
        MenuItem uranusP = new MenuItem("Uranus Position");
        uranusV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Uranus", 9, trajectory, false));
        });
        uranusP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Uranus", 9, trajectory, true));
        });
        uranus.getItems().add(uranusP);
        uranus.getItems().add(uranusV);

        //NEPTUNE BAR
        MenuItem neptuneV = new MenuItem("Neptune Velocity");
        MenuItem neptuneP = new MenuItem("Neptune Position");
        neptuneV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Vel_Neptune", 10, trajectory, false));
        });
        neptuneP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries("Pos_Neptune", 10, trajectory, true));
        });
        neptune.getItems().add(neptuneP);
        neptune.getItems().add(neptuneV);

        //PROBE BAR
        MenuItem probeV = new MenuItem("Probe Velocity");
        MenuItem probeP = new MenuItem("Probe Position");
        probeV.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries(null, 11, trajectory, false));
        });
        probeP.setOnAction(e -> {
            lineChart.getData().clear();
            lineChart.getData().addAll(createSeries(null, 11, trajectory, true));
        });
        probe.getItems().add(probeP);
        probe.getItems().add(probeV);

        //Adds of bars into a MenuBar
        menuBar.getMenus().add(sun);
        menuBar.getMenus().add(mercury);
        menuBar.getMenus().add(venus);
        menuBar.getMenus().add(earth);
        menuBar.getMenus().add(moon);
        menuBar.getMenus().add(mars);
        menuBar.getMenus().add(jupiter);
        menuBar.getMenus().add(saturn);
        menuBar.getMenus().add(titan);
        menuBar.getMenus().add(uranus);
        menuBar.getMenus().add(neptune);
        menuBar.getMenus().add(probe);
        menuBar.getMenus().add(exit);
        menuBar.getMenus().add(solver);

        return menuBar;
    }

    /**
     *
     * @param planet Name of the planet
     * @param numberPlanet index of the planet
     * @param trajectory Data of trajectories
     * @param flag TRUE = Position, FALSE = VEloicty
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
     * @return Trajectories of all planets and the probe
     */
    private State[] trajectory(){
        double day = 24*60*60;
        double year = 365.25*day;
        ODEFunction odeFunction = new ODEFunction();

        Data data = new Data();

        LinkedList<PlanetBody> solarSystem = data.getPlanets();

        Vector3dInterface probe_relative_position = new Vector3d(35760.650634765625,-48159.48486328125,-604.095458984375);
        Vector3dInterface probe_relative_velocity = new Vector3d(4301000.0,-4692000.0,-276000.0);

        State launchPosition = new State(15000, probe_relative_position, probe_relative_velocity, solarSystem, true);
        ODESolverInterface simulator = new ODESolver();

        return  (State[]) simulator.solve(odeFunction, launchPosition, year, day);
    }
}