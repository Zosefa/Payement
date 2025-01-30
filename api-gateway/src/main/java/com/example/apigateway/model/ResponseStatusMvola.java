package com.example.apigateway.model;

public record ResponseStatusMvola(String status, String serverCorrelationId, String notificationMethod, String objectReference) {
}
