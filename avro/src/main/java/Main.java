import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import ru.cwl.examples.avro.User;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);
// Leave favorite color null

// Alternate constructor
        User user2 = new User("Ben", 7,25, "red");

// Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(77)
                .setFavoriteNumber33(23)
                .build();

        // Serialize user1, user2 and user3 to disk
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        try {
            File file = new File("users.avro");
            dataFileWriter.create(user1.getSchema(), file);
            dataFileWriter.append(user1);
            dataFileWriter.append(user2);
            dataFileWriter.append(user3);
            for (int i = 0; i < 5; i++) {
                dataFileWriter.append(user1);
            }
            dataFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize Users from disk
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        File file = new File("users.avro");
        DataFileReader<User> dataFileReader = null;
        try {
            dataFileReader = new DataFileReader<User>(file, userDatumReader);
            User user = null;
            while (dataFileReader.hasNext()) {
// Reuse user object by passing it to next(). This saves us from
// allocating and garbage collecting many objects for files with
// many items.
                user = dataFileReader.next(user);
                System.out.println(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
