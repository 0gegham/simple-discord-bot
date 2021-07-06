package bot.commands.impl;

import bot.commands.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AvatarCommand implements ICommand {

    private final JDA jda;
    private final int SIZE = 2048;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] content = getContentRawAsArray(event);

        if (validateCommandWithMentionedUser(event, content)) {
            Optional.ofNullable(event.getMessage()
                    .getMentionedMembers().get(0).getUser().getAvatarUrl())
                    .ifPresent(url -> event.getChannel().sendMessage(url + "?size=" + SIZE).queue());
        }
    }
}
