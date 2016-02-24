
import java.util.HashMap;
import java.util.StringTokenizer;


public class RAgent extends AgentInterface {

    public static void main(String[] args) {
        RAgent client = new RAgent("localhost", MAEDENPORT);
        client.run();
    }

    /**
     * CONSTRUCTOR
     */
    public RAgent(String h, int p) {
        registerWithGrid(h, p); //connect to the grid server socket
    }

    public void run() {
        String[] sensoryData = new String[8];
        while (true) {
            sensoryData = getSensoryInfo();
            String Direction = sensoryData[0].substring(0, 1);
            HashMap<String, Integer> obstacles = processRetinalField(sensoryData[2]);
            char move = chooseMove(Direction, obstacles).toCharArray()[0];
            gridOut.println(move);
        }
    }

    public String chooseMove(String directionToFood, HashMap<String, Integer> flags) {
        /**System.out.println("Flags Forwards: " + flags.get("f"));
         System.out.println("Flags Right: "+flags.get("r"));
         System.out.println("Flags Left: "+flags.get("l"));
         System.out.println("Flags Back: "+flags.get("b"));
         */
        System.out.println("Direction to the Food is: " + directionToFood);
        /**if (directionToFood.equals("b")) {
         return "l";
         }*/
        if (Math.random() < 0.25) {
            String D = randomDirection(Math.random());
            return D; // for a chance of a random move
        }
        Integer wallsBeforeFood = flags.get(directionToFood);
        Integer forwardWalls = flags.get("f");

        System.out.println("Walls before food: " + wallsBeforeFood);
        System.out.println("Distance to nearest forward wall is " + forwardWalls);
        if (wallsBeforeFood != null) {
            /**if (wallsBeforeFood==2) {
             if (directionToFood.equals("f")) { // If the direction to the food is forward while a wall
             if (Math.random() < 0.33) {   // is present 2 spaces forward then there is a 50% chance
             return "f";              // for a forward movement.
             }
             }
             }*/
            if (forwardWalls != null) {
                if (forwardWalls == 1) {
                    if (Math.random() > 0.50) { // if both forward and the direction to the food are blocked
                        return "r";          // choose to either go right or left randomly.
                    } else {
                        return "l";
                    }
                }
            }
            if (directionToFood.equals("f")) {
                return "r";
            }
            return "f"; // if the direction to the food is blocked but forward isn't: Go forward!
        }
        return directionToFood; // if the direction to the food isn't blocked: Go to the Food!
    }

    public HashMap<String, Integer> processRetinalField(String info) {
        HashMap<String, Integer> flags = new HashMap<String, Integer>();
        StringTokenizer visTokens = new StringTokenizer(info, "(", true);
        visTokens.nextToken();
        for (int i = 6; i >= 3; i--) {              //iterate backwards so character printout displays correctly
            visTokens.nextToken();
            for (int j = 0; j <= 4; j++) {             //iterate through the columns
                visTokens.nextToken();
                String visChars = visTokens.nextToken();
                char[] visArray = visChars.toCharArray();
                for (int x = 0; x < visChars.length(); x++) {
                    char cellChar = visArray[x];
                    switch (cellChar) {    //do something beneath
                        //case ' ':  break;
                        //case '@':  break;         //Rock
                        //case '+':  break;         //Food
                        //case '#':  break;         //Door
                        case '*':
                            if (i == 5 || j == 2) {
                                if (i < 5) {
                                    if (flags.get("f") == null) {
                                        flags.put("f", 5 - i);
                                    }
                                }
                                if (j > 2) {
                                    if (flags.get("r") == null) {
                                        flags.put("r", j - 2);
                                    }
                                }
                                if (i > 5) {
                                    flags.put("b", i - 5);
                                }
                                if (j < 2) {
                                    flags.put("l", 2 - j);
                                }
                            }                break;         //Wall
                                //case '=':  break;         //Narrows
                                //case 'K':  break;         //Key
                                //case 'T':  break;         //Hammer
                                //case 'Q':  break;         //Quicksand
                                //case 'O':  break;         //Food Collection
                                //case '$':  break;         //Gold
                                default:     break;
                            }
                    }
                }
            }
            /**
             System.out.println("Flags Forwards: " + flags.get("f"));
             System.out.println("Flags Right: "+flags.get("r"));
             System.out.println("Flags Left: "+flags.get("l"));
             System.out.println("Flags Back: "+flags.get("b"));
             */
            return flags;
        }

    }
