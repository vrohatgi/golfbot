package hello;

import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;

public class MyApp {
    private static final Leader l = new Leader();

    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET, SLACK_APP_TOKEN)
        App app = new App();

        app.command("/score", (req, ctx) -> {
            String p = req.getPayload().getText();
            l.parseAndAdd(p);
            return ctx.ack(":wave: " + p);
        });

        app.command("/leader", (req, ctx) -> {
            String x = l.getLeaderBoard();
            x = "\n:golf: Leader Board :golf:\n";
            return ctx.ack(x);
        });

        // SocketModeApp expects an env variable: SLACK_APP_TOKEN
        new SocketModeApp(app).start();
    }
}
