package com.example.androidproject.core.utils;

public class UserTierUtils {
    public static int getTierFromAmount(long totalSpent) {
        if (totalSpent > 50000000) {
            return 4;
        } else if (totalSpent > 10000000) {
            return 3;
        } else if (totalSpent > 5000000) {
            return 2;
        } else if (totalSpent > 1000000) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String getTierStringFromAmount(long totalSpent) {
        if (totalSpent > 50000000) {
            return "Platinum Technie";
        } else if (totalSpent > 10000000) {
            return "Gold Technie";
        } else if (totalSpent > 5000000) {
            return "Silver Technie";
        } else if (totalSpent > 1000000) {
            return "Bronze Technie";
        } else {
            return "New-Technie";
        }
    }

    public static String getTierStringFromTier(int tier) {
        if (tier == 4) {
            return "Platinum Technie";
        } else if (tier == 3) {
            return "Gold Technie";
        } else if (tier == 2) {
            return "Silver Technie";
        } else if (tier == 1) {
            return "Bronze Technie";
        } else {
            return "New-Technie";
        }
    }
}
