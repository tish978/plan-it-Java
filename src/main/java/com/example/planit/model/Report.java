package com.example.planit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "reports")
public class Report {
    @Id
    private String id;
    private String userId;
    private List<String> locationIds;
    private String timestamp;

    // Getters and setters
}
