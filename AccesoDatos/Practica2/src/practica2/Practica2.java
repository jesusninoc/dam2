package practica2;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AlumnoT
 */
public class Practica2 {

    public static void main(String[] args) {

        // Numero de alumnos
        int numeroAlumnos = 2;
        // Metodo para leer por teclado
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        // Map de alumnos con su dni
        HashMap<String, Alumno> mapAlumno = new HashMap();

        // Creamos los alumnos pidiendo sus datos y los anadimos al hashmap
        for (int i = 0; i < numeroAlumnos; i++) {
            try {
                anadirHashMap(teclado, mapAlumno);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //Creamos el fichero del map
        try {
            serializarMap(mapAlumno, "C:\\petra\\alumnos.obj");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Mostramos la lista
        mostrarLista();

        // Variable donde se almacenará la letra del menu introducida por el usuario
        String letra = null;
        try {
            System.out.print("Tu opcion: ");
            letra = teclado.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Switch para filtrar el resultado
        while (!letra.equals("F")) {
            switch (letra) {
                case "A": {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\petra\\alumnos.obj"));
                        leerMap(ois);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    //Mostramos la lista
                    mostrarLista();

                    // Variable donde se almacenará la letra del menu introducida por el usuario
                    letra = null;
                    try {
                        System.out.print("Tu opcion: ");
                        letra = teclado.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
                case "B":
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\petra\\alumnos.obj"));

                        calcularMedia(ois);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    //Mostramos la lista
                    mostrarLista();

                    // Variable donde se almacenará la letra del menu introducida por el usuario
                    letra = null;
                    try {
                        System.out.print("Tu opcion: ");
                        letra = teclado.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "C":
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\petra\\alumnos.obj"));

                        mostrarNovels(ois);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    //Mostramos la lista
                    mostrarLista();

                    // Variable donde se almacenará la letra del menu introducida por el usuario
                    letra = null;
                    try {
                        System.out.print("Tu opcion: ");
                        letra = teclado.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Letra Incorrecta, vuelve a intentarlo.");

                    //Mostramos la lista
                    mostrarLista();

                    // Variable donde se almacenará la letra del menu introducida por el usuario
                    letra = null;
                    try {
                        System.out.print("Tu opcion: ");
                        letra = teclado.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * Metodo para pedir dni por teclado ya nadir objeto y dni al HashMap
     *
     * @param ob Array de objetos
     * @param num Numero del objeto
     * @param te BufferedReader para leer por teclado
     * @param map HashMap donde anadiremos los objetos
     * @throws IOException
     */
    public static void anadirHashMap(BufferedReader te, HashMap map) throws IOException {

        System.out.print("Introduce nombre: ");
        String nombre = te.readLine();

        System.out.print("Introduce nota: ");
        int nota = Integer.parseInt(te.readLine());

        System.out.print("Introduce el dni: ");
        String dni = te.readLine();

        Alumno a = new Alumno(nombre, nota);
        System.out.println(a);
        map.put(dni, a);
    }

    /**
     * Metodo para crear un fichero con el HashMap serializado
     *
     * @param map HashMap
     * @param ruta Ruta del archivo
     * @throws IOException
     */
    public static void serializarMap(HashMap map, String ruta) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(map);
        oos.close();
    }

    /**
     * Metodo para leer un map dentro de un fichero
     *
     * @param ob ObjectInputStream con la ruta del fichero
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void leerMap(ObjectInputStream ob) throws IOException, ClassNotFoundException {
        HashMap m = null;
        try {
            while (true) {
                m = (HashMap) ob.readObject();
                System.out.println(m);

                Iterator it = m.keySet().iterator();
                while (it.hasNext()) {
                    String clave = (String) it.next();
                    Object valor = (Object) m.get(clave);
                    System.out.println(clave);
                    System.out.println(valor.toString());
                }
            }
        } catch (EOFException e) {
            System.out.println("Final del fichero");
        } finally {
            if (ob != null) {
                ob.close();
            }
        }
    }

    /**
     * Metodo para leer un map dentro de un fichero y calcular la media de las
     * notas
     *
     * @param ob ObjectInputStream con la ruta del fichero
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void calcularMedia(ObjectInputStream ob) throws IOException, ClassNotFoundException {
        HashMap<String, Alumno> m = null;
        try {
            while (true) {
                m = (HashMap) ob.readObject();

                int media = 0;
                int count = 0;

                Iterator it = m.keySet().iterator();
                while (it.hasNext()) {
                    String clave = (String) it.next();
                    media += m.get(clave).getNota();
                    count++;
                }

                media /= count;

                System.out.println(media);
            }
        } catch (EOFException e) {
            System.out.println("Final del fichero");
        } finally {
            if (ob != null) {
                ob.close();
            }
        }
    }

    /**
     * Metodo para leer un map dentro de un fichero y filtrar alumnos por notas
     *
     * @param ob ObjectInputStream con la ruta del fichero
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void mostrarNovels(ObjectInputStream ob) throws IOException, ClassNotFoundException {
        HashMap<String, Alumno> m = null;
        try {
            while (true) {
                m = (HashMap) ob.readObject();

                int media = 0;
                int count = 0;

                Iterator it = m.keySet().iterator();
                while (it.hasNext()) {
                    String clave = (String) it.next();
                    media += m.get(clave).getNota();
                    count++;
                }
                media /= count;

                Iterator it2 = m.keySet().iterator();
                while (it.hasNext()) {
                    String clave = (String) it2.next();
                    int nota = m.get(clave).getNota();

                    if (nota > media) {
                        System.out.println("Alumno " + m.get(clave).getNombre() + " nota: " + nota);
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Final del fichero");
        } finally {
            if (ob != null) {
                ob.close();
            }
        }
    }

    public static void leerMapSerializable(ObjectInputStream leer) throws IOException, ClassNotFoundException {
        Map m = null;
        try {

            while (true) {
                m = (HashMap) leer.readObject();
            }
        } catch (EOFException ex) {
            System.out.println("FINAL DE FICHERO");
            System.out.println(m.toString());
        } finally {
            if (leer != null) {
                leer.close();
            }
        }
    }

    /**
     * Metodo para mostrar el menu
     */
    public static void mostrarLista() {
        System.out.println("Elige una opción:\n\t"
                + "A) Mostrar alumnos y sus notas\n\t"
                + "B) Mostrar media de clase\n\t"
                + "C) Mostrar alumnos con media superior a la clase\n\t"
                + "F) Salir");
    }
}
