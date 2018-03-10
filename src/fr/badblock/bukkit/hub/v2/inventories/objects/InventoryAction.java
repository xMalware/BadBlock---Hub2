package fr.badblock.bukkit.hub.v2.inventories.objects;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InventoryAction
{

	private InventoryActionType		actionType;
	private CustomItemActionType	action;
	private String			   		actionData;

	public InventoryAction(DBObject action)
	{
		this.actionType = (InventoryActionType) action.get("actionType");
		this.action = (CustomItemActionType) action.get("action");
		this.actionData = (String) action.get("actionData");
	}
	
	public DBObject toObject()
	{
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("actionType", this.actionType.name());
		dbObject.append("action", this.action.name());
		dbObject.append("actionData", this.actionData);
		return dbObject;
	}

}
