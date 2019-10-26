package com.zipcodewilmington.phone;

import com.zipcodewilmington.exceptions.InvalidPhoneNumberFormatException;
import com.zipcodewilmington.tools.RandomNumberFactory;

import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by leon on 5/1/17.
 */
public final class PhoneNumberFactory {
    private static final Logger logger = Logger.getGlobal();

    private PhoneNumberFactory() {
        /** This constructor is private
         *  This class is uninstantiable */
    }

    public static PhoneNumber[] createRandomPhoneNumberArray(int phoneNumberCount) throws InvalidPhoneNumberFormatException {
        PhoneNumber[] phoneNumbers = new PhoneNumber[phoneNumberCount];
        for(int i = 0; i < phoneNumberCount; i++){
            phoneNumbers[i] = createRandomPhoneNumber();
        }
        return phoneNumbers;
    }

    public static PhoneNumber createRandomPhoneNumber() throws InvalidPhoneNumberFormatException {
        int areaCode = RandomNumberFactory.createInteger(1, 999);
        int centralOfficeCode = RandomNumberFactory.createInteger(1, 999);
        int phoneLineCode = RandomNumberFactory.createInteger(1, 9999);

        return createPhoneNumberSafely(areaCode, centralOfficeCode, phoneLineCode);
    }

    public static PhoneNumber createPhoneNumberSafely(int areaCode, int centralOfficeCode, int phoneLineCode) throws InvalidPhoneNumberFormatException {
        String cent = Integer.toString(centralOfficeCode);
        String line = Integer.toString(phoneLineCode);

        if (String.valueOf(centralOfficeCode).length() < 3){
            cent = String.format("%03d", centralOfficeCode);
        }
        if (String.valueOf(phoneLineCode).length() < 4){
            line = String.format("%04d", phoneLineCode);
        }

        String stringRepresentation = "(" + areaCode + ")" + "-" + cent + "-" + line;
        PhoneNumber phoneNumber = null;
        try{
            phoneNumber = new PhoneNumber(stringRepresentation);
        }
        catch(InvalidPhoneNumberFormatException e){
            logger.log(Level.INFO, stringRepresentation + " is not a valid phone number" );
            return null;
        }
        return createPhoneNumber(stringRepresentation);
    }

    public static PhoneNumber createPhoneNumber(String phoneNumberString) throws InvalidPhoneNumberFormatException {
        logger.log(Level.INFO, "Attempting to create a new PhoneNumber object with a value of " + phoneNumberString);
        return new PhoneNumber(phoneNumberString);
    }
}
