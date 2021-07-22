package mcare.artemis.benefit.resend;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WalletPayloadTransform {

    @Handler
    public Map doHandler(Exchange exchange){

        return buildData(exchange);

    }

    private Map buildData(Exchange exchange){

        AthenaItem athenaItem = (AthenaItem) exchange.getIn().getBody();

        Map<String, Object> requestBody = new HashMap<>();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        requestBody.put("timestamp", sf.format(new Date()));
        requestBody.put("identifier", athenaItem.getIdentifier());

        Map context = new HashMap<>();
        context.put("externalId", athenaItem.getExternalId());
        context.put("originCode", athenaItem.getOriginCode());

        Map benefit = new HashMap<>();
        benefit.put("quantity", athenaItem.getQuantity());
        benefit.put("unit", athenaItem.getUnit());

        requestBody.put("context", context);
        requestBody.put("benefit", benefit);

        return requestBody;
    }

}
