package bot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Getter
@Component
public class GuildAudioPlayerManager {
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public GuildAudioPlayerManager(AudioPlayerManager playerManager) {
        this.playerManager = playerManager;
        this.musicManagers = new HashMap<>();
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public synchronized BlockingQueue<AudioTrack> getQueue(Guild guild) {
        return musicManagers.get(Long.parseLong(guild.getId())).scheduler.getQueue();
    }
}
