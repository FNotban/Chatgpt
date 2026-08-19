package com.lynx.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lynx.core.items.StormBreakerItem;
import com.lynx.core.items.PartnerItemListener;
import com.lynx.core.profiles.ProfileManager;
import com.lynx.core.factions.FactionManager;
import com.lynx.core.economy.EconomyManager;
import com.lynx.core.classes.ClassManager;
import com.lynx.core.classes.PvPClassListener;
import com.lynx.core.staff.StaffManager;
import com.lynx.core.staff.StaffListener;
import com.lynx.core.events.EventManager;
import com.lynx.core.listeners.ProfileListener;
import com.lynx.core.listeners.EconomySignListener;
import com.lynx.core.listeners.ClassicHcfListener;
import com.lynx.core.commands.ModCommand;
import com.lynx.core.listeners.EnchantLimiterListener;
import com.lynx.core.timers.TimerManager;
import com.lynx.core.utils.LocationAdapter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class LynxPlugin extends JavaPlugin {
    private static LynxPlugin instance;
    private Gson gson;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private TimerManager timerManager;
    private ProfileManager profileManager;
    private FactionManager factionManager;
    private EconomyManager economyManager;
    private ClassManager classManager;
    private StaffManager staffManager;
    private EventManager eventManager;

    public static LynxPlugin getInstance() { return instance; }
    public Gson getGson() { return gson; }
    public MongoDatabase getMongoDatabase() { return mongoDatabase; }
    public TimerManager getTimerManager() { return timerManager; }
    public ProfileManager getProfileManager() { return profileManager; }
    public FactionManager getFactionManager() { return factionManager; }
    public EconomyManager getEconomyManager() { return economyManager; }

    @Override public void onEnable() {
        instance = this;
        saveDefaultConfig();
        gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).disableHtmlEscaping().create();
        String uri = getConfig().getString("mongo.uri", "mongodb://127.0.0.1:27017");
        String db = getConfig().getString("mongo.database", "lynx_core");
        mongoClient = new MongoClient(new MongoClientURI(uri));
        mongoDatabase = mongoClient.getDatabase(db);
        profileManager = new ProfileManager(mongoDatabase);
        factionManager = new FactionManager(mongoDatabase, gson);
        economyManager = new EconomyManager(profileManager);
        classManager = new ClassManager();
        staffManager = new StaffManager();
        eventManager = new EventManager(this);
        timerManager = new TimerManager(this);
        timerManager.start();
        eventManager.start();
        getServer().getPluginManager().registerEvents(new ProfileListener(profileManager), this);
        getServer().getPluginManager().registerEvents(new PvPClassListener(this, classManager), this);
        getServer().getPluginManager().registerEvents(new StormBreakerItem(this), this);
        getServer().getPluginManager().registerEvents(new PartnerItemListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantLimiterListener(), this);
        getServer().getPluginManager().registerEvents(new EconomySignListener(economyManager), this);
        getServer().getPluginManager().registerEvents(new ClassicHcfListener(), this);
        getServer().getPluginManager().registerEvents(new StaffListener(staffManager), this);
        if (getCommand("mod") != null) getCommand("mod").setExecutor(new ModCommand(staffManager));
        getLogger().info("Lynx Core enabled with MongoDB database '" + db + "'.");
    }

    @Override public void onDisable() {
        if (timerManager != null) timerManager.shutdown();
        if (eventManager != null) eventManager.shutdown();
        if (mongoClient != null) mongoClient.close();
        instance = null;
    }
}
