package org.kowlintech.commands.misc;

import kong.unirest.Callback;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import org.kowlintech.utils.EmbedHelper;
import org.kowlintech.utils.SettingsManager;
import org.kowlintech.utils.command.objects.Command;
import org.kowlintech.utils.command.objects.CommandEvent;
import org.kowlintech.utils.command.objects.CommandExecutor;
import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.constants.Global;

import java.sql.SQLException;

@Command(name = "weather", description = "Tells you the weather. But of course... Gordon Ramsay style.", category = Category.MISCELLANEOUS, args = "<location>")
public class WeatherCommand implements CommandExecutor {

    @Override
    public void execute(CommandEvent event) throws SQLException {
        if(event.getArgs().isEmpty()) {
            event.reply("You gotta provide a city, you fucking donkey!");
            return;
        }
        String apiKey = "608a7239e3dec30f4bf3fe0628b19ea5";
        String tempSystem;
        String ab;
        if(SettingsManager.getGuildTemperatureUnit(event.getGuild().getIdLong()) == null) {
            SettingsManager.setGuildTemperatureUnit(event.getGuild().getIdLong(), "imperial");
            tempSystem = "imperial";
            event.reply(":warning: The guild temperature unit was not set. Due to this, it has been automatically set to `imperial`.");
        } else {
            tempSystem = SettingsManager.getGuildTemperatureUnit(event.getGuild().getIdLong());
        }
        if(tempSystem.contains("metric")) {
            ab = "C";
        } else {
            ab = "F";
        }
        Unirest.get(String.format("http://api.openweathermap.org/data/2.5/weather?appid=%s&q=%s&units=%s", apiKey, event.getArgs().replace(" ", "%20"), tempSystem)).asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> response) {
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    JSONObject jsonObject = response.getBody().getObject();
                    String cityName = jsonObject.getString("name");
                    eb.setTitle("Here's the weather for " + cityName + ", you fucking donkey!");
                    eb.setColor(Global.COLOR);

                    eb.addField("Coordinates", "**Longitude:** " + jsonObject.getJSONObject("coord").getString("lon") + ", **Latitude:** " + jsonObject.getJSONObject("coord").getString("lat"), true);
                    eb.addField("Country", jsonObject.getJSONObject("sys").getString("country"), true);
                    eb.addField("Temperature", String.format("%s °%s (Min: %s °%s, Max: %s °%s)", jsonObject.getJSONObject("main").getString("temp"), ab, jsonObject.getJSONObject("main").getString("temp_min"), ab, jsonObject.getJSONObject("main").getString("temp_max"), ab), true);
                    eb.addField("Humidity", jsonObject.getJSONObject("main").getString("humidity") + "%", true);
                    eb.addField("Pressure", jsonObject.getJSONObject("main").getString("pressure"), true);
                    event.reply(eb.build());
                } catch (Exception ex) {
                    event.reply(EmbedHelper.buildErrorEmbed("That isn't a valid city."));
                    ex.printStackTrace();
                    return;
                }
            }
        });
    }
}
