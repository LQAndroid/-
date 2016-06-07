package com.zftlive.android.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.zftlive.android.MApplication;
/**
 * HTTP�ͻ��˲���������
 * @author lvqiang
 * @version 1.0
 *
 */
public abstract class ToolHTTP {
	/**�첽��HTTP�ͻ���ʵ��**/
	protected static AsyncHttpClient client = new AsyncHttpClient();
	
	/**Ĭ���ַ���**/
	public static String DEFAULT_CHARSET = "UTF-8";
	/**
	 * ģ��GET�����޲�����
	 * @param url ����URL
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void get(String url, ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.get(url, responseHandler);
		}
	}
	
	/**
	 * ģ��GET�����в�����
	 * @param url ����URL
	 * @param parmsMap ����
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void get(String url, Map<String,?> parmsMap, ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.get(url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * ģ��GET�����в�����
	 * @param context ������
	 * @param url ����URL
	 * @param parmsMap ����
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void get(Context context,String url,Map<String,?> parmsMap,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.get(context, url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * ģ��POST�����޲�����
	 * @param url ����URL
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void post(String url, ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.post(url, responseHandler);
		}
	}
	
	/**
	 * ģ��POST�����в�����
	 * @param url ����URL
	 * @param parmsMap ����
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void post(String url, Map<String,?> parmsMap, ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * ģ��POST�����в�����
	 * @param context ������
	 * @param url ����URL
	 * @param parmsMap ����
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void post(Context context,String url,Map<String,?> parmsMap,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(context, url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * ģ���ύPOST�����޲�����
	 * @param context ������
	 * @param url ����URL
	 * @param entity ����ʵ��,����null
	 * @param contentType ��contentType ��"multipart/form-data"��
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void post(Context context,String url,HttpEntity entity, String contentType,ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.post(context, url, entity, contentType, responseHandler);
		}
	}
	
	/**
	 * ģ���ύPOST�����в�����
	 * @param context ������
	 * @param url ����URL
	 * @param headers ����Header
	 * @param parmsMap ����
	 * @param contentType ��contentType ��"multipart/form-data"��
	 * @param responseHandler �ص�Handler��BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler��
	 */
	public static void post(Context context,String url,Header[] headers, Map<String,?> parmsMap, String contentType,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//�رչ�������.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(context, url, headers, fillParms(parmsMap,DEFAULT_CHARSET), contentType, responseHandler);
		}
	}
	/**
	 * װ�����
	 * @param map ����
	 * @return 
	 */
	public static RequestParams fillParms(Map<String,?> map,String charset) {
		RequestParams params = new RequestParams();
		if(null != map && map.entrySet().size() > 0)
		{
			//�����ַ���,��ֹ�����ύ����
			if(!"".equals(charset)){
				params.setContentEncoding(charset);
			}
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) 
			{
				Entry entity = (Entry) iterator.next();
				Object key = entity.getKey();
				Object value = entity.getValue();
				if (value instanceof File) {
					try {
						params.put((String) key, new FileInputStream((File) value),((File) value).getName());
					} catch (FileNotFoundException e) {
						throw new RuntimeException("�ļ������ڣ�", e);
					}
				} else if (value instanceof InputStream) {
					params.put((String) key, (InputStream) value);
				} else {
					params.put((String) key, value.toString());
				}
			}
		}
		return params;
	}
	
	/**
	 * �������״̬
	 * @return �����Ƿ�����
	 */
	public static boolean checkNetwork(){
		boolean isConnected = MApplication.isNetworkReady();
		if(isConnected){
			return true;
		}else{
			ToolAlert.toastShort("��������ʧ��");
			return false;
		}
	}
	
	/**
	 * ֹͣ����
	 * @param mContext ���������������
	 */
	public static void stopRequest(Context mContext){
		client.cancelRequests(mContext, true);
	}
	
	/**
	 * ֹͣȫ������
	 */
	public static void stopAllRequest(){
		client.cancelAllRequests(true);
	}
	
}