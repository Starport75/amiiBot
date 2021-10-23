package amiiBot;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListAmiiboCommand extends AbstractCommand {
    String description = "Lists all amiibo in the database";
    String command = "listAmiibo";

    public EmbedBuilder getOutput(String userDiscordID, ArrayList<String> parameters) {

        String output = "";
        UserAmiiboList userCollection = new UserAmiiboList(userDiscordID);

        int seriesMin;
        int seriesMax;
        String typeName;

        if (parameters.size() < 1) {
            EmbedBuilder embed = new EmbedBuilder().setDescription(
                    "Error: No parameters defined. Command structure is !listAmiibo <Type> [optional]<Series>");
            return embed;
        }

        int typeIndex = userCollection.getTypeList().indexOf(parameters.get(0));

        if (typeIndex == -1) {
            int seriesIndex;
            if (userCollection.seriesToTypeIndex(parameters.get(0)) != -1) {
                String currType = userCollection.getTypeList().get(userCollection.seriesToTypeIndex(parameters.get(0)));
                System.out.println("currType = " + currType);
                seriesIndex = userCollection.getSeriesList(currType).indexOf(parameters.get(0));
                typeIndex = userCollection.seriesToTypeIndex(parameters.get(0));
                typeName = userCollection.getTypeList().get(typeIndex);
                System.out.println("typeIndex = " + typeIndex);
                System.out.println("seriesIndex = " + seriesIndex);
                seriesMin = seriesIndex;
                seriesMax = seriesIndex + 1;
            } else {

                EmbedBuilder embed = new EmbedBuilder()
                        .setDescription("Error: Parameter \"" + parameters.get(0) + "\" was not recognized!");
                return embed;
            }
        } else {
            typeName = userCollection.getTypeList().get(typeIndex);
            seriesMin = 0;
            seriesMax = userCollection.getNumOfSeries(typeName);
        }

        // outputs the type of amiibo listed
        output = output + "***" + userCollection.getTypeList().get(typeIndex) + ":***\n";

        for (int seriesIndex = seriesMin; seriesIndex < seriesMax; seriesIndex++) {
            String seriesName = userCollection.getSeriesList(typeName).get(seriesIndex);
            // outputs the current series being listed
            output = output + "\n**" + userCollection.getSeriesAt(seriesIndex, typeName) + ":** ";
            // outputs the number in the current series being listed
            output = output + "*(" + userCollection.getNumOfAmiibo(seriesName) + ")*\n";

            for (int amiiboIndex = 0; amiiboIndex < userCollection.getNumOfAmiibo(seriesName); amiiboIndex++) {
                // outputs the name of the amiibo
                output = output + userCollection.getAmiiboList(seriesName).get(amiiboIndex).getName();

                if (amiiboIndex < userCollection.getMasterList().get(typeIndex).get(seriesIndex).size() - 2) {

                    output = output + ", ";

                } else if (amiiboIndex == userCollection.getMasterList().get(typeIndex).get(seriesIndex).size() - 2) {
                    if (userCollection.getMasterList().get(typeIndex).get(seriesIndex).size() != 2) {

                        output = output + ",";

                    }

                    output = output + " and ";
                }
            }

            output = output + "\n";
        }

        EmbedBuilder embed = new EmbedBuilder().setDescription(output);
        updateLength(output);
        return embed;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public void updateLength(String output) {
        length = output.length();
    }

    public int getLength() {
        return length;
    }
}
