package me.anchore.draft.event;

import me.anchore.log.Loggers;
import me.anchore.util.Assert;
import me.anchore.util.concurrent.Execs;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;

/**
 * @author anchore
 * @date 12/4/2018
 */
public class EventDispatcher {
    private static final ConcurrentMap<Event, Set<EventListener>> EVENTS = new ConcurrentHashMap<>();

    private static final ExecutorService EXEC = Execs.newSingleThreadExecutor();

    public static void listen(Event event, EventListener<?> listener) {
        Assert.notNull(event);
        Assert.notNull(listener);

        EVENTS.computeIfAbsent(event, e -> new CopyOnWriteArraySet<>()).add(listener);
    }

    public static void remove(EventListener<?> listener) {
        if (listener == null) {
            return;
        }

        for (Set<EventListener> listeners : EVENTS.values()) {
            listeners.remove(listener);
        }
    }

    public static <T> void fire(Event event, T content) {
        Assert.notNull(event);

        EXEC.execute(() -> syncFire(event, content));
    }

    public static void fire(Event event) {
        fire(event, null);
    }

    public static <T> void syncFire(Event event, T content) {
        Assert.notNull(event);

        Set<EventListener> listeners = EVENTS.getOrDefault(event, Collections.emptySet());
        if (listeners.isEmpty()) {
            Loggers.getLogger().warn("no listener for event {}", event);
            return;
        }

        for (EventListener listener : listeners) {
            try {
                listener.on(content);
            } catch (Throwable t) {
                Loggers.getLogger().error(t);
            }
        }
    }

    public static void syncFire(Event event) {
        syncFire(event, null);
    }
}