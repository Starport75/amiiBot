package amiiBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MasterList {

	ArrayList<Amiibo>[][] masterList;

	@SuppressWarnings("unchecked")
	public MasterList() {
		masterList = new ArrayList[TypeEnum.getNumOfTypes()][];
		for (int i = 0; i < TypeEnum.values().length; i++) {
			masterList[i] = new ArrayList[SeriesEnum.getNumOfType(TypeEnum.intToType(i))];
			for (int j = 0; j < SeriesEnum.getNumOfType(TypeEnum.intToType(i)); j++) {
				masterList[i][j] = new ArrayList<Amiibo>();
			}
		}
	}

	public void updateMasterList() {

		String name;
		TypeEnum type;
		SeriesEnum series;
		String wholeLine;

		File amiiboDatabaseFile = new File("src\\main\\resources\\amiiboDatabase.bin");
		try {
			Scanner amiiboDatabaseScanner = new Scanner(amiiboDatabaseFile);
			while (amiiboDatabaseScanner.hasNextLine()) {
				wholeLine = amiiboDatabaseScanner.nextLine();
				name = wholeLine.substring(0, wholeLine.indexOf("/"));
				series = SeriesEnum.valueOf(wholeLine.substring(wholeLine.indexOf("/") + 1));
				type = series.amiiboType();
				addAmiiboToList(new Amiibo(name, type, series));
			}
			amiiboDatabaseScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("AMIIBODATABASE NOT FOUND");
			e.printStackTrace();
		}
	}

	public ArrayList<Amiibo>[][] getMasterList() {
		return masterList;
	}

	public void addAmiiboToList(Amiibo amiiboToAdd) {
		int type = amiiboToAdd.getType().typeToInt();
		int series = amiiboToAdd.getSeries().seriesToInt();
		System.out.println("Type = " + type + " (" + TypeEnum.intToType(type) + "), Series = " + series + ", ("
				+ SeriesEnum.intToSeries(series) + ")");

		masterList[type][series].add(amiiboToAdd);
	}
}
