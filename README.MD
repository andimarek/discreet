# Discreet  

## Source code dependencies without conflicts

Discreet adds dependencies to your project by import source code while 
automatically rewriting it to avoid any conflicts by keeping it truly private.





## How to use it

Add the `discreet` plugin to your `build.gradle.kts`:


```kotlin
plugins {
    java
    id("com.andimarek.discreet")
}
```


After that you can add `discreet` dependencies to your project.
 
For example: 

```kotlin
dependencies {
    "discreet"("com.andimarek.discreet:async:2019-03-18T08-44-53:sources")
}

```



