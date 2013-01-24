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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuezliLoginCommandExecutor implements CommandExecutor {
    
	public static MuezliLogin plugin;
	
	public MuezliLoginCommandExecutor(MuezliLogin instance){
		plugin = instance;
	}
 
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
		// implementation exactly as before...
	}
}
