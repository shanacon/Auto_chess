package Auto_chess;

import java.io.*;
import java.net.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Client extends Application {

	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	public boolean game_end = false;
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("background.png")));
	public Scene first_scene;
	public AnchorPane anchor = new AnchorPane();
	public int now_mod = 0;
	public Room room = new Room();
	public Socket socket;
	@Override
	public void start (Stage primaryStage) throws Exception {
		first_scene = new Scene(anchor,1280,720);
		background.setLayoutX(0);
		background.setLayoutY(0);
		TextField name_input = new TextField ();
		name_input.setPromptText("¿é¤J§Aªº¼ÊºÙ");
		name_input.setLayoutX(560);
		name_input.setLayoutY(375);
		name_input.resize(200, 50);
		anchor.getChildren().addAll(background,name_input);
		anchor.requestFocus();;
		primaryStage.setScene(first_scene);
		primaryStage.show();
		primaryStage.setTitle("Client");
		primaryStage.setScene(first_scene);
		primaryStage.show();
		Thread task = new Thread(new Ctask(primaryStage));
		name_input.addEventHandler(KeyEvent.KEY_PRESSED , (KeyEvent e) -> {
			if(e.getCode().equals(KeyCode.ENTER))
			{
				String name = name_input.getText();
				try {
					socket = new Socket("localhost", 8182);
					fromServer = new DataInputStream(socket.getInputStream());
					toServer = new DataOutputStream(socket.getOutputStream());
					battle_p.pdata.name = name;
					battle_p.refresh_pd();
					toServer.writeUTF(name);
					toServer.flush();
//					change_sence(room.anchor,battle_p.anchor,battle_p.scene,primaryStage);
					change_sence(anchor,room.anchor,room.scene,primaryStage);
					now_mod = 1;
					task.start();
				} catch (IOException e1) {
//					e1.printStackTrace();
				}
			}
		});
	}
	private class Ctask implements Runnable {
		public Stage primaryStage;
		public Ctask(Stage ins) {
			primaryStage =  ins;
		}
		@Override
		public void run() {
			int people = 0;
			while(now_mod==1&&people!=4){
				try {
					int in = fromServer.read();
					if(in > people)
						people = in;
					room.refresh_people(people);
					Thread.sleep(50);
				} catch (IOException e) {
//					e.printStackTrace();
				} catch (InterruptedException e) {
//					e.printStackTrace();
				}
			}
			//////////////////game start
			try {
				System.out.println("waiting player name");
				String sin = fromServer.readUTF();
				System.out.println(sin);
				String sp[] = sin.split("\t");
				int tag = -1;
				for(int i = 0;i < 4;i++) {
					if(sp[i].equals(battle_p.pdata.name))
						tag = i;
				}
				if(tag==-1)
					System.out.println("error");
				for(;tag < 3;tag++)
					sp[tag] = sp[tag+1].toString();
				battle_p.set_apm(sp[0],sp[1],sp[2]);
				Thread task2 = new Thread(new Cupdata());
				task2.start();
				Thread task3 = new Thread(new Cupdata2());
				task3.start();
			} catch (IOException e) {
//				e.printStackTrace();
			}
			room.game_start();
			Timeline gap = new Timeline();
			gap.getKeyFrames().addAll(
				new KeyFrame(new Duration(3000),(a)-> {change_sence(room.anchor,battle_p.anchor,battle_p.scene,primaryStage);
				/*battle_p.lastt.play();*/}));
			gap.play();
			now_mod=2;/////////////prepare battle
			for(int i = 0;i < 6;i++)
				enemy[i] = new C_in_prepare();
			while(game_end==false) {
				while(now_mod==2) {
					try {
						if(game_end==true)
							break;
						Thread.sleep(200);
					} catch (InterruptedException e) {
//						e.printStackTrace();
					}
				}
				if(game_end==true)
					break;
//				change_sence(room.anchor,battle_p.anchor,battle_p.scene,primaryStage);
				/////////////////////////////////start_battle
				if(now_mod==3) {
					try {
						for(int i = 0;i < 6;i++) {
							System.out.println("out b data¡G"+battle_p.pdata.send_p_data(i));
							toServer.writeUTF(battle_p.pdata.send_p_data(i));
							toServer.flush();
						}
						Thread.sleep(1000);
						int count = 0;
						String p1n = battle_p.pdata.name.toString(),p2n = "";
						while(count < 6) {
							enemy_data[count] = fromServer.readUTF();
//							System.out.println(enemy_data[count]);
							String[] check = enemy_data[count].split("\t");
							if(check[1].equals("bb")==true) {
								count++;
								p2n = check[2].toString();
							}
						}
						aya_enemy();
						A_battle battle_s = new A_battle(battle_p.pdata.cinp,enemy,p1n,p2n,left);
//						primaryStage.setScene(battle_s.scene);
//						change_sence(battle_p.anchor,battle_s.anchor,battle_s.scene,primaryStage);
						if(game_end==true)
							break;
						Timeline t2 = new Timeline();
						t2.getKeyFrames().addAll(new KeyFrame(new Duration(0),(a)-> {primaryStage.setScene(battle_s.scene);})
								,new KeyFrame(new Duration(0), new KeyValue(battle_s.anchor.opacityProperty(), 0))
								,new KeyFrame(new Duration(500), new KeyValue(battle_s.anchor.opacityProperty(), 1)));
						t2.play();
//						if(left==true) {
							toServer.writeUTF(battle_s.f_send_battle());
							System.out.println(battle_s.f_send_battle());
							toServer.flush();
//						}
//						System.out.println(in);
						while(battle_end==false) {
							String in = fromServer.readUTF();
							System.out.println("in¡G"+in);
							if(in.equals("end")==true) {
								battle_end = true;
								break;
							}
							String out = battle_s.b_action(in,left);
							toServer.writeUTF(out);
							System.out.println("out¡G"+out);
							toServer.flush();
							Thread.sleep(500);
						}
						battle_end = false;
						String all = "";
						while(all.equals("all end")==false) {
							all = fromServer.readUTF();
							Thread.sleep(500);
						}
						now_mod=2;
						next_round(battle_s);
						change_sence(battle_s.anchor,battle_p.anchor,battle_p.scene,primaryStage);
					} catch (IOException e) {
//						e.printStackTrace();
					} catch (InterruptedException e) {
//						e.printStackTrace();
					}
				}
				while(now_mod!=2) {
					try {
						if(game_end==true)
							break;
						Thread.sleep(200);
					} catch (InterruptedException e) {
//						e.printStackTrace();
					}
				}
				if(game_end==true)
					break;
			}
			if(battle_p.pdata.HP>0)
				battle_p.win.setVisible(true);
			else
				battle_p.lose.setVisible(true);
			Timeline end = new Timeline();
			end.getKeyFrames().addAll(new KeyFrame(new Duration(5000),(a)-> {primaryStage.close();
			try {
				fromServer.close();
				toServer.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}));
			end.play();
		}	
	}
	private class Cupdata implements Runnable{
		public void run() {
			while(game_end==false) {
				if(game_end==true)
					break;
				while(now_mod==2) {
					String out = battle_p.pdata.send_data();
					if(battle_p.time==0)
						now_mod = 3;
					out = out + now_mod+ "\t";
					try {
						if(game_end==true)
							break;
//						System.out.println("waiting out¡G");
						toServer.writeUTF(out);
//						System.out.println("out¡G"+out);
						toServer.flush();
						if(now_mod==3)
							break;
						Thread.sleep(1000);
					} catch (IOException e1) {
//						e1.printStackTrace();
					} catch (InterruptedException e) {
//						e.printStackTrace();
					}
				}
				if(game_end==true)
					break;
				Timeline t = new Timeline();
				t.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(battle_p.anchor.opacityProperty(), 1))
						,new KeyFrame(new Duration(500), new KeyValue(battle_p.anchor.opacityProperty(), 0)));
				t.play();
				///////////////////
				if(game_end==true)
					break;
				while(now_mod!=2) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private class Cupdata2 implements Runnable{
		public void run() {
			while(game_end==false) {
				while(now_mod==2){
					try {
						String get = "";
						if(now_mod==3)
							break;
						System.out.println("waiting get¡G");
						get = fromServer.readUTF();
						if(get.equals("game end")) {
							now_mod = 5;
							game_end = true;
							break;
						}
						String[] check = get.split("\t");
						if(check.length>47) {
							if(check[47].equals("true")==true&&battle_p.lastt.getStatus()==Animation.Status.STOPPED)
								battle_p.lastt.play();
						}
						System.out.println("get¡G"+get);
//						if(get.equals("go")==true)
//							break;
						refresh_anp(get);
						Thread.sleep(250);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				///
				while(now_mod!=2) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void next_round(A_battle battle_s) {
		battle_p.time = 10;
		battle_p.pdata.money+=battle_p.pdata.level*10;
		boolean win = true;
		int hpdmg = 1;
		if(left==true) {
			for(int i = 0;i < 6;i++) {
				if(battle_s.p2.cb[i].HP!=0) {
					win = false;
					hpdmg+=battle_s.p2.cb[i].LV;
				}
			}
		}
		else {
			for(int i = 0;i < 6;i++) {
				if(battle_s.p1.cb[i].HP!=0) {
					win = false;
					hpdmg+=battle_s.p1.cb[i].LV;
				}
			}
		}
//		hpdmg*=10;
		if(win==false)
			battle_p.pdata.HP-=hpdmg;
		battle_p.refresh_have_i();
	}
	public static void main (String[] args) {
		Application.launch(args);
	}
	public void change_sence(AnchorPane f_pane,AnchorPane t_pane,Scene t_scene,Stage stage) {
		Timeline t = new Timeline();
		t.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(f_pane.opacityProperty(), 1))
			,new KeyFrame(new Duration(500), new KeyValue(f_pane.opacityProperty(), 0))
			,new KeyFrame(new Duration(500),(a)-> {stage.setScene(t_scene);})
			,new KeyFrame(new Duration(500), new KeyValue(t_pane.opacityProperty(), 0))
			,new KeyFrame(new Duration(1000), new KeyValue(t_pane.opacityProperty(), 1)));
		t.play();
	}
	public void refresh_anp(String aya) {
		String data[] = aya.split("\t");
		if(battle_p.apm.get(data[0])!=null)
			battle_p.anp[battle_p.apm.get(data[0])].refresh(aya);;
	}
	public void aya_enemy() {
		for(int i = 0;i < 6;i++) {
			String[] aya = enemy_data[i].split("\t");
			int pos = Integer.valueOf(aya[3]);
			enemy[pos].prof = Integer.valueOf(aya[4]);
			enemy[pos].HP = Integer.valueOf(aya[5]);
			enemy[pos].ATK = Integer.valueOf(aya[6]);
			enemy[pos].ATKt = Integer.valueOf(aya[7]);
			enemy[pos].DEF = Integer.valueOf(aya[8]);
			enemy[pos].LV = Integer.valueOf(aya[9]);
			enemy[pos].far_atk = Double.valueOf(aya[10]);
			enemy[pos].col_atk = Boolean.valueOf(aya[11]);
			enemy[pos].row_atk = Boolean.valueOf(aya[12]);
			enemy[pos].CATK = Integer.valueOf(aya[13]);
			enemy[pos].Cmul = Integer.valueOf(aya[14]);
			enemy[pos].CP = Double.valueOf(aya[15]);
			enemy[pos].CA = Double.valueOf(aya[16]);
			enemy[pos].MD = Integer.valueOf(aya[17]);
			enemy[pos].anp_get_data(enemy[pos].prof,enemy[pos].LV);
			enemy[pos].c_skill[2].decode_tag(aya[18].toString());  
			enemy[pos].c_skill[3].decode_tag(aya[19].toString()); 
			left = Boolean.valueOf(aya[20]);
		}
	}
	public boolean battle_end = false;
	public boolean left  = true;
	public C_in_prepare[] enemy = new C_in_prepare[6];
	public String[] enemy_data = {"","","","","",""};
	public Battle_space battle_p = new Battle_space();
}