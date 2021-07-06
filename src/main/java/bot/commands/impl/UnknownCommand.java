package bot.commands.impl;

import bot.commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class UnknownCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Unknown command").queue();
    }
}
