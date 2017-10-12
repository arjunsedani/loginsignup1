package com.androidtutorialshub.loginregister.jsonEntityObjects;


public class response {

    private String create_time;

    private String id;

    private String intent;

    private String state;

    public response(String create_time, String id, String intent, String state) {
        this.create_time = create_time;
        this.id = id;
        this.intent = intent;
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getId() {
        return id;
    }

    public String getIntent() {
        return intent;
    }

    public String getState() {
        return state;
    }
}
