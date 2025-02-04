package com.example.apigateway.Controller;

import com.example.apigateway.service.IsoMessage;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/iso")
public class IsoController {
    @Autowired
    private IsoMessage isoMessage;

    @PostMapping
    public ResponseEntity<?> parseIsoMessage(@RequestBody Map<String, String> request) {
        try {
            String isoMsg = request.get("isoMsg");
            if (isoMsg == null || isoMsg.isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'isoMsg' est requis");
            }

            Map<String, Object> parsedData = isoMessage.parseISOMessage(isoMsg);

            return ResponseEntity.ok(parsedData);
        } catch (ISOException e) {
            return ResponseEntity.status(500).body("Erreur lors du traitement du message ISO: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur de parsing: " + e.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateIsoMessage(@RequestBody Map<String, Object> isoFields) {
        try {
            if (isoFields == null || isoFields.isEmpty()) {
                return ResponseEntity.badRequest().body("Les données ISO sont requises");
            }

            String isoMessageHex = isoMessage.generateISOMessage(isoFields);

            return ResponseEntity.ok(Map.of("isoMessage", isoMessageHex));
        } catch (ISOException e) {
            return ResponseEntity.status(500).body("Erreur lors de la génération du message ISO: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne: " + e.getMessage());
        }
    }
}
