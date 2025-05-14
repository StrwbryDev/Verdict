package dev.strwbry.verdict.utility;

import dev.strwbry.verdict.Verdict;

/**
 * Utility class for accessing configuration values from the plugin's config.yml file.
 * Provides static methods to retrieve various tournament and event settings.
 */
public class Config
{
    /**
     * Reloads the plugin's configuration from disk.
     * This will update all config values to their latest values from the config file.
     */
    public static void reloadConfig() {
        Verdict.getPlugin().reloadConfig();
    }
}