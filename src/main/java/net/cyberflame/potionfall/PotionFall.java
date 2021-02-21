package net.cyberflame.potionfall;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class PotionFall extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        Bukkit.broadcastMessage("[Herbs] Plugin loaded and enabled successfully!");

        final double speed = getConfig().getDouble("speed");

        getServer().getPluginManager().registerEvents(
                new Listener() {

                    @EventHandler
                    void onProjectileLaunch(final ProjectileLaunchEvent event) {
                        if (event.getEntityType() == EntityType.SPLASH_POTION) {
                            final Projectile projectile = event.getEntity();

                            if (projectile.getShooter() instanceof Player && ((Player) projectile.getShooter()).isSprinting()) {
                                final Vector velocity = projectile.getVelocity();

                                velocity.setY(velocity.getY() - speed);
                                projectile.setVelocity(velocity);
                            }
                        }
                    }

                    @EventHandler
                    void onPotionSplash(final PotionSplashEvent event) {
                        if (event.getEntity().getShooter() instanceof Player) {
                            final Player shooter = (Player) event.getEntity().getShooter();

                            if (shooter.isSprinting() && event.getIntensity(shooter) > 0.5D) {
                                event.setIntensity(shooter, 1.0D);
                            }
                        }
                    }
                }, this);
    }
}
