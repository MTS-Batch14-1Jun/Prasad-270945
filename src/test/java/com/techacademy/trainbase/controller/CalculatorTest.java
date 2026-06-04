package com.techacademy.trainbase.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void givenTwoNumbers_whenAdd_thenReturnsSum() {
        // GIVEN
        int a = 5;
        int b = 10;
        
        // WHEN
        int result = a + b;
        
        // THEN
        Assertions.assertThat(result).isEqualTo(15);
    }
}