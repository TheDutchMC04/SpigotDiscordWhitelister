package nl.thedutchmc.spigotdiscordwhitelister.discord;

import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import nl.thedutchmc.spigotdiscordwhitelister.spigot.DutchWhitelistBot;

public class Bot {
	
	public static void init(String token, Long roleId) {
		String roleIdString = roleId.toString();
		
		if(!token.equalsIgnoreCase("ENTER TOKEN HERE!")) {
			
			if(!roleIdString.equalsIgnoreCase("ENTER ID HERE")) {
				DutchWhitelistBot.tellConsole("bot", "Bot Starting");
				bot(token, roleIdString);
			} else {
				DutchWhitelistBot.tellConsole("bot", "Please set roleId!");
			}
		} else {
			DutchWhitelistBot.tellConsole("bot", "Please set botToken!");
		}
	}

	public static void main(String[] args) {
		String token = "NTUyODc5ODg4OTIzNzU0NTMx.D2a56Q.pxIyEsYlWD0zQ0h3RJr06OeE32g";
		String roleId = "Nah";
		bot(token, roleId);
	}
	
	public static void bot(String token, String roleId) {
		
		DutchWhitelistBot.tellConsole("bot", "Initializing");
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		DutchWhitelistBot.tellConsole("bot", "Logged in");

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!help")) {
                
            	event.getChannel().sendMessage("Discord Whitelister Bot\nI do not like to help");
            
            } else if(event.getMessageContent().equalsIgnoreCase("!whitelist")) {
            	
            	//List<Role> role = event.getMessageAuthor().asUser().get().getRoles(event.getServer().get());
            	
            	//String roleString = role.toString();
            
            	User user = event.getMessageAuthor().asUser().get();
            	Server server = event.getServer().get();
            	//List<Long> roles = user.getRoles(server).stream().map(Role::getId).collect(Collectors.toList());
            	String[] roleIdsAsString = user.getRoles(server).stream().map(Role::getId).map(Object::toString).toArray(String[]::new);
        		
            	Boolean shouldBeWhitelisted = false;
            	
            	for(int i = 0; i < roleIdsAsString.length; i++) {
            		String removeCharString = roleIdsAsString[i].toString();
            		removeCharString = removeCharString.replace("[", "");
            		removeCharString = removeCharString.replace("]", "");
            		roleIdsAsString[i] = removeCharString;
            		
            		if(roleIdsAsString[i].toString().equalsIgnoreCase(roleId)) {
            			shouldBeWhitelisted = true;
            		}
            	}
           		if(shouldBeWhitelisted) {
           			DutchWhitelistBot.whitelist(event.getMessageAuthor().getName());
           			event.getChannel().sendMessage("YES YES YES WE HAVE GOT A BINGO! (You're whitelisted)");
           		} else {
           			event.getChannel().sendMessage("Errr check your role, ya can't play here");
           		}          	
            }
        });
	}
}