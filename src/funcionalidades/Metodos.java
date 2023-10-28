package funcionalidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tarea.Tarea;

public class Metodos {

    Vector vPrincipal = new Vector();

    // Guardar datos en el vector
    public void guardarTarea(Tarea tarea) {
        vPrincipal.addElement(tarea);
    }

    // Guardar datos en un archivo txt
    public void guardarArchivo(Tarea tarea) {
        try {
            FileWriter fw = new FileWriter("tareas.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.print(tarea.getTitulo());
            pw.print("|" + tarea.getDescripcion());
            pw.print("|" + tarea.getFechaVencimiento());
            pw.print("|" + tarea.getPrioridad() + "\n");
            pw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // Mostrar los datos en un jTable
    public DefaultTableModel listaTareas() {
        Vector cabeceras = new Vector();
        cabeceras.addElement("Título");
        cabeceras.addElement("Descrippción");
        cabeceras.addElement("Fecha Vencimiento");
        cabeceras.addElement("Prioridad");

        // modelo de tabla para agregar cabeceras de la tabla
        DefaultTableModel mdlTabla = new DefaultTableModel(cabeceras, 0);

        try {
            FileReader fr = new FileReader("tareas.txt");
            BufferedReader br = new BufferedReader(fr);
            String d;

            while ((d = br.readLine()) != null) {
                StringTokenizer dato = new StringTokenizer(d, "|");

                Vector x = new Vector();
                while (dato.hasMoreTokens()) {
                    x.addElement(dato.nextToken());
                }
                mdlTabla.addRow(x);
            }

            fr.close();
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return mdlTabla;
    }

    public void eliminarTarea(int lineToRemove) {
        try {

            File inFile = new File("tareas.txt");

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename. 
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader("tareas.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new 
            //unless content matches data to be removed.
            int numberOfLine = 0;

            while ((line = br.readLine()) != null) {

                if (numberOfLine != lineToRemove) {
                    pw.println(line);
                    pw.flush();
                }

                numberOfLine++;
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile)) {
                System.out.println("Could not rename file");
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
