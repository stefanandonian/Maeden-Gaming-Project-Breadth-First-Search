// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AgentInterface.java

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class AgentInterface
{

	public Socket gridSocket;
    public PrintWriter gridOut;
    public BufferedReader gridIn;
    public static final int MAEDENPORT = 7237;
    public static final String HOST = "localhost";
    String sensoryInfo[];

    public AgentInterface() {
        sensoryInfo = new String[8];
    }

    public abstract void run();

    public PrintWriter registerWithGrid(String s, int i)
    {
        try
        {
            gridSocket = new Socket(s, i);
            gridOut = new PrintWriter(gridSocket.getOutputStream(), true);
            gridOut.println("base");
            gridIn = new BufferedReader(new InputStreamReader(gridSocket.getInputStream()));
            gridIn.readLine();
            return gridOut;
        }
        catch(UnknownHostException unknownhostexception)
        {
            System.err.println((new StringBuilder()).append("Don't know about host: ").append(s).toString());
            System.exit(1);
        }
        catch(IOException ioexception)
        {
            System.err.println((new StringBuilder()).append("Couldn't get I/O for the connection to: ").append(s).toString());
            System.exit(1);
        }
        return null;
    }

    public String[] getSensoryInfo()
    {
        try
        {
            String s = gridIn.readLine().toLowerCase();
            if(s.equals("die") || s.equals("success") || s.equals("end"))
            {
                System.out.println((new StringBuilder()).append("Final status: ").append(s).toString());
                System.exit(1);
            }
            if(!s.equals("8"))
            {
                System.out.println((new StringBuilder()).append("getSensoryInfo: Unexpected number of data lines - ").append(s).toString());
                System.exit(1);
            }
            String s1 = direction(gridIn.readLine().toCharArray()[0]);
            String s2 = gridIn.readLine();
            String s3 = gridIn.readLine();
            String s4 = gridIn.readLine();
            String s5 = gridIn.readLine();
            String s6 = gridIn.readLine();
            String s7 = gridIn.readLine();
            String s8 = gridIn.readLine();
            sensoryInfo[0] = s1;
            sensoryInfo[1] = s2;
            sensoryInfo[2] = s3;
            sensoryInfo[3] = s4;
            sensoryInfo[4] = s5;
            sensoryInfo[5] = s6;
            sensoryInfo[6] = s7;
            sensoryInfo[7] = s8;
            return sensoryInfo;
        }
        catch(Exception exception)
        {
            System.out.println("Error: sensoryInfo was not returned....");
        }
        return sensoryInfo;
    }

    public String direction(char c)
    {
        switch(c)
        {
        case 102: // 'f'
            return "forward";

        case 98: // 'b'
            return "back";

        case 108: // 'l'
            return "left";

        case 114: // 'r'
            return "right";

        case 104: // 'h'
            return "here!";
        }
        return "error with the direction";
    }

    public String randomDirection(Double double1)
    {
        if(double1.doubleValue() < 0.25D)
            return "f";
        if(double1.doubleValue() < 0.5D)
            return "r";
        if(double1.doubleValue() < 0.75D)
            return "b";
        if(double1.doubleValue() < 1.0D)
            return "l";
        else
            return "r";
    }

    
}
