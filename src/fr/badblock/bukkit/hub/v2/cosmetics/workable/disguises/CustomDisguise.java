package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class CustomDisguise {

    public abstract EntityType getEntityType();

    public abstract CustomDisguiseEffect getEffect();

}
