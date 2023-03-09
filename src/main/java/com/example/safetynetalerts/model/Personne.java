package com.example.safetynetalerts.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * class person
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personne {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonIgnore
    public Id getId() {
        return  new Id(firstName, lastName);
    }

}
