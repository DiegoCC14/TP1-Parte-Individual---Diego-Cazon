package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class JpaPrueba {

    public static void main(String[] args) {
        Controladora control = new Controladora();

        try {
            System.out.println("====================================================================");
            System.out.println("  INICIANDO DEMOSTRACIÓN JPA CON SQLITE3 - ACTIVIDAD TODOCODE");
            System.out.println("====================================================================\n");

            ejecutarDemostracionCompleta(control);

            // Si se pasa el argumento --interactive, se abre el menú interactivo por consola
            if (args.length > 0 && args[0].equalsIgnoreCase("--interactive")) {
                mostrarMenuInteractivo(control);
            }
        } finally {
            control.cerrar();
        }
    }

    /**
     * Ejecuta paso a paso todas las operaciones del CRUD y relaciones enseñadas en los videos.
     */
    public static void ejecutarDemostracionCompleta(Controladora control) {
        try {
            // -----------------------------------------------------------------
            // PASO 1: CREACIÓN DE MATERIAS Y CARRERAS (Relación 1 a N)
            // Video 9: "CRUD in JAVA with JPA: One-To-Many Relationships"
            // -----------------------------------------------------------------
            System.out.println("------------------------------------------------------------");
            System.out.println("1. CREACIÓN DE MATERIAS Y CARRERAS (Relación 1 a N)");
            System.out.println("------------------------------------------------------------");

            // Carrera 1: Tecnicatura Universitaria en Programación
            Carrera carre1 = new Carrera();
            carre1.setNombre("Tecnicatura Universitaria en Programación");
            control.crearCarrera(carre1);
            System.out.println(">> Carrera 1 creada: " + carre1.getNombre() + " (ID: " + carre1.getId() + ")");

            // Creamos materias y las vinculamos a la Carrera 1
            Materia mate1 = new Materia();
            mate1.setNombre("Programación I");
            mate1.setTipo("Cuatrimestral");
            mate1.setCarrera(carre1);
            control.crearMateria(mate1);

            Materia mate2 = new Materia();
            mate2.setNombre("Bases de Datos I");
            mate2.setTipo("Cuatrimestral");
            mate2.setCarrera(carre1);
            control.crearMateria(mate2);

            Materia mate3 = new Materia();
            mate3.setNombre("Ingeniería de Software 2");
            mate3.setTipo("Anual");
            mate3.setCarrera(carre1);
            control.crearMateria(mate3);

            LinkedList<Materia> listaMaterias1 = new LinkedList<>();
            listaMaterias1.add(mate1);
            listaMaterias1.add(mate2);
            listaMaterias1.add(mate3);
            carre1.setListaMaterias(listaMaterias1);
            control.editarCarrera(carre1);

            // Carrera 2: Licenciatura en Sistemas de Información
            Carrera carre2 = new Carrera();
            carre2.setNombre("Licenciatura en Sistemas de Información");
            control.crearCarrera(carre2);
            System.out.println(">> Carrera 2 creada: " + carre2.getNombre() + " (ID: " + carre2.getId() + ")");

            Materia mate4 = new Materia();
            mate4.setNombre("Algoritmos y Estructuras de Datos");
            mate4.setTipo("Anual");
            mate4.setCarrera(carre2);
            control.crearMateria(mate4);

            LinkedList<Materia> listaMaterias2 = new LinkedList<>();
            listaMaterias2.add(mate4);
            carre2.setListaMaterias(listaMaterias2);
            control.editarCarrera(carre2);

            System.out.println(">> Materias asociadas a cada carrera correctamente.");

            // -----------------------------------------------------------------
            // PASO 2: CREACIÓN DE ALUMNOS CON CARRERA ASIGNADA (Relación 1 a 1)
            // Video 5 y 8: "CRUD: CREATE" y "One-to-One Relationship"
            // -----------------------------------------------------------------
            System.out.println("\n------------------------------------------------------------");
            System.out.println("2. CREACIÓN DE ALUMNOS (Relación 1 a 1 con Carrera)");
            System.out.println("------------------------------------------------------------");

            Alumno alu1 = new Alumno(0, "Diego", "Fernandez", new Date(), carre1);
            control.crearAlumno(alu1);
            System.out.println(">> Alumno 1 creado: " + alu1.getNombre() + " " + alu1.getApellido() +
                    " (ID: " + alu1.getId() + ") | Carrera: " + carre1.getNombre());

            Alumno alu2 = new Alumno(0, "Luisina", "de Paula", new Date(), carre2);
            control.crearAlumno(alu2);
            System.out.println(">> Alumno 2 creado: " + alu2.getNombre() + " " + alu2.getApellido() +
                    " (ID: " + alu2.getId() + ") | Carrera: " + carre2.getNombre());

            // Alumno temporal sin carrera para prueba de baja
            Alumno aluBorrar = new Alumno(0, "Carlos", "Temporal", new Date(), null);
            control.crearAlumno(aluBorrar);
            int idParaEliminar = aluBorrar.getId();
            System.out.println(">> Alumno temporal creado para prueba de baja (ID: " + idParaEliminar + ")");

            // -----------------------------------------------------------------
            // PASO 3: LECTURA DE DATOS (Find individual y FindEntities)
            // Video 7: "CRUD: FIND y FIND ENTITIES - LECTURA"
            // -----------------------------------------------------------------
            System.out.println("\n------------------------------------------------------------");
            System.out.println("3. LECTURA DE ENTIDADES (FIND Y FIND ALL)");
            System.out.println("------------------------------------------------------------");

            System.out.println("--- LISTA COMPLETA DE ALUMNOS EN BD ---");
            ArrayList<Alumno> listaAlumnos = control.traerAlumnos();
            for (Alumno alu : listaAlumnos) {
                String nombreCarrera = (alu.getCarrera() != null) ? alu.getCarrera().getNombre() : "Sin carrera asignada";
                System.out.println("• ID: " + alu.getId() +
                        " | Nombre: " + alu.getNombre() + " " + alu.getApellido() +
                        " | Fecha Nac: " + alu.getFechaNac() +
                        " | Carrera: " + nombreCarrera);
            }

            System.out.println("\n--- BÚSQUEDA INDIVIDUAL (find) DEL ALUMNO CON ID " + alu1.getId() + " ---");
            Alumno alumnoEncontrado = control.traerAlumno(alu1.getId());
            if (alumnoEncontrado != null) {
                System.out.println("Alumno encontrado: " + alumnoEncontrado);
            }

            System.out.println("\n--- DETALLE DE CARRERAS Y SUS MATERIAS (1 a N) ---");
            ArrayList<Carrera> listaCarreras = control.traerCarreras();
            for (Carrera c : listaCarreras) {
                System.out.println("• Carrera: " + c.getNombre() + " (ID: " + c.getId() + ")");
                if (c.getListaMaterias() != null && !c.getListaMaterias().isEmpty()) {
                    for (Materia m : c.getListaMaterias()) {
                        System.out.println("   └─ Materia: " + m.getNombre() + " [" + m.getTipo() + "]");
                    }
                }
            }

            // -----------------------------------------------------------------
            // PASO 4: MODIFICACIÓN / ACTUALIZACIÓN DE REGISTROS (Edit)
            // Video 6: "CRUD: DESTROY y EDIT - EDICIÓN"
            // -----------------------------------------------------------------
            System.out.println("\n------------------------------------------------------------");
            System.out.println("4. EDICIÓN / ACTUALIZACIÓN (EDIT)");
            System.out.println("------------------------------------------------------------");
            Alumno aluAEditar = control.traerAlumno(alu1.getId());
            if (aluAEditar != null) {
                System.out.println("Nombre original: " + aluAEditar.getNombre() + " " + aluAEditar.getApellido());
                aluAEditar.setApellido("Fernández Editado");
                control.editarAlumno(aluAEditar);

                Alumno aluVerif = control.traerAlumno(alu1.getId());
                System.out.println(">> Alumno actualizado en BD: " + aluVerif.getNombre() + " " + aluVerif.getApellido());
            }

            // -----------------------------------------------------------------
            // PASO 5: ELIMINACIÓN DE REGISTROS (Destroy)
            // Video 6: "CRUD: DESTROY y EDIT - BAJA"
            // -----------------------------------------------------------------
            System.out.println("\n------------------------------------------------------------");
            System.out.println("5. ELIMINACIÓN / BAJA (DESTROY)");
            System.out.println("------------------------------------------------------------");
            System.out.println("Eliminando alumno temporal con ID: " + idParaEliminar);
            control.eliminarAlumno(idParaEliminar);

            Alumno aluEliminado = control.traerAlumno(idParaEliminar);
            if (aluEliminado == null) {
                System.out.println(">> Verificación exitosa: El alumno con ID " + idParaEliminar + " ya NO existe en la base de datos.");
            } else {
                System.out.println("!! Advertencia: El alumno aún existe.");
            }

            System.out.println("\n====================================================================");
            System.out.println("  TODAS LAS OPERACIONES JPA SE COMPLETARON SATISFACTORIAMENTE");
            System.out.println("  Base de datos SQLite3 almacenada en: alumnos.db");
            System.out.println("====================================================================\n");

        } catch (Exception e) {
            System.err.println("Error durante la ejecución JPA: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarMenuInteractivo(Controladora control) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n--- MENÚ INTERACTIVO JPA ---");
            System.out.println("1. Listar todos los alumnos");
            System.out.println("2. Crear un nuevo alumno");
            System.out.println("3. Buscar un alumno por ID");
            System.out.println("4. Eliminar un alumno por ID");
            System.out.println("5. Listar carreras y sus materias");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    ArrayList<Alumno> alumnos = control.traerAlumnos();
                    System.out.println("\nListado de alumnos (" + alumnos.size() + "):");
                    for (Alumno a : alumnos) {
                        System.out.println(a);
                    }
                    break;
                case 2:
                    System.out.print("Ingrese nombre: ");
                    String nom = scanner.nextLine();
                    System.out.print("Ingrese apellido: ");
                    String ape = scanner.nextLine();
                    Alumno nuevo = new Alumno(0, nom, ape, new Date(), null);
                    control.crearAlumno(nuevo);
                    System.out.println("Alumno creado con ID: " + nuevo.getId());
                    break;
                case 3:
                    System.out.print("Ingrese ID del alumno: ");
                    int idBuscar = Integer.parseInt(scanner.nextLine());
                    Alumno encontrado = control.traerAlumno(idBuscar);
                    System.out.println(encontrado != null ? encontrado : "No se encontró el alumno con ID " + idBuscar);
                    break;
                case 4:
                    System.out.print("Ingrese ID a eliminar: ");
                    int idElim = Integer.parseInt(scanner.nextLine());
                    control.eliminarAlumno(idElim);
                    System.out.println("Operación de eliminación enviada.");
                    break;
                case 5:
                    ArrayList<Carrera> carreras = control.traerCarreras();
                    for (Carrera c : carreras) {
                        System.out.println("Carrera: " + c.getNombre());
                        for (Materia m : c.getListaMaterias()) {
                            System.out.println("   └─ " + m.getNombre() + " (" + m.getTipo() + ")");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
