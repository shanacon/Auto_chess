package Auto_chess;

import java.net.Socket;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Room {
	public Scene scene;
	public Text waiting = new Text("等待其他玩家中");
	public Text starting = new Text("遊戲即將開始");
	public AnchorPane anchor = new AnchorPane();
	public Room() {
		background.setLayoutX(0);
		background.setLayoutY(0);
		waiting.setLayoutX(455);
		waiting.setLayoutY(375);
		waiting.setStyle("-fx-font-size: 50px;");
		waiting.setFill(Color.WHITE);
		starting.setLayoutX(425);
		starting.setLayoutY(412);
		starting.setStyle("-fx-font-size: 70px;");
		starting.setFill(Color.WHITE);
		starting.setVisible(false);
		anchor.getChildren().addAll(this.background,waiting,starting);
		scene  = new Scene(anchor,1280,720);
		set_ani();
		text1.play();
	}
	public void game_start() {
		text1.stop();
		waiting.setVisible(false);
		starting.setVisible(true);
		text2.play();
	}
	public void refresh_people(int people)
	{
		waiting.setText("等待其他玩家中..." + '\n' + "       目前" + people +"人");
	}
	public void set_ani()
	{
		text1.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO, new KeyValue(waiting.opacityProperty(), 1))
			,new KeyFrame(new Duration(500), new KeyValue(waiting.opacityProperty(), 0))
			,new KeyFrame(new Duration(1000), new KeyValue(waiting.opacityProperty(), 1))
			);
		text1.setCycleCount(Animation.INDEFINITE);
		text2.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO, new KeyValue(starting.opacityProperty(), 1))
			,new KeyFrame(new Duration(500), new KeyValue(starting.opacityProperty(), 0))
			,new KeyFrame(new Duration(1000), new KeyValue(starting.opacityProperty(), 1))
			);
		text2.setCycleCount(Animation.INDEFINITE);
	}
	public Timeline text1 = new Timeline();
	public Timeline text2 = new Timeline();
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("background.png")));
}
