package nl.ceesbos.firefly_csv_preprocessor;

import nl.ceesbos.firefly_csv_preprocessor.preprocessor.Preprocessor;

import picocli.CommandLine;
import picocli.CommandLine.Command;


import java.io.File;

@Command(name = "preprocess", mixinStandardHelpOptions = true)
public class PreprocessorCommand implements Runnable {

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input file", defaultValue = "input.csv")
    String inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output file", defaultValue = "output.csv")
    String outputFile;

    @CommandLine.Option(names = {"-m", "--main-account"}, description = "Main accountnumber", required = true)
    String mainAccount;

    @CommandLine.Option(names = {"-ca", "--column-accountnumber"}, description = "Column name for the accountnumber", defaultValue = "IBAN/BBAN")
    String columnAccountNumber;

    @CommandLine.Option(names = {"-cn", "--column-name"}, description = "Column name for the counterparty", defaultValue = "Naam tegenpartij")
    String columnCounterParty;

    @CommandLine.Option(names = {"-cda", "--column-destination-account"}, description = "Column name for the destination accountnumber", defaultValue = "Tegenrekening IBAN/BBAN")
    String columnDestinationAccountNumber;

    @CommandLine.Option(names = {"-ncn", "--new-column-name"}, description = "The name of the new column to created for the orignal counterparty name", defaultValue = "Originele naam tegenpartij")
    String newColumnName;


    @Override
    public void run() {

        System.out.println("Delete old file: " + outputFile);
        File oldFile = new File(outputFile);
        oldFile.delete();

        Preprocessor.builder()
                .inputFile(inputFile)
                .outputFile(outputFile)
                .mainAccount(mainAccount)
                .columnAccountNumber(columnAccountNumber)
                .columnCounterParty(columnCounterParty)
                .columnDestinationAccountNumber(columnDestinationAccountNumber)
                .newColumnName(newColumnName)
                .build()
                .enrichTransactions();
    }
}
