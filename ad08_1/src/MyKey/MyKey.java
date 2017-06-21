package MyKey;

public class MyKey {
	String str;
	/*
	 * s keyとなる文字列
	 */
	public MyKey(String s){
		str = s;
	}
	/*
	 * o 比較するkey
	 *return このキーとキーが等しければtrue,等しくなければfalse
	 */
	public boolean equals(Object o){
		if(!(o instanceof MyKey)){
			return false;
		}
		//パラメータoをMyKey型にキャストして,内容を比較する
		MyKey k=(MyKey)o;
		return str.equals(k.str);
	}
	/*
	@return このkeyのハッシュ値
	*/ 
	public int hashCode(){
		int sum=0;
		for(int i=0;i<str.length();i++){
			sum+=(int)str.charAt(i);
		}
		return sum;
	}
	/*
	 * @return key を文字列で表示したもの
	 */
	public String toString(){
		return str;
	}
}
