package com.med.codechallenge.service;


import com.med.codechallenge.model.Language;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

@Service
public class LanguageService {

    List<Language> mylanguages  = new ArrayList<>();
    JSONArray jsonArray;

    public List<Language> getTrendyLanguages() throws IOException, JSONException {
        Set<String> allLanguages = new HashSet<>();

        // Getting Last Month Date and Formating it to yyyy-MM-dd Format
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, -30);
        dt = c.getTime();
        String lastMonth = formatter.format(dt);

        JSONObject json = readJsonFromUrl("https://api.github.com/search/repositories?q=created:%3E"+lastMonth+"&sort=stars&order=desc&per_page=100");
        jsonArray = (JSONArray) json.getJSONArray("items");
        for (int i=0; i < jsonArray.length(); i++) {
            String language = jsonArray.getJSONObject(i).getString("language");
            if(!language.equals("null"))
                allLanguages.add(language);
        }

        for (Iterator<String> it = allLanguages.iterator(); it.hasNext(); ) {
            String f = it.next();
            Language l = new Language();
            l.setName(f);
            int num = 0;
            for (int i=0; i < jsonArray.length(); i++) {
               if(f.equals(jsonArray.getJSONObject(i).getString("language"))){
                   l.setRepository(jsonArray.getJSONObject(i).getString("html_url"));
                   num ++;
               }
            }

            l.setNumberRepos(num);
            mylanguages.add(l);
        }

        Collections.sort(mylanguages);

        return mylanguages;
    }





    ///////////////////////// HELPING FUNCTIONS/////////////////////////////////

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
