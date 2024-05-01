package com.pius.boilerplate

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class FailTest {

    @Test
    fun `실패 테스트`() {
        println("테스트가 돌았니?")
        Assertions.assertThat(true).isFalse()
    }
}