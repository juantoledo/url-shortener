package io.houmlab.urlshortener.model.impl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.houmlab.urlshortener.model.Url;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Document(collection = "urls")
public class BasicUrl implements Url {

    @Id
    @Schema(description = "Unique identifier of the URL entry", example = "6hA43r-R", required = false)
    private String code;

    @NotBlank(message = "Destination must not be empty or null")
    @Pattern(regexp = "^(https?):\\/\\/([\\w\\-\\.]+)+(:\\d+)?(\\/[\\w\\-\\.]*)*(\\?[\\w\\-&=]*)?(#[\\w\\-]*)?$", message = "Destination must be a valid URL")
    @Schema(description = "The destination URL to be resolved", example = "https://juantoledo.dev", required = true)
    private String destination;

    @Schema(description = "Flag for whether the URL is currently enabled", example = "true")
    private boolean enabled;

    @Schema(description = "The expiration time as a EPOCH", example = "0")
    private long expirationTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
