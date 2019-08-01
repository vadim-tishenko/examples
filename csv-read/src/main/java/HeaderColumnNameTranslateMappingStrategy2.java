import com.google.common.base.CaseFormat;
import com.opencsv.CSVReader;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeaderColumnNameTranslateMappingStrategy2<T> extends HeaderColumnNameMappingStrategy<T> {
//    private final Map<String, String> columnMapping;

    /**
     * Default constructor.
     */
    public HeaderColumnNameTranslateMappingStrategy2(Class<T> ccc) {
//        columnMapping = new HashMap<String, String>();
        setType(ccc);
    }

    String s[]=null;

    @Override
    public String getColumnName(int col) {
//        synchronized (this) {
//            if (s == null) {
//
//                final int maxIndex = headerIndex.findMaxIndex() + 1;
//
//                s = new String[maxIndex];
//                for (int i = 0; i < maxIndex; i++) {
//                    String name = headerIndex.getByPosition(i);
//                    name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
//                    s[i] = name;
//                }
//            }
//        }
        return s[col];
    }

    @Override
    public void captureHeader(CSVReader reader) throws IOException, CsvRequiredFieldEmptyException {
        super.captureHeader(reader);
        synchronized (this) {
            if (s == null) {

                final int maxIndex = headerIndex.findMaxIndex() + 1;

                s = new String[maxIndex];
                for (int i = 0; i < maxIndex; i++) {
                    String name = headerIndex.getByPosition(i);
                    name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
                    s[i] = name;
                }
            }
        }
    }

    /**
     * Retrieves the column mappings of the strategy.
     * @return The column mappings of the strategy.
     */
//    public Map<String, String> getColumnMapping() {
//        return columnMapping;
//    }

//    /**
//     * Sets the column mapping to those passed in.
//     * @param columnMapping Source column mapping.
//     */
//    public void setColumnMapping(Map<String, String> columnMapping) {
//        this.columnMapping.clear();
//        for (Map.Entry<String, String> entry : columnMapping.entrySet()) {
//            this.columnMapping.put(entry.getKey().toUpperCase(), entry.getValue());
//        }
//    }

    /**
     * This mapping strategy is not compatible with annotations.
     *
     * @return {@code false}
     */
    @Override
    public boolean isAnnotationDriven() {
        return false;
    }
}

