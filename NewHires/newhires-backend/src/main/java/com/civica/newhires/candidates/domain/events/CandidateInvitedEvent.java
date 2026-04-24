package com.civica.newhires.candidates.domain.events;

public class CandidateInvitedEvent {
    private final String email;
    private final String name;
    private final String token;

    public CandidateInvitedEvent(String email, String name, String token) {
        this.email = email;
        this.name = name;
        this.token = token;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getToken() { return token; }
}