package db.cfg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class CfgReader {

    String url;
    String user;
    String password;
    String path = Paths.get("").toAbsolutePath().toString() + "/src/db/cfg/dbCfg.txt";

    public CfgReader() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            url = br.readLine().split(" ")[1];
            user = br.readLine().split(" ")[1];
            password = br.readLine().split(" ")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public String getUrl() {
    return url;
    }

    public String getUser() {
    return user;
    }

    public String getPassword() {
    return password;
    }
}
