package bot.listeners;

import bot.commands.CommandsProvider;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommandMessageListener extends ListenerAdapter {

    private final String PREFIX = "+";
    private final CommandsProvider commandsProvider;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            String command = event.getMessage()
                    .getContentRaw().split(" ")[0].toUpperCase(Locale.ROOT);
            if (command.startsWith(PREFIX) && command.length() > 1) {
                commandsProvider.getCommand(command.substring(PREFIX.length())).handle(event);
            }
        }
    }
}
