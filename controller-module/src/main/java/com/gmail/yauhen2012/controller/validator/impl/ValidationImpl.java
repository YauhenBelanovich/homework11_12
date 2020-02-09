package com.gmail.yauhen2012.controller.validator.impl;

import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;

import com.gmail.yauhen2012.controller.validator.Validation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.yauhen2012.controller.validator.validationConstant.ValidationConstant.NAME_PATTERN;
import static com.gmail.yauhen2012.controller.validator.validationConstant.ValidationConstant.PASSWORD_PATTERN;
import static com.gmail.yauhen2012.controller.validator.validationConstant.ValidationConstant.STRING_MAX_LENGTH;

public class ValidationImpl implements Validation {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static volatile Validation instance;

    private ValidationImpl() {
    }

    public static Validation getInstance() {
        Validation localInstance = instance;
        if (localInstance == null) {
            synchronized (Validation.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ValidationImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public String nameValidation(String name) {

        int length = name.trim().length();
        if (length > 0 && length <= STRING_MAX_LENGTH) {
            if (!Pattern.matches(NAME_PATTERN, name)) {
                logger.error("Incorrect format name");
                return "Invalid field 'name' format";
            }
            return null;

        } else {
            logger.error("Empty field or name too long");
            return "Fill the field 'name'";
        }

    }

    @Override
    public String passwordValidation(String password) {

        int length = password.trim().length();
        if (length >= 6 && length <= STRING_MAX_LENGTH) {
            if (!Pattern.matches(PASSWORD_PATTERN, password)) {
                logger.error("Incorrect format password");
                return "Invalid field 'password' format";
            }
            return null;

        } else {
            logger.error("Wrong length field password");
            return "Wrong length field password. Fill the field 'password'";
        }

    }

    @Override
    public String roleValidation(String role) {

        int length = role.trim().length();

        if (length != 0) {
            if (Pattern.matches("ADMIN", role) || Pattern.matches("USER", role)) {
                return null;
            } else {
                logger.error("Incorrect format role");
                return "Invalid field 'role' format";
            }

        } else {
            logger.error("Empty field role");
            return "Fill the field 'role'";
        }

    }

}
