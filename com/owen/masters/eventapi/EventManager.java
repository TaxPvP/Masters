package com.owen.masters.eventapi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.owen.masters.eventapi.callable.Event;

public final class EventManager {

	private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, List<MethodData>>();

	private EventManager() {
	}

	public static void listen(Object object) {
		for (final Method method : object.getClass().getDeclaredMethods()) {
			if (isMethodBad(method)) {
				continue;
			}

			listen(method, object);
		}
	}

	public static void listen(Object object, Class<? extends Event> eventClass) {
		for (final Method method : object.getClass().getDeclaredMethods()) {
			if (isMethodBad(method)) {
				continue;
			}

			listen(method, object);
		}
	}

	public static void flush(Object object) {
		for (final List<MethodData> dataList : REGISTRY_MAP.values()) {
			for (final MethodData data : dataList) {
				if (data.getSource().equals(object)) {
					dataList.remove(data);
				}
			}
		}

		cleanMap(true);
	}

	public static void flush(Object object, Class<? extends Event> eventClass) {
		if (REGISTRY_MAP.containsKey(eventClass)) {
			for (final MethodData data : REGISTRY_MAP.get(eventClass)) {
				if (data.getSource().equals(object)) {
					REGISTRY_MAP.get(eventClass).remove(data);
				}
			}

			cleanMap(true);
		}
	}

	private static void listen(Method method, Object object) {
		Class<? extends Event> indexClass = (Class<? extends Event>) method
				.getParameterTypes()[0];
		final MethodData data = new MethodData(object, method);
		if (!data.getTarget().isAccessible()) {
			data.getTarget().setAccessible(true);
		}

		if (REGISTRY_MAP.containsKey(indexClass)) {
			if (!REGISTRY_MAP.get(indexClass).contains(data)) {
				REGISTRY_MAP.get(indexClass).add(data);
			}
		} else {
			REGISTRY_MAP.put(indexClass,
					new CopyOnWriteArrayList<MethodData>() {
						private static final long serialVersionUID = 666L;

						{
							add(data);
						}
					});
		}
	}

	public static void removeEntry(Class<? extends Event> indexClass) {
		Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP
				.entrySet().iterator();

		while (mapIterator.hasNext()) {
			if (mapIterator.next().getKey().equals(indexClass)) {
				mapIterator.remove();
				break;
			}
		}
	}

	public static void cleanMap(boolean onlyEmptyEntries) {
		Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP
				.entrySet().iterator();

		while (mapIterator.hasNext()) {
			if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty()) {
				mapIterator.remove();
			}
		}
	}

	private static boolean isMethodBad(Method method) {
		return method.getParameterTypes().length != 1
				|| !method.isAnnotationPresent(Subscriber.class);
	}

	public static final Event call(final Event event) {
		List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

		if (dataList != null) {
			for (final MethodData data : dataList) {
				invoke(data, event);
			}
		}

		return event;
	}

	private static void invoke(MethodData data, Event argument) {
		try {
			data.getTarget().invoke(data.getSource(), argument);
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
	}

	private static final class MethodData {

		private final Object source;
		private final Method target;

		public MethodData(Object source, Method target) {
			this.source = source;
			this.target = target;
		}

		public Object getSource() {
			return source;
		}

		public Method getTarget() {
			return target;
		}

	}

}
