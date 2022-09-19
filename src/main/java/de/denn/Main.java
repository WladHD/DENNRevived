package de.denn;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import de.denn.data.TSP_DataLoader;
import de.denn.data.TourFileWriter;
import de.denn.data.model.TSP_RawData;
import de.denn.errors.ClientMovedOnException;
import de.denn.errors.TSPUnsupportedTypeException;
import de.denn.graph.DENN;
import de.denn.graph.DENNAlgorithm;
import de.denn.graph.startnode.DENNStartNodeSelectType;
import de.denn.sandbox.Sandbox;
import de.denn.server.Server;

public class Main {

    public static String tspDirectory = null;
    public static int port = 25565;
    private static Scanner sc;
    private static final boolean sandbox = false;

    public static void main(String[] args) throws Exception {
        if (sandbox) {
            Sandbox.run();
            return;
        }

        sc = new Scanner(System.in);

        startListening();
    }

    public static void printMenu() {
        System.out.println("1. Ordner der TSP Probleme festlegen");

        if (!Server.getInstance().wasSetup() || !Server.getInstance().isRunning()) {
            System.out.println("2. Port des Servers verändern (" + Main.port + ")");

            if (tspDirectory != null && new File(tspDirectory).isDirectory())
                System.out.println("3. Server starten");
        } else if (Server.getInstance().wasSetup()) {
            System.out.println("4. Server stoppen");
        }

        System.out.println("5. Programm beenden");

    }

    public static void startListening() {
        new Thread(() -> {
            while (true) {
                printMenu();


                try {
                    String line = sc.nextLine();
                    int cmd = Integer.parseInt(line);

                    switch (cmd) {
                        case 1:
                            String s = sc.nextLine();
                            System.out.println(s);
                            tspDirectory = s;

                            if (tspDirectory == null || !new File(tspDirectory).isDirectory()) {
                                System.out.println("Ordner nicht gefunden");
                                tspDirectory = null;
                                break;
                            }

                            System.out.println("Ordner festgelegt: " + tspDirectory);
                            break;
                        case 2:
                            port = sc.nextInt();
                            System.out.println("Port festgelegt: " + port);
                            break;
                        case 3:
                            if (tspDirectory == null || !new File(tspDirectory).isDirectory()) {
                                System.out.println("TSP Ordner nicht festgelegt!");
                                break;
                            }

                            try {
                                Server.getInstance().start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            if (!Server.getInstance().isRunning()) {
                                System.out.println("Server ist nicht gestartet.");
                                break;
                            }

                            try {
                                Server.getInstance().stop();
                            } catch (IOException e) {
                            }

                            System.out.println("Server wurde geschlossen");
                            break;
                        case 5:
                            if (Server.getInstance().isRunning()) {
                                try {
                                    Server.getInstance().stop();
                                } catch (IOException e) {
                                }
                            }

                            System.exit(0);
                            return;
                        case 6:
                            System.out.println("Which problem?");
                            manualCalculation(sc.nextLine());
                            break;
                        default:
                            System.out.println("Unbekannter Befehl");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Zahl erwartet");
                } catch (Exception ex) {
                }
            }
        }).start();
    }

    private static void manualCalculation(String problem) {
        File load = new File(tspDirectory + "\\" + problem + ".tsp");

        try {
            System.out.println("Started calculating");
            TSP_RawData cont = TSP_DataLoader.getInstance().constructFromFile(load);
            DENNAlgorithm da = DENN.getInstance().setupAlgorithm(cont, null);

            File save = new File(tspDirectory + "\\" + problem + ".tsp.tour");

            if(da != null)
                new TourFileWriter(da.getTourByPreference(DENNStartNodeSelectType.GEOGRAPHICAL_CENTER)).save(save);

            System.out.println("Calc completed and saved");
            System.out.println(save.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
