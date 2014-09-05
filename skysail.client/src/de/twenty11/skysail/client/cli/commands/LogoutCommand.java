//package de.twenty11.skysail.client.cli.commands;
//
//import org.apache.http.HttpResponse;
//import org.clamshellcli.api.Context;
//
//import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
//import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
//import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
//
//
//public class LogoutCommand extends AbstractCommand {
//
//	private static final String LOGOUT_CMD = "logout";
//
//	public LogoutCommand() {
//		commandDescriptor = new HttpCommandDescriptor(LOGOUT_CMD, "logout", "logs out the currently logged in user");
//	}
//
//	@Override
//	public Object doExecute(Context ctx) {
//		String originalPath = CtxUtils.getCurrentPath(ctx);
//		
//		CtxUtils.setCurrentPath(ctx, "/_logout?targetUri=/");
//		
//		String url = CtxUtils.getUrl(ctx) ;
//		// TODO wrong, need POST to logout properly in REST
//		HttpResponse response = HttpUtils.get(url);
//		ConsoleUtils.writeStatus(ctx, response);
//		ConsoleUtils.writeHeader(ctx, response);
//		ConsoleUtils.writeBody(ctx, response);
//
//		
//		CtxUtils.setCurrentPath(ctx, originalPath);
//
//		return null;
//	}
//
//}
