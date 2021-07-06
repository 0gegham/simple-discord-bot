package bot.commands.impl.admin;

import bot.commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.Optional;

public abstract class AdminCommand implements ICommand {
    
    public boolean validateAdminCommand(GuildMessageReceivedEvent event, String[] content, Permission ...permissions) {
        return Optional.of(event.getAuthor())
                .map(user -> event.getGuild().getMember(event.getAuthor()))
                .filter(member -> PermissionUtil.checkPermission(member, permissions))
                .isPresent() && validateCommandWithMentionedUser(event, content);
    }

    public String getReason(String[] content) {
        return content.length >= 3 ? content[2] : "Without any reason";
    }
}
