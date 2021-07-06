package bot.commands;

import bot.commands.impl.AvatarCommand;
import bot.commands.impl.HelpCommand;
import bot.commands.impl.UnknownCommand;
import bot.commands.impl.admin.BanCommand;
import bot.commands.impl.admin.KickCommand;
import bot.commands.impl.admin.MuteCommand;
import bot.commands.impl.music.*;
import lombok.Getter;

import static java.util.Objects.nonNull;

public enum Commands {
    UNKNOWN(UnknownCommand.class, ""),
    HELP(HelpCommand.class, "Help"),
    AVATAR(AvatarCommand.class, "Get user avatar"),

    // Audio
    PLAY(PlayCommand.class, "Play audio"),
    SKIP(SkipCommand.class, "Skip/Next audio"),
    QUEUE(QueueCommand.class, "List of audio"),
    CLEAR(ClearCommand.class, "Clear list of audio"),

    EXIT(ExitCommand.class, "Exit from voice chat"),

    // Admin
    KICK(KickCommand.class, "Kick user(s)"),
    BAN(BanCommand.class, "Ban user"),
    MUTE(MuteCommand.class, "Mute user"),
    UNMUTE(MuteCommand.class, "Unmute user");

    @Getter private final Class<? extends ICommand> classCommand;
    @Getter private final String description;

    Commands(Class<? extends ICommand> classCommand, String description) {
        this.classCommand = classCommand;
        this.description = description;
    }

    public boolean hasDescription() {
        return nonNull(this.description) && !this.description.isEmpty();
    }
}
