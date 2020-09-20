package advisor;

import advisor.config.Config;
import advisor.controller.UserInteractor;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if ("-access".equals(args[i])) {
                Config.updateLink(args[i + 1]);
            }
            if ("-resource".equals(args[i])) {
                Config.updateApiLink(args[i + 1]);
            }
            if ("-page".equals(args[i])) {
                Config.updateNumberOfEntries(Integer.parseInt(args[i + 1]));
            }
        }
        UserInteractor.getInstance().process();
    }
}
