package ad08_2;
import MyKey.MyKey;
public class HashOA {
	private static class Bucket{
		MyKey key;
		Object data;
		private Bucket(MyKey key,Object data){
			this.key=key;
			this.data=data;
		}
	}
	Bucket[] table;
	int bucketSize;
	int nElements;
	static final MyKey DELETED=new MyKey(null);//削除済みであることを示す特別なキー
	static final MyKey EMPTY=new MyKey(null);//空きであることを示す
	static final int DEFAULT_BUCKET_SIZE=53;//素数が望ましい
	public HashOA(){
		this(DEFAULT_BUCKET_SIZE);
	}
	public HashOA(int bucketSize){
		table=new Bucket[bucketSize];
		for(int i=0;i<bucketSize;i++){
			table[i]=new Bucket(EMPTY,null);
		}
		this.bucketSize=bucketSize;
		nElements=0;
	}
	private int hash(MyKey key){
		return key.hashCode()%bucketSize;
	}
	private int rehash(int h){
		return (h+1)%bucketSize;
	}
	public Object find(MyKey key){
		int count=0;
		int h=hash(key);
		MyKey k;
		while((k=table[h].key)!=EMPTY){
			if(k!=DELETED&&key.equals(k)){
				return table[h].data;
			}
			if(++count>bucketSize){
				return null;
			}
			h=rehash(h);
		}
		return null;
	}
	public boolean insert(MyKey key,Object data){
		int count = 0;
		int h=hash(key);
		
		MyKey k;
		while((k=table[h].key)!=EMPTY&&key!=DELETED){
			if(key.equals(k)){
				return false;
			}
			if(++count>bucketSize){
				throw new IllegalStateException(
					"ハッシュ表にこれ以上データを登録できません");
			}
			h=rehash(h);
		}	
		table[h].key=key;
		table[h].data=data;
		nElements++;
		return true;
	}
	public boolean delete(MyKey key){
		int count = 0;
		int h = hash(key);
		MyKey k;
		while((k=table[h].key)!=EMPTY){
			if(k!=DELETED&&key.equals(k)){
				table[h].key=DELETED;
				table[h].data=null;
				nElements--;
				return true;
			}
			if(++count>bucketSize){
				return false;
			}
			h=rehash(h);
		}
		return false;
	}
	public String toString(){
		String s="";
		for(int i=0;i<table.length;i++){
			s+="パケット"+i+";";
			MyKey k=table[i].key;
			if(k==EMPTY){
				s+="空\n";
			}else if(k==DELETED){
				s+="削除済み\n";
			}else {
				s+="key=["+k+"] data=["+table[i].data+"]\n";
			}
		}
		s+="要素の個数"+nElements+"\n";
		return s;
	}
	
	public static void main(String[] args){
		String[] words={
			"one","two","three","four","five",
			"six","seven","eight","nine","ten",
			"one"//登録されない
		};
	HashOA hash=new HashOA(15);
	System.out.println("--<<パート1>>--[登録]--");
	for(int i=0;i<words.length;i++){
		boolean stat=hash.insert(new MyKey(words[i]), "順番="+(i+1));
		if(stat==false){
			System.out.println(words[i]+"の登録に失敗しました");
		}
	}
	System.out.println(hash);
	System.out.println("--<パート2>>--[探索]--");
	String[] keys ={"ten","one","ones","five"};
	for(int i=0;i<keys.length;i++){
		Object result =hash.find(new MyKey(keys[i]));
		if(result!=null){
			System.out.println("キー["+keys[i]+"の値は["+result+"]です");
		}else{
			System.out.println("キー["+keys[i]+"]は見つかりませんでした");
		}
	}
	System.out.println("--<パート3>>--[削除]--");
	for(int i=0;i<keys.length;i++){
		if(hash.delete(new MyKey(keys[i]))){
			System.out.println("キー["+keys[i]+"を削除しました");
		}else{
			System.out.println("キー["+keys[i]+"]の削除に失敗しました(登録されていません)");
		}
	}
	System.out.println("--<パート4>>--[探索:全て失敗する]--");
	for(int i=0;i<keys.length;i++){
		Object result =hash.find(new MyKey(keys[i]));
		if(result!=null){
			System.out.println("キー["+keys[i]+"の値は["+result+"]です");
		}else{
			System.out.println("キー["+keys[i]+"]は見つかりませんでした");
		}
	}
	System.out.println("--<<パート5>>--");
	System.out.println(hash);
	}
}
