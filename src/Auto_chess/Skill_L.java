package Auto_chess;

public class Skill_L {
		public Skill_L() {
			sc[0] = new String("血量增加：40");
			sc[1] = new String("攻擊增加：15");
			sc[2] = new String("防禦增加：10");
			sc[3] = new String("必殺增加：10");
			sc[4] = new String("致命必殺：15");
			sc[5] = new String("裝甲無視： 5");
			////////////////
			sb[0] = new String("血量增加：80");
			sb[1] = new String("攻擊增加：30");
			sb[2] = new String("防禦增加：20");
			sb[3] = new String("必殺增加：20");
			sb[4] = new String("致命必殺：25");
			sb[5] = new String("裝甲無視：20");
			sb[6] = new String("遠隔攻擊： 1");
			sb[7] = new String("反擊加倍： 2");
			//////////////////
			sa[0] = new String("血量增加：99");
			sa[1] = new String("攻擊增加：40");
			sa[2] = new String("防禦增加：30");
			sa[3] = new String("必殺增加：30");
			sa[4] = new String("致命必殺：35");
			sa[5] = new String("裝甲無視：30");
			sa[6] = new String("追加攻擊： 1");
			sa[7] = new String("反擊加倍： 4");
			////////////////
			ss[0] = new String("次元攻擊： 1");//30破甲+100%遠隔
			ss[1] = new String("追加攻擊： 2");
			ss[2] = new String("反擊加倍： 6");
			ss[3] = new String("必殺增加：40");
			ss[4] = new String("致命必殺：50");
			ss[5] = new String("裝甲無視：50");
			ss[6] = new String("貫通攻擊： 1");
			ss[7] = new String("扇形攻擊： 1");
		}
		public String getskill(int level) {
			String rt = new String();
			double ra = Math.random();
			if(level == 0)
				rt = "--------------";
			else if(level==1)
			{
				if(ra < 0.166&&ra >= 0)
					rt = sc[0].toString();
				else if(ra < 0.332&&ra >= 0.166)
					rt = sc[1].toString();
				else if(ra < 0.498&&ra >= 0.332)
					rt = sc[2].toString();
				else if(ra < 0.664&&ra >= 0.498)
					rt = sc[3].toString();
				else if(ra < 0.83&&ra >= 0.664)
					rt = sc[4].toString();
				else if(ra <= 1&&ra >= 0.83)
					rt = sc[5].toString();
			}
			else if(level==2)
			{
				if(ra < 0.125&&ra >= 0)
					rt = sb[0].toString();
				else if(ra < 0.25&&ra >= 0.125)
					rt = sb[1].toString();
				else if(ra < 0.375&&ra >= 0.25)
					rt = sb[2].toString();
				else if(ra < 0.5&&ra >= 0.375)
					rt = sb[3].toString();
				else if(ra < 0.625&&ra >= 0.5)
					rt = sb[4].toString();
				else if(ra <= 0.75&&ra >= 0.625)
					rt = sb[5].toString();
				else if(ra <= 0.875&&ra >= 0.75)
					rt = sb[6].toString();
				else if(ra <= 1&&ra >= 0.875)
					rt = sb[7].toString();
			}
			else if(level==3)
			{
				if(ra < 0.125&&ra >= 0)
					rt = sa[0].toString();
				else if(ra < 0.25&&ra >= 0.125)
					rt = sa[1].toString();
				else if(ra < 0.375&&ra >= 0.25)
					rt = sa[2].toString();
				else if(ra < 0.5&&ra >= 0.375)
					rt = sa[3].toString();
				else if(ra < 0.625&&ra >= 0.5)
					rt = sa[4].toString();
				else if(ra <= 0.75&&ra >= 0.625)
					rt = sa[5].toString();
				else if(ra <= 0.875&&ra >= 0.75)
					rt = sa[6].toString();
				else if(ra <= 1&&ra >= 0.875)
					rt = sa[7].toString();
			}
			if(level==4)
			{
				if(ra < 0.125&&ra >= 0)
					rt = ss[0].toString();
				else if(ra < 0.25&&ra >= 0.125)
					rt = ss[1].toString();
				else if(ra < 0.375&&ra >= 0.25)
					rt = ss[2].toString();
				else if(ra < 0.5&&ra >= 0.375)
					rt = ss[3].toString();
				else if(ra < 0.625&&ra >= 0.5)
					rt = ss[4].toString();
				else if(ra <= 0.75&&ra >= 0.625)
					rt = ss[5].toString();
				else if(ra <= 0.875&&ra >= 0.75)
					rt = ss[6].toString();
				else if(ra <= 1&&ra >= 0.875)
					rt = ss[7].toString();
			}
			return rt;
		}
		public String[] sc = new String[6];
		public String[] sb = new String[8];
		public String[] sa = new String[8];
		public String[] ss = new String[8];
}
