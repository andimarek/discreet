# Discreet (Not released yet)

## Source code dependencies without conflicts

Discreet adds dependencies to your project by import source code while 
automatically "rewriting" it to avoid any conflicts.

### Why using discreet 

Sometimes you can't use another library: 
For example when writing a library itself adding a new dependency puts 
the burden on the user to resolve and maintain these transitive dependencies.  

The opposite can also be true: you can't use a library in a specific version because it is already 
in your dependency tree in a different incompatible version.

Sometimes maintaining a new library is too much work, especially for a few lines of code. 
Discreet makes this easier: just open a new PR in this repo and done.


### How it works 

Discreet downloads the dependency source code and puts it in a special `{buildDir}/discreet-src` directory.
The source code is also prefixed with your project name to make it private.
For example if you wanna use the `Async` class in your `example` project it will be in the `example.async` package.


## How to use it

Add the `discreet` plugin to your `build.gradle.kts`:


```groovy
plugins {
    java
    id("com.andimarek.discreet")
}
```


After that you can add `discreet` dependencies to your project.
 
For example: 

```groovy
dependencies {
    "discreet"("com.andimarek.discreet:async:2019-03-18T08-44-53:sources")
}

```

## Contribute new modules

Have a look at the existing [modules](modules) directory and create a new PR with new subfolder in it.  

## Available modules


### Async

...

