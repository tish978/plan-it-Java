package com.example.planit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "destinations")
public class Destination {
    @Id
    private String id;
    private String continent;
    private int weather;
    private String language;
    private int budget;
    private String cuisine;
    private boolean familyFriendly;
    private boolean groupFriendly;
    private boolean partyScene;
    private boolean romantic;

    // Getters and setters
}

