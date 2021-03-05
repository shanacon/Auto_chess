package Auto_chess;

public class Player_data {
	public int HP;
	public int money;
	public int level;
	public int[] up = new int[3];
	public String name;
	public Skill_O[] sini = new Skill_O[7];
	public C_in_prepare[] cinp= new C_in_prepare[6];
	public Player_data() {
		for(int i = 0;i < 6;i++)
			cinp[i] = new C_in_prepare();
		HP = 50;
		money = 999;
		level = 1;
		this.name = "";
		up[0] = 10;
		up[1] = 10;
		up[2] = 10;
	}
	public void set_c(int p,int pos){
		cinp[pos].get_data(p,1,pos);
		cinp[pos].df.pane.setVisible(false);
	}
	public String send_data() {
		String ret = "";
		ret = ret + name + "\t";//0
		ret = ret + level + "\t";//1
		ret = ret + money + "\t";//2
		for(int i = 0;i < 6;i++) {
			ret = ret + cinp[i].prof + "\t";//3
			ret = ret + cinp[i].HP + "\t";//4
			ret = ret + cinp[i].ATK + "\t";//5
			ret = ret + cinp[i].DEF + "\t";
			ret = ret + cinp[i].LV + "\t";
			for(int j = 2;j < 4;j++)
				ret = ret + cinp[i].c_skill[j].send_tag() + "\t";
		}
		ret = ret + HP + "\t";
		return ret;
	}
	public String send_p_data(int pos) {
		String ret = "bb" + "\t";
		ret = ret + name + "\t";//
		ret = ret + pos + "\t";//
		ret = ret + cinp[pos].prof + "\t";//
		ret = ret + cinp[pos].HP + "\t";//
		ret = ret + cinp[pos].ATK + "\t";//
		ret = ret + cinp[pos].ATKt + "\t";//
		ret = ret + cinp[pos].DEF + "\t";//
		ret = ret + cinp[pos].LV + "\t";//
		ret = ret + cinp[pos].far_atk + "\t";//
		ret = ret + cinp[pos].col_atk + "\t";//
		ret = ret + cinp[pos].row_atk + "\t";//
		ret = ret + cinp[pos].CATK + "\t";//
		ret = ret + cinp[pos].Cmul + "\t";//
		ret = ret + cinp[pos].CP + "\t";//
		ret = ret + cinp[pos].CA + "\t";//
		ret = ret + cinp[pos].MD + "\t";//
		ret = ret + cinp[pos].c_skill[2].send_tag()+ "\t";//
		ret = ret + cinp[pos].c_skill[3].send_tag()+ "\t";//
		return ret;
	}
}
