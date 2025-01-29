package com.example.mvola.Response;

public record ResponseTransaction(String status, String serverCorrelationId, String notificationMethod) {
}
