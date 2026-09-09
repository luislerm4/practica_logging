import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ProcesadorCalificaciones {

    private static final Logger LOG =
            Logger.getLogger(
                    ProcesadorCalificaciones.class.getName()
            );

    public static void main(String[] args) {

        LOG.setUseParentHandlers(false);

        ConsoleHandler consola = new ConsoleHandler();
        consola.setLevel(Level.ALL);
        LOG.addHandler(consola);

        try {
            FileHandler archivoLog = new FileHandler("aplicacion.log");
            SimpleFormatter formato = new SimpleFormatter();
            archivoLog.setFormatter(formato);
            LOG.addHandler(archivoLog);
        } catch (IOException e) {
            System.err.println("No fue posible crear el log");
        }

        LOG.setLevel(Level.ALL);

        if (args.length == 0) {
            LOG.severe("No se especificó archivo de entrada");
            return;
        }

        String archivo = args[0];

        LOG.info("Archivo recibido: " + archivo);

        try (
                BufferedReader lector =
                        new BufferedReader(
                                new FileReader(archivo)
                        )
        ) {

            String linea;
            int contador = 0;

            while ((linea = lector.readLine()) != null) {

                contador++;

                try {

                    int calificacion = Integer.parseInt(linea.trim());

                    if (calificacion < 0 || calificacion > 100) {

                        LOG.warning(
                                "Calificación fuera de rango: "
                                        + calificacion
                        );

                        continue;
                    }

                    LOG.fine(
                            "Calificación válida: "
                                    + calificacion
                    );

                } catch (NumberFormatException e) {

                    LOG.warning(
                            "Dato no numérico: "
                                    + linea
                    );
                }
            }

            LOG.info("Registros procesados: " + contador);

        } catch (IOException e) {

            LOG.log(
                    Level.SEVERE,
                    "Error durante la lectura del archivo",
                    e
            );
        }
    }
}