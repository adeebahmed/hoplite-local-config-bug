package com.deeb.hoplitelocalconfigbug

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.PropertySource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ConfigTest {

    @Test
    fun `Config loader fails to substitute variable containing uppercase letters`() {
        val configLoader = ConfigLoaderBuilder
            .default()
            .addSource(PropertySource.resource("/local.conf"))
            .build()

        val config = configLoader.loadConfigOrThrow<Config>()
        config.localLower shouldBe "local"
        // This assertion fails because the value of `localUpper` is the string literal "{{envLocal}}" instead of the substituted "local"
        config.localUpper shouldBe "local"
    }
}
