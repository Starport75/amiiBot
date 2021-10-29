package amiiBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EasterEgg {

	ArrayList<String[]> imageList = new ArrayList<String[]>();

	public EasterEgg() throws FileNotFoundException {

		File eeFile = new File("src\\main\\resources\\easterEgg.txt");
		String fullString;
		String url = null;
		String name = null;
		String series = null;
		Scanner eeScanner = new Scanner(eeFile);
		
		while (eeScanner.hasNextLine()) {
		fullString = eeScanner.nextLine();
		System.out.println(fullString);
		url = fullString.substring(0, fullString.indexOf('<'));
		name = fullString.substring(fullString.indexOf('<') + 1, fullString.indexOf('>'));
		series = fullString.substring(fullString.indexOf('>') + 1);
		imageList.add(new String[] {url, name, series});
		}
		eeScanner.close();
	}
	
	public void addEasterEgg(UserAmiiboList amiiboList) {
		for (int i = 0; i < imageList.size(); i++) {
			System.out.println("Name: " + imageList.get(i)[1] + ", Series: " + imageList.get(i)[2]);
			amiiboList.getAmiibo(imageList.get(i)[1], imageList.get(i)[2]).setEEImage(imageList.get(i)[0]);
		}
	}
}
