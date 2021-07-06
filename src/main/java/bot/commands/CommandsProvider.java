package bot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CommandsProvider {

    private final ApplicationContext applicationContext;

    public ICommand getCommand(String name) {
        return (commandExists(name)) ? applicationContext.getBean(Commands.valueOf(name).getClassCommand())
                : applicationContext.getBean(Commands.UNKNOWN.getClassCommand());
    }

    public boolean commandExists(String name) {
        return Arrays.stream(Commands.values())
                .anyMatch(commands ->  commands.name().equals(name));
    }
}
