package net.supersmashcraft.Commands;

import org.bukkit.entity.Player;

public abstract class SSCCommand {
	
	public SSCCommand(String command, String permission){
		
	}

	public abstract void onCommand(Player p, String[] args);
	
}
