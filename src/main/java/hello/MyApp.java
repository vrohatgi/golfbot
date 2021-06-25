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
            boolean success = l.parseAndAdd(p);
            if (success) {
                return ctx.ack(":woman-golfing: " + p);
            }
            return ctx.ack("Please enter all necessary arguments!");
        });

        app.command("/leader", (req, ctx) -> {
            String x = l.getLeaderBoard();
            String y = "\n:golf: Leader Board :golf:\n";
            return ctx.ack(y + x);
        });

        // SocketModeApp expects an env variable: SLACK_APP_TOKEN
        new SocketModeApp(app).start();
    }
}
