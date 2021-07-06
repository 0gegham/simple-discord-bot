package bot.commands.impl.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MuteCommand extends AdminCommand {
    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] content = getContentRawAsArray(event);
        boolean mute = !content[0].contains("unmute");

        if (validateAdminCommand(event, content, Permission.VOICE_MUTE_OTHERS)) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            Optional.ofNullable(member).map(Member::getVoiceState)
                    .filter(GuildVoiceState::inVoiceChannel)
                    .map(GuildVoiceState::getMember)
                    .ifPresent(mmb -> {
                        event.getChannel().sendMessage(member.getUser()
                                .getName() + " was " + (mute ? "muted" : "unmuted")).queue();
                        mmb.mute(mute).reason(getReason(content)).complete();
                    });
        }
    }
}
