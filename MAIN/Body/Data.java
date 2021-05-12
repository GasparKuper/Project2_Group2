package MAIN.Body;


import java.util.LinkedList;

public class Data {
    public LinkedList<PlanetBody> planets;

    /**
     * Create a data of the solar system
     */
    public Data(){

        planets = new LinkedList<>();

        //Sun
        planets.add(new PlanetBody(1.988500e30,
                new Vector3d(-680784785.147, 1079960529.0, 6577801.8963),
                new Vector3d(-14.2051117403, -4.95472061105, 0.39940752142)));
        //Mercury
        planets.add(new PlanetBody( 3.302e23,
                new Vector3d(5941270.49299,  -68018045514.2,  -5702728562.92),
                new Vector3d(38925.8518673, 2978.34216955, -3327.96432208)));
        //Venus
        planets.add(new PlanetBody(4.8685e24,
                new Vector3d(-94353561181.3, 53503550623.6,  6131466820.35),
                new Vector3d(-17264.0429114, -30734.3251661, 574.178306439)));
        //Earth
        planets.add(new PlanetBody(5.97219e24,
                new Vector3d(-147192316663.54238892,  -28610002992.464771271, 8291942.4644112586975),
                new Vector3d(5427.1933760188148881, -29310.566234715199244, 0.65751148935788705785)));
        //Moon
        planets.add(new PlanetBody(7.349e22,
                new Vector3d(-147234496959.0, -28225828445.3, 10541669.8367),
                new Vector3d(4433.12157588, -29484.5361603, 88.9659486734)));
        //Mars
        planets.add(new PlanetBody( 6.4171e23,
                new Vector3d(-36156389215.3, -216763303705.0, -3687670305.94),
                new Vector3d(24815.5197512, -1816.36800546, -646.732161902)));
        //Jupiter
        planets.add(new PlanetBody( 1.89813e27,
                new Vector3d(178130313859.0, -755111843625.0, -853283852.447),
                new Vector3d(12558.5255466, 3622.68020612, -295.862040413)));
        //Saturn
        planets.add(new PlanetBody( 5.6834e26,
                new Vector3d(632864664150.0, -1.3581728e+12, -1578520137.93),
                new Vector3d(8220.84218655, 4052.13737898, -397.622471927)));
        //Titan
        planets.add(new PlanetBody( 1.34553e23,
                new Vector3d(6.332873118527889e+11,  -1.357175556995868e+12, -2.134637041453660e+09),
                new Vector3d(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02)));
        //Uranus
        planets.add(new PlanetBody( 8.6813e25,
                new Vector3d(2.395195716207961E+12, 1.744451068474438E+12, -2.455128792996490E+10),
                new Vector3d(-4.059468388338293E+03, 5.187467342275506E+03, 7.182537902566288E+01)));
        //Neptune
        planets.add(new PlanetBody(1.02413e26,
                new Vector3d(4.382692942729912E+12, -9.093501655460005E+11, -8.227728929321569E+10),
                new Vector3d(1.068410753078312E+03, 5.354959504463812E+03, -1.343918419749561E+02)));
    }

    /**
     * Gets planets
     * @return Planets of the solar system
     */
    public LinkedList<PlanetBody> getPlanets(){
        return planets;
    }
}
