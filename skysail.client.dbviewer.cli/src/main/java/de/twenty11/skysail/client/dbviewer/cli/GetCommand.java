package de.twenty11.skysail.client.dbviewer.cli;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

import de.twenty11.skysail.client.dbviewer.cli.internal.Utils;

public class GetCommand implements Command {

    protected static final String ACTION_NAME = "get";
    private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME, "get", "executes http GET request on current path (see command 'pwd')");

    @Override
    public Object execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
        if (!Utils.isConnected(ctx)) {
            console.writeOutput("please connect first before using this command\n");
            return "not connected";
        }

        String url = Utils.getUrl(ctx);
        console.writeOutput("issuing GET request on '" + url + "'\n");

        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            // con.setRequestMethod("POST");
            // con.getOutputStream().write("LOGIN".getBytes("UTF-8"));
            
            String userpass = "scott:tiger";
            String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
            con.setRequestProperty("Authorization", basicAuth);
            
            InputStream inputStream = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

    @Override
    public void plug(Context arg0) {}

}
