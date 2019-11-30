package github.azurehanchen;


import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
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
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import com.comphenix.protocol.events.*;

public class Tab extends JavaPlugin implements Listener  {
	
	/**
	*Powered by AzureHanChen
	*/
	

    final String version = "1.1.0";
    private PluginLogger logger = null;
    private FileConfiguration newxxx = null;
    File xxxFile = new File(getDataFolder(), "config.yml");
    private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f[INFO] §aEasyTab §f加载成功 ");
		Bukkit.getConsoleSender().sendMessage("§f[INFO] §f本插件在MCBBS免费发布 作者AzureHanChen  ");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getPluginManager().registerEvents(this, this);
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Bukkit.getConsoleSender().sendMessage("§f[INFO] §f成功Hook to §aPlaceholderAPI");
        } else {
    		Bukkit.getConsoleSender().sendMessage("§f[§cWARN] §c未找到PlaceholderAPI,插件可能会出现错误");
        }
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
			Bukkit.getConsoleSender().sendMessage("§f[INFO] §f成功Hook to §aProtocolLib");
        } else {
    		Bukkit.getConsoleSender().sendMessage("§f[§cWARN] §c未找到PlaceholderAPI,插件可能会出现错误");
            throw new RuntimeException("");
        }
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
    }
    if (!(xxxFile.exists())){
            saveDefaultxxx();
    }
    
    reloadxxx();

}
	
    private String FixColor(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
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
	                    return true;
	                    
	            	}
	            	else {
						sender.sendMessage("§7[§eEasyTab§7] §c您没有使用该命令的权限");
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
	
	
	@EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
		if (getxxx().getBoolean("Tablist.Enable")) {
		String header = FixColor(getxxx().getString("Tablist.Header"));
		header = PlaceholderAPI.setPlaceholders(e.getPlayer(), header);
		String footer = FixColor(getxxx().getString("Tablist.Footer"));
		footer = PlaceholderAPI.setPlaceholders(e.getPlayer(), footer);
        final PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, (WrappedChatComponent)WrappedChatComponent.fromText(header)).write(1, (WrappedChatComponent)WrappedChatComponent.fromText(footer));
        try {
            this.protocolManager.sendServerPacket(e.getPlayer(), pc);
            update(e.getPlayer());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	}
	
	
    
    public void updateTablist(final Player p) {
		if (getxxx().getBoolean("Tablist.Enable")) {
		String header = FixColor(getxxx().getString("Tablist.Header"));
		header = PlaceholderAPI.setPlaceholders(p , header);
		String footer = FixColor(getxxx().getString("Tablist.Footer"));
		footer = PlaceholderAPI.setPlaceholders(p , footer);
        final PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents().write(0, (WrappedChatComponent)WrappedChatComponent.fromText(header)).write(1, (WrappedChatComponent)WrappedChatComponent.fromText(footer));
        try {
            this.protocolManager.sendServerPacket(p , pc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }
    
    public void update(Player p) {
		new BukkitRunnable()
		{
			public void run()
			{
				updateTablist(p);
			}
			}.runTaskTimerAsynchronously((this), 5L, 20L);
    }



	
	/*
	 * 截取16位字符
	 */
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
    	if (getxxx().getBoolean("Tab.Enable")) {
    		
    	
    	final Player p = event.getPlayer();
    	String Text = getxxx().getString("Tab.Tab");
    	
    	Text = " " + PlaceholderAPI.setPlaceholders(event.getPlayer(), Text );
        final String s = ChatColor.translateAlternateColorCodes('&', Text);
        p.setPlayerListName(s);
    	}
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