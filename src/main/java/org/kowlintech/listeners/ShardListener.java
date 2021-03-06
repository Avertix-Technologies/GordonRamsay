package org.kowlintech.listeners;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ResumedEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.kowlintech.GordonRamsay;

import java.time.Instant;

public class ShardListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        GordonRamsay.shard_start_times.remove(event.getJDA().getShardInfo().getShardId());
        GordonRamsay.shard_start_times.put(event.getJDA().getShardInfo().getShardId(), System.currentTimeMillis());

        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/817506614306275360/F_dImlHEj1NsRY4kEFpbn-idGc1oXCgAdwpBaB9tv_P3sulSte-gQAcql16ZTEslbUiD");
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Shard Logs");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        WebhookClient client = builder.build();

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Shard " + event.getJDA().getShardInfo().getShardId() + " Connected", ""))
                .setColor(0x31B404)
                .setTimestamp(Instant.now())
                .build();

        client.send(embed);
        client.close();
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/817506614306275360/F_dImlHEj1NsRY4kEFpbn-idGc1oXCgAdwpBaB9tv_P3sulSte-gQAcql16ZTEslbUiD");
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Shard Logs");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        WebhookClient client = builder.build();

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Shard " + event.getJDA().getShardInfo().getShardId() + " Shutting Down", ""))
                .setColor(0xFF0000)
                .setTimestamp(Instant.now())
                .build();

        client.send(embed);
        client.close();
    }

    @Override
    public void onResume(ResumedEvent event) {
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/817506614306275360/F_dImlHEj1NsRY4kEFpbn-idGc1oXCgAdwpBaB9tv_P3sulSte-gQAcql16ZTEslbUiD");
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Shard Logs");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        WebhookClient client = builder.build();

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Shard " + event.getJDA().getShardInfo().getShardId() + " Resumed", ""))
                .setColor(0x31B404)
                .setTimestamp(Instant.now())
                .build();

        client.send(embed);
        client.close();
    }

    @Override
    public void onDisconnect(DisconnectEvent event) {
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/817506614306275360/F_dImlHEj1NsRY4kEFpbn-idGc1oXCgAdwpBaB9tv_P3sulSte-gQAcql16ZTEslbUiD");
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Shard Logs");
            thread.setDaemon(true);
            return thread;
        });

        builder.setWait(true);
        WebhookClient client = builder.build();

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setTitle(new WebhookEmbed.EmbedTitle("Shard " + event.getJDA().getShardInfo().getShardId() + " Disconnected", ""))
                .setColor(0xFF0000)
                .setTimestamp(Instant.now())
                .build();

        client.send(embed);
        client.close();
    }
}
