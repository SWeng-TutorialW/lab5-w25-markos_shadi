package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Start;

public class StartEvent {
    private Start start;

    public Start getStart() {
        return start;
    }

    public StartEvent(Start start) {
        this.start = start;
    }
}