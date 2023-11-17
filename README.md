# Flight HUD: Reborn

__Flight HUD: Reborn__ is a fork and an unofficial port of [Flight HUD](https://github.com/frodare/FlightHud)

__Flight HUD: Reborn__ is a client side Minecraft Mod that adds a flight-simulator-style HUD for elytra flying.

![HUD Demo](images/flighthud_demo.gif?raw=true "HUD Screenshot")

## Features(Original)
- pitch ladder
- artificial horizon
- flight path (prograde vector)
- air speed with sliding scale
- heading with sliding scale
- direction ordinals and axes
- altitude with sliding scale (relative to block zero)
- height (distance from ground)
- elevation summary showing
  - void damage mark
  - block zero
  - ground level
  - build limit
  - current altitude
- elytra health percentage
- location in x/z

## Features(New)
- elytra bombing TNT count
- PullUp warning texts

## Components

![HUD Components](images/hud-diagram.png?raw=true "HUD Components")

## For Developers

### Import As A Gradle Dependency

#### Repositories
```groovy
repositories {
    //...
    maven {
        url "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}
```

#### Dependencies:

##### Fabric:

```groovy
dependencies {
    //...
    modImplementation "curse.maven:flighthud-reborn-936558:${project.flighthud_file_id}"
    modImplementation "curse.maven:forge-config-api-port-fabric-547434:${project.fcapi_file_id}" // Dependency of flighthud-reborn fabric
    compileOnly('com.electronwill.night-config:core:3.6.5') 
    compileOnly('com.electronwill.night-config:toml:3.6.5') // Dependency of forge config api port
}
```

##### Forge:

```groovy
dependencies {
    //...
    implementation fg.deobf("curse.maven:flighthud-reborn-936558:${project.flighthud_file_id}")
}
```

### Register a new indicator into the flight hud
```java
import com.plr.flighthud.api.HudRegistry;

void foo() {
  HudRegistry.addComponent((computer, dim) -> new ExampleIndicator(computer, dim));
}
```