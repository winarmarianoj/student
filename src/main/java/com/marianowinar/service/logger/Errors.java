package com.marianowinar.service.logger;

import java.io.*;

public class Errors {
	private static final String LOG_PATH = "ErrorLog.txt";

    private static Errors logger;
    private  BufferedWriter bufferedWriter;

    private Errors() {
        openLog();
    }

    public static Errors getInstance(){
        if(logger == null){
            logger = new Errors();
        }
        return logger;
    }

    private void openLog(){
        File log = new File(LOG_PATH);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(log));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logError(String message){
        try{
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //no esta en uso
    public void closeLog(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
