/**
 * desc
 *
 * @author: liangzhen
 * @version: 1.0
 * @date: 2017-11-15
 * @since 1.8
 */

import javax.mail.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class CheckingMails {
    static ExecutorService executorService = newCachedThreadPool();
    static String host = "pop.mail.ru";// change accordingly
    static String mailStoreType = "pop3";
    static AtomicInteger fail = new AtomicInteger(0);
    static AtomicInteger success = new AtomicInteger(0);

    public static void check(String host, String storeType, String user,
                             String password) {
        Properties properties = new Properties();
        properties.put("mail.pop3s.host", host);
        properties.put("mail.pop3s.port", "995");
       properties.put("mail.pop3s.starttls.enable", "true");
        try {
            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    user, password);
                        }
                    });
            Store store = emailSession.getStore("pop3s");


            store.connect();


            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            //这这里说明邮箱验证成功
            //    System.out.println(user + " is Ok");
            success.incrementAndGet();
            System.out.println(user +"------"+password+   " is OK");
            // close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println(user + " is Fail");
//            e.printStackTrace();
            fail.incrementAndGet();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Eamil> email = getEmail();
        emailCheck(email);
    }

    public static void emailCheck(List<Eamil> email) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(email.size());
        for (Eamil item : email) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    check(host, mailStoreType, item.getUser(), item.getPassWord());
                    countDownLatch.countDown();
                }
            });
        }
        System.out.println("-------------------------------------------------------");
        countDownLatch.await();
        System.out.println("-------------------------------------------------------");
        executorService.shutdown();
        System.out.println("成功总数：" + success.get() + "----失败总数:" + fail.get());
    }


    public static List<Eamil> getEmail() throws IOException {
        List<Eamil> list = new ArrayList<>();
        InputStream resourceAsStream = CheckingMails.class.getResourceAsStream("email.txt");
        InputStreamReader reader = new InputStreamReader(resourceAsStream);
        BufferedReader br = new BufferedReader(reader);
        String contentLine = br.readLine().trim();
        int num = 0;
        while (contentLine != null) {
            if (contentLine.length() > 0 && contentLine.contains("@mail.ru")) {
                num++;
                String[] split = contentLine.split("----");
                if (split == null || split.length != 2) {
                    continue;
                }
                Eamil em = new Eamil(split[0], split[1]);
                list.add(em);
            }
            if ((contentLine = br.readLine()) != null) {
                contentLine = contentLine.trim();
            }
        }
        System.out.println("总数为" + num);
        return list;
    }

}
