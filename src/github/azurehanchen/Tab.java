package github.azurehanchen;


import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginAwareness;
import org.bukkit.plugin.PluginLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;


import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class Tab extends JavaPlugin implements Listener  {

    final String version = "1.0.0";
    private PluginLogger logger = null;
    private FileConfiguration newxxx = null;
    File xxxFile = new File(getDataFolder(), "config.yml");
    

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f[INFO] §aEasyTab §f加载成功 ");
		Bukkit.getConsoleSender().sendMessage("§f[INFO] §f本插件在MCBBS免费发布 作者AzureHanChen  ");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getPluginManager().registerEvents(this, this);
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
    }
    if (!(xxxFile.exists())){
            saveDefaultxxx();
    }
    
    reloadxxx();

}
	
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)
	{
		if(label.equalsIgnoreCase("easytab"))
		{
			if(args.length==0)

			{
				if(sender instanceof Player)
				{
					Player p = (Player)sender;
	            	if(p.hasPermission("easytab.admin")){
	                	p.sendMessage("§f");
	                	p.sendMessage("§eＥａｓｙＴａｂ");
	                	p.sendMessage("§f");
	                	p.sendMessage("§f插件版本: §e"+(version));
	                	p.sendMessage("§f插件作者: §eAzureHanChen");
	                	p.sendMessage("§e");
	                	p.sendMessage("§f使用 §e§n/easytab help§r §f获取帮助");         
		                String title_temp1 = "§f§lEasyTab";
	                    String subtitle_temp1 = "§c感谢您使用此插件,不妨来评个分?";
	                    final PlayerConnection pc = ((CraftPlayer)p).getHandle().playerConnection;
	                    final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, (IChatBaseComponent)new ChatComponentText(title_temp1), 1, 2, 1);
	                    final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, (IChatBaseComponent)new ChatComponentText(subtitle_temp1), 1, 2, 1);
	                    pc.sendPacket((Packet<?>)title);
	                    pc.sendPacket((Packet<?>)subtitle);
	                    return true;
	                    
	            	}
	            	else {
	                String title_temp = "§f§lEasyTab";
	    			String subtitle_temp =  "§c§l对不起,您没有权限";
	                final PlayerConnection pc = ((CraftPlayer) ((CraftPlayer) sender).getPlayer()).getHandle().playerConnection;
	                final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, (IChatBaseComponent)new ChatComponentText(title_temp), 1, 2, 1);
	                final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, (IChatBaseComponent)new ChatComponentText(subtitle_temp), 1,2,2);
	                pc.sendPacket((Packet<?>)title);
	                pc.sendPacket((Packet<?>)subtitle);
	                return true;
	            	}
				}
				sender.sendMessage("§f[§eINFO§f] §a该命令只能由玩家进行");
				return true;
				
			}
			
			else if(args[0].equalsIgnoreCase("reload"))
			{
					if (sender.hasPermission("easytab.admin")) {
						reloadxxx();
						sender.sendMessage("§7[§eEasyTab§7] §a尝试重载插件配置中");
					return true;
					}
					else {
						sender.sendMessage("§7[§eEasyTab§7] §c您没有使用该命令的权限");
					
				}
				return true;
			}

			
			else if(args[0].equalsIgnoreCase("help"))
			{
			
				if (sender.hasPermission("easytab.admin")) {
					sender.sendMessage("§f");
					sender.sendMessage("§eＥａｓｙＴａｂ Ｈｅｌｐ ( 1 / 1 )");
					sender.sendMessage("§f");
					sender.sendMessage("§f/easytab reload §a重载插件配置");
					sender.sendMessage("§f");
					return true;
			}
				else {
					sender.sendMessage("§7[§eEasyTab§7] §c您没有使用该命令的权限");
				}
			}

		
			}
		return true;
		
	}


    public static String getPlayer(final Player player) {

        String name;
        name = player.getName();
        		
        if (name.length() > 16) {
            name = name.substring(0, 16);
        }
        return name;
    }
    
    

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
    	final Player p = event.getPlayer();
    	String Text = "%player_name%";
    	Text = " " + PlaceholderAPI.setPlaceholders(event.getPlayer(), getxxx().getString("Tab"));
        final String s = ChatColor.translateAlternateColorCodes('&', Text);
        p.setPlayerListName(s);
    }
    
    
    
    
    
    
    public void saveDefaultxxx(){
        if (!this.xxxFile.exists())
        saveResource("config.yml", false);
    }
    public FileConfiguration getxxx(){
        if (this.newxxx == null) {
          reloadxxx();
        }
        return this.newxxx;
    }
    public void reloadxxx(){
            this.newxxx = YamlConfiguration.loadConfiguration(this.xxxFile);
            InputStream defConfigStream = getResource("config.yml");
            if (defConfigStream == null)
                    return;
            YamlConfiguration defConfig;
            byte[] contents;
            if(isStrictlyUTF8()) {
                    defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
            }else{
                    defConfig = new YamlConfiguration();
                    try {
                            contents = ByteStreams.toByteArray(defConfigStream);
                            }catch (IOException e){
                                    getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", e);
                                    return;
                            }
                    String text = new String(contents, Charset.defaultCharset());
                    if (!text.equals(new String(contents, Charsets.UTF_8))) {
                            getLogger().warning("Default system encoding may have misread config.yml from plugin jar");
                    }
                    try{
                            defConfig.loadFromString(text);
                            }catch (InvalidConfigurationException e){
                                    getLogger().log(Level.SEVERE, "Cannot load configuration from jar", e);
                            }
                    }
            this.newxxx.setDefaults(defConfig);
            }
    @SuppressWarnings("deprecation")
	private boolean isStrictlyUTF8() {
            return getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
    }
    public void savexxx(){
            try {
                    getxxx().save(this.xxxFile);
            } catch (IOException ex) {
                    this.logger.log(Level.SEVERE, "Could not save config to " + this.xxxFile, ex);
        }
    }
}