package cn.abc.sshweb1.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ���ܹ�����
 * @author �����
 *
 */
public class SecurityUtil {
	/**
	 * MD5�����㷨
	 * @param str
	 * @return
	 */
	//��Ŀ��ʹ�õļ����㷨���Ƚ����е��㷨��MDX(MD2,MD5), SHA-X(SHA-1,SHA-256,384,512)
	public static String md5(String str) {
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");//����������Ǽ��ܵ��㷨
			md.update(str.getBytes());	//�������ַ������µ�md������
			byte[] rs=md.digest();	//���ܼ���,��������m����
			//byte[]ת16�����ַ���
			return new BigInteger(1,rs).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	//������
	public static String md5(String str1,String str2) {
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");//����������Ǽ��ܵ��㷨
			md.update(str1.getBytes());	//�������ַ������µ�md������
			md.update(str2.getBytes());
			byte[] rs=md.digest();	//���ܼ���,��������m����
			//byte[]ת16�����ַ���
			return new BigInteger(1,rs).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * SHA-1�����㷨
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
				//���Խ�byte����ת16���Ƹ�ʽ���ַ���
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
