package com.zx.extension;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceEvent {

    @EventListener(classes = {ApplicationEvent.class})
    public void listern(ApplicationEvent applicationEvent) {
        System.out.println("HelloServiceEvent 监听到的事件： " + applicationEvent);
    }
}
