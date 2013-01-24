/*
   Copyright 2013 Scott Spittle, James Loyd

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package me.ScottSpittle.MuezliLogin;

import java.io.File;

import net.milkbowl.vault.permission.Permission;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import uk.org.whoami.geoip.GeoIPLookup;
import uk.org.whoami.geoip.GeoIPTools;

public class MuezliLogin extends JavaPlugin{

	public static MuezliLogin plugin;
	public final BukkitLogger blo = new BukkitLogger(this);
  //public final ConfigManager cfManager = new ConfigManager(this);
	public final PlayerListener playerListener = new PlayerListener(this);
    public static Permission perms = null;
    public boolean isPlayer = false;

	@Override
	public void onDisable(){
		blo.enabled(false);
	}

	@Override
	public void onEnable(){
        //ConfigManager cfManager = new ConfigManager(this);
		//set plugin to this instance.
		plugin = this;
	    //run BukkitLogger class on enable.
		blo.enabled(true);
		//register with the plugin manager
		PluginManager pm = getServer().getPluginManager();
		//register player events
		pm.registerEvents(this.playerListener, this);
		//using vault setting up permissions.
		setupPermissions();
		//create config if it doesn't exsist
		createConfig();
	}
    
	//Creates the config file
	public void createConfig(){
		File file = new File(getDataFolder()+File.separator+"config.yml");
		if(!file.exists()){
			getLogger().info("[MuezliPlugin] Creating default config file ...");
			saveDefaultConfig();
			getLogger().info("[MuezliPlugin] Config created successfully!");
		}else {
			getLogger().info("[MuezliPlugin] Config Already Exsists!");
		}
	}
	
	//Register Permissions via Vault.
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
	
	//Disables the plugin
	public void disablePlugin(){
		Bukkit.getPluginManager().disablePlugin(this);
	}
	
	public GeoIPLookup getGeoIPLookup() {
	    Plugin pl = this.getServer().getPluginManager().getPlugin("GeoIPTools");
	    if(pl != null) {
	        return ((GeoIPTools) pl).getGeoIPLookup();
	    } else {
	        return null;
	    }
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(sender instanceof Player){
			isPlayer = true;
		}
		if(commandLabel.equalsIgnoreCase("login")){
			if(isPlayer){
				Player player = (Player) sender;
				if(args.length == 0){
					return false;
				}
				if(args.length >= 1){
					if (args[0].equalsIgnoreCase("set")) {
						if(perms.has(player, "muezli.login.set")){
						     String lm = StringUtils.join(args, " ", 1, args.length);
						     String playerName = player.getName();
						     player.sendMessage(ChatColor.DARK_GREEN+ "Your new login message is " + ChatColor.GRAY + "'" + ChatColor.ITALIC + ChatColor.GOLD + playerName + ChatColor.GRAY + " joined from " + ChatColor.GOLD + lm + ChatColor.GRAY + "'");
							this.getConfig().getConfigurationSection("loginMessages").createSection(playerName);
							this.getConfig().set("loginMessages." + playerName, lm);
							saveConfig();
						}else{
							player.sendMessage(ChatColor.RED + "you don't have permission to use that command");
						}
						return true;
					}
					if (args[0].equalsIgnoreCase("remove")) {
						if(perms.has(player, "muezli.login.set")){
							String playerName = player.getName();
							String privateLoginMessage = this.getConfig().getString("loginMessages." + playerName);
							if (privateLoginMessage == "") {
								player.sendMessage(ChatColor.DARK_GREEN + "You haven't set a custom login message yet.");
							}else {
								this.getConfig().set("loginMessages." + playerName, "");
								saveConfig();
								player.sendMessage(ChatColor.DARK_GREEN + "Your custom login message has been removed");
							}
						}else{
							player.sendMessage(ChatColor.RED + "you don't have permission to use that command");
						}
						return true;
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED + "You must be a player");
			}
		}
			return false;
	}
}