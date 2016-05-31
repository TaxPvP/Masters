package com.owen.masters.file.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.owen.masters.file.ConfigFile;
import com.owen.masters.friend.Friend;
import com.owen.masters.friend.manager.FriendManager;
import com.owen.masters.logger.Log;

public class FriendsFile extends ConfigFile {

	public FriendsFile() {
		super("Friends");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save() throws IOException {
		List<Friend> friendList = FriendManager.getFriends();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				this.file))) {
			friendList.stream().forEach(
					friend -> {
						try {
							writer.write(String.format("%s:%s",
									friend.getName(), friend.getAlias()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		}
	}

	@Override
	public void load() throws IOException {
		Files.lines(this.file.toPath()).forEach(line -> {
			final String[] split = line.split(":");
			final Friend friend = new Friend(split[0], split[1]);
			FriendManager.addFriend(friend);
		});

		final StringBuilder sb = new StringBuilder();

		FriendManager.getFriends().stream().forEach(friend -> {
			sb.append(friend.getName() + " | " + friend.getAlias() + " ");
		});

		Log.log("Loaded " + FriendManager.getFriends().size() + " friends. ( "
				+ sb.toString() + ")");
	}

}
