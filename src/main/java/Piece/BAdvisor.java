package Piece;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import static com.example.darkchess.Board.anchorPane;

public class BAdvisor extends ChessPiece
{
    public static Circle circle = new Circle();
    public static Text text = new Text();
    private static boolean judge = true;
    public void setJudge(boolean judge1)
    {
        judge = judge1;
    }
    public BAdvisor(PieceType pieceType)
    {
        super(pieceType);
        if(judge)
        {
            circle.setFill(null);
            text.setFill(null);
            anchorPane.getChildren().add(circle);
            anchorPane.getChildren().add(text);
        }
        circle.toFront();
        text.toFront();
        judge = false;
    }

    public void re()
    {
        if(!status)
            this.reFlipAChess();
        if(i <= 0)
        {
            circle.setFill(null);
            text.setFill(null);
        }
        else
        {
            text.setText(i.toString());
        }
    }

    public Circle getCircle()
    {
        return circle;
    }

    public Text getText()
    {
        return text;
    }
    public static Integer i2 = 0;
    public void setEatenNumber()
    {
        if(i2 == -1)
            i2 = 0;
        i2++;
        this.getText().setText(i2.toString());
        System.out.println(i2);
    }
}