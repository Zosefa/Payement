package com.example.apigateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class IsoMessage {
    private final ISOPackager packager;

    public IsoMessage() throws ISOException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("iso8583.xml");
            if (is == null) {
                throw new ISOException("Fichier iso8583.xml introuvable");
            }
            this.packager = new GenericPackager(is);
        } catch (ISOException e) {
            throw new ISOException("Erreur de chargement du fichier ISO8583 XML", e);
        }
    }

    public String generateISOMessage(Map<String, Object> isoFields) throws ISOException {
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        ObjectMapper objectMapper = new ObjectMapper();

        for (Map.Entry<String, Object> entry : isoFields.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            int fieldNumber = extractFieldNumber(key);

            String fieldValue;
            if (value instanceof Map) {
                try {
                    fieldValue = objectMapper.writeValueAsString(value);
                } catch (Exception e) {
                    throw new ISOException("Erreur de conversion JSON pour le champ " + key, e);
                }
            } else {
                fieldValue = value.toString();
            }

            isoMsg.set(fieldNumber, fieldValue);
        }

        byte[] isoMessageBytes = isoMsg.pack();
        return byteArrayToHexString(isoMessageBytes);
    }

    public Map<String, Object> parseISOMessage(String isoMessageHex) throws ISOException {
        byte[] isoMessageBytes = hexStringToByteArray(isoMessageHex);

        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);
        isoMsg.unpack(isoMessageBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> response = new java.util.HashMap<>();

        for (int i = 0; i <= isoMsg.getMaxField(); i++) {
            if (isoMsg.hasField(i)) {
                String value = isoMsg.getString(i);
                try {
                    Map<String, Object> jsonValue = objectMapper.readValue(value, Map.class);
                    response.put("field n" + i, jsonValue);
                } catch (Exception e) {
                    response.put("field n" + i, value);
                }
            }
        }

        return response;
    }
    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private int extractFieldNumber(String key) throws ISOException {
        try {
            return Integer.parseInt(key.replace("field n", ""));
        } catch (NumberFormatException e) {
            throw new ISOException("Format de champ invalide : " + key);
        }
    }
}
