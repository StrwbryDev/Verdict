package dev.strwbry.verdict;

import dev.strwbry.verdict.commands.CommandRootVerdict;
import dev.strwbry.verdict.database.DatabaseManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Verdict extends JavaPlugin
{

    private static Verdict instance;
    private static DatabaseManager databaseManager;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        instance = this;
        saveResource("config.yml", /* replace */ false);

        databaseManager = new DatabaseManager();


        //initializes verdict base command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(CommandRootVerdict.buildCommand());
        });
        int pluginId = 25859; // from bStats website
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
    }

    public static Verdict getPlugin()
    {
        return instance;
    }

    public static DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }
}
