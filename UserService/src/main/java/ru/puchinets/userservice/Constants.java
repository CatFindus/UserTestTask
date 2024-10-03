package ru.puchinets.userservice;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    //Roles
    public static final String DEFAULT_ROLE = "user";
    //Validation Messages
    public final static String PASSWORD_LENGTH_MSG = "Password need to between 8 and 128 characters";

    //Logging message
    public static final String LOG_BEGIN = "Beginning method Class: {}, Method: {}, args: {}";
    public static final String LOG_END = "Ending method Class: {}, Method: {}, result: {}";
    public static final String LOG_EXCEPTION = "Exception in method Class: {}, Method: {}, result: {}";
    public static final String KAFKA_SEND_MESSAGE = "Message sent to topic {} : {}";
    public static final String SERIALIZE_MESSGAGE_EX = "Can't serialize message to kafka: {}, trace: {}";
    //Swagger examples
    public static final String PAGINATION_EXAMPLE = "{\n\"page\": 0,\n\"size\": 2,\n\"sort\": \"id,desc\"\n}";

    //kafka
    public static final String USER_UPDATE_KAFKA_TOPIC = "user-updated";


}
