import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String XML_FILE = "RecetaDOM.xml";

    public static void main(String[] args) {

        File archivoXML = new File("C:\\Users\\Alber\\IdeaProjects\\Recetas\\src\\RecetaDOM.xml");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivoXML);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menú Principal:");
                System.out.println("1. Leer ficheros XML con ese formato.");
                System.out.println("2. Añadir una nueva receta.");
                System.out.println("3. Modificar el título de la receta.");
                System.out.println("4. Eliminar la receta.");
                System.out.println("0. Salir del programa.");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        // Lógica para leer el archivo XML y mostrar las recetas
                        leerRecetas(doc);
                        break;
                    case 2:
                        // Lógica para añadir una nueva receta
                        agregarReceta(doc);
                        break;
                    case 3:
                        // Lógica para modificar el título de la receta
                        modificarTituloReceta(doc);
                        break;
                    case 4:
                        // Lógica para eliminar la receta
                        eliminarReceta(doc);
                        break;
                    case 0:
                        guardarDocumento(doc);
                        System.out.println("Saliendo del programa.");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void leerRecetas(Document doc) {
        NodeList recetas = doc.getElementsByTagName("Receta");
        for (int i = 0; i < recetas.getLength(); i++) {
            Element receta = (Element) recetas.item(i);
            String titulo = receta.getElementsByTagName("titulo").item(0).getTextContent();
            System.out.println("Título: " + titulo);
            // Puedes mostrar otros detalles de la receta aquí
        }
    }

    private static void agregarReceta(Document doc) {
        Scanner scanner = new Scanner(System.in);
        Element nuevaReceta = doc.createElement("Receta");
        System.out.print("Ingrese el título de la nueva receta: ");
        String titulo = scanner.nextLine();
        Element tituloElement = doc.createElement("titulo");
        tituloElement.appendChild(doc.createTextNode(titulo));
        nuevaReceta.appendChild(tituloElement);
        System.out.println("Receta agregada con éxito.");

        // Lógica para ingresar ingredientes, procedimiento, tiempo, etc.

        // Agregar la nueva receta al documento
        doc.getDocumentElement().appendChild(nuevaReceta);
    }

    private static void modificarTituloReceta(Document doc) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el título de la receta que desea modificar: ");
        String tituloAnterior = scanner.nextLine();

        NodeList recetas = doc.getElementsByTagName("Receta");
        for (int i = 0; i < recetas.getLength(); i++) {
            Element receta = (Element) recetas.item(i);
            String titulo = receta.getElementsByTagName("titulo").item(0).getTextContent();
            if (titulo.equalsIgnoreCase(tituloAnterior)) {
                System.out.print("Ingrese el nuevo título: ");
                String nuevoTitulo = scanner.nextLine();
                receta.getElementsByTagName("titulo").item(0).setTextContent(nuevoTitulo);
                System.out.println("Título modificado con éxito.");
                return;
            }
        }
        System.out.println("Receta no encontrada.");
    }

    private static void eliminarReceta(Document doc) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el título de la receta que desea eliminar: ");
        String tituloEliminar = scanner.nextLine();

        NodeList recetas = doc.getElementsByTagName("Receta");
        for (int i = 0; i < recetas.getLength(); i++) {
            Element receta = (Element) recetas.item(i);
            String titulo = receta.getElementsByTagName("titulo").item(0).getTextContent();
            if (titulo.equalsIgnoreCase(tituloEliminar)) {
                doc.getDocumentElement().removeChild(receta);
                System.out.println("Receta eliminada con éxito.");
                return;
            }
        }
        System.out.println("Receta no encontrada.");
    }

    private static void guardarDocumento(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XML_FILE));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

