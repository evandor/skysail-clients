package de.twenty11.skysail.client.cli.commands.utils;

import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.Const;

public class CtxUtils {

	public static void setServer(Context ctx, String serverUrl) {
		ctx.putValue(Const.SERVER, serverUrl);
	}

	public static void set(Context ctx, String key, String value) {
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			ctx.putValue(key, Boolean.valueOf(value));
		} else {
			ctx.putValue(key, value);
		}
	}

	public static String getServer(Context ctx) {
		return (String) ctx.getValue(Const.SERVER);
	}

	public static String getCurrentPath(Context ctx) {
		Object pathAsObject = ctx.getValue(Const.CURRENT_PATH);
		if (pathAsObject == null) {
			ctx.putValue(Const.CURRENT_PATH, "/");
			return "/";
		}
		return (String) pathAsObject;
	}

	public static void setCurrentPath(Context ctx, String currentPath) {
		ctx.putValue(Const.CURRENT_PATH, currentPath);
	}

	public static String getUrl(Context ctx) {
		String path = CtxUtils.getCurrentPath(ctx);
		return getServer(ctx) + path;
	}

	public static boolean showHeader(Context ctx) {
		return showElement(ctx, Const.SHOW_HEADER);
	}

	private static boolean showElement(Context ctx, String key) {
		Object value = ctx.getValue(key);
		if (value == null || !(value instanceof Boolean)) {
			return true;
		}
		return (Boolean)value;
	}

	public static boolean showBody(Context ctx) {
		return showElement(ctx, Const.SHOW_BODY);
	}

}
