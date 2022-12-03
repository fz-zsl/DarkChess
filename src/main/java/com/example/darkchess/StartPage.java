package com.example.darkchess;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPage {
    @FXML
    private MenuItem contectUs;

    @FXML
    private Button login;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem preference;

    @FXML
    private Button register;

    @FXML
    private MenuItem version;

    public static Stage theRegisterStage;
    public static Stage theLogInStage;
    public static Boolean preferenceBoolean = false;
    private static Boolean loginBoolean = true;
    private static Boolean registerBoolean = true;



    @FXML
    void cContactUs(ActionEvent event)
    {
        Showing.Info("关注微信公众号“Vitee Town”");
    }

    @FXML
    void cLogOut(ActionEvent event) throws IOException
    {
        // TODO: 2022/11/15 添加保存信息的功能
        Platform.exit();
    }

    @FXML
    void cPreference(ActionEvent event) throws IOException
    {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("preference.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            stage.setTitle("偏好设置");
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    void cRegister(MouseEvent event) throws IOException
    {
        InitializationApplication.theStartPage.close();
        if(registerBoolean)
        {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("登录界面");
            stage.setScene(scene);
            stage.show();
            theRegisterStage = stage;
            registerBoolean = false;
        }
        else
            theRegisterStage.show();
    }

    @FXML
    void cLogIn(MouseEvent event)throws IOException
    {
        InitializationApplication.theStartPage.close();
        if(loginBoolean)
        {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            stage.setTitle("登录界面");
            stage.setScene(scene);
            stage.show();
            StartPage.theLogInStage = stage;
        }
        else
            theLogInStage.show();

    }

    @FXML
    void cVersion(ActionEvent event)
    {
        Showing.Info(Preference.version);
    }

}
