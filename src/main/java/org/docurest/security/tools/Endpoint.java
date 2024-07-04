package org.docurest.security.tools;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
@Builder
public class Endpoint {
    private HttpMethod method;
    private String path;
    private boolean permitAll;
}
