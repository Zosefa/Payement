package com.example.mvola.Response;

public record ResponseStatus(String status, String serverCorrelationId, String notificationMethod, String objectReference) {
}
