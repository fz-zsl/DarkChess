package com.example.darkchess;

import fileOperations.SaveGame;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Naming
{

    @FXML
    private TextField name;
    private String str;

    @FXML
    void clickOK(MouseEvent event)
    {
        //str中有用户输入的文件名字。
        // TODO: 2022/12/8 保存文件操作
        try {
            SaveGame.writeGameFile(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Showing.Info("进度已保存");
        Board.theNamingStage.close();
    }

    @FXML
    void keyPressed(KeyEvent event)
    {
        str = name.getText();
    }

}