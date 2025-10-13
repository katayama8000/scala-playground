## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

### Code Formatting

This project uses [Scalafmt](https://scalameta.org/scalafmt/) to ensure consistent code style.

To automatically format all source files, run the following command from your terminal:

```shell
cd "$(git rev-parse --show-toplevel)" && sbt scalafmt
```

To check if the code is correctly formatted without making any changes (useful for CI), run:

```shell
cd "$(git rev-parse --show-toplevel)" && sbt scalafmtCheck
```

### Running a specific main function

This project contains multiple `main` functions. To run a specific one without being prompted, use the `runMain` task followed by the fully qualified object name.

For example:
```shell
sbt "runMain signal.SignalMain"
```