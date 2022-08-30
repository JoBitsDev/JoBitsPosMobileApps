package com.services.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VentaId {

    //@JsonProperty("id")
    private final Integer id;

    @JsonCreator
    public VentaId( @JsonProperty("id") Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


}
