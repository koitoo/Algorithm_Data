package hash;
import ad08_1.HashC;
import MyKey.MyKey;
public class hash{
static int hash(String s) {
	int sum=0;
	for(int i=0;i<s.length();i++){
		sum+=(int)s.charAt(i);
	}
	return sum%100;
}

}
