package org.kowlintech.commands.misc;

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
        String apiKey = "e4e4d3476bf4449b8c2200515201808";
        String tempSystem;
        String ab;
        String inmm;
        String speed;
        if(SettingsManager.getGuildTemperatureUnit(event.getGuild().getIdLong()) == null) {
            SettingsManager.setGuildTemperatureUnit(event.getGuild().getIdLong(), "imperial");
            tempSystem = "imperial";
            event.reply(":warning: The guild temperature unit was not set. Due to this, it has been automatically set to `imperial`.");
        } else {
            tempSystem = SettingsManager.getGuildTemperatureUnit(event.getGuild().getIdLong());
        }
        if(tempSystem.contains("metric")) {
            ab = "C";
            inmm = "mm";
            speed = "kph";
        } else {
            ab = "F";
            inmm = "in";
            speed = "mph";
        }
        Unirest.get(String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", apiKey, event.getArgs().replace(" ", "%20"))).asJsonAsync(response -> {
            try {
                EmbedBuilder eb = new EmbedBuilder();
                JSONObject jsonObject = response.getBody().getObject();
                String cityName = jsonObject.getJSONObject("location").getString("name");
                eb.setTitle("Here's the weather for " + cityName + ", you fucking donkey!");
                eb.setColor(Global.COLOR);
                eb.setDescription("**" + jsonObject.getJSONObject("current").getJSONObject("condition").getString("text") + "**");

                eb.addField("Current Time", jsonObject.getJSONObject("location").getString("localtime") + " (Timezone: " + jsonObject.getJSONObject("location").getString("tz_id") + ")", true);
                eb.addField("Coordinates", "**Longitude:** " + jsonObject.getJSONObject("location").getString("lon") + ", **Latitude:** " + jsonObject.getJSONObject("location").getString("lat"), true);
                eb.addField("Region", jsonObject.getJSONObject("location").getString("region") + " (" + jsonObject.getJSONObject("location").getString("country") + ")", true);
                eb.addField("Temperature", String.format("%s °%s (Feels like: %s °%s)", jsonObject.getJSONObject("current").getString("temp_" + ab.toLowerCase()), ab, jsonObject.getJSONObject("current").getString("feelslike_" + ab.toLowerCase()), ab), true);
                eb.addField("Humidity", jsonObject.getJSONObject("current").getString("humidity") + "%", true);
                eb.addField("Estimated Precipitation Today", String.format("%s %s", jsonObject.getJSONObject("current").getString("precip_" + inmm), (inmm == "mm" ? "Millimeters" : "Inches")), true);
                eb.addField("Wind Speed", String.format("%s %s", jsonObject.getJSONObject("current").getString("wind_" + speed), speed.toUpperCase()), true);
                eb.addField("Wind Direction", jsonObject.getJSONObject("current").getString("wind_dir"), true);
                eb.setFooter("Want to change the unit system? Do g.settings tempunit <imperial/metric> | Last Updated: " + jsonObject.getJSONObject("current").getString("last_updated"));
                eb.setThumbnail("https://" + jsonObject.getJSONObject("current").getJSONObject("condition").getString("icon").replace("//", ""));
                event.reply(eb.build());
            } catch (Exception ex) {
                event.reply(EmbedHelper.buildErrorEmbed("That isn't a valid city."));
                ex.printStackTrace();
                return;
            }
        });
    }
}
