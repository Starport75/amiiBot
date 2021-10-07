package amiiBot;

import java.util.ArrayList;

public class MasterList {
	@SuppressWarnings("unchecked")
	ArrayList<Amiibo>[][] masterList = new ArrayList[TypeEnum.values().length][SeriesEnum.values().length];

	public void createMasterList() {
		for (int i = 0; i < 1; i++) {
			Amiibo newAmiibo1 = new Amiibo("Mario", TypeEnum.FIGURE, SeriesEnum.MARIO);
			Amiibo newAmiibo2 = new Amiibo("Luigi", TypeEnum.FIGURE, SeriesEnum.MARIO);
			Amiibo newAmiibo3 = new Amiibo("Toad", TypeEnum.FIGURE, SeriesEnum.MARIO);
			Amiibo newAmiibo4 = new Amiibo("Inkling Girl", TypeEnum.FIGURE, SeriesEnum.SPLATOON);
			Amiibo newAmiibo5 = new Amiibo("Inkling Boy", TypeEnum.FIGURE, SeriesEnum.SPLATOON);
			Amiibo newAmiibo6 = new Amiibo("Inking Squid", TypeEnum.FIGURE, SeriesEnum.SPLATOON);
			addAmiiboToList(newAmiibo1);
			addAmiiboToList(newAmiibo2);
			addAmiiboToList(newAmiibo3);
			addAmiiboToList(newAmiibo4);
			addAmiiboToList(newAmiibo5);
			addAmiiboToList(newAmiibo6);
		}

	}

	public void addAmiiboToList(Amiibo amiiboToAdd) {
		int type = amiiboToAdd.getType().typeToInt();
		int series = amiiboToAdd.getSeries().seriesToInt();
		masterList[type][series].add(amiiboToAdd);
	}
}
