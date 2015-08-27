package com.bimaas.services.outlier;

import java.io.File;
import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {

		String home = System.getProperty("user.dir");
		System.out.println(home);
		String f = home + "/src/main/resources/mvd/tempifc";
		System.out.println(f);

		try {
			PrintWriter writer = new PrintWriter(f + "/empty.te", "UTF-8");
			writer.close();
		} catch (Exception e) {

		}

		File folder = new File(f);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}
}
