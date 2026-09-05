package com.app.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    // ---------- Name validation ----------

    @Test
    void testValidName_normalName_returnsTrue() {
        assertTrue(ValidationUtil.isValidName("Kamal Perera"));
    }

    @Test
    void testInvalidName_containsNumbers_returnsFalse() {
        assertFalse(ValidationUtil.isValidName("Kamal123"));
    }

    @Test
    void testInvalidName_tooShort_returnsFalse() {
        assertFalse(ValidationUtil.isValidName("K"));
    }

    // ---------- Phone number validation ----------

    @Test
    void testValidPhoneNumber_tenDigits_returnsTrue() {
        assertTrue(ValidationUtil.isValidPhoneNumber("0771234567"));
    }

    @Test
    void testInvalidPhoneNumber_nineDigits_returnsFalse() {
        assertFalse(ValidationUtil.isValidPhoneNumber("077123456"));
    }

    @Test
    void testInvalidPhoneNumber_elevenDigits_returnsFalse() {
        assertFalse(ValidationUtil.isValidPhoneNumber("07712345678"));
    }

    @Test
    void testInvalidPhoneNumber_containsLetters_returnsFalse() {
        assertFalse(ValidationUtil.isValidPhoneNumber("077abc4567"));
    }

    // ---------- Address validation ----------

    @Test
    void testValidAddress_normalLength_returnsTrue() {
        assertTrue(ValidationUtil.isValidAddress("123 Main Street, Colombo"));
    }

    @Test
    void testInvalidAddress_tooShort_returnsFalse() {
        assertFalse(ValidationUtil.isValidAddress("abc"));
    }

    // ---------- Date validation ----------

    @Test
    void testValidFutureDate_tomorrow_returnsTrue() {
        java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);
        assertTrue(ValidationUtil.isValidFutureDate(tomorrow.toString()));
    }

    @Test
    void testInvalidFutureDate_yesterday_returnsFalse() {
        java.time.LocalDate yesterday = java.time.LocalDate.now().minusDays(1);
        assertFalse(ValidationUtil.isValidFutureDate(yesterday.toString()));
    }

    @Test
    void testInvalidFutureDate_malformedString_returnsFalse() {
        assertFalse(ValidationUtil.isValidFutureDate("not-a-date"));
    }

    // ---------- Clinic hours validation ----------

    @Test
    void testWithinClinicHours_nineAM_returnsTrue() {
        assertTrue(ValidationUtil.isWithinClinicHours("09:00"));
    }

    @Test
    void testWithinClinicHours_exactlyOpeningTime_returnsTrue() {
        assertTrue(ValidationUtil.isWithinClinicHours("08:00"));
    }

    @Test
    void testWithinClinicHours_exactlyClosingTime_returnsTrue() {
        assertTrue(ValidationUtil.isWithinClinicHours("18:00"));
    }

    @Test
    void testOutsideClinicHours_earlyMorning_returnsFalse() {
        assertFalse(ValidationUtil.isWithinClinicHours("06:30"));
    }

    @Test
    void testOutsideClinicHours_lateNight_returnsFalse() {
        assertFalse(ValidationUtil.isWithinClinicHours("20:00"));
    }

    // ---------- Sanitize ----------

    @Test
    void testSanitize_removesAngleBracketsAndQuotes() {
        String result = ValidationUtil.sanitize("<script>alert('x')</script>");
        assertFalse(result.contains("<"));
        assertFalse(result.contains(">"));
        assertFalse(result.contains("'"));
    }

    @Test
    void testSanitize_trimsWhitespace() {
        assertEquals("Kamal", ValidationUtil.sanitize("  Kamal  "));
    }

    // ---------- Blank check ----------

    @Test
    void testIsBlank_emptyString_returnsTrue() {
        assertTrue(ValidationUtil.isBlank(""));
    }

    @Test
    void testIsBlank_nullValue_returnsTrue() {
        assertTrue(ValidationUtil.isBlank(null));
    }

    @Test
    void testIsBlank_normalText_returnsFalse() {
        assertFalse(ValidationUtil.isBlank("Kamal"));
    }
}