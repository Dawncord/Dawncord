# Dawncord

Dawncord is a Java library for creating Discord bots. It provides a simple and intuitive way to interact with the Discord API, allowing developers to create powerful and customizable bots with ease.

To view the documentation, visit the website [here](https://dawncord.github.io).

## Features

- Easy-to-use API for handling various Discord interactions, including messages, events, and commands.
- Support for slash commands, message components (buttons, select menus), and modals.
- Flexible and extensible architecture, allowing for customization and integration with existing projects.

## Javadocs
Explore the [Javadocs](https://javadoc.io/doc/io.github.dawncord/Dawncord/latest/index.html) for detailed information on all classes, methods, and configurations available in Dawncord.

## Installation

To use Dawncord in your Java project, you can include it as a dependency using Maven or Gradle.

### Maven

```xml
<dependency>
    <groupId>io.github.dawncord</groupId>
    <artifactId>Dawncord</artifactId>
    <version>${version}</version>
</dependency>
```

### Gradle

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.dawncord:Dawncord:$version'
}
```

Replace $version with the latest version of Dawncord available.

## Example
Here's a simple example of how to create a basic Discord bot using Dawncord:

```java
import io.github.dawncord.api.Dawncord;
import io.github.dawncord.api.types.GatewayIntent;

public class MyDiscordBot {
    public static void main(String[] args) {
        Dawncord bot = new Dawncord("YOUR_BOT_TOKEN");
        bot.setIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES);

        bot.onSlashCommand("ping", event -> {
            event.reply("Pong!");
        });

        bot.start();
    }
}
```

Replace "YOUR_BOT_TOKEN" with your actual bot token obtained from the Discord Developer Portal.

## Contributing
Contributions to Dawncord are welcome! If you find any bugs, have feature requests, or want to contribute to the project, please open an issue or submit a pull request on GitHub.
