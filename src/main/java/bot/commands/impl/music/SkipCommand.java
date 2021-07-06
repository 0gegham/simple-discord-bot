package bot.commands.impl.music;

import bot.audio.GuildAudioPlayerManager;
import bot.audio.GuildMusicManager;
import bot.commands.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkipCommand implements ICommand {
    private final GuildAudioPlayerManager guildAudioPlayerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        GuildMusicManager manager = guildAudioPlayerManager.getGuildAudioPlayer(channel.getGuild());
        manager.scheduler.nextTrack();
        channel.sendMessage("Skipped to next track.").queue();
    }
}
