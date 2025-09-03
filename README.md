# firefly-csv-preprocessor

This preprocessor is used to transform CSV files into a format that can be used by the Firefly CSV Importer.
The default CSV file from my bank has three problems:

- The banking costs are booked weird, both source and destination account are the same. Firefly does not accept that.
  This is solved by just clearing the destination account.
- I have multiple accounts, the transfer transactions appear twice in the list. This is solved by skipping the transfer transactions on other accounts then main.
- When Firefly did import earlier files, the account name for 'payment providers' was set. But due to that, it is sometimes hard to see for which party the payment was made. 
  I solved this by creating an additional column. In the import that extra column is added to the note.

## How to use this tool?

- Build the native executable as described below.
- Run the executable

```shell
alias firefly-csv-preprocessor='./target/firefly-csv-preprocessor-1.0.0-SNAPSHOT-runner'

cp ./target/firefly-csv-preprocessor-1.0.0-SNAPSHOT-runner $HOME/.local/bin/firefly-csv-preprocessor

firefly-csv-preprocessor -i input.csv \
                         -o output.csv \
                         -ca "IBAN/BBAN" \
                         -cda "Tegenrekening IBAN/BBAN" \
                         -cn "Naam tegenpartij" \
                         -ncn "Originele naam tegenpartij" \
                         -m "NL..ACOUNTNUMBER"

```




# Quarkus - firefly-csv-preprocessor 

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/firefly-csv-preprocessor-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- Picocli ([guide](https://quarkus.io/guides/picocli)): Develop command line applications with Picocli

## Provided Code

### Picocli Example

Hello and goodbye are civilization fundamentals. Let's not forget it with this example picocli application by changing the <code>command</code> and <code>parameters</code>.

[Related guide section...](https://quarkus.io/guides/picocli#command-line-application-with-multiple-commands)

Also for picocli applications the dev mode is supported. When running dev mode, the picocli application is executed and on press of the Enter key, is restarted.

As picocli applications will often require arguments to be passed on the commandline, this is also possible in dev mode via:

```shell script
./mvnw quarkus:dev -Dquarkus.args='Quarky'
```
