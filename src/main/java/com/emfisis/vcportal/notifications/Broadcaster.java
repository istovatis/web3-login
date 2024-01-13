package com.emfisis.vcportal.notifications;

import com.vaadin.flow.shared.Registration;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<VpNotification>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<VpNotification> listener) {
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(VpNotification message) {
        for (Consumer<VpNotification> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
}
