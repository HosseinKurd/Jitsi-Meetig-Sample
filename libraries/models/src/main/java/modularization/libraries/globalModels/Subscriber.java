package modularization.libraries.globalModels;

import java.io.Serializable;

public class Subscriber implements Serializable {

    String session;
    String id;
    String name;

    public Subscriber() {
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
