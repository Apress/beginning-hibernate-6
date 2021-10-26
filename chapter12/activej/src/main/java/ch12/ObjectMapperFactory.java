package ch12;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperFactory {
  public ObjectMapper buildMapper() {
    ObjectMapper mapper = new ObjectMapper()
      .setSerializationInclusion(
        JsonInclude.Include.NON_NULL
      )
      .disable(
        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
      );

    mapper.registerModule(new JavaTimeModule());
    return mapper;

  }
}
