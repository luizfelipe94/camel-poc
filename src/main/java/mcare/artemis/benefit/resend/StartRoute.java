package mcare.artemis.benefit.resend;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class StartRoute extends RouteBuilder {

    @Override
    public void configure() {

        errorHandler(defaultErrorHandler()
        .maximumRedeliveries(3)
        .redeliveryDelay(1000)
        .retryAttemptedLogLevel(LoggingLevel.ERROR));

        onException(HttpOperationFailedException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "${exchangeProperty.CamelExceptionCaught.errorMessage}")
                .maximumRedeliveries(5)
                .redeliveryDelay(1000);

        from("file:{{csv.location}}?delay=1s&noop=true")
                .log(LoggingLevel.INFO, "Iniciando processo")
                .unmarshal(new BindyCsvDataFormat(AthenaItem.class))
                .split(body())
                .bean(new WalletPayloadTransform())
                .to("direct:wallet");

        from("direct:wallet")
                .delay(500)
                .process(exchange -> exchange.getIn().setHeader("X-TRANSACTION-ID", UUID.randomUUID().toString()))
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Authorization", simple("{{claro-free.token}}"))
                .marshal().json()
//                .log("########### ENVIANDO - ${body}")
//                .throwException(new Exception("teste!!!"));
                .to("{{claro-free.url}}")
                .log(LoggingLevel.INFO,"Sucesso - ${body}");
//                .process(exchange -> System.exit(0));


    }

}
