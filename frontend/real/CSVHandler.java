import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class CSVHandler {
    public List<Contact> readCSV(String filePath) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (data.length >= 9) {
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].replaceAll("^\"|\"$", "").trim();
                    }
                    
                    contacts.add(new Contact(
                        data[0], data[1], data[2], data[3], 
                        data[4], data[5], data[6], data[7], data[8]
                    ));
                }
            }
        }
        return contacts;
    }
}
