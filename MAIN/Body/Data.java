package MAIN.Body;

public class Data {

    public PlanetBody[] SolarSystem(){
        PlanetBody[] planets = new PlanetBody[12];
        //Object
        planets[0] = new PlanetBody(1,
                new PointCoordinate(0, 0, 0),
                new Vector3D(0, 0, 0));
        //Sun
        planets[1] = new PlanetBody(
                1.988500e30,
                new PointCoordinate(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06),
                new Vector3D(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01));
        //Mercury
        planets[2] = new PlanetBody(3.302e23,
                new PointCoordinate(6.047855986424127e+06,  -6.801800047868888e+10,  -5.702742359714534e+09),
                new Vector3D(3.892585189044652e+04, 2.978342247012996e+03, -3.327964151414740e+03));
        //Venus
        planets[3] = new PlanetBody(4.8685e24,
                new PointCoordinate(-9.435345478592035e+10, 5.350359551033670e+10,  6.131453014410347e+09),
                new Vector3D(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
        //Earth
        planets[4] = new PlanetBody(5.97219e24,
                new PointCoordinate(-1.471922101663588e+11,  -2.860995816266412e+10, 8.278183193596080e+06),
                new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
        //Moon
        planets[5] = new PlanetBody(7.349e22,
                new PointCoordinate(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07),
                new Vector3D(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01));
        //Mars
        planets[6] = new PlanetBody(6.4171e23,
                new PointCoordinate(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09),
                new Vector3D(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02));
        //Jupiter
        planets[7] = new PlanetBody(1.89813e27,
                new PointCoordinate(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08),
                new Vector3D(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02));;
        //Saturn
        planets[8] = new PlanetBody(5.6834e26,
                new PointCoordinate(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09),
                new Vector3D(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02));
        //Titan
        planets[9] = new PlanetBody(1.34553e23,
                new PointCoordinate(6.332873118527889e+11,  -1.357175556995868e+12, -2.134637041453660e+09),
                new Vector3D(3.056877965721629e+03, 6.125612956428791e+03, -9.523587380845593e+02));
        //Uranus
        planets[10] = new PlanetBody(8.6813e25,
                new PointCoordinate(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10),
                new Vector3D(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01));
        //Neptune
        planets[11] = new PlanetBody(1.02413e26,
                new PointCoordinate(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10),
                new Vector3D(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02));

        return planets;
    }
}