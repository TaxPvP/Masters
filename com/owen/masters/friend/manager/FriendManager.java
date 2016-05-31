package com.owen.masters.friend.manager;

import java.util.ArrayList;
import java.util.List;

import com.owen.masters.context.Context;
import com.owen.masters.friend.Friend;

public class FriendManager {

	private static List<Friend> friendsList = new ArrayList<>();

	public static List<Friend> getFriends() {
		return friendsList;
	}

	public static void addFriend(final Friend friend) {
		if (friend != null && !friendsList.contains(friend)) {
			friendsList.add(friend);
		}
	}
	
	public static void removeFriend(final Friend friend) {
		if (friendsList.contains(friend)) {
			friendsList.remove(friend);
		}
	}

	public static Friend getByName(final String name) {
		for (final Friend friend : friendsList) {
			if (friend.getName().equalsIgnoreCase(name)) {
				return friend;
			}
		}

		return null;
	}

	public static boolean isFriend(String name) {
		for (final Friend friend : friendsList) {
			if (friend.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

}
