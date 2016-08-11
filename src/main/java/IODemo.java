import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

import java.io.*;

/**
 * Created by cgspine on 16/7/21.
 */
public class IODemo {
    public static void main(String[] args){
//        InputStream in = null;
//        OutputStream out = null;
//
//        try {
//            in = new FileInputStream("res" + File.separator +"test.html");
//            out = new FileOutputStream("res" + File.separator + "testCopy.html", true);
//
//            int b;
//            while ((b = in.read()) != -1) {//-1为文件读完的标志
//                out.write((byte)b);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.close(in);
//            Util.close(out);
//        }




//        InputStream in = null;
//        OutputStream out = null;
//
//        try {
//            in = new FileInputStream("res" + File.separator +"test.html");
//            out = new FileOutputStream("res" + File.separator + "testCopy.html", true);
//
//            int size;
//            byte[] buffer = new byte[20];
//            while ((size = in.read(buffer)) != -1) {//-1为文件读完的标志
//                out.write(buffer,0, size);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.close(in);
//            Util.close(out);
//        }






//        Send send = new Send();
//        Receive recive = new Receive();
//        try {
//            send.getOut().connect(recive.getIn());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        new Thread(send).start();
//        new Thread(recive).start();






//        InputStream in = null;
//        OutputStream out = null;
//
//        try {
//            in = new FileInputStream("res" + File.separator +"test.html");
//            out = new FileOutputStream("res" + File.separator + "testCopy.html", true);
//
//            Reader reader = new InputStreamReader(in);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//
//            Writer writer = new OutputStreamWriter(out);
//            BufferedWriter bufferedWriter = new BufferedWriter(writer);
//
//
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                bufferedWriter.write(line);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();
//            }
//            bufferedReader.close();
//            bufferedWriter.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            Util.close(in);
//            Util.close(out);
//        }

        InputStream in = null;
        try {
            in = new FileInputStream("res" + File.separator + "gift.png");
            decodePng(in);
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.close(in);
        }

    }

    static class Send implements Runnable {
        private PipedOutputStream out;

        Send(){
            out = new PipedOutputStream();
        }

        public PipedOutputStream getOut() {
            return out;
        }

        @Override
        public void run() {
            String str = "Hello world";
            System.out.println("[" + Thread.currentThread().getName() + "] source data: " + str);
            try {
                out.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                Util.close(out);
            }
        }
    }

    static class Receive implements Runnable {
        private PipedInputStream in;

        Receive(){
            in = new PipedInputStream();
        }

        public PipedInputStream getIn() {
            return in;
        }

        @Override
        public void run() {
            byte[] output = new byte[1024];
            int len = 0;
            try {
                len = in.read(output);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                Util.close(in);
            }
            System.out.println("[" + Thread.currentThread().getName() + "] receive data: " + new String(output,0,len));
        }
    }

    //http://www.libpng.org/pub/png/spec/1.2/PNG-Rationale.html#R.PNG-file-signature
    private static final ByteString PNG_HEADER = ByteString.decodeHex("89504e470d0a1a0a");

    public static void decodePng(InputStream in) throws IOException {
        BufferedSource pngSource = Okio.buffer(Okio.source(in));

        ByteString header = pngSource.readByteString(PNG_HEADER.size());

        if (!header.equals(PNG_HEADER)) {
            throw new IOException("Not a PNG.");
        }

        while (true){
            // Each chunk is a length, type, data, and CRC offset.
            Buffer chunk = new Buffer();
            int length = pngSource.readInt();
            String type = pngSource.readUtf8(4);
            pngSource.readFully(chunk,length);
            int crc = pngSource.readInt();

            decodeChunk(type, chunk);
            if (type.equals("IEND")) break;
        }

        pngSource.close();
    }

    private static void decodeChunk(String type, Buffer chunk) {
        if (type.equals("IHDR")) {
            int width = chunk.readInt();
            int height = chunk.readInt();
            System.out.printf("%08x: %s %d x %d%n", chunk.size(), type, width, height);
        } else {
            System.out.printf("%08x: %s%n", chunk.size(), type);
        }
    }
}
