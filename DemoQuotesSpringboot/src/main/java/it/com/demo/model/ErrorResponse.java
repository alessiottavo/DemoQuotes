package it.com.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@Data
@EqualsAndHashCode
public class ErrorResponse implements Serializable {


    @JsonProperty("timestamp")
    @ApiModelProperty(required = true, value = "Orario dell'errore")
    private LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Paris"));

    @JsonProperty("code")
    @ApiModelProperty(required = true, value = "Codice HTTP dell'errore")
    private int code;

    @JsonProperty("messages")
    @ApiModelProperty(required = true, value = "Messaggi dell'errore")
    private List<String> messages;

    public ErrorResponse(int code, String message) {
        this.code = code;
        setMessage(message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorResponse {\n");
        sb.append(this.getCommonField());
        sb.append("}");
        return sb.toString();
    }

    @JsonIgnore
    protected String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n ");
    }

    @JsonIgnore
    public void setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.messages = new LinkedList<>();
            this.messages.add(message);
        }
    }

    @JsonIgnore
    public String listToString(List<String> messages) {
        StringBuilder sb = new StringBuilder();
        for (String s : messages) {
            sb.append("\n" + s);
        }
        return sb.toString();
    }

    @JsonIgnore
    public String getCommonField() {
        StringBuilder sb = new StringBuilder();
        sb.append(" localDateTime: ").append(this.localDateTime).append("\n");
        sb.append(" code: ").append(this.toIndentedString(this.code)).append("\n");
        sb.append(" messages: ").append(listToString(this.messages));
        return sb.toString();
    }
}
