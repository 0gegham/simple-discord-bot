package bot.config;

import bot.listeners.CommandMessageListener;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
@RequiredArgsConstructor
public class BotConfiguration {

    @Value("${bot.token}")
    private String token;

    @Bean
    public JDA jda(CommandMessageListener commandMessageListener) throws LoginException {
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(commandMessageListener);
        return jda;
    }

    @Bean
    public AudioPlayerManager audioPlayerManager() {
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
        return playerManager;
    }

}
