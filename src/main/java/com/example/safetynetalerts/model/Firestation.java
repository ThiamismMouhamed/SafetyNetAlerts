package com.example.safetynetalerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The type Firestation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {

    @NonNull
    private int station;
    @NonNull
    private String address;
}
