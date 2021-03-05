package Auto_chess;
import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Server extends Application {
	DataInputStream ips[] = new DataInputStream[4];
	DataOutputStream ops[] = new DataOutputStream[4];
	public int people_num = 0;
	private TextArea logdata = new TextArea();
	public Scene first_scene;
	public AnchorPane anchor = new AnchorPane();
	public int[] mod = {0,0,0,0}; 
	public boolean game_start = false;
	public boolean game_end = false;
	public Button ts = new Button();
	private Socket[] socket = new Socket[4];
	private Server_database database = new Server_database();
	@Override
	public void start (Stage primaryStage) throws Exception {
		logdata.setEditable(false);
		ts.setLayoutX(200);
		ts.setLayoutY(300);
		ts.setText("time start");
		ts.addEventHandler(MouseEvent.MOUSE_PRESSED ,(MouseEvent e) -> {
			time_start = true;
		});
		anchor.getChildren().addAll(logdata,ts);
		Scene first_scene = new Scene(anchor,500,500);
		primaryStage.setScene(first_scene);
		primaryStage.setTitle("Server");
		primaryStage.show();
		new Thread( () -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8182);
				Platform.runLater( () -> {
					logdata.appendText("Server started time " + new Date() + '\n');
				});
				int count = 0;
				while (count < 4 ) {
					socket[count] = serverSocket.accept();
					Platform.runLater( () -> {
						logdata.appendText("Starting client " + people_num +'\n');
					});
					new Thread(new ThreadClient(socket[count],count)).start();
					count++;
				}
				boolean still_waiting = true;
				while(still_waiting == true) {
					for(int i = 0;i < 4;i++){
						if(mod[i]==0)
							break;
						if(i==3)
							still_waiting = false;
					}
					Thread.sleep(100);
				}
				for(int i = 0;i < 4;i++)
					mod[i]=2;
				game_start = true;
			} catch (Exception e) {
				logdata.appendText(e.toString() + '\n');
			}
		}).start();
