package ad08_1;
import MyKey.MyKey;
public class HashC {
	/*
	 * 連結リストのセル
	 */
	private static class Cell{
		MyKey key;
		Object data;
		Cell next;//次のセル
		private Cell(MyKey key ,Object data){
			this.key=key;
			this.data=data;
		}
	}
	Cell[] table;//ハッシュ表の実体
	int bucketSize;//バケットの個数
	int nElements;//登録されいるデータの個数
	static final int DEAFAULT_BUCKET_SIZE=50;
	/*
	 * ハッシュ表生成(大きさはデフォルトサイズ)
	 */
	public HashC(){
		this(DEAFAULT_BUCKET_SIZE);
	}
	/*
	 * ハッシュ表生成(大きさはbacketSize
	 */
	public HashC(int bucketSize){
		table =new Cell[bucketSize];
		this.bucketSize=bucketSize;
		nElements=0;
	}
	private int hash(MyKey key){
		return key.hashCode()%bucketSize;
	}
	public Object find(MyKey key){
		for(Cell p=table[hash(key)];p!=null;p=p.next){
			if(key.equals(p.key)){
				return p.data;
			}
		}
		return null;
	}
	public boolean insert(MyKey key,Object data){
		if(find(key)!=null){
			return false;
		}
		Cell p=new Cell(key,data);
		int h=hash(key);
		p.next=table[h];
		table[h]=p;
		nElements++;
		return true;
	}
	public boolean delete(MyKey key){
		int h=hash(key);
		if(table[h]==null){
			return false;
		}
		if(key.equals(table[h].key)){
			Cell p=table[h];
			table[h]=p.next;
			nElements--;
			return true;
		}
		Cell p;
		Cell q;
		for(q=table[h],p=q.next;p!=null;p=p.next){
			if(key.equals(p.key)){
				q.next=p.next;
				nElements--;
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String s="";
		
		for(int i=0;i<table.length;i++){
			s+="バケット"+i+";";
			for(Cell p=table[i];p!=null;p=p.next){
				s+="["+p.key+":"+p.data+"]";
			}
			s+="\n";
		}
		s+="要素の個数:"+nElements+"\n";
		return s;
	}
	public static void main(String[] args){
		String[] words={"one","two","three","four","five","six","seven","eight","nine","ten",
				"one",
				"eleven","twelve","thirteen","fourteen","fifteen",
				"sixteen","seventeen","eighteen","nineteen","twenty"};
		
		HashC hash = new HashC(15);
		for(int i=0;i<words.length;i++){
			boolean stat=hash.insert(new MyKey(words[i]),"順番="+(i+1));
			if(stat==false){
				System.out.println(words[i]+"の登録に失敗しました(重複のため)");
			}
		}
		System.out.println(hash);//ハッシュ表のダンプ
		System.out.println("--＜＜パート２＞＞--[探索]--");
		String[] keys={"ten","thirteen","one","ones","five"};
		for(int i=0;i<keys.length;i++){
			Object result=hash.find(new MyKey(keys[i]));
			if(result!=null){
				System.out.println("キー["+keys[i]+"]の値は["+result+"]");
			}else{
				System.out.println("キー"+keys[i]+"]の値は見つかりませんでした");
			}
		}
		System.out.println("--<<パート3>>--[削除]--");
		for(int i=0;i<keys.length;i++){
			if(hash.delete(new MyKey(keys[i]))){
				System.out.println("キー["+keys[i]+"]を削除しました");
			}else{
				System.out.println("キー["+keys[i]+"]の削除に失敗しました(登録されていません");
			}
		}
		System.out.println("--<<パート4>>--[探索:全て失敗する]----");
		for(int i=0;i<keys.length;i++){
			Object result= hash.find(new MyKey(keys[i]));
			if(result!=null){
				System.out.println("キー["+keys[i]+"]の値は["+result+"]");
			}else{
				System.out.println("キー["+keys[i]+"]は見つかりませんでした");
			}
		}
		System.out.println("--<<パート5>>--");
		System.out.println(hash);
		
		}
}
