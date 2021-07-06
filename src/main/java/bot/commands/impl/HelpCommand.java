package bot.commands.impl;

import bot.commands.Commands;
import bot.commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class HelpCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String descriptions = "List of commands:\n```" + Arrays.stream(Commands.values()).filter(Commands::hasDescription)
                .map(command -> command + " - " + command.getDescription())
                .collect(Collectors.joining("\n")) + "\n```";

        event.getChannel().sendMessage(descriptions).queue();
    }
}
