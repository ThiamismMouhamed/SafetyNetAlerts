package com.example.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Medicalrecord.
 */
@Data
@NoArgsConstructor
public class Medicalrecord {

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonIgnore
    public Id getId() {
        return  new Id(firstName, lastName);
    }

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private LocalDate birthdate = LocalDate.now();
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();

    /**
     * Instantiates a new Medicalrecord.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param birthdate   the birthdate
     * @param medications the medications
     * @param allergies   the allergies
     */
    public Medicalrecord(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = parseStringToLocalDate(birthdate);
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Gets birthdate.
     *
     * @return the birthdate
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * Set birthdate.
     *
     * @param birthdate the birthdate
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    public void setBirthdate(String birthdate){
        this.birthdate = parseStringToLocalDate(birthdate);
    }

    private LocalDate parseStringToLocalDate(String birthdate) {
        if(birthdate == null) {
            throw new IllegalArgumentException("Birthdate cannot be null");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(birthdate, formatter);
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    @JsonIgnore
    public Integer getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    /**
     * Sets birthdate.
     *
     * @param birthdate the birthdate
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

}
