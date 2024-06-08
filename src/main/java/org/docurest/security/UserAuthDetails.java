package org.docurest.security;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthDetails {

    @NotNull
    private String id;

}
