package converte;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import Home.BrowsePath;

public class DotToImageConverter {
	public String DotFile, outputFile;

	public void setSourcePath(String FullPath) {
		DotFile = FullPath;
		outputFile = DotFile.concat(".jpg");
		System.out.println("Image would be at:"+outputFile);
		DotFile = DotFile + ".dot";
		System.out.println("Dot file would be at:"+DotFile);
	}

	public void ImageCreator() {
		try {
			Runtime t = Runtime.getRuntime();

			String jarPath1 = "C:\\Program Files\\Graphviz 2.28\\bin\\dot -T jpg -o" + outputFile + " " + DotFile;
			Process p1 = t.exec(jarPath1);

			BufferedReader input1 = new BufferedReader(new InputStreamReader(
					p1.getInputStream()));
			String line = null;
			while ((line = input1.readLine()) != null) {
				System.out.println(line);
			}
			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p1.getErrorStream()));
			String line1 = null;
			while ((line1 = stdError.readLine()) != null) {
				System.out.println(line1);
			}
			int exitVal2 = p1.waitFor();
			System.out.println("Exited with error code " + exitVal2);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
