package bot.commands.impl.music;

import bot.audio.GuildAudioPlayerManager;
import bot.commands.ICommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QueueCommand implements ICommand {

    private final GuildAudioPlayerManager guildAudioPlayerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        BlockingQueue<AudioTrack> queue = guildAudioPlayerManager.getQueue(event.getGuild());

        String messageAboutQueue = "Empty List";
        if (!queue.isEmpty()) {
            messageAboutQueue = "List of music:\n```\n" + queue.stream().map(audioTrack -> audioTrack.getInfo().title)
                    .collect(Collectors.joining("\n"))  + "\n```";
        }

        event.getChannel().sendMessage(messageAboutQueue).queue();
    }
}
