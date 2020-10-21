package com.med.codechallenge.controller;


import com.med.codechallenge.service.LanguageService;
import com.med.codechallenge.model.Language;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class LanguageController {
    private LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;

    }


    @GetMapping("/trendyLanguages")
    public ResponseEntity<List<Language>> getTrendyLanguages() throws IOException, JSONException {
        List<Language> languages = this.languageService.getTrendyLanguages();

        return ResponseEntity.ok(languages);
    }
}
