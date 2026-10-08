import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ParserMensajes {

    // --- FORMATO JSON (TEXTO) ---
    public static byte[] aJSON(Transaccion t) {
        String json = String.format(Locale.ROOT, "{\"id\":%d,\"origen\":\"%s\",\"monto\":%.2f,\"timestamp\":%d}",
                t.getIdTransaccion(), t.getOrigen(), t.getMonto(), t.getTimestamp());
        return json.getBytes(StandardCharsets.UTF_8);
    }

    public static Transaccion desdeJSON(String jsonStr) {
        String limpia = jsonStr.replace("{", "").replace("}", "").replace("\"", "");
        String[] parejas = limpia.split(",");

        int id = 0;
        String origen = "";
        double monto = 0.0;
        long timestamp = 0L;

        for (String pareja : parejas) {
            String[] claveValor = pareja.split(":");
            if (claveValor.length < 2) continue;
            
            String clave = claveValor[0].trim();
            String valor = claveValor[1].trim();

            switch (clave) {
                case "id": id = Integer.parseInt(valor); break;
                case "origen": origen = valor; break;
                case "monto": monto = Double.parseDouble(valor.replace(",", ".")); break;
                case "timestamp": timestamp = Long.parseLong(valor); break;
            }
        }
        return new Transaccion(id, origen, monto, timestamp);
    }

    // --- FORMATO BINARIO DIRECTO ---
    public static byte[] aBinario(Transaccion t) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(t.getIdTransaccion());
        dos.writeUTF(t.getOrigen());
        dos.writeDouble(t.getMonto());
        dos.writeLong(t.getTimestamp());
        dos.flush();

        return baos.toByteArray();
    }

    public static Transaccion desdeBinario(DataInputStream dis) throws IOException {
        int id = dis.readInt();
        String origen = dis.readUTF();
        double monto = dis.readDouble();
        long timestamp = dis.readLong();

        return new Transaccion(id, origen, monto, timestamp);
    }
}