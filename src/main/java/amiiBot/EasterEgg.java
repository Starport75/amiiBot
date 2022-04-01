package amiiBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class EasterEgg {

	ArrayList<String[]> imageList = new ArrayList<String[]>();
	ArrayList<String> eggList = new ArrayList<String>();
	boolean active = true;

	public EasterEgg(){

		File eeFile = new File("src\\main\\resources\\easterEgg.dat");
		String fullString;
		String url = null;
		String name = null;
		String series = null;
		Scanner eeScanner = null;
		try {
			eeScanner = new Scanner(eeFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (eeScanner.hasNextLine()) {
		fullString = eeScanner.nextLine();
		url = fullString.substring(0, fullString.indexOf('<'));
		name = fullString.substring(fullString.indexOf('<') + 1, fullString.indexOf('>')).toLowerCase();
		series = fullString.substring(fullString.indexOf('>') + 1).toLowerCase();
		imageList.add(new String[] {url, name, series});
		}
		eeScanner.close();
	}
	
	public void addEasterEgg(UserAmiiboList amiiboList) {
		for (int i = 0; i < imageList.size(); i++) {
			amiiboList.getAmiibo(imageList.get(i)[1], imageList.get(i)[2]).setEEImage(imageList.get(i)[0]);
		}
	}
	
	public String setEE(boolean bool, String discordID) {
		if (bool) {
			if (!eggList.contains(discordID)) {
				eggList.add(discordID);
				return "You sense a disquieting metamorphosis... you now have ligma!\n*...whatever that means*";
			} else {
				return "Silly goose, you already have ligma!";
			}
		} else {
			eggList.remove(discordID);
			return "You feel a weight off your shoulders. You no longer have ligma!";
		}
	}
	
	public boolean getEE(String discordID) {
		LocalDate date = LocalDate.now();
		return eggList.contains(discordID) || (date.getDayOfMonth() == 1 && date.getMonthValue() == 4);
	}
	

    
    public boolean isActive() {
    	return active;
    }
    
}
