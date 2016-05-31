package com.owen.masters.command.impl;

import java.io.IOException;

import com.owen.masters.command.Command;
import com.owen.masters.command.annotation.CommandEntry;
import com.owen.masters.file.files.FriendsFile;
import com.owen.masters.friend.Friend;
import com.owen.masters.friend.manager.FriendManager;
import com.owen.masters.utility.Invoker;

@CommandEntry
public class Friends extends Command {

	public Friends() {
		super(new String[] {"friend", "fr"}, "Friends", "-friend add <name> <alias> / -friend del <name> / -friend list");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args) {
		switch (args[1]) {
		case "add":
			if (args.length == 4) {
				final Friend friend = new Friend(args[2], args[3]);
				
				if (FriendManager.isFriend(friend.getName())) {
					Invoker.writeChatMessage("Friend §3" + friend.getName() + "§e already exists");
					return;
				}
				else
				{
					FriendManager.addFriend(friend);
					Invoker.writeChatMessage(String.format("Added friend %s with the alias §3%s", friend.getName(), friend.getAlias()));
					try {
						new FriendsFile().save();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
			else
			{
				Invoker.writeChatMessage(this.getSyntax());
			}
						
			break;
		case "del":
			if (args.length == 3) {
				if (FriendManager.isFriend(args[2])) {
					final Friend friend = FriendManager.getByName(args[2]);
					FriendManager.removeFriend(friend);
					Invoker.writeChatMessage("Removed friend §3" + args[2]);
					
				}
				else
				{
					Invoker.writeChatMessage("§3" + args[2] + "§e is not a friend");
				}
			}
			
			break;
			
		case "list":
			if (args.length == 2) {
				final StringBuilder sb = new StringBuilder();
				
				for (final Friend friend : FriendManager.getFriends()) {
					 final boolean last = FriendManager.getFriends().get(FriendManager.getFriends().size() - 1) == friend;
			            sb.append(last ? (String.format("§e%s: §3%s", friend.getName(), friend.getAlias())) : (String.format("§e%s: §3%s §e| ", friend.getName(), friend.getAlias())));
				}
				Invoker.writeChatMessage("Counting (§3" + FriendManager.getFriends().size() + "§e) Friends:");
				Invoker.writeChatMessage(sb.toString());
			}
			else
			{
				Invoker.writeChatMessage(this.getSyntax());
			}
		}
	}

}
