package me.anchore.draft.event;

import me.anchore.log.Loggers;
import me.anchore.util.concurrent.Execs;

/**
 * @author anchore
 * @date 12/4/2018
 */
public interface Event {
}

enum DemoEvt implements Event {
    EVENT;

    public static void main(String[] args) {
        EventDispatcher.listen(EVENT, data -> {
            Loggers.getLogger().error("{}", data);
        });

        EventDispatcher.fire(EVENT, "ad");
        Execs.shutdownAll();
    }
}
