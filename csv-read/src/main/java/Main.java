import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        final String srcPath = "C:\\dev\\tmp\\refr\\";
        List<MyBean> result = loadTsv(srcPath + "TRAFFIC.tsv", MyBean.class);

        final String fileName = srcPath + "TFC_SENSOR_21007.tsv";
        final Class aClass = TfcSensor.class;

        List<TfcSensor> result2 = loadTsv(fileName, aClass);

        String inFiles[] = {
                "TFC_SENSOR_3.tsv",
                "TFC_SENSOR_21007.tsv",
                "TFC_SENSOR_111007.tsv",
                "TFC_SENSOR_112007.tsv",
                "TFC_SENSOR_113007.tsv",
                "TFC_SENSOR_115007.tsv",
                "TFC_SENSOR_905007.tsv",
                "TFC_SENSOR_906007.tsv",
                "TFC_SENSOR_945007.tsv"
        };
        List<TfcSensor> resultAll = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (String inFile : inFiles) {
                List<TfcSensor> result3 = loadTsv(srcPath+inFile, TfcSensor.class);
                resultAll.addAll(result3);
            }
        }

        System.out.println(resultAll.size());

    }

    private static <T> List<T> loadTsv(String fileName, Class<T> aClass) throws FileNotFoundException {
        HeaderColumnNameToPropertyMappingStrategy<T> mappingStrategy = new HeaderColumnNameToPropertyMappingStrategy<T>(aClass);

        CsvToBean<T> tsvToBean = new CsvToBeanBuilder<T>(new FileReader(fileName))
                .withSeparator('\t')
                .withMappingStrategy(mappingStrategy)
                .withType(aClass).build();

        return tsvToBean.parse();
    }
}
