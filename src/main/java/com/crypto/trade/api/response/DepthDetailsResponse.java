package com.crypto.trade.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepthDetailsResponse implements Serializable {
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("timestamp")
    private long timeStamp;
    @JsonProperty("bids")
    private List<List<String>> bids;
    @JsonProperty("asks")
    private List<List<String>> asks;
}
