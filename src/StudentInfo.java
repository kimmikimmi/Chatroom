/**
 * Created by Jaden on 21/10/2015.
 */
public class StudentInfo {

    private String name; //object's name.
    private String passWord; //object's password
    private String iD; // object's id

    public StudentInfo(String name, String passWord, String iD) {
        this.name = name;
        this.passWord = passWord;
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }
}
