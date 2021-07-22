package mcare.artemis.benefit.resend;

import lombok.Data;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@CsvRecord(separator = ",")
public class AthenaItem {

    @DataField(pos = 1)
    private String identifier;

    @DataField(pos = 2)
    private String externalId;

    @DataField(pos = 3)
    private String originCode;

    @DataField(pos = 4)
    private String quantity;

    @DataField(pos = 5)
    private String unit;

    @Override
    public String toString() {
        return "AthenaItem{" +
                "identifier='" + identifier + '\'' +
                ", externalId='" + externalId + '\'' +
                ", originCode='" + originCode + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
