package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class ConnectCommand implements Command {

    public static final String ACTION_NAME = "connect";

    private Command.Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME, "connect",
            "Connects to skysail business server") {
        @Override
        public Map<String, String> getArguments() {
            Map<String, String> args = new LinkedHashMap<String, String>();
            args.put(Const.KEY_ARGS_HOST + ":<Host>", "host to connect to");
            return args;
        }
    };

    public String execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
        String urlAsString = Utils.getUrl(ctx);
        InputStream inputStream = null;
        try {
            URL url = new URL(urlAsString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String userpass = "admin:skysail";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            // con.getOutputStream().write("LOGIN".getBytes("UTF-8"));
            inputStream = con.getInputStream();
        } catch (Exception e) {
            console.writeOutput("Could not execute command [connect]: got '" + e.getClass().getName() + "'\n");
            setHost(ctx, null);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }
            e.printStackTrace();
            return e.getClass().getName();
        }
        return "\n";
    }

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

    public void plug(Context plug) {
    }

    private void setHost(Context ctx, String host) {
        ctx.putValue(Const.SERVER, host);
    }

}
