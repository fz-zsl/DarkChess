package com.example.darkchess;

import Piece.ChessPiece;
import algorithm.ClickOnBoard;
import algorithm.GeneralInit;
import algorithm.UndoPreviousOperation;
import autoPlayer.Greedy;
import datum.ChessBoardStatus;
import datum.UserStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop.GameEndsException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Properties.Property.version;


public class Board
{
    static public double gird = 50;
    static public double boardW = 4 * gird + 1f / 3 * gird;
    static public double boardH = 8 * gird + 1f / 3 * gird;
    static Canvas canvas = new Canvas(boardW + 2 * 41.65, boardH + 41.65);
    static public AnchorPane anchorPane = new AnchorPane();
    static public GraphicsContext gc = canvas.getGraphicsContext2D();
    static protected Text bTurn = new Text("先手翻棋");
    static public ArrayList<ChessPiece> chessPieceArrayList = new ArrayList<>();
    static protected Text r = new Text("红方");
    static protected Text b = new Text("黑方");
    static protected Text rText = new Text("分数 0");
    static protected Text bText = new Text("分数 0");

    public static void startGame(int mode) throws IOException
    {
        Stage stage = new Stage();
        Group group = new Group();

        anchorPane.setMinSize(boardW * 3, boardH * 1.5);
        anchorPane.setMinWidth(boardW * 2);
        anchorPane.setMinHeight(boardH * 1.5);

        //页面背景
        ImageView imageView1 = new ImageView("file:/D:/DarkChess/1.jpg");
        anchorPane.getChildren().add(imageView1);

        //菜单
        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("文件");
        Menu menu2 = new Menu("设置");
        Menu menu4 = new Menu("操作");
        Menu menu3 = new Menu("帮助");
        MenuItem menuItem1 = new MenuItem("退出登录");
        MenuItem menuItem3 = new MenuItem("版本信息");
        MenuItem menuItem4 = new MenuItem("联系我们");
        MenuItem menuItem5 = new MenuItem("悔棋");
        MenuItem menuItem6 = new MenuItem("作弊模式");
        MenuItem menuItem7 = new MenuItem("重新开始");
        menu1.getItems().add(menuItem1);
        menu3.getItems().addAll(menuItem6, menuItem3, menuItem4);
        menu4.getItems().addAll(menuItem5, menuItem7);
        menuBar.getMenus().addAll(menu1, menu2, menu4, menu3);
        EventHandler<ActionEvent> eventHandler1 = e ->
        {
            Showing.Info("关注微信公众号“Vitee Town”");
        };
        EventHandler<ActionEvent> eventHandler2 = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("quit.fxml"));
                Scene scene = null;
                try
                {
                    scene = new Scene(fxmlLoader.load(), 320, 240);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                stage.setTitle("退出");
                stage.setScene(scene);
                stage.show();
            }
        };

        EventHandler<ActionEvent> eventHandler4 = e ->
        {
            Showing.Info(version);
        };

        EventHandler<ActionEvent> eventHandler5 = e ->
        {
            System.out.println("按下了悔棋按钮");
            UndoPreviousOperation.undoPreviousOperation();
            //重新设定计分板
            Integer redScore = UserStatus.getRedScore();
            rText.setText("分数 " + redScore.toString());
            Integer blackScore = UserStatus.getBlackScore();
            bText.setText("分数 " + blackScore.toString());
            if (UserStatus.currentSide == 0)
                bTurn.setText("轮到红方");
            else if (UserStatus.currentSide == 1)
                bTurn.setText("轮到黑方");
        };

        EventHandler<ActionEvent> eventHandler6 = e ->
        {
            Showing.Info("鼠标右击翻看棋子");
        };

        EventHandler<ActionEvent> eventHandler7 = e ->
        {
            System.out.println("restart");
            anchorPane.getChildren().removeAll(chessPieceArrayList);
            chessPieceArrayList = CanvasUtils.setAllChess();
            GeneralInit.generalInit();
            //重新设定计分板
            Integer redScore = UserStatus.getRedScore();
            rText.setText("分数 " + redScore.toString());
            Integer blackScore = UserStatus.getBlackScore();
            bText.setText("分数 " + blackScore.toString());
            if(mode == 1)
            {
                r.setText("红方");
                r.setFill(Color.RED);
                b.setText("黑方");
                rText.setFill(Color.RED);
                bTurn.setText("先手翻棋");
            }

            else if (mode == 3)
            {
                r.setText("玩家");
                r.setFill(Color.BLACK);
                b.setText("机器");
                rText.setFill(Color.BLACK);
                bTurn.setText("玩家翻棋");
            }
        };

        menuItem1.setOnAction(eventHandler2);
        menuItem3.setOnAction(eventHandler4);
        menuItem4.setOnAction(eventHandler1);
        menuItem5.setOnAction(eventHandler5);
        menuItem7.setOnAction(eventHandler7);
        anchorPane.getChildren().add(menuBar);
        menuBar.setTranslateX(-10.4);
        menuBar.setTranslateY(-0.8);

        //阵营显示
        //红方
        r.setX(100);
        r.setY(100);
        r.setText("红方");
        r.setFill(Color.RED);
        r.setFont(new Font("verdana", 30));
        anchorPane.getChildren().add(r);
        //黑方
        b.setX(700);
        b.setY(100);
        b.setText("黑方");
        b.setFont(new Font("verdana", 30));
        anchorPane.getChildren().add(b);

        //分数显示
        //红方
        rText.setFill(Color.RED);
        rText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        rText.setX(85);
        rText.setY(180);
        anchorPane.getChildren().add(rText);
        //黑方
        bText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        bText.setX(685);
        bText.setY(180);
        anchorPane.getChildren().add(bText);

        //棋盘背景
        ImageView imageView = new ImageView("file:D:/DarkChess/wood.jpg");
        imageView.setFitHeight(boardH);
        imageView.setFitWidth(boardW);
        imageView.setX(41.65 + 300);
        imageView.setY(41.65);
        anchorPane.getChildren().add(imageView);
        //imageView.toBack();


        //棋盘
        CanvasUtils.drawChessBoard(gc);
        canvas.setHeight(458.35);
        canvas.setWidth(300);
        canvas.setTranslateX(300);
        anchorPane.getChildren().add(canvas);
        canvas.toFront();

        //画出棋子
        chessPieceArrayList = CanvasUtils.setAllChess();

        //轮到提示
        bTurn.setFill(Color.BLACK);
        bTurn.setTranslateX(393.04);
        bTurn.setTranslateY(505.40515);
        bTurn.setFont(Font.font("verdana", FontWeight.BOLD, 30));
        anchorPane.getChildren().add(bTurn);


        //废弃子
        // TODO: 2022/11/23

        //游戏初始化
        //显示界面
        group.getChildren().add(anchorPane);
        Scene scene = new Scene(group, 900, 600);
        stage.setTitle("游戏界面");
        stage.setScene(scene);
        stage.show();

        //设置棋盘，画出棋子
        if (mode == 1)
            offlineAction(group);
        else if (mode == 3)
            aiAction(group);
    }

    static private void offlineAction(Group group)
    {
        //设置棋盘，画出棋子
        GeneralInit.generalInit();
        EventHandler<MouseEvent> eventHandler = mouseEvent ->
        {
            int y = (int) ((mouseEvent.getX() - 341.65 - 1f / 6 * gird) / gird + 1);
            int x = (int) ((mouseEvent.getY() - 41.65 - 1f / 6 * gird) / gird + 1);
            //Showing.Info(mouseEvent.getX()+" "+mouseEvent.getY());
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());

            MouseButton button = mouseEvent.getButton();

            if (button == MouseButton.PRIMARY)//左击
            {
                //调用控制程序，判断棋子的类型
                if (x > 0 && x < 9 && y > 0 && y < 9)
                {
                    try
                    {
                        ClickOnBoard.clickOnBoard(x, y);
                    }
                    catch (GameEndsException e)
                    {
                        flip();
                        Showing.Info(e.toString());
                    }
                }
                //重新设定计分板
                Integer redScore = UserStatus.getRedScore();
                rText.setText("分数 " + redScore.toString());
                Integer blackScore = UserStatus.getBlackScore();
                bText.setText("分数 " + blackScore.toString());
                if (UserStatus.currentSide == 0)
                    bTurn.setText("轮到红方");
                else if (UserStatus.currentSide == 1)
                    bTurn.setText("轮到黑方");
            }
            else if (button == MouseButton.SECONDARY)
                chessPieceArrayList.get(ChessBoardStatus.getObjectIndex(x, y)).cheatingFlip();
        };
        group.setOnMouseClicked(eventHandler);
    }

    static private void aiAction(Group group)
    {
        r.setText("玩家");
        r.setFill(Color.BLACK);
        b.setText("机器");
        rText.setFill(Color.BLACK);
        bTurn.setText("玩家翻棋");
        //设置棋盘，画出棋子
        GeneralInit.generalInit();
        EventHandler<MouseEvent> eventHandler = mouseEvent ->
        {
            int y = (int) ((mouseEvent.getX() - 341.65 - 1f / 6 * gird) / gird + 1);
            int x = (int) ((mouseEvent.getY() - 41.65 - 1f / 6 * gird) / gird + 1);
            //Showing.Info(mouseEvent.getX()+" "+mouseEvent.getY());
            System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());

            MouseButton button = mouseEvent.getButton();

            if (button == MouseButton.PRIMARY)//左击
            {
                //调用控制程序，判断棋子的类型
                if (x > 0 && x < 9 && y > 0 && y < 9)
                {
                    try
                    {
                        //这里是GUI
                        if(UserStatus.AISide == 1)
                        {
                            r.setFill(Color.RED);
                            rText.setFill(Color.RED);
                        }
                        else if (UserStatus.AISide == 0)
                        {
                            b.setFill(Color.RED);
                            bText.setFill(Color.RED);
                        }
                        ClickOnBoard.clickOnBoard(x, y);
                        if (UserStatus.AISide == UserStatus.currentSide)
                            Greedy.greedy(4, true);
                    }
                    catch (GameEndsException e)
                    {
                        flip();
                        Showing.Info(e.toString());
                    }
                }
                //重新设定计分板
                if(UserStatus.AISide == 0)
                {
                    Integer redScore = UserStatus.getRedScore();
                    Integer blackScore = UserStatus.getBlackScore();
                    rText.setText("分数 " + blackScore.toString());
                    bText.setText("分数 " + redScore.toString());
                }
                else if (UserStatus.AISide == 1)
                {
                    Integer redScore = UserStatus.getRedScore();
                    rText.setText("分数 " + redScore.toString());
                    Integer blackScore = UserStatus.getBlackScore();
                    bText.setText("分数 " + blackScore.toString());
                }

                if (UserStatus.AISide == UserStatus.currentSide)
                    bTurn.setText("轮到机器");
                else
                    bTurn.setText("轮到玩家");
            }
            else if (button == MouseButton.SECONDARY)
                chessPieceArrayList.get(ChessBoardStatus.getObjectIndex(x, y)).cheatingFlip();
        };
        group.setOnMouseClicked(eventHandler);
    }

     private static void flip()
    {
        for(ChessPiece c: chessPieceArrayList)
            c.flipAChess();
    }


}
