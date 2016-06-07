package com.zftlive.android.tools;
import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ToolDateTime {
	/** ���ڸ�ʽ��yyyy-MM-dd HH:mm:ss **/
	public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/** ���ڸ�ʽ��yyyy-MM-dd HH:mm **/
	public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	/** ���ڸ�ʽ��yyyy-MM-dd **/
	public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
	/** ���ڸ�ʽ��HH:mm:ss **/
	public static final String DF_HH_MM_SS = "HH:mm:ss";
	/** ���ڸ�ʽ��HH:mm **/
	public static final String DF_HH_MM = "HH:mm";
	private final static long minute = 60 * 1000;// 1����
	private final static long hour = 60 * minute;// 1Сʱ
	private final static long day = 24 * hour;// 1��
	private final static long month = 31 * day;// ��
	private final static long year = 12 * month;// ��
	/** Log�����ʶ **/
	private static final String TAG = ToolDateTime.class.getSimpleName();
	/**
	 * �����ڸ�ʽ�����Ѻõ��ַ�����������ǰ����Сʱǰ������ǰ������ǰ������ǰ���ո�
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFriendly(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "��ǰ";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "����ǰ";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "��ǰ";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "��Сʱǰ";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "����ǰ";
		}
		return "�ո�";
	}
	/**
	 * ��������yyyy-MM-dd HH:mm:ss��ʽ��
	 * 
	 * @param dateL
	 *            ����
	 * @return
	 */
	public static String formatDateTime(long dateL) {
		SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
		Date date = new Date(dateL);
		return sdf.format(date);
	}
	/**
	 * ��������yyyy-MM-dd HH:mm:ss��ʽ��
	 * 
	 * @param dateL
	 *            ����
	 * @return
	 */
	public static String formatDateTime(long dateL, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(new Date(dateL));
	}
	/**
	 * ��������yyyy-MM-dd HH:mm:ss��ʽ��
	 * 
	 * @param dateL
	 *            ����
	 * @return
	 */
	public static String formatDateTime(Date date, String formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(date);
	}
	/**
	 * �������ַ���ת������
	 * 
	 * @param strDate
	 *            �ַ�������
	 * @return java.util.date��������
	 */
	public static Date parseDate(String strDate) {
		DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
		Date returnDate = null;
		try {
			returnDate = dateFormat.parse(strDate);
		} catch (ParseException e) {
			Log.v(TAG, "parseDate failed !");
		}
		return returnDate;
	}
	/**
	 * ��ȡϵͳ��ǰ����
	 * 
	 * @return
	 */
	public static Date gainCurrentDate() {
		return new Date();
	}
	/**
	 * ��֤�����Ƿ�ȵ�ǰ������
	 * 
	 * @param target1
	 *            �Ƚ�ʱ��1
	 * @param target2
	 *            �Ƚ�ʱ��2
	 * @return true �����target1��target2������target2�������target2��
	 */
	public static boolean compareDate(Date target1, Date target2) {
		boolean flag = false;
		try {
			String target1DateTime = ToolDateTime.formatDateTime(target1,
					DF_YYYY_MM_DD_HH_MM_SS);
			String target2DateTime = ToolDateTime.formatDateTime(target2,
					DF_YYYY_MM_DD_HH_MM_SS);
			if (target1DateTime.compareTo(target2DateTime) <= 0) {
				flag = true;
			}
		} catch (Exception e1) {
			System.out.println("�Ƚ�ʧ�ܣ�ԭ��" + e1.getMessage());
		}
		return flag;
	}
	/**
	 * �����ڽ������Ӳ���
	 * 
	 * @param target
	 *            ��Ҫ�������������
	 * @param hour
	 *            Сʱ
	 * @return
	 */
	public static Date addDateTime(Date target, double hour) {
		if (null == target || hour < 0) {
			return target;
		}
		return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
	}
	/**
	 * �����ڽ����������
	 * 
	 * @param target
	 *            ��Ҫ�������������
	 * @param hour
	 *            Сʱ
	 * @return
	 */
	public static Date subDateTime(Date target, double hour) {
		if (null == target || hour < 0) {
			return target;
		}
		return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
	}
}