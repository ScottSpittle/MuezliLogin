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

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

	public static MuezliLogin plugin;
	
	public PlayerListener(MuezliLogin instance){
		plugin = instance;
	}

    @EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String privateLoginMessage = plugin.getConfig().getString("loginMessages." + player.getName());
		if (privateLoginMessage == null) {
			String country = plugin.getGeoIPLookup().getCountry(player.getAddress().getAddress()).getName();
				event.setJoinMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has joined from " + ChatColor.GOLD + country);
		}else {
			event.setJoinMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has joined from " + ChatColor.GOLD + privateLoginMessage);
		}
	}
	
}
