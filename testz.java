
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class testz {
	public String getExperssion(){
		Scanner in = new Scanner(System.in);
		String experssion = in.nextLine();
		Matcher m = Pattern.compile("^(([a-z]|[1-9][0-9]*)(\\*([a-z]|[1-9][0-9]*))*)(\\+([a-z]|[1-9][0-9]*)(\\*([a-z]|[1-9][0-9]*))*)*$").matcher(experssion);
		if(m.find()){
			System.out.println(experssion);
			return experssion;}
		else{
			System.out.println("format error!");
			return null;
		}
	}
	public  String change(String exp){
		Scanner in = new Scanner(System.in);
		String tar1 = in.nextLine();
		Matcher n = Pattern.compile("^!simplify *$").matcher(tar1);
		Matcher e = Pattern.compile("^!simplify( +[a-z]=(0|[1-9][0-9]*))+ *$").matcher(tar1);
		Matcher m = Pattern.compile("[a-z]=\\d+").matcher(tar1);
		if(!(n.find()||e.find())){System.out.println("error!");return null;}
		else if(n.find()){
			System.out.println(exp);
			return null;}
		else{
			while(m.find()){
				Matcher p = Pattern.compile("[a-z]").matcher(m.group());
				Matcher q = Pattern.compile("\\d+").matcher(m.group());
				if(p.find()&&q.find()){
					if(Pattern.compile(p.group()).matcher(exp).find()){exp=exp.replaceAll(p.group(),q.group());}
					else{
						System.out.println("No such variable!");
						return null;}}}
			return exp;}
		}
	public String simplify(String str){
		String[] sz = str.split("\\+");
		int i=1;
		String j = "";
		StringBuilder builder = new StringBuilder();
		for(String a:sz){
			Matcher m = Pattern.compile("0|([1-9][0-9]*)").matcher(a);
			Matcher n = Pattern.compile("[a-z]").matcher(a);
			i=1;
			j = "";
			while(m.find()){i*=Integer.parseInt(m.group());}
			while(n.find()){j+=n.group()+"*";}
			if(i==0){builder.append("0"+"+");}
			else if(i==1){builder.append(j.substring(0,j.length()-1)+"+");}
			else if(j==""){builder.append(String.valueOf(i)+"+");}
			else{builder.append(String.valueOf(i)+"*"+j.substring(0,j.length()-1)+"+");}
		}
		String s = builder.toString();
		s = s.substring(0,s.length()-1);
		String[] sz1 = s.split("\\+");
		i=0;
		for(String a:sz1){
			Matcher k = Pattern.compile("^[1-9][0-9]*$|0").matcher(a);
			if(k.find()){i+=Integer.parseInt(k.group());}
		}
		j="";
		for(String a:sz1){
			Matcher o = Pattern.compile("[a-z](\\*[a-z])*|[1-9][0-9]*(\\*[a-z])+").matcher(a);
			if(o.find()){j+=o.group()+"+";}
		}
		if(i==0&&j==""){s="0";}
		else if(i==0&&j!=""){s = j.substring(0,j.length()-1);}
		else if(j==""&&i!=0){s = String.valueOf(i);}
		else{s = String.valueOf(i)+"+"+j.substring(0,j.length()-1);}
		return s;
	}
	public String creat(int a,String b){
		String g = "";
		int c = a;
		if(a==1){return "1";}
		else{
			while(c>1){
				g=g+b+"*";
				c--;
			}
			return String.valueOf(a)+"*"+g.substring(0, g.length()-1);
		}
	}
	public int count(String str1,char str2){
		int c = 0;
		for(int i=0;i<str1.length();i++){
			if(str1.charAt(i)==str2){c++;}
		}
		return c;
	}
	public String Der(String str1,String str2){
		String d = "";
		Matcher m = Pattern.compile("[a-z]|[1-9][0-9]*").matcher(str1);
		while(m.find()){
			if(!m.group().equals(str2)){
				d=d+m.group()+"*";
			}
		}
		return(d+creat(count(str1,str2.charAt(0)),str2));
	}
	public  String derivation(String str) {
		Scanner in = new Scanner(System.in);
		String a = in.nextLine();
		in.close();
		String[] arr = str.split("\\+");
		String c = null;
		String cf = "";
		Matcher m = Pattern.compile("!d/d [a-z]").matcher(a);
		test3 demo= new test3();
		if(!m.find()){
			System.out.println("Error!");
			return null;
		}
		else{
			c = m.group().substring(m.group().length()-1,m.group().length());
			Matcher n = Pattern.compile(c).matcher(a);
			if(!n.find()){System.out.println("Error!");}
			else{
				for(String u:arr){
					if(demo.count(u,c.charAt(0))>0){cf=cf+demo.Der(u,c)+"+";}
				}
			}
			return cf.substring(0, cf.length()-1);
		}
	}
	public static void main(String[] args) {
		testz s = new testz();
		String exp = s.getExperssion();
		if(exp!=null){
			String exp1 = s.change(exp);
			if(exp1!=null){
				exp1 = s.simplify(exp1);
				System.out.println(exp1);
				exp = s.simplify(s.derivation(exp));
				System.out.println(exp);
			}
		}
	}
}
