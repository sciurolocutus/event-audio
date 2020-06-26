package com.kanjisoup.audio.event.sdk.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class PlayEventValidationTest {

    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEmptyUuidIsInvalid() {
        PlayEvent event = PlayEvent.builder()
            .filePath("/path")
            .duration("1s")
            .uuid(null)
            .build();

        Set<ConstraintViolation<PlayEvent>> violations = validator.validate(event);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    public void testBlankFilePathIsInvalid() {
        PlayEvent event = PlayEvent.builder()
            .filePath("  ")
            .duration("1s")
            .uuid("abcd-1234")
            .build();

        Set<ConstraintViolation<PlayEvent>> violations = validator.validate(event);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}
