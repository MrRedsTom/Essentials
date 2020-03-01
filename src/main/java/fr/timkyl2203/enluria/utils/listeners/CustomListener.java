package fr.timkyl2203.enluria.utils.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public abstract class CustomListener<T> implements Listener, EventExecutor {

    public abstract void run(T event);

    @Override
    public void execute(Listener listener, Event event) {
        run((T) event);
    }
}
