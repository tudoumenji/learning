package com.example.javafxdemo.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class SampleController {
    @FXML
    private WebView webView;

    public void initialize() {
        webView.getEngine().load("https://www.baidu.com");
    }
}
