package edu.eci.cvds.userManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * The Responsible class represents a person responsible for a student.
 * This class holds information about the responsible person, such as their name,
 * contact details, and identification information.
 */


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


    /**
     * Gets the type of document used for identification.
     *
     * @return the type of document (e.g., ID, passport).
     */
    public String getSiteDocument() {
        return siteDocument;
    }
    /**
     * Gets the document number of the responsible person.
     *
     * @return the identification number.
     */

    public String getDocument() {
        return document;
    }


    /**
     * Gets the name of the responsible person.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the phone number of the responsible person.
     *
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the email address of the responsible person.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Set email.
     *
     * @param email the email of economic responsible
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set phone nomber
     *
     * @param phoneNumber Of economic responsible
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
