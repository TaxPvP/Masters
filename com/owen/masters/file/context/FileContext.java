package com.owen.masters.file.context;

import java.util.Arrays;
import java.util.List;

import com.owen.masters.context.Context;
import com.owen.masters.file.ConfigFile;
import com.owen.masters.file.files.FriendsFile;

public class FileContext extends Context {

	private List<ConfigFile> fileList;

	public FileContext() {
		super("Files");
		this.fileList = Arrays.asList(new FriendsFile());
	}

	@Override
	public void setup() {
		if (fileList.size() != 0) {
			this.fileList.stream().forEach(file -> {
				try {
					file.load();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

}
