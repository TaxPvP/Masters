package com.owen.masters.context;

import java.util.Arrays;
import java.util.List;

import com.owen.masters.command.context.CommandContext;
import com.owen.masters.file.context.FileContext;
import com.owen.masters.listener.context.ListenerContext;
import com.owen.masters.logger.Log;
import com.owen.masters.module.context.ModuleContext;
import com.owen.masters.value.context.ValueContext;

public class ContextManager {

	private List<Context> contextList;

	public ContextManager() {
		this.contextList = Arrays.asList(new ValueContext(),
				new ListenerContext(), new ModuleContext(),
				new CommandContext(), new FileContext());

		if (this.contextList.size() == 0 || this.contextList == null) {
			Log.log("Context list is corrupt");
			return;
		}

		contextList.stream().forEach(context -> {
			context.setup();
			Log.log("Loaded context: " + context.getName());
		});

	}

}
