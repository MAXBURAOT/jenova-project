package com.angelis.tera.game.xml.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.angelis.tera.game.models.drop.enums.DropChanceEnum;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="drop", namespace="http://angelis.com/base")
public class DropEntity {
    
    @XmlAttribute(name = "chance")
    private DropChanceEnum dropChance;
    
    @XmlAttribute(name = "max_amount")
    private int maxAmount;
    
    @XmlAttribute(name = "min_amount")
    private int minAmount;
    
    @XmlAttribute(name = "item_id")
    private int itemId;

    public DropChanceEnum getDropChance() {
        return dropChance;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getItemId() {
        return itemId;
    }
}
