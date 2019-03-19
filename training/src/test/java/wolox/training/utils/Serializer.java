package wolox.training.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class Serializer {
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static  ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    public static String serializeObject(Object o) throws Exception {
        return ow.writeValueAsString(o);
    }
}
