import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolDateTime;
import com.zftlive.android.tools.ToolFile;
import com.zftlive.android.tools.ToolHTTP;
import org.apache.http.Header;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * ��ͨ����-�汾���/����
 * @author lvqiang
 * @version 1.0
 *
 */
public class VersionChecker {
	public static final String SERVER_VERSION = "version";
	public static final String CHANGE_LOG = "changeLog";
	public static final String APK_URL = "apkURL";
	public final static String SD_FOLDER = ToolFile.gainSDCardPath()+"/VersionChecker/";
	private static VersionChecker mChecker = new VersionChecker();
	private static final String TAG = VersionChecker.class.getSimpleName();
	public static void requestCheck(Context mContext,String strURL){
		mChecker.getLatestVersion(mContext, strURL);
	}
	/**
	 * ��ȡ���°汾
	 * @param mContext
	 * @param strURL
	 * @return
	 */
	private JSONObject getLatestVersion(final Context mContext,String strURL){
		ToolHTTP.get(strURL, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,final JSONObject response) {
				if(getLocalVersion(mContext) >= response.optDouble(SERVER_VERSION, 0.0)){
					ToolAlert.toastShort(mContext, "�������°汾");
				}else{
					ToolAlert.dialog(mContext, "�汾����", "��⵽���°汾���Ƿ����ϸ��£�",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									try {
										downLoadApk(mContext,response.getString("apkURL"));
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									dialog.dismiss();
								}
							});
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		return null;
	}
	/**
	 * �ӷ�����������APK
	 */
	private void downLoadApk(final Context mContext,final String downURL) {
		final ProgressDialog pd; // �������Ի���
		pd = new ProgressDialog(mContext);
		pd.setCancelable(false);// ����һֱ�����꣬����ȡ��
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("��������");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = downloadFile(downURL, pd);
					sleep(3000);
					installApk(mContext,file);
					// �������������Ի���
					pd.dismiss();
				} catch (Exception e) {
					pd.dismiss();
					Log.e(TAG, "�����°汾ʧ�ܣ�ԭ��" + e.getMessage());
				}
			}
		}.start();
	}
	/**
	 * �ӷ������������¸����ļ�
	 *
	 * @param path
	 *            ����·��
	 * @param pd
	 *            ������
	 * @return
	 * @throws Exception
	 */
	private File downloadFile(String path, ProgressDialog pd) throws Exception {
		// �����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// ��ȡ���ļ��Ĵ�С
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			String fileName = SD_FOLDER+ToolDateTime.formatDateTime(new java.util.Date(),"yyyyMMddHHmm") + ".apk";
			File file = new File(fileName);
			// Ŀ¼�����ڴ���Ŀ¼
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// ��ȡ��ǰ������
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			throw new IOException("δ������SD��");
		}
	}
	/**
	 * ��װapk
	 */
	private void installApk(Context mContext,File file) {
		Uri fileUri = Uri.fromFile(file);
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setDataAndType(fileUri, "application/vnd.android.package-archive");
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// ��ֹ�򲻿�Ӧ��
		mContext.startActivity(it);
	}
	/**
	 * ��ȡӦ�ó���汾��versionName��
	 * @return ��ǰӦ�õİ汾��
	 */
	private static double getLocalVersion(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "��ȡӦ�ó���汾ʧ�ܣ�ԭ��"+e.getMessage());
			return 0.0;
		}
		return Double.valueOf(info.versionName);
	}
}