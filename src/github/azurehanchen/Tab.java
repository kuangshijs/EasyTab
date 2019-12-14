package github.azurehanchen;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.DumperOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import java.lang.reflect.Field;
import java.net.URL;

import me.clip.placeholderapi.PlaceholderAPI;
import com.comphenix.protocol.*;
import com.comphenix.protocol.wrappers.*;
import com.comphenix.protocol.events.*;

public class Tab extends JavaPlugin implements Listener  {
	
	/**
	*Powered by AzureHanChen
	*/
	

    final String version = "1.2.0";
    FileConfiguration config;
    private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[INFO] §aEasyTab §f加载成功 ");
		Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[INFO] §f本插件在MCBBS免费发布 作者AzureHanChen  ");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getConsoleSender().sendMessage("§f");
		Bukkit.getPluginManager().registerEvents(this, this);
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[INFO] §f成功Hook to §aPlaceholderAPI");
        } else {
    		Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN] §c未找到PlaceholderAPI,插件可能会出现错误");
        }
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
			Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[INFO] §f成功Hook to §aProtocolLib");
        } else {
    		Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN] §c未找到PlaceholderAPI,插件可能会出现错误");
            throw new RuntimeException("");
        }
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
    }
        reload();
        
        if (this.config.getBoolean("Update")) {
        	updateCheck();
        }
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
	                	p.sendMessage("§f插 件作者: §eAzureHanChen");
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

					    	reload();

						
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
		long cooldown = this.config.getLong("Tablist.Cooldown");
		Player p = e.getPlayer();
		new BukkitRunnable() {
	            //当前播放广播的位置
	            @Override
	            public void run(){
 updateTablist(p);

	            }
	        }.runTaskTimerAsynchronously(this, 0, cooldown*20);
	    }
	
	@EventHandler
    public void onPlayerJoin1(final PlayerJoinEvent e) {
		long cooldown = this.config.getLong("Tab.Cooldown");
		Player p = e.getPlayer();
		new BukkitRunnable() {
	            //当前播放广播的位置
	            @Override
	            public void run(){
 updateTab(p);
	            }
	        }.runTaskTimerAsynchronously(this, 0, cooldown*20);
	    }
	
	
	
    
    public void updateTablist(final Player p) {
		if (this.config.getBoolean("Tablist.Enable")) {
		String header = FixColor(this.config.getString("Tablist.Header"));
		header = PlaceholderAPI.setPlaceholders(p , header);
		String footer = FixColor(this.config.getString("Tablist.Footer"));
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

    
    public void updateTab(final Player p) {
    	if (this.config.getBoolean("Tab.Enable")) {
    		
        	
    	String Text = this.config.getString("Tab.Tab");
    	
    	Text = PlaceholderAPI.setPlaceholders(p , Text );
        final String s = ChatColor.translateAlternateColorCodes('&', Text);
        p.setPlayerListName(s);
    	}
    }
    
    
	public String getLatestVersion(){
		String ver=null;
		try
		{
			URL url=new URL("https://raw.githubusercontent.com/AzureHanChen/EasyTab-CheckUpdate/master/version.txt");
			InputStream is=url.openStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
			ver=br.readLine();
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			ver= "0.0.0";
			
			Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN] §c错误:无法检查更新");
		}
		return ver;
   }
	public boolean isLatestVersion() 
	{
		boolean isLatest=false;
		String latest=getLatestVersion();
		String current=(version);
		if(latest.equalsIgnoreCase(current))
				{
			isLatest=true;
				}
		return isLatest;
	}
	public void updateCheck()
	{
		new BukkitRunnable()
		{
			public void run()
			{
				if(isLatestVersion())
				{
					Bukkit.getConsoleSender().sendMessage("§f");
					Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§eINFO§f] §f您运行的是最新版本的插件 V" + (version));
					Bukkit.getConsoleSender().sendMessage("§f");
				}
				else
				{
					Bukkit.getConsoleSender().sendMessage("§f");
					Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN§f] §e您运行的不是最新版本的插件!");
					Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN§f] §f您的当前版本 V" + (version));
					Bukkit.getConsoleSender().sendMessage("§f[§eEasyTab§f] §f[§cWARN§f] §f当前最新版本 V" + getLatestVersion());
					Bukkit.getConsoleSender().sendMessage("§f");
				}
			}
		}.runTaskTimerAsynchronously((this), 20L, 36000L);
	}


	
	/*
	 * 截取16位字符
	 */
   // public static String getPlayer(final Player player) {

     //   String name;
     //   name = player.getName();
        		
        // if (name.length() > 16) {
         //   name = name.substring(0, 16);
       // }
      //  return name;
    //}
    
    
    
    
    
    
    
    public void reload() {
        final File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveDefaultConfig();
        }
        
        this.reloadConfig();
        DumperOptions yamlOptions = null;
        try {
            final Field f = YamlConfiguration.class.getDeclaredField("yamlOptions");
            f.setAccessible(true);
            yamlOptions = new DumperOptions() {
                public void setAllowUnicode(final boolean allowUnicode) {
                    super.setAllowUnicode(false);
                }
                
                public void setLineBreak(final DumperOptions.LineBreak lineBreak) {
                    super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                }
            };
            yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            f.set(this.getConfig(), yamlOptions);
        }
        catch (ReflectiveOperationException ex) {}
        this.config = this.getConfig();
    }
}