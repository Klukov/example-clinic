package org.klukov.example.clinic.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    public static LocalDateTime atEndOfDay(LocalDate localDate) {
        return localDate.plusDays(1).atStartOfDay().minusNanos(1);
    }
}
