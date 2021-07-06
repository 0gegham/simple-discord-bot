package bot.commands.impl.music;

import bot.audio.GuildAudioPlayerManager;
import bot.commands.ICommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class ClearCommand implements ICommand {

    private final GuildAudioPlayerManager guildAudioPlayerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        BlockingQueue<AudioTrack> queue = guildAudioPlayerManager.getQueue(event.getGuild());
        if (!queue.isEmpty()) {
            guildAudioPlayerManager.getQueue(event.getGuild()).clear();
            event.getChannel().sendMessage("Done!").queue();
        }
    }
}
