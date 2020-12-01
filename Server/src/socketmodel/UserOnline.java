
package socketmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author KyThuat88
 */
public class UserOnline implements Serializable{
    private ArrayList userOnl;

    public UserOnline() {
    }

    public UserOnline(ArrayList userOnl) {
        this.userOnl = userOnl;
    }

    public ArrayList getUserOnl() {
        return userOnl;
    }

    public void setUserOnl(ArrayList userOnl) {
        this.userOnl = userOnl;
    }
    
    
}
