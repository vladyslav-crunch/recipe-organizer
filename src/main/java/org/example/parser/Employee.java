package org.example.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Employee {
    @NonNull
    private String fullName;

    @NonNull
    private String academicDegree;

    @NonNull
    private String researchUnit;

    @Override
    public String toString() {
        return String.format("Pracownik: %s %s; %s", academicDegree, fullName, researchUnit);
    }
}
