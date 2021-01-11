import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class writeExcelJSON {
    public static void main(String[] args) {
        try {
            //para saber que metricKeys há para analisar:
            //url = new URL("http://localhost:9000/api/metrics/search");

            // metricas** número do grupo
            FileWriter file = new FileWriter(new File("metricas78.txt"));
            file.write("{items: [");
            file.flush();
                // %3A** número do grupo
                URL url = new URL("http://localhost:9000/api/measures/component?component=groupId%3A78&metricKeys=ncloc,complexity,violations,bugs,code_smells,cognitive_complexity,comment_lines,comment_lines_density,file_complexity,duplicated_blocks,duplicated_lines,duplicated_lines_density,functions,sqale_rating,security_hotspots,security_rating");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream response = con.getInputStream();
                String json = new BufferedReader(new InputStreamReader(response)).lines().collect(Collectors.joining("\n"));
                try {
                    JSONObject output = new JSONObject(json);
                    JSONArray docs = output.getJSONObject("component").getJSONArray("measures");
                    file.write(docs.toString());
                    file.write(",\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            file.write("]}");
            file.flush();
            file.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

}
