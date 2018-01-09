/**
 * desc
 *
 * @author: liangzhen
 * @version: 1.0
 * @date: 2017-11-15
 * @since 1.8
 */
public class Eamil {
    private String user;
    private String passWord;

    public Eamil(String user, String passWord) {
        this.user = user;
        this.passWord = passWord;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
