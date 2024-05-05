package healthStore.system;

import io.javalin.http.HttpStatus;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {


    public static void exceptionLog(HttpStatus code, String in){
        try(FileWriter fileWriter = new FileWriter("exceptionLog.txt", true)){
            fileWriter.write(in);
            fileWriter.append("Status code: "+code.getCode());
            fileWriter.append(" | ");
            fileWriter.append(LocalDateTime.now().toString());
            fileWriter.append("\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void consoleLog(String in){
        System.out.println(in);
    }
}
