package bot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

@RequiredArgsConstructor
public class AudioLoadResultHandlerImpl implements AudioLoadResultHandler {

    private final TextChannel channel;
    private final GuildMusicManager manager;
    private final String trackUrl;
    private final String voiceChannelById;

    @Override
    public void trackLoaded(AudioTrack track) {
        channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
        play(channel.getGuild(), manager, track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
        play(channel.getGuild(), manager, firstTrack);
    }

    @Override
    public void noMatches() {
        channel.sendMessage("Nothing found by " + trackUrl).queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToChannel(guild.getAudioManager());
        musicManager.scheduler.queue(track);
    }

    private void connectToChannel(AudioManager audioManager) {
        if (!audioManager.isConnected()) {
            VoiceChannel voiceChannelById = audioManager.getGuild().getVoiceChannelById(this.voiceChannelById);
            audioManager.openAudioConnection(voiceChannelById);
        }
    }
}
