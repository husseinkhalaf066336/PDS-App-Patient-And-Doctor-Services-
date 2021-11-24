package com.eng_hussein_khalaf066336.pds.model;

public class CurrentUser {
    public static String currentUserId;
    public static String currentUserType;

    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        CurrentUser.currentUserId = currentUserId;
    }

    public static String getCurrentUserType() {
        return currentUserType;
    }

    public static void setCurrentUserType(String currentUserType) {
        CurrentUser.currentUserType = currentUserType;
    }
}
