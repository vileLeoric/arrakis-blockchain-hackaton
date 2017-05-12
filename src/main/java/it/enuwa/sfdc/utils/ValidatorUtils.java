package it.enuwa.sfdc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by Mike on 4/3/2016.
 */
public class ValidatorUtils {


    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtils.class);

    private static DateTimeFormatter dateDashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter dateDashFormatterStaging = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static Validator<String> emptyOrNullValidator = (String s) -> {
        return Optional.ofNullable(s).filter(k -> !k.isEmpty()).isPresent();
    };

    public static boolean validDouble(String s){
        try{
        Double.parseDouble(s);
        }catch(Throwable t){
            if(emptyOrNullValidator.validate(s)){
                logger.warn("invalid number "+s);
            }
            return false;
        }
        return true;
    }

    public static boolean validInt(String s){
        try{
            Integer.parseInt(s);
        }catch(Throwable t){
            logger.warn("invalid number "+s);
            return false;
        }
        return true;
    }

    public static boolean validLong(String s){
        try{
            Long.parseLong(s);
        }catch(Throwable t){
            logger.warn("invalid number "+s);
            return false;
        }
        return true;
    }

    public static String getDate(String originDate){
        return LocalDate.parse(originDate,dateDashFormatterStaging).format(dateDashFormatter);
    }

    public static Validator<String> getEmptyOrNullValidator() {
        return emptyOrNullValidator;
    }
}
