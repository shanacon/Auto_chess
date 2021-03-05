package Auto_chess;

public class C_in_prepare {
	public int prof= 0;//1 for tank,2 for archer,3 for sword
	public int HP = 0;
	public int ATK = 0;
	public int ATKt = 0;
	public int DEF = 0;
	public int LV = 0;
	public double far_atk = 0;//1 for no,0.5 for half,0 for all
	public boolean col_atk = false;
	public boolean row_atk = false;
	public int CATK = 0;
	public int Cmul = 0;
	public double CP = 0;
	public double CA = 0;
	public int MD = 0;
	public Skill_O[] c_skill = new Skill_O[4];
	public int position;
	public C_data_field df;
	public C_in_prepare(int p,int level,int position) {
		df = new C_data_field();
		get_data(p,level,position);
	}
	public C_in_prepare() {
		df = new C_data_field();
		for(int i = 0;i < 4;i++) {
			if(c_skill[i]==null)
				c_skill[i] = new Skill_O(0,i,'a');
		}
		LV = 0;
	}
	public void copy(C_in_prepare in) {
		LV = in.LV;
		prof = in.prof;
		position = in.position;
		HP = in.HP;
		ATK = in.ATK;
		ATKt = in.ATKt;
		DEF = in.DEF;
		far_atk = in.far_atk;
		CATK = in.CATK;
		Cmul = in.Cmul;
		CP = in.CP;
		CA = in.CA;
		MD = in.MD;
		row_atk = in.row_atk;
		col_atk = in.col_atk;
		for(int i = 0;i < 4;i++)
			c_skill[i] = in.c_skill[i];
		position = in.position;
		df.set_text(this);
	}
	public void get_out(int p) {
		prof = p;
		HP = 0;
		ATK = 0;
		ATKt = 0;
		DEF = 0;
		LV = 0;
		far_atk = 0;//1 for no,0.5 for half,0 for all
		col_atk = false;
		row_atk = false;
		CATK = 0;
		Cmul = 0;
		CP = 0;
		CA = 0;
		MD = 0;
		for(int i = 0;i < 4;i++) {
			c_skill[i].level = 0;
			c_skill[i].skill = "--------------";
		}
	}
	public void eq_skill(int epos,Skill_O eskill) {
		c_skill[epos].skill = eskill.skill.toString();
		c_skill[epos].text.setText(c_skill[epos].skill);
		c_skill[epos].level = eskill.level;
		set_s_e(epos,true);
		eskill.level=0;
	}
	public void get_data(int p,int level,int position) {
		if(p==0) {
			for(int i = 0;i < 4;i++) {
				if(c_skill[i]==null)
					c_skill[i] = new Skill_O(0,i,'a');
			}
		}
		LV = level;
		row_atk = false;
		col_atk = false;
		far_atk = 1;
		CP = 0;
		CA = 1.5;
		MD = 0;
		Cmul = 1;
		ATKt = 1;
		if(level!=1) {
			for(int i = 0;i < 4;i++)
				set_s_e(i,false);
		}
		if(p==1) {
			prof = p;
			HP = level*200;
			ATK = level*50;
			DEF = level*50;
			CATK = level*45;
			if(level==2)
				c_skill[0].skill="防禦增加：20";
			if(level==3) {
				c_skill[0].skill="防禦增加：45";
				c_skill[1].skill="反擊倍加： 4";
			}
		}
		////////////
		if(p==2) {
			prof = p;
			HP = level*100;
			ATK = level*90;
			DEF = level*20;
			CATK = level*15;
			if(level==2)
				c_skill[0].skill="遠隔攻擊： 1";
			if(level==3) {
				c_skill[0].skill="遠隔攻擊： 1";
				c_skill[1].skill="致命必殺：70";
			}
		}
		/////////////
		if(p==3) {
			prof = p;
			HP = level*150;
			ATK = level*70;
			DEF = level*30;
			CATK = level*45;
			if(level==2)
				c_skill[0].skill="裝甲無視：20";
			if(level==3) {
				c_skill[0].skill="裝甲無視：30";
				c_skill[1].skill="追加攻擊： 1";
			}
		}
		if(level==1) {
			for(int i = 0;i < 4;i++) {
				if(c_skill[i]==null)
					c_skill[i] = new Skill_O(0,i,'a');
				else {
					c_skill[i].skill="--------------";
				}
			}
		}
		else {
			if(level!=1) {
				for(int i = 0;i < 4;i++)
					set_s_e(i,true);
			}
		}
		df.set_text(this);
		this.position = position;
	}
	public void anp_get_data(int p,int level) {
		if(p==1) {
			if(level==2) {
				c_skill[0].skill="防禦增加：20";
				c_skill[1].skill="--------------";
			}
			if(level==3) {
				c_skill[0].skill="防禦增加：45";
				c_skill[1].skill="反擊倍加： 4";
			}
		}
		if(p==2) {
			if(level==2) {
				c_skill[0].skill="遠隔攻擊： 1";
				c_skill[1].skill="--------------";
			}
			if(level==3) {
				c_skill[0].skill="遠隔攻擊： 1";
				c_skill[1].skill="致命必殺：70";
			}
		}
		if(p==3) {
			if(level==2) {
				c_skill[0].skill="裝甲無視：20";
				c_skill[1].skill="--------------";
			}
			if(level==3) {
				c_skill[0].skill="裝甲無視：30";
				c_skill[1].skill="追加攻擊： 1";
			}
		}
	}
	public void set_s_e(int i,boolean set) {
		c_skill[i].set_s_e(this,c_skill[i].skill,set);
	}
}
