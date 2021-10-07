package amiiBot;

import java.util.ArrayList;

public class MasterList {
	@SuppressWarnings("unchecked")
	ArrayList<Amiibo>[][] masterList = new ArrayList[TypeList.values().length][SeriesList.values().length];

	public void createMasterList() {
		for (int i = 0; i < 1; i++) {
			Amiibo newAmiibo1 = new Amiibo("Mario", TypeList.FIGURE, SeriesList.MARIO);
			Amiibo newAmiibo2 = new Amiibo("Luigi", TypeList.FIGURE, SeriesList.MARIO);
			Amiibo newAmiibo3 = new Amiibo("Toad", TypeList.FIGURE, SeriesList.MARIO);
			Amiibo newAmiibo4 = new Amiibo("Inkling Girl", TypeList.FIGURE, SeriesList.SPLATOON);
			Amiibo newAmiibo5 = new Amiibo("Inkling Boy", TypeList.FIGURE, SeriesList.SPLATOON);
			Amiibo newAmiibo6 = new Amiibo("Inking Squid", TypeList.FIGURE, SeriesList.SPLATOON);
			addAmiiboToList(newAmiibo1);
			addAmiiboToList(newAmiibo2);
			addAmiiboToList(newAmiibo3);
			addAmiiboToList(newAmiibo4);
			addAmiiboToList(newAmiibo5);
			addAmiiboToList(newAmiibo6);
		}

	}

	public void addAmiiboToList(Amiibo amiiboToAdd) {
		int type = amiiboToAdd.getType().typeToInt(amiiboToAdd.getType());
		int series = amiiboToAdd.getSeries().seriesToInt(amiiboToAdd.getSeries());
		masterList[type][series].add(amiiboToAdd);
	}
}
