package org.docurest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docurest.infra.InfraAware;
import org.docurest.queries.types.BooleanValue;
import org.docurest.drivers.JacksonDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
public abstract class AdminController<S> extends InfraAware {

    private final Class<S> docClass;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @PermitAll
    @GetMapping("/populate")
    public ResponseEntity<?> populate() throws IOException {
        Path resourcePath;
        try {
            resourcePath = getDefaultDatasetPath();
        } catch (DatasetResourceNotFound e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        log.info("Populating from " + resourcePath + docClass.getName());

        final var rootNode = new ObjectMapper().readTree(Files.readString(resourcePath));
        if (rootNode.isArray()) {
            Stream<JsonNode> nodeStream = StreamSupport.stream(rootNode.spliterator(), false);
            final var documents = nodeStream
                    .map(JacksonDocument::new);

            infra.getStringImportMutator(docClass).upsert(documents);
        }

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/backup")
    public ResponseEntity<String> export() throws JsonProcessingException {
        final var backup =  infra.select(docClass, new BooleanValue(true)).collect(Collectors.toList());
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"backup.json\"")
                .body(objectMapper.writeValueAsString(backup));
    }

    private Path getDefaultDatasetPath() throws DatasetResourceNotFound {
        String file = infra.getConfig(docClass).getRootClass().getPackage().getName().replaceAll("\\.", "/") + "/default-dataset.json";
        var resource = infra.getConfig(docClass).getRootClass().getClassLoader().getResource(file);
        if (resource==null) {
            throw new DatasetResourceNotFound(String.format("Could not find resource: %s", file));
        }

        return Paths.get(resource.getPath());
    }
}
