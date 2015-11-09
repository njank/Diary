package diary;

import utils.EncryptUtils;
import java.io.File;

public class User {
    private String username, password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getPassword(){
        return this.password;
    }

    public File getPath() {
        return new File("data/"+EncryptUtils.md5(username));
    }
}
