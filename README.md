[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.zicenter/shortener/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.zicenter/shortener)

# ZiCenter URL Shortener


## What is this ?

Maven client for the zicenter shortener service

## Usage

#### Create a service instance
```java
    // import dependency
    import io.github.zicenter.shortener.ShortenerService;
    ...
    // Instantiate the service. This should be called only once in your app's lifetime
    ShortenerService shortener = ShortenerService.getInstance("myemail@domain.com", "mypassword");
```

#### Shorten Urls
```java
     // Generate short url from a long one
     shortener.generate("https://example.com/path/to/my/very/long/url");

    // Bulk generate multiple short urls
    List<String> longUrls = Arrays.asList(
        "https://example.com/path/to/my/very/long/url",
        "https://example.com/path/to/another/url"
    );
    shortener.generate(longUrls);
```

## Installation

#### Apache Maven
```xml
<dependency>
  <groupId>io.github.zicenter</groupId>
  <artifactId>shortener</artifactId>
  <version>1.0.1</version>
</dependency>
```

#### Gradle Groovy DSL
```groovy
implementation 'io.github.zicenter:shortener:1.0.1'
```

#### Gradle Kotlin DSL
```kotlin
implementation("io.github.zicenter:shortener:1.0.1")
```

#### Scala SBT
```scala
libraryDependencies += "io.github.zicenter" % "shortener" % "1.0.1"
```

#### Apache Ivy
```xml
<dependency org="io.github.zicenter" name="shortener" rev="1.0.1" />
```

#### Groovy Grape
```groovy
@Grapes(
  @Grab(group='io.github.zicenter', module='shortener', version='1.0.1')
)
```

#### Leiningen
```
[io.github.zicenter/shortener "1.0.1"]
```

#### Apache Buildr
```
'io.github.zicenter:shortener:jar:1.0.1'
```

#### PURL
```
pkg:maven/io.github.zicenter/shortener@1.0.1
```

#### Bazel
```groovy
maven_jar(
    name = "shortener",
    artifact = "io.github.zicenter:shortener:1.0.1",
    sha1 = "0a2fcafa807a0c0712d81947716fd653fc7147e7",
)
```



