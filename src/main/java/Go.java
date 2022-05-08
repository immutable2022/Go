import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class Go {

    public static void main(String[] args) throws IOException {

        java.util.logging.Logger logger =  java.util.logging.Logger.getLogger("Go.class");

        FileHandler fileHandler = new FileHandler(System.currentTimeMillis()+ ".log");
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);

        var time = System.currentTimeMillis();
        String ipAddress = "";

        Go app = new Go();
        InputStream is = app.getFileFromResourceAsStream("address.txt");

        final List<String> list = getAddreeList(is);

        ipAddress = printIpAddress(true);

        logger.info(ipAddress + "\n");

        try {
            Thread.sleep(3000);
        } catch (Exception ex){

        }

        long counter =0;

        while (true){

            counter++;
            if (time + 200000 < System.currentTimeMillis()){

                //System.out.println("Printed # of requests/second: " + counter/200 );

                logger.info("Printed # of requests/second: " + counter/200 + "\n");

                var tmpIpAddress = printIpAddress(false);
                if (tmpIpAddress != ipAddress){
                    System.out.println("****** NEW IP ADDRESS *******");
                    System.out.println(tmpIpAddress+"\n"+tmpIpAddress+"\n"
                            +"\n"+tmpIpAddress+"\n"+tmpIpAddress
                            +tmpIpAddress+"\n"+tmpIpAddress+"\n"+tmpIpAddress);
                    System.out.println("****** NEW IP ADDRESS *******");
                    ipAddress = tmpIpAddress;
                    logger.info(ipAddress + "\n");
                }
                time = System.currentTimeMillis();
                counter = 0;
            }

            var randomInt = getRandomInt(0)/25;
            try {
                Thread.sleep(randomInt);
            } catch (Exception ex){

            }

            //System.out.println(randomInt);

            if (randomInt >19){

                new Thread(() -> {
                    String[] commands1 = {"curl", "-X", "GET", "-m", "1", "http://" +getRandomAddress(list)};
                    //System.out.println(commands1[5]);
                    Process process = null;
                    InputStream isss = null;
                    try {
                        process = Runtime.getRuntime().exec(commands1);
                        isss = process.getInputStream();
                        isss.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            isss.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        process.destroy();
                    }
                }).start();
            } else {
                new Thread(() -> {
                    String[] commands2 = {"curl", "-X", "GET", "-m", "1", "https://" + getRandomAddress(list)};
                    System.out.println(commands2[5]);
                    Process process = null;
                    InputStream isss = null;
                    try {
                        process = Runtime.getRuntime().exec(commands2);
                        isss = process.getInputStream();
                        process.destroy();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            isss.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        process.destroy();
                    }
                }).start();
            }
        }
    }

    private static String printIpAddress(boolean print) throws IOException {
        String ip = "IP not found";
        try{
            URL whatismyip = new URL("https://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine(); //you get the IP as a String
            if (print)
                System.out.println(ip);
            in.close();
        } catch (UnknownHostException ex){
            System.out.println(ip);
        }
        return ip;
    }

    private static List<String> getAddreeList(InputStream is) {
        List<String> list = new ArrayList<>();
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            System.out.println("START LIST");
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                list.add(line);
            }
            reader.close();
            streamReader.close();
            System.out.println("END LIST");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    static int getRandomInt(Integer listSize){
        Random r = new Random();
        int low = 1;
        int high = 1000;
        if (listSize > 0)
            high = listSize;
        return r.nextInt(high-low) + low;
    }

    static String getRandomAddress(List<String> addressList){
        return addressList.get(getRandomInt(addressList.size()));
    }
}
