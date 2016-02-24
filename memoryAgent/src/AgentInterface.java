import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * abstract class AgentInterface: Provides a template for an AI agent to a Grid world simulation.
 *
 *@author:  Stefan Andonian, *Josh Holm, *Wayne Iba
 *@date:    1-30-16
 *@version: Beta 0.2
 */
public abstract class AgentInterface {

    /** Socket connection globals */
    public Socket gridSocket;
    public PrintWriter gridOut;
    public BufferedReader gridIn;
    public static final int MAEDENPORT = 7237;
    public static final String HOST    = "localhost";
    String[] sensoryInfo               = new String[8];

    /**
     * run iterates through program commands
     * pre: sockets are connected to each other
     * post: program is run and exited when the agent reaches the food
     */
    public abstract void run();

     /** processRetinalField: takes a string input from the Maeden server and returns the 4 flags in the form
     *                      of a int[] representing how far away a wall is from the forward, right, backwards
     *                      and left directions.
     * Pre: String info contains list of list of list of chars(?)
     * Post: int[] of flags for where walls are located in reference to bot
     */
    public abstract HashMap<String, Integer> processRetinalField(String info);

    /**
     * chooseMove returns a direction to send to the server
     * pre: a direction to the food source and a hashmap of directions mapping to obstacles
     * post: a direction to send to the server
     */
    public abstract String chooseMove(String directionToFood, HashMap<String, Integer> flags);

    /**
     * registerWithGrid takes a string and an int
     * and creates a socket with the specified network name and port number
     * PRE: h is the name of the machine on the network, p is the port number of the server socket
     * POST: socket connects with the server socket on the given host
     */
    public PrintWriter registerWithGrid(String h, int p) {
        try {
            // connects to h machine on port p
            gridSocket = new Socket(h, p);

            // create output stream to communicate with grid
            gridOut = new PrintWriter(gridSocket.getOutputStream(), true);
            gridOut.println("base"); // send role to server

            //buffered reader reads from input stream from grid
            gridIn = new BufferedReader(new InputStreamReader(gridSocket.getInputStream()));
            //myID =
            gridIn.readLine(); // read this agent's ID number
            return gridOut;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + h);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + h);
            System.exit(1);
        }
        return null;
    }

    /**
     * getSensoryInfo gets the direcion to the food
     * LINE0: # of lines to be sent or one of: die, success, or End
     * LINE1: smell (food direction)
     * LINE2: inventory
     * LINE3: visual contents
     * LINE4: ground contents
     * LINE5: messages
     * LINE6: remaining energy
     * LINE7: lastActionStatus
     * LINE8: world time
     * pre: gridIn is initialized and connected to the grid server socket
     * post: heading stores direction to the food f, b, l, r, or h
     */
    public String[] getSensoryInfo() {
        try {
            String status = gridIn.readLine().toLowerCase();
            if((status.equals("die") || status.equals("success")) || status.equals("end")) {
                System.out.println("Final status: " + status);
                System.exit(1);
            }
            if ( ! status.equals("8") ){
                System.out.println("getSensoryInfo: Unexpected number of data lines - " + status);
                System.exit(1);
            }

            String heading = direction(gridIn.readLine().toCharArray()[0]); // 1: get the smell info
            String inventory = gridIn.readLine();                          // 2: get the inventory
            String info = gridIn.readLine();                              // 3: get the visual info
            String ground = gridIn.readLine();                           // 4: get ground contents
            String message = gridIn.readLine();                         // 5: get messages
            String energy = gridIn.readLine();                         // 6: energy
            String lastActionStatus = gridIn.readLine();              // 7: lastActionStatus
            String worldTime = gridIn.readLine();                    // 8: world time

            //create new array of the data that was just received.
            sensoryInfo[0] = heading;
            sensoryInfo[1] = inventory;
            sensoryInfo[2] = info;
            sensoryInfo[3] = ground;
            sensoryInfo[4] = message;
            sensoryInfo[5] = energy;
            sensoryInfo[6] = lastActionStatus;
            sensoryInfo[7] = worldTime;
            return sensoryInfo;
        }
        catch(Exception e) {}
        System.out.println("Error: sensoryInfo was not returned....");
        return sensoryInfo;
    }

    /**
     * direction  and returns a string to display in the terminal
     * pre: heading has char value f, b, l, r, or h
     * post: corresponding string is returned
     */
    public String direction(char h) {
        switch(h) {
            case 'f': return "forward";
            case 'b': return "back";
            case 'l': return "left";
            case 'r': return "right";
            case 'h': return "here!";
        }
        return "error with the direction";
    }

    /**
     * This method matches up a double with a return value of either "r","b","f", or "l"
     * @param D a double between 0.00 and 0.99
     * @return a string representing a direction for the bot to move
     */
    public String randomDirection(Double D) {
        if (D < 0.25) {
            return "f";
        }
        if (D < 0.50) {
            return "r";
        }
        if (D < .75) {
            return "b";
        }
        if (D < 1.0) {
            return "l";
        }
        return "r";
    }
}

