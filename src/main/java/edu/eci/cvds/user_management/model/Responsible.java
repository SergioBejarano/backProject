package edu.eci.cvds.user_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * The Responsible class represents a person responsible for a student.
 * This class holds information about the responsible person, such as their name,
 * contact details, and identification information.
 */


@Getter
@Setter
@Entity
@Table(name = "responsibles", schema = "public")
public class Responsible {

    @Id
    @Column(name = "document")
    private String document;
    @Column(name = "site_document")
    private String siteDocument;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;

    /**
     * Default constructor for JPA.
     * This is required by the JPA specification.
     */
    protected Responsible() {
    }

    /**
     * Constructs a new Responsible object with the specified details.
     *
     * @param document      the identification number of the responsible person.
     * @param siteDocument  the site of document.
     * @param name          the name of the responsible person.
     * @param phoneNumber   the phone number of the responsible person.
     * @param email         the email address of the responsible person.
     */
    public Responsible(String document, String siteDocument, String name, String phoneNumber, String email) {
        this.document = document;
        this.siteDocument = defSiteDocument(siteDocument);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Normalizes the `siteDocument` field to ensure consistent formatting of location names.
     * If the `siteDocument` matches predefined variations of known locations, it is standardized.
     * If it does not match any known location, it is marked as "UNKNOWN".
     *
     * @return A standardized string representing the location, or "UNKNOWN" if it does not match known locations.
     */
    private String defSiteDocument(String siteDocument) {
        if (siteDocument == null || siteDocument.trim().isEmpty()) {
            return "DESCONOCIDO";
        }
        String normalized = siteDocument.trim().toUpperCase();
        String correctedValue = switch (normalized) {
            case "BOGOTA", "BOGOTÁ", "BOGOTA DC", "BOGOTÁ DC", "BOGOTA D.C", "BOGOTÁ D.C", "BOGOTA D.C.",
                 "BOGOTÁ D.C." -> "BOGOTÁ D.C.";
            case "MEDELLIN" -> "MEDELLÍN";
            case "GUAMO (TOL)" -> "GUAMO";
            case "LA DORADA (CALDAS)" -> "LA DORADA";
            case "ZIPAQUIRA" -> "ZIPAQUIRÁ";
            case "CHIPAQUE CUNDINAMARCA" -> "CHIPAQUE";
            case "SANTIAGO DE CALI" -> "CALI";
            case "CARTAGENA DE INDIAS" -> "CARTAGENA";
            default -> null;
        };
        if (correctedValue != null) {
            return correctedValue;
        } else {
            return normalized;
        }
    }
}
