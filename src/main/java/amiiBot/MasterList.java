package amiiBot;

import java.util.ArrayList;

public class MasterList {
	
	ArrayList<Amiibo>[][] masterList;
			
	@SuppressWarnings("unchecked")
	public MasterList() {
		masterList = new ArrayList[TypeEnum.values().length][];
		for (int i = 0; i < TypeEnum.values().length; i++) {
			masterList[i] = new ArrayList[SeriesEnum.getNumOfType(TypeEnum.intToType(i))];
		}
	}

	public void updateMasterList() {
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
	
	public ArrayList<Amiibo>[][] getMasterList(){
		return masterList;
	}

	public void addAmiiboToList(Amiibo amiiboToAdd) {
		int type = amiiboToAdd.getType().typeToInt();
		int series = amiiboToAdd.getSeries().seriesToInt();
		masterList[type][series].add(amiiboToAdd);
	}	
}
