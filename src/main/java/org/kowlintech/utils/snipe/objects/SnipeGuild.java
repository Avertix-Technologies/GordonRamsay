package org.kowlintech.utils.snipe.objects;

import net.dv8tion.jda.api.entities.Guild;

public class SnipeGuild {

    private Guild guild;
    private boolean enabled;

    public SnipeGuild(Guild guild, boolean enabled) {
        this.guild = guild;
        this.enabled = enabled;
    }

    public Guild getRawGuild() {
        return guild;
    }

    public boolean isSnipeEnabled() {
        return enabled;
    }
}
