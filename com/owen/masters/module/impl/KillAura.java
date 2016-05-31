package com.owen.masters.module.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.owen.masters.event.events.updates.EventPreMotionUpdate;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.friend.manager.FriendManager;
import com.owen.masters.module.Module;
import com.owen.masters.module.annotation.Loadable;
import com.owen.masters.module.enums.EnumCategory;
import com.owen.masters.utility.Timer;
import com.owen.masters.value.Value;

@Loadable
public class KillAura extends Module {
	
	private Value<Boolean> animals = new Value<Boolean>(true, "killaura_animals");
	private Value<Boolean> mobs = new Value<Boolean>(true, "killaura_mobs");
	private Value<Boolean> players = new Value<Boolean>(true, "killaura_players");
	private Value<Boolean> raytracing = new Value<Boolean>(false, "killaura_raytracing");
	private Value<Double> range = new Value<Double>(4.2D, "killaura_range");
	private Timer timer;
	private List<EntityLivingBase> entries;
	private Entity target;
	private boolean setupTick;
    private boolean switchingTargets;
    private int index;

	public KillAura() {
		super("KillAura", 'K', EnumCategory.Combat);
		this.timer = new Timer();
		this.entries = new ArrayList<>();
	}
	
	private Long getDelay() {
		return 83L;
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerValues() {
		// TODO Auto-generated method stub
		
	}
	
	@Subscriber
	public void onPreMotionUpdate(final EventPreMotionUpdate event) {
		this.entries = this.getTargets();
		if (!entries.isEmpty()) {
			
		}
		
	}
	
	private boolean hasSomeArmour(final EntityPlayer player) {
		int peices = 0;
		
		for (final ItemStack item : player.inventory.armorInventory) {
			if (item != null) {
				peices++;
			}
		}
		
		return peices != 0;
	}
	
	private List<EntityLivingBase> getTargets() {
        final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (final Object i : mc.theWorld.loadedEntityList) {
        	final Entity entity = (Entity) i;
            if (this.validTarget(entity)) {
                targets.add((EntityLivingBase)entity);
            }
        }
        targets.sort(new Comparator<EntityLivingBase>() {
            @Override
            public int compare(final EntityLivingBase target1, final EntityLivingBase target2) {
                return Math.round(target2.getHealth() - target1.getHealth());
            }
        });
        return targets;
    }
	
	private final boolean validTarget(final Entity entity) {
		
		if (!(entity instanceof EntityLivingBase)) {
			return false;
		}
		
		boolean var = mc.thePlayer.getDistanceToEntity(entity) <= this.range.getValue();
		boolean var2 = this.timer.delay(this.getDelay());
		boolean var3 = entity.ticksExisted > 5;
		boolean var4 = FriendManager.isFriend(entity.getName());
		boolean var6 = entity.isInvisible() || entity.isInvisibleToPlayer(mc.thePlayer);
		boolean var7 = entity != mc.thePlayer;
		
		final boolean valid = var  && var2  && !var6 && var7;
		
		if (entity instanceof IMob) {
			return valid && this.mobs.getValue();
		}
		else if (entity instanceof IAnimals) {
			return valid && this.animals.getValue();
		}
		else if (entity instanceof EntityPlayer) {
			return var && var7 && var3 && !var4  && !var6 && var2 && this.players.getValue();
		}
		return false;
	}

}
