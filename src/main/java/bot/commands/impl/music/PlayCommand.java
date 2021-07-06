package bot.commands.impl.music;

import bot.audio.AudioLoadResultHandlerImpl;
import bot.audio.GuildAudioPlayerManager;
import bot.audio.GuildMusicManager;
import bot.commands.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlayCommand implements ICommand {

    private final GuildAudioPlayerManager guildAudioPlayerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ", 2);

        if (command.length > 1) {
            Optional.ofNullable(event.getMember()).map(Member::getVoiceState)
                    .filter(GuildVoiceState::inVoiceChannel)
                    .map(GuildVoiceState::getChannel)
                    .ifPresent(vCh -> {
                        String id = vCh.getId();
                        loadAndPlay(event.getChannel(), command[1], id);
                    });
        }
    }

    private void loadAndPlay(TextChannel channel, String trackUrl, String voiceChannelId) {
        GuildMusicManager manager = guildAudioPlayerManager.getGuildAudioPlayer(channel.getGuild());
        guildAudioPlayerManager.getPlayerManager()
                .loadItemOrdered(manager, trackUrl, new AudioLoadResultHandlerImpl(channel, manager, trackUrl, voiceChannelId));
    }
}