//		while(game_start==false) {
//			Thread.sleep(200);
//		}
//		while(game_start!=false) {
//			Thread.sleep(200);
//		}
//		primaryStage.close();
//		for(int i = 0;i < 4;i++) {
//			ips[i].close();
//			ops[i].close();
//			socket[i].close();
//		}
	}
	class ThreadClient implements Runnable {
		private Socket socket;
		public int player_id;
		public ThreadClient(Socket socket,int player_id) {
			this.socket = socket;
			this.player_id = player_id;
		}
		@Override
		public void run () {
			try {
				ips[player_id] = new DataInputStream(socket.getInputStream());
				ops[player_id] = new DataOutputStream(socket.getOutputStream());
				String normal = ips[player_id].readUTF();
				database.player_name[player_id] = normal;
				//////////////////
				mod[player_id] = 1;
				people_num++;
				//////////////////
				refresh_people(people_num);
				Platform.runLater( () -> {
					logdata.appendText("add player "+'\n');
				});
				while(game_start == false)
					Thread.sleep(100);
				String sout = "";
				for(int i = 0; i < 4;i++)
					sout = sout+database.player_name[i]+"\t";
				ops[player_id].writeUTF(sout);
				ops[player_id].flush();
				Thread task2 = new Thread(new Supdata(ips[player_id],player_id));
				task2.start();
				while(game_start==true){
					boolean flag = false;
					while(flag==false) {
						for(int i  = 0;i < 4;i++) {
							if(mod[i]!=4)
								break;
							if(i==3)
								flag=true;
						}
						Thread.sleep(200);
					}
					///////////////////////battle start
					while(bend[player_id]==false) {
						String in = "";
//						System.out.println("Tag "+player_id +" "+b_w[player_id]);
						if(b_w[player_id]==-1)
							;
						else if(b_w[player_id]==0) {
							if(left[player_id]==true) {
								System.out.println(player_id+" waiting get:"+in);
								in = ips[player_id].readUTF();
								System.out.println(player_id+" get:"+in);
								if(in.equals("end")) {
									bend[player_id] = true;
								}
								b_w[player_id] = -1;
								b_s[player_id] = S_to(in);
//								b_s[bm.get(player_id)] = b_s[player_id];
								System.out.println("return " + b_s[player_id]);
							}
						}
						else if(b_w[player_id]==1) {
							if(left[player_id]==false) {
								System.out.println(player_id+" waiting get:"+in);
								in = ips[player_id].readUTF();
								System.out.println(player_id+" get:"+in);
								if(in.equals("end")) {
									bend[player_id] = true;
								}
								b_w[player_id] = -1;
								b_s[player_id] = S_to(in);
//								b_s[bm.get(player_id)] = b_s[player_id];
								System.out.println("return " + b_s[player_id]);
							}
						}
						else {
								System.out.println(player_id+" waiting get:"+in);
								in = ips[player_id].readUTF();
								System.out.println(player_id+" get:"+in);
								if(in.equals("end")) {
									bend[player_id] = true;
								}
								b_w[player_id] = -1;
								b_s[player_id] = S_to(in);
//								b_s[bm.get(player_id)] = b_s[player_id];
								System.out.println("return " + b_s[player_id]);
						}
						Thread.sleep(500);
						if(b_s[player_id]==0) {
							if(left[player_id]==true) {
								if(in.equals("")==false) {
									System.out.println(player_id+" waiting out:"+in);
									ops[player_id].writeUTF(in);
									ops[player_id].flush();
									System.out.println(player_id+" out:"+in);
									b_w[player_id] = 0;
									b_w[bm.get(player_id)] = 0;
								}
							}
						}
						else if(b_s[player_id]==1) {
							if(left[player_id]==false) {
								if(in.equals("")==false) {
									System.out.println(player_id+" waiting out:"+in);
									ops[player_id].writeUTF(in);
									ops[player_id].flush();
									System.out.println(player_id+" out:"+in);
									b_w[player_id] = 1;
									b_w[bm.get(player_id)] = 1;
								}
							}
						}
						else if(b_s[player_id]==2){
//							if(left[player_id]==true) {
								if(in.equals("")==false) {
									System.out.println(player_id +" "+bm.get(player_id)+" waiting out:"+in);
									ops[player_id].writeUTF(in);
									ops[player_id].flush();
									ops[bm.get(player_id)].writeUTF(in);
									ops[bm.get(player_id)].flush();
									System.out.println(player_id+" and "+bm.get(player_id)+" out:"+in);
									b_w[player_id] = 2;
									b_w[bm.get(player_id)] = 2;
								}
//							}
						}
					}
					bend[player_id]=false;
					System.out.println(player_id + " end");
					mod[player_id] = 2;
				}
			} catch (Exception e) {
				logdata.appendText(e.toString() + '\n');
			}
			
//			finally {
//				try {
//					socket.close();
//				} catch (IOException e) {
//					System.out.println("Disconnecting to " + clientAddress + ":" + clientPort);
//				}
//			}
		}
	}
	private class Supdata implements Runnable{
		public DataInputStream ips;
		public int id;
		public Supdata(DataInputStream ips,int id) {
			this.ips = ips;
			this.id = id;
		}
		public void run() {
			while(game_start==true) {
				while(mod[id]==2) {
					try {
//						System.out.println("waiting get¡G");
						ayn[id] = ips.readUTF();
						System.out.println("get¡G"+ayn[id]);
						String sp[] = ayn[id].split("\t");
						ayn[id] = ayn[id] + time_start;
						if(sp.length > 46) {
							if(Integer.valueOf(sp[46])==3)
								mod[id]=3;
//							System.out.println("out¡G"+time_start);
							if(Integer.valueOf(sp[45])<=0)
								pal[id]=false;
							int d = 0;
							for(int i = 0;i < 4;i++) {
								if(pal[i]==false)
									d++;
							}
							if(d>=3&&id==0) {
								System.out.println("test");
								for(int j = 0;j < 4;j++) {
									ops[j].writeUTF("game end");
									ops[j].flush();
								}
								game_start = false;
								break;
							}
							else {
								for(int j = 0;j < 4;j++) {
									ops[j].writeUTF(ayn[id]);
									ops[j].flush();
								}
							}
						}
					} catch (IOException e1) {
//						e1.printStackTrace();
					}	
				}
				///////////////send battle
				if(id==0){
					battle_match();
//					left[0] = true;
//					left[1] = false;
//					left[2] = true;
//					left[3] = false;
//					bm.put(0, 1);
//					bm.put(1, 0);
//					bm.put(2, 3);
//					bm.put(3, 2);
				}
				try {
					boolean lock = false;
					while(lock==false) {
						for(int i = 0;i < 4;i++) {
							if(mod[i]!=3)
								break;
							else if(i==3)
								lock = true;
						}
						Thread.sleep(200);
					}
					String[] c_data =  {"","","","","",""};
					int in_count = 0;
					while(in_count<6) {
						c_data[in_count] = ips.readUTF();
						String[] check = c_data[in_count].split("\t");
						if(check[0].equals("bb")==true)
							in_count++;
					}
					for(int i = 0;i < 6;i++) {
						if(id==0) {
							c_data[i] = "0" + "\t" +c_data[i]+left[bm.get(0)]+"\t";
							ops[bm.get(0)].writeUTF(c_data[i]);
							ops[bm.get(0)].flush();
						}
						if(id==1) {
							c_data[i] = "1" + "\t" +c_data[i]+left[bm.get(1)]+"\t";
							ops[bm.get(1)].writeUTF(c_data[i]);
							ops[bm.get(1)].flush();
						}
						if(id==2) {
							c_data[i] = "2" + "\t" +c_data[i]+left[bm.get(2)]+"\t";
							ops[bm.get(2)].writeUTF(c_data[i]);
							ops[bm.get(2)].flush();
						}
						if(id==3) {
							c_data[i] = "3" + "\t" +c_data[i]+left[bm.get(3)]+"\t";
							ops[bm.get(3)].writeUTF(c_data[i]);
							ops[bm.get(3)].flush();
						}
					}
					//////////////////////get_q
					boolean flag = false;
					while(flag==false) {
						for(int i  = 0;i < 4;i++) {
							if(bm.get(i)==null)
								break;
							if(i==3)
								flag=true;
						}
						Thread.sleep(200);
					}
					mod[id]=4;
				///////////////////	start battle
					//////////////  end battle
					boolean flag2 = false;
					while(flag2==false) {
						for(int i  = 0;i < 4;i++) {
							if(mod[i]!=2)
								break;
							if(i==3)
								flag2=true;
						}
						Thread.sleep(200);
					}
					b_w[id] = 2;
					b_s[id] = 0;
					time_start = false;
					if(id==0) {
						for(int i =0;i < 4;i++) {
							ops[i].writeUTF("all end");
							ops[i].flush();;
						}
					}
				} catch (IOException e) {
//					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void refresh_people(int people){
			try {
				for(int i = 0;i < 4;i++){
					if(mod[i]==1)
					{
						ops[i].write(people);
						ops[i].flush();
					}
				}
			} catch(Exception e){
				e.printStackTrace();;
			}
		
	}
	public int S_to(String in) {
		if(in.charAt(0)=='l') {
			if(in.charAt(2)=='0')
				return 0;
			else
				return 2;
		}
		else if(in.charAt(0)=='r') {
			if(in.charAt(2)=='0')
				return 1;
			else
				return 2;
		}
		else if(in.charAt(0)=='e')
			return 2;
		else
			return peek_next(in);
	}
	public int peek_next(String aya) {
		int l = -1,r = -1;
		String data[] = aya.split("\t");
		if(data[0].equals("HP") == false) {
			System.out.println("error");
			return 0;
		}
		for(int i = 5;i > -1;i--) {
			if(data[i+1].equals("0")==false) {
				l = i;
				break;
			}
		}
		for(int i = 5;i > -1;i--) {
			if(data[i+7].equals("0")==false) {
				r = i;
				break;
			}
		}
//		System.out.println(l+" "+r);
		if(l==-1||r==-1)
			return 2;
		else if(l >= r)
			return 0;
		else
			return 1;
	}
	public void battle_match() {
		double[] ra = {0,0,0,0};
		for(int i = 0;i < 4;i++)
			ra[i] = Math.random();
		Map<Double,Integer> check = new  HashMap<Double,Integer>();
		for(int i = 0;i < 4;i++)
			check.put(ra[i], i);
		Arrays.sort(ra);
		for(int i = 0;i < 4;i++) {
			if(i%2==0)
				left[check.get(ra[i])] = true;
			else
				left[check.get(ra[i])] = false;
			if(i==0||i==2) {
				bm.put(check.get(ra[i]), check.get(ra[i+1]));
				bm.put(check.get(ra[i+1]), check.get(ra[i]));
				System.out.println(check.get(ra[i])+ " " +check.get(ra[i+1]));
			}
		}
	}
	public String[] ayn = {"","","",""};
	public Map<Integer,Integer> bm = new  HashMap<Integer,Integer>();
	public int[] b_w = {2,2,2,2};
	public int[] b_s = {0,0,0,0};
	public boolean time_start = false;
//	public boolean[] wait = {true,true,true,true};
	public boolean[] left = {false,false,false,false};
	public boolean[] bend = {false,false,false,false};
	public boolean[] pal = {true,true,true,true};
	public static void main (String[] args) {
		Application.launch(args);
	}
}