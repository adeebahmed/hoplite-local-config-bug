# hoplite-local-config-bug

## Introduction

Created this sample project which includes a test to demonstrate an issue with `local.conf` variables not being substituted correctly. 
It seems that if the variable contains any uppercase letters, the config loader fails to replace the variable, and uses the variables string literal instead.

### Version
This seems to be a new issue with the latest version of hoplite `2.8.x` as it was working fine with `2.7.x`

### Steps to reproduce

In this repo there is an example of this bug in the `local.conf` file. 
The `localUpper` value is not being substituted. Causing the test in `ConfigTest` to fail


#### Setup
`src/main/resources/local.conf`
```conf
localLower = "{{envlocal}}"
localUpper = "{{envLocal}}" // has uppercase letter "L"
```

`.userconfig.conf`
```conf
envLocal: "local"
```

#### Move `.userconfig.conf` to home directory
> `mv .userconfig.conf ~/.userconfig.conf`


#### Build config loader
```kotlin
val configLoader = ConfigLoaderBuilder
    .default()
    .addSource(PropertySource.resource("/local.conf"))
    .build()

val config = configLoader.loadConfigOrThrow<Config>()
```

#### Result
```kotlin
config.localLower shouldBe "local" // -> true
config.localUpper shouldBe "local" // -> false 
// Expected :"local"  Actual   :"{{envLocal}}"
```

### Conclusion
Seems like there might be a `toLowerCase(]` call in the `ConfigLoader` library that is causing this issue. 
The current workaround for our team was to lowercase all `{{variable}}` names `local.confs`.

So if we had `{{envLocal}}` we would change it to `{{envlocal}}` and it would work as expected.
