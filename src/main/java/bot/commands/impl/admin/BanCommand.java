package bot.commands.impl.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class BanCommand extends AdminCommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] content = getContentRawAsArray(event);

        if (validateAdminCommand(event, content, Permission.BAN_MEMBERS)) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            event.getChannel().sendMessage(member.getUser()
                    .getName() + " was banned. Reason: " + getReason(content)).queue();
            member.ban(delDays(content)).reason(getReason(content)).complete();
        }
    }

    private int delDays(String[] content) {
        boolean canParseInt = content.length == 4 && content[3].matches("\\d+");
        int delay = 7, tmp;
        if (canParseInt && (tmp = Integer.parseInt(content[3])) < delay) {
                delay = tmp;
        }

        return delay ;
    }

}
