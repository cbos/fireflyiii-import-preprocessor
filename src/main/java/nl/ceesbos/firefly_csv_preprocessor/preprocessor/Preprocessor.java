package nl.ceesbos.firefly_csv_preprocessor.preprocessor;

import lombok.Builder;
import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

@Builder
public class Preprocessor {

    private String inputFile;
    private String outputFile;
    private String mainAccount;
    private String columnAccountNumber;
    private String columnCounterParty;
    private String columnDestinationAccountNumber;
    private String newColumnName;

    public void enrichTransactions() {
        try {
            Reader in = new FileReader(inputFile);

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader() //auto parse headers
                    .setSkipHeaderRecord(true)
                    .build();

            FileWriter out = new FileWriter(outputFile);

            CSVParser parser = csvFormat.parse(in);

            int accountNumberIndex = -1;
            int accountNameIndex = -1;
            int destinationAccountIndex = -1;
            int newColumnIndex = -1;


            String[] headers = parser.getHeaderNames().toArray(String[]::new);
            for (int index = 0; index < headers.length; index++) {
                String header = headers[index];
                if (header.equals(columnAccountNumber)) {
                    accountNumberIndex = index;
                }
                if (header.equals(columnCounterParty)) {
                    accountNameIndex = index;
                }
                if (header.equals(columnDestinationAccountNumber)) {
                    destinationAccountIndex = index;
                }
                if (header.equals(newColumnName)) {
                    newColumnIndex = index;
                }
            }
            if(newColumnIndex == -1) {
                newColumnIndex = headers.length;

                headers = java.util.Arrays.copyOf(headers, newColumnIndex+1);
                System.out.println("Adding new column: " + newColumnName + " with index " + newColumnIndex);
                headers[newColumnIndex] = newColumnName;
            }

            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.builder()
                    .setQuoteMode(QuoteMode.ALL)
                    .setHeader(headers)
                    .build());


            Iterable<CSVRecord> records = parser.stream().toList();

            for (CSVRecord record : records) {
                String accountNumber = record.get(accountNumberIndex);
                String accountName = record.get(accountNameIndex);
                String destinationAccount = record.get(destinationAccountIndex);
                String counterParty = record.get(accountNameIndex);

//                record.get(accountNumberIndex);
//                record.get(accountNameIndex), record.get(descriptionIndex));
//

//
                String[] values = java.util.Arrays.copyOf(record.values(), newColumnIndex+1);
                boolean skipRecord = false;

                // Skip if source account is not main account and the counter party is main account
                // This is a tranfer between 2 accounts and should not be processed twice
                if(!accountNumber.equals(mainAccount) && destinationAccount.equals(mainAccount)) {
                    skipRecord = true;
                }

                // If source account number and destination account number are the same
                // Clear the destination account column
                if(accountNumber.equals(destinationAccount)) {
                    values[destinationAccountIndex] = "";
                }

                // Counter party is stored in additional column
                values[newColumnIndex] = counterParty;

                if(!skipRecord) {
                    printer.printRecord(values);
                }
            }

            printer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
