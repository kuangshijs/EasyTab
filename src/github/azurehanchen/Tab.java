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
	

    final String version = "1.3.0";
    FileConfiguration config;
    private ProtocolManager protocolManager;
    Mvdw m;
    
    public String head;
    public String foot;
    public Long headcooldown;
    public Long tabcooldown;
    public String tab;
    public Boolean headenable;
    public Boolean tabenable;
    public Boolean Update;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("��f");
		Bukkit.getConsoleSender().sendMessage("��f");
		Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[INFO] ��aEasyTab ��f���سɹ� ");
		Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[INFO] ��f�������MCBBS��ѷ��� ����AzureHanChen  ");
		Bukkit.getConsoleSender().sendMessage("��f");
		Bukkit.getConsoleSender().sendMessage("��f");
		Bukkit.getPluginManager().registerEvents(this, this);
		this.protocolManager = ProtocolLibrary.getProtocolManager();
		this.head = this.config.getString("Tablist.Header");
		this.foot = this.config.getString("Tablist.Footer");
		this.headcooldown = this.config.getLong("Tablist.Cooldown");
		this.tabcooldown = this.config.getLong("Tab.Cooldown");
		this.tab = this.config.getString("Tab.Tab");
		this.headenable = this.config.getBoolean("Tablist.Enable");
		this.tabenable = this.config.getBoolean("Tab.Enable");
		this.Update = this.config.getBoolean("Update");
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[INFO] ��f�ɹ�Hook to ��aPlaceholderAPI");
        } else {
    		Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN] ��cδ�ҵ�PlaceholderAPI,������ܻ���ִ���");
        }
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
			Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[INFO] ��f�ɹ�Hook to ��aProtocolLib");
        } else {
    		Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN] ��cδ�ҵ�PlaceholderAPI,������ܻ���ִ���");
            throw new RuntimeException("");
        }
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") != null) {
			Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[INFO] ��f�ɹ�Hook to ��aMVdWPlaceholderAPI");
        } else {
    		Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN] ��cδ�ҵ�MVdWPlaceholderAPI,������ܻ���ִ���");
        }
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
    }
        reload();
        
        if (this.Update) {
        
        
        new BukkitRunnable()
		{
			public void run()
			{
				if(isLatestVersion())
				{
					Bukkit.getConsoleSender().sendMessage("��f");
					Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��eINFO��f] ��f�����е������°汾�Ĳ�� V" + (version));
					Bukkit.getConsoleSender().sendMessage("��f");
				}
				else
				{
					Bukkit.getConsoleSender().sendMessage("��f");
					Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN��f] ��e�����еĲ������°汾�Ĳ��!");
					Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN��f] ��f���ĵ�ǰ�汾 V" + (version));
					Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN��f] ��f��ǰ���°汾 V" + getLatestVersion());
					Bukkit.getConsoleSender().sendMessage("��f");
				}
			}
		}.runTaskTimerAsynchronously((this), 20L, 36000L);
        }
    	}
	
	
       
	

    



	
    public String FixColor(final String string,Player p) {
    	
        String text =  ChatColor.translateAlternateColorCodes('&', string);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
        	text = PlaceholderAPI.setPlaceholders(p , text);
        }
        
        return m.FixWithMvdw(text, p);
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
	                	p.sendMessage("��f");
	                	p.sendMessage("��e�ţ����ԣ��");
	                	p.sendMessage("��f");
	                	p.sendMessage("��f����汾: ��e"+(version));
	                	p.sendMessage("��f�� ������: ��eAzureHanChen");
	                	p.sendMessage("��e");
	                	p.sendMessage("��fʹ�� ��e��n/easytab help��r ��f��ȡ����");         
	                    return true;
	                    
	            	}
	            	else {
						sender.sendMessage("��7[��eEasyTab��7] ��c��û��ʹ�ø������Ȩ��");
	                return true;
	            	}
				}
				sender.sendMessage("��f[��eEasyTab��f] ��f[��eINFO��f] ��fʹ�� ��e/easytab help ��f��ȡ����");
				return true;
				
			}
			
			else if(args[0].equalsIgnoreCase("reload"))
			{
					if (sender.hasPermission("easytab.admin")) {

					    	reload();

						
						sender.sendMessage("��7[��eEasyTab��7] ��a�������ز��������");
						sender.sendMessage("��7[��eEasyTab��7] ��a������޸���Updateѡ��,��ô�������������Կ�������");
					return true;
					}
					else {
						sender.sendMessage("��7[��eEasyTab��7] ��c��û��ʹ�ø������Ȩ��");
					
				}
				return true;
			}

			
			else if(args[0].equalsIgnoreCase("help"))
			{
			
				if (sender.hasPermission("easytab.admin")) {
					sender.sendMessage("��f");
					sender.sendMessage("��e�ţ����ԣ�� �ȣ��� ( 1 / 1 )");
					sender.sendMessage("��f");
					sender.sendMessage("��f/easytab reload ��a���ز������");
					sender.sendMessage("��f");
					return true;
			}
				else {
					sender.sendMessage("��7[��eEasyTab��7] ��c��û��ʹ�ø������Ȩ��");
				}
			}

		
			}
		return true;
		
	}
	
	
	@EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
		long cooldown = this.headcooldown;
		Player p = e.getPlayer();
		new BukkitRunnable() {
	            @Override
	            public void run(){
           updateTablist(p);

	            }
	        }.runTaskTimerAsynchronously(this, 0, cooldown);
	    }
	
	@EventHandler
    public void onPlayerJoin1(final PlayerJoinEvent e) {
		long cooldown = this.tabcooldown;
		Player p = e.getPlayer();
		new BukkitRunnable() {
	            //��ǰ���Ź㲥��λ��
	            @Override
	            public void run(){
 updateTab(p);
	            }
	        }.runTaskTimerAsynchronously(this, 0, cooldown);
	    }
	
	
	
    
    public void updateTablist(final Player p) {
		if (this.headenable) {
		String header = FixColor(this.head,p);
		header = PlaceholderAPI.setPlaceholders(p , header);
		String footer = FixColor(this.foot,p);
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
    	if (this.tabenable) {
    		
        	
    	String Text = this.tab;
    	
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
			
			Bukkit.getConsoleSender().sendMessage("��f[��eEasyTab��f] ��f[��cWARN��f] ��c����:�޷�������");
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



	
	/*
	 * ��ȡ16λ�ַ�
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
		this.head = this.config.getString("Tablist.Header");
		this.foot = this.config.getString("Tablist.Footer");
		this.headcooldown = this.config.getLong("Tablist.Cooldown");
		this.tabcooldown = this.config.getLong("Tab.Cooldown");
		this.tab = this.config.getString("Tab.Tab");
		this.headenable = this.config.getBoolean("Tablist.Enable");
		this.tabenable = this.config.getBoolean("Tab.Enable");
		this.Update = this.config.getBoolean("Update");
    }
}