package il.cshaifasweng.OCSFMediatorExample.client;


import il.cshaifasweng.OCSFMediatorExample.entities.Update;

public class UpdateEvent {
    private Update update;

    public Update getUpdate() {
        return update;
    }

    public UpdateEvent(Update update) {
        this.update = update;
    }
}
