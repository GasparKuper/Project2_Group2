/**
 * Scale: 1 = 1e+09
 **/

package MAIN.Body;

import MAIN.Interfaces.Vector3dInterface;

public class Initialize{

	private Vector3dInterface[][] planets;
	public Initialize(){
		String[] planetNames = {"Sun","Mercury","Venus","Earth","Moon","Mars","Jupiter","Saturn","Titan","Uranus","Neptune"};
		planets = new Vector3dInterface[11][2];
		planets[0][0] = new Vector3d(-0.6806783239281648, 1.080005533878725, 0.006564012751690170);
		planets[0][1] = new Vector3d(-0.00000001420511669610689, -0.000000004954714716629277, 0.0000000003994237625449041);
		planets[1][0] = new Vector3d(0.006047855986424127,  -68.01800047868888,  -5.702742359714534);
		planets[1][1] = new Vector3d(0.00003892585189044652, 0.000002978342247012996, -0.000003327964151414740);
		planets[2][0] = new Vector3d(-94.35345478592035, 53.50359551033670,  6.131453014410347);
		planets[2][1] = new Vector3d(-0.00001726404287724406, -0.00003073432518238123, 0.0000000000005741783385280979);
		planets[3][0] = new Vector3d(-147.1922101663588,  -28.60995816266412, 0.008278183193596080);
		planets[3][1] = new Vector3d(0.000005427193405797901, -0.00002931056622265021, 0.0000000006575428158157592);
		planets[4][0] = new Vector3d(-147.2343904597218, -28.22578361503422, 0.01052790970065631);
		planets[4][1] = new Vector3d(0.000004433121605215677, -0.00002948453614110320, 0.00000008896598225322805);
		planets[5][0] = new Vector3d(-36.15638921529161, -216.7633037046744, -3.687670305939779);
		planets[5][1] = new Vector3d(0.00002481551975121696, -0.000001816368005464070, -0.0000006467321619018108);
		planets[6][0] = new Vector3d(178.1303138592153, -755.1118436250277, -0.8532838524802327);
		planets[6][1] = new Vector3d(0.00001255852555185220, 0.000003622680192790968, -0.0000002958620380112444);
		planets[7][0] = new Vector3d(632.8646641500651, -1358.172804527507, -1.578520137930810);
		planets[7][1] = new Vector3d(0.000008220842186554890, 0.000004052137378979608, -0.0000003976224719266916);
		planets[8][0] = new Vector3d(633.2873118527889,  -1357.175556995868, -2.134637041453660);
		planets[8][1] = new Vector3d(0.000003056877965721629, 0.000006125612956428791, -0.0000009523587380845593);
		planets[9][0] = new Vector3d(2395.195786685187, 1744.450959214586, -24.55116324031639);
		planets[9][1] = new Vector3d(-0.000004059468635313243, 0.000005187467354884825, 0.00000007182516236837899);
		planets[10][0] = new Vector3d(4382.692942729203, -909.3501655486243, -82.27728929479486);
		planets[10][1] = new Vector3d(0.000001068410720964204, 0.000005354959501569486, -0.0000001343918199987533);

	}

	public Vector3dInterface[][] getPlanets() {
		return this.planets;
	}
}