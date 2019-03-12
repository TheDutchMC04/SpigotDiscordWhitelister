package nl.thedutchmc.spigotdiscordwhitelister.spigot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.spigotdiscordwhitelister.discord.Bot;

public class DutchWhitelistBot extends JavaPlugin {
	
	boolean autoWhitelist;
	FileConfiguration config = this.getConfig();
	
	public static void tellConsole(String origin, String string) {
		if(origin.equalsIgnoreCase("bot")) {
			System.out.println("[SpigotDiscordWhitelister][Bot] " + string);
		} else if(origin.equalsIgnoreCase("plugin")) {
			System.out.println("[SpigotDiscordWhitelister][Plugin] " + string);
		} else {
			System.out.println("[SpigotDiscordWhitelister][Unkown Origin] " + string);
		}

	}
	
	@Override
	public void onEnable() {
		tellConsole("plugin", "Starting");
		
		config();
		if (config.getBoolean("autoWhitelist")) {
				autoWhitelist = true;
			} else {
				autoWhitelist = false;
			}
		//Enabling Discord Bot
		Bot.init(config.getString("botToken"), config.getLong("roleId"));
		
	}
	
	@Override
	public void onDisable() {
		tellConsole("plugin", "Exiting");
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if(alias.equalsIgnoreCase("sw")) {
			if(args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Missing Arguments!");
				
				return true;
			} 
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.RED + "--SW Help--");
					sender.sendMessage(ChatColor.GOLD + "/sw:" + ChatColor.WHITE +" General SW command");
					sender.sendMessage(ChatColor.GOLD + "/sw help:" + ChatColor.WHITE +" Shows this page");
					sender.sendMessage(ChatColor.GOLD + "/sw whitelist <player>: " + ChatColor.WHITE + " Whitelist a player");
					sender.sendMessage(ChatColor.GOLD + "/sw auto-whitelist <true/false>:" + ChatColor.WHITE + " Toggle Auto-Whitelist");
					
					return true;
				}  else {
					sender.sendMessage(ChatColor.RED + "Invalid Argument!");
					return true;
				}
			} 
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("whitelist")) {
					if(args[1] != null) {
						String toWhitelist = args[1];
						
						Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + toWhitelist);
						sender.sendMessage(ChatColor.GOLD + toWhitelist + " has been whitelisted!");
						
						return true;
					}
				} else if(args[0].equalsIgnoreCase("auto-whitelist")) {
					if(args[1].equalsIgnoreCase("true")) {
						sender.sendMessage(ChatColor.GOLD + "Enabled Auto-Whitelist!");
						autoWhitelist = true;
						tellConsole("plugin", "Auto-Whitelist Enabled!");
						
						this.getConfig().set("auto-whitelist", true);
						saveConfig();
						
						return true;
					} else if(args[1].equalsIgnoreCase("false")) {
						sender.sendMessage(ChatColor.GOLD + "Disabled Auto-Whitelist!");
						autoWhitelist = false;
						tellConsole("plugin", "Auto-Whitelist Disabled!");
						
						this.getConfig().set("auto-whitelist", false);
						saveConfig();
						
						return true;
					} else { //Doesn't work for some reason
						sender.sendMessage(ChatColor.RED + "Invalid Argument!");

						return true;
					}
				}
				return false;
			} else {
				sender.sendMessage(ChatColor.RED + "You messed up something!");
			}

 			return true;
		}
		return false;
	}
	
	public static void whitelist(String string) {
		tellConsole("Plugin", "I Shall Whitelist the crap out of you!");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + string);
	}
	
	public void config() {
		//TODO
		tellConsole("plugin", "Config Sector");
		
		this.saveDefaultConfig();
		tellConsole("plugin", "done");

	}
}
