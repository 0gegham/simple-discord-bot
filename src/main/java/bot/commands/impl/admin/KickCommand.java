package bot.commands.impl.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class KickCommand extends AdminCommand {
    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] content = getContentRawAsArray(event);

        if (validateAdminCommand(event, content, Permission.KICK_MEMBERS)) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            event.getChannel().sendMessage(member.getUser()
                    .getName() + " was kicked. Reason: " + getReason(content)).queue();
            member.kick().complete();
        }
    }
}
