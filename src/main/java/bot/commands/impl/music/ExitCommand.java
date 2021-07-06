package bot.commands.impl.music;

import bot.audio.GuildAudioPlayerManager;
import bot.commands.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExitCommand implements ICommand {

    private final GuildAudioPlayerManager guildAudioPlayerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        AudioManager audioManager = event.getChannel().getGuild().getAudioManager();
        if (audioManager.isConnected()) {
            guildAudioPlayerManager.getQueue(event.getGuild()).clear();
            audioManager.closeAudioConnection();
        }
    }
}
