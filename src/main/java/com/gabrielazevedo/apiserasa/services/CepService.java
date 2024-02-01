package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.models.CepModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLConnection;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;


@Service
public class CepService {
    private static final String CONTENT_TYPE_API_CEP = "/json";
    @Value("${url.api.via.cep}")
    private String urlApiViaCep;
    public CepModel getDadosCep(String cep) throws Exception {
        URI uri = URI.create(urlApiViaCep + cep + CONTENT_TYPE_API_CEP);
        URLConnection connection = uri.toURL().openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String cepResponse = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((cepResponse = bufferedReader.readLine()) != null) {
            jsonCep.append(cepResponse);
        }

        return new Gson().fromJson(jsonCep.toString(), CepModel.class);
    }
}
