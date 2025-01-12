package ec.com.sofka.listener;

import ec.com.sofka.command.CreateUserCommand;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    @EventListener
    public void onUserCreated(CreateUserCommand event) {
        //TODO Registrar player
        System.out.println("Event received for user: " + event.getUsername());
    }
}
