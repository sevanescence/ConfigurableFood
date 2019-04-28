package io.github.makotomiyamoto.ConfigurableFood;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomFood {

    private int saturation;

    private double health;

    private ItemStack itemStack;

    private ArrayList<String> loreToInclude;

    CustomFood(int saturation, double health, ItemStack itemStack, ArrayList<String> loreToInclude) {

        this.saturation = saturation;

        this.health = health;

        this.itemStack = itemStack;

        this.loreToInclude = loreToInclude;

    }

    public int getSaturation() {

        return saturation;

    }

    public double getHealth() {

        return health;

    }

    public ItemStack getItemStack() {

        return itemStack;

    }

    public ArrayList<String> getLoreToInclude() {

        return loreToInclude;

    }

}
