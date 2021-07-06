package bot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {
    void handle(GuildMessageReceivedEvent event);

    default boolean validateCommandWithMentionedUser(GuildMessageReceivedEvent event, String[] content) {
        return content.length > 1 && content[1].matches("<@\\d{18}>") &&
                !event.getMessage().getMentionedMembers().isEmpty();
    }

    default String[] getContentRawAsArray(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentRaw().replaceAll("\\s+", " ").split(" ");
    }
}
