package kr.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

public class FileUtils {
	/**
	 * html5 canvas => file save
	 * @param img	canvas.toDataURL()
	 * @param Path	저장될 위치(C:\...\)
	 * @param name	저장될 파일명(test.jpg, test.png ...)
	 * @param ext	저장될 파일 확장자명
	 * @return
	 */
	public static boolean setCanvasToFile(String img, String Path , String name , String ext) throws Exception{
		boolean result = false;
		try {
			BufferedImage bfi = toCanvasBuffer(img);

			File outputfile = new File(Path + name);
			ImageIO.write(bfi , ext, outputfile);
			bfi.flush();
			bfi = null;
			result = true;
		} catch(Exception ex) {
			throw ex;
		}
		return result;
	}

	/**
	 * html5 canvas => save other server
	 * @param url		Send server url
	 * @param img		canvas.toDataURL()
	 * @param name		저장될 파일명(test.jpg, test.png ...)
	 * @param ext		저장될 파일 확장자명
	 * @return
	 * @throws Exception
	 */
	public static boolean setCanvasToUrl(String url, String img, String name , String ext) throws Exception{
		boolean result = false;
		try {
			BufferedImage bfi = toCanvasBuffer(img);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bfi, ext, baos);
			baos.flush();
			byte[] bt = baos.toByteArray();
			result = doUploadFile(url, name , ext, bt);

			bfi.flush();
			bfi = null;
		} catch(Exception ex) {
			throw ex;
		}
		return result;
	}

	/**
	 * Html5 Canvas -> BufferedImage
	 * @param img		canvas.toDataURL()
	 * @return
	 * @throws Exception
	 */
	private static BufferedImage toCanvasBuffer(String img) throws Exception {
		int start = img.indexOf(",");
		img = img.substring(start + 1);

		BASE64Decoder bd = new BASE64Decoder();

		byte[] buffer = bd.decodeBuffer(img);

		BufferedImage bf = ImageIO.read(new ByteArrayInputStream(buffer));
		BufferedImage bfi = new BufferedImage(bf.getWidth(),bf.getHeight(), BufferedImage.TYPE_INT_RGB);
		bfi.createGraphics().drawImage(bf ,0, 0, Color.WHITE, null);

		bf.flush();
		bf = null;
		return bfi;
	}

	/**
	 * 파일 전송
	 * @param url			Send url
	 * @param fileName		modify file name
	 * @param ext			file extension
	 * @param bytes			file byte
	 * @return
	 * @throws Exception
	 */
	public static boolean doUploadFile(String url, String fileName, String ext, byte[] bytes) throws Exception {
		boolean isOK = false;
		String boundary = "----------V2ymHFg03ehbqgZCaKO6jy";
		StringBuffer boundaryMessage = new StringBuffer("--").append(boundary).append("\r\n");
		boundaryMessage.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
		.append(fileName)
		.append("\"\r\n")
		.append("Content-Type: image/")
		.append(ext)
		.append("\r\n\r\n");

		String endBoundary = "\r\n--" + boundary + "--\r\n";

		URLConnection conn = null;
		BufferedReader bfr = null;
		ByteArrayOutputStream bos = null;
		BufferedOutputStream dout = null;
		try {
			bos = new ByteArrayOutputStream();
			bos.write(boundaryMessage.toString().getBytes());
			bos.write(bytes);
			bos.write(endBoundary.getBytes());

			conn = new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			dout = new BufferedOutputStream(conn.getOutputStream());
			dout.write(bos.toByteArray());
			dout.flush();
			dout.close();
			bos.close();
			bfr = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			bfr.close();
			isOK = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			isOK = false;
		} finally {
			if(bos != null) {
				bos.close();
			}
			if(bfr != null) {
				bfr.close();
			}
			if(dout != null) {
				dout.close();
			}
			bfr = null;
			conn = null;
			bos = null;
			dout = null;
		}
		return isOK;
	}

	/**
	 * Image resize complete file upload
	 * @param url			Send url
	 * @param bt			file byte
	 * @param filename		modify file name
	 * @param ext			file extension
	 * @param width			새로운 파일 넓이
	 * @param height		새로운 파일 높이
	 * @param type			비율(1:비율에맞게 , 2 : 강제 , 3: 원본 이미지 사이즈)
	 * @return
	 * @throws Exception
	 */
	public static boolean doUploadFile(String url, byte[] bt, String filename, String ext, int width, int height, int type) throws Exception {
		return doUploadFile(url, filename, ext, getResizeImage(bt, width, height, type));
	}

	/**
	 * Image Convert(byte -> byte)
	 * @param bt		image byte
	 * @param width		새로운 파일 넓이
	 * @param height	새로운 파일 높이
	 * @param type		비율(1:비율에맞게 , 2 : 강제 , 3: 원본 이미지 사이즈)
	 * @return
	 * @throws Exception
	 */
	public static byte[] getResizeImage(byte[] bt, int width, int height, int type) throws Exception {
		BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(bt));
		// 변환할 이미지의 가로/세로
		int img_width = 0 , img_height = 0;
		if ( type == 1 ) {
			img_width = bfi.getWidth();
			img_height = bfi.getHeight();

			img_height = (img_height * width) / img_width;
			img_width = width;
		} else if ( type == 2 ) {
			img_width = width;
			img_height = height;
		} else if ( type == 3 ) {
			img_width = bfi.getWidth();
			img_height = bfi.getHeight();
		}

		ResampleOp resampleOp = new ResampleOp(img_width, img_height);
		resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
		bfi = resampleOp.filter(bfi, null);

		WritableRaster raster = bfi.getRaster();
		DataBufferByte db = (DataBufferByte) raster.getDataBuffer();

		return db.getData();
	}

	/**
	 * 외부 주소 링크 결과 값 반환
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static String doUrl(URL url) throws Exception {
		StringBuilder str = new StringBuilder();
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String read = null;
		try {
			inputStream = url.openStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			while ((read = bufferedReader.readLine()) != null) {
				str.append(read);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();

			inputStream = null;
			inputStreamReader = null;
			bufferedReader = null;
			read = null;
			url = null;
		} catch(Exception ex) {
			throw ex;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			inputStream = null;
			inputStreamReader = null;
			bufferedReader = null;
			read = null;
			url = null;
		}
		return str.toString();
	}

	/**
	 * 외부 json을 가져와서 json 타입으로 변환
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public static JSONObject doJsonUrl(URL url) throws Exception {
		return new JSONObject(doUrl(url));
	}

	/**
	 * 파일 다운로드
	 * @param response
	 * @param request
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	public static void getFileDown(HttpServletResponse response, HttpServletRequest request, String path, String fileName) throws Exception {
		response.setContentType("application/octet-stream");
		String agent = request.getHeader("USER-agent").toLowerCase();

		if (agent.indexOf("msie") > 0 || agent.indexOf("trident") > 0) {
			int i = agent.indexOf('M', 2);

			String IEV = agent.substring(i + 5, i + 8);

			if (IEV.equalsIgnoreCase("5.5")) {
				response.setHeader("Content-Disposition" , "filename=" + fileName);
			} else {
				response.setHeader("Content-Disposition" , "attachment;filename=" + fileName);
			}
		} else {
			response.setHeader("Content-Disposition" , "attachment;filename=" + fileName);
		}
		byte b[] = new byte[1024];
		File file = new File(path + fileName);

		if (file.isFile()) {
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;
			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
			outs.flush();
			outs.close();
			fin.close();
		}
	}
}