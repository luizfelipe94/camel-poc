package mcare.artemis.benefit.resend;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.apache.camel.quarkus.main.CamelMainApplication;

@QuarkusMain
public class Main {

    public static void main(String[] args) {

        // carregar parametros, etc

        Quarkus.run(CamelMainApplication.class);

    }

}
