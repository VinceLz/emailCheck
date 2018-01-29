import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static void main(String arg[]) {
        int i = 25;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        Calendar now = Calendar.getInstance();
//        int i = now.get(Calendar.DAY_OF_MONTH);
        int y = now.get(Calendar.MONTH);
        if (i >= 26) {
            // 26日-9日进入的时候带出的为30天预测
            // 获取下月1-月底
            now.set(Calendar.DAY_OF_MONTH, 1);
            now.add(Calendar.MONTH, 1);
            System.out.println(sdf.format(now.getTimeInMillis()));
            String format = sdf2.format(now.getTime());
            now.add(Calendar.MONTH, 1);
            now.set(Calendar.DATE, 0);
            System.out.println(sdf.format(now.getTimeInMillis()));
            System.out.println(format);
        } else if (i <= 9) {
            // 本月1-31
            now.add(Calendar.MONTH, 0);
            now.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
            System.out.println(sdf.format(now.getTimeInMillis()));
            String format = sdf2.format(now.getTime());
            now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
            System.out.println(sdf.format(now.getTimeInMillis()));
            System.out.println(format);
        } else {
            // 10日-25日进入的时候带出的为45天预测
            //本月16 到下月月底
            now.add(Calendar.MONTH, 0);
            now.set(Calendar.DAY_OF_MONTH, 16);// 设置为16号,当前日期既为本月第一天
            System.out.println(sdf.format(now.getTimeInMillis()));
            String format = sdf2.format(now.getTime());

            //下月月底
            now.add(Calendar.MONTH, 2);
            now.set(Calendar.DATE, 0);
            System.out.println(sdf.format(now.getTimeInMillis()));
            System.out.println(format);
        }
    }
}
