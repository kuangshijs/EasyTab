package github.azurehanchen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import be.maximvdw.placeholderapi.PlaceholderAPI;

public class Mvdw {
	
	public String FixWithMvdw(final String string,Player p) {
		
        String text =  ChatColor.translateAlternateColorCodes('&', string);
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
        	text = PlaceholderAPI.replacePlaceholders(p , text);
        }
        
        return text;
		
	}

}
