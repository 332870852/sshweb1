package cn.abc.sshweb1.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * @author 何旭杰
 *
 */
public class SecurityUtil {
	/**
	 * MD5加密算法
	 * @param str
	 * @return
	 */
	//项目中使用的加密算法，比较流行的算法：MDX(MD2,MD5), SHA-X(SHA-1,SHA-256,384,512)
	public static String md5(String str) {
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");//这个参数就是加密的算法
			md.update(str.getBytes());	//将明文字符串更新到md对象里
			byte[] rs=md.digest();	//加密计算,计算结果是m密文
			//byte[]转16进制字符串
			return new BigInteger(1,rs).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	//加密深
	public static String md5(String str1,String str2) {
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");//这个参数就是加密的算法
			md.update(str1.getBytes());	//将明文字符串更新到md对象里
			md.update(str2.getBytes());
			byte[] rs=md.digest();	//加密计算,计算结果是m密文
			//byte[]转16进制字符串
			return new BigInteger(1,rs).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * SHA-1加密算法
	 * @return
	 */
	public static String sha1(String str) {
		try {
			MessageDigest md=MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[]rs=md.digest();
			System.out.println("rs:"+str.getBytes().length);
			StringBuffer sb=new StringBuffer();
			for(byte b:rs) {
				//可以将byte类型转16进制格式的字符串
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
		String s="123456";
		sha1(s);
	}
}
