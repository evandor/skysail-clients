package de.twenty11.skysail.client.cli.commands;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Form;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

public class PostCommand extends AbstractCommand {

	@Override
	public Object doExecute(Context ctx) {
		@SuppressWarnings("unchecked")
		Map<String, Object> argsMap = (Map<String, Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
		Form form = Form.form();
		for (String key : argsMap.keySet()) {
			form.add(key, (String)argsMap.get(key));
		}
		
		String url = CtxUtils.getUrl(ctx) ;
		HttpResponse response = HttpUtils.post(url, form);
		ConsoleUtils.writeStatus(ctx, response);
		ConsoleUtils.writeHeader(ctx, response);
		ConsoleUtils.writeBody(ctx, response);

		
		return response;
	}
}
