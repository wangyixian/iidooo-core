package com.iidooo.core.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author 本类主要关于文件操作 上传文件 删除文件 创建文件夹 判断路径是否存在 判断文件是否是图片 获取随机名字 图片压缩 图片的裁剪
 *         文件的读与写
 */
public class ImageUtil {

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            要保存的源文件
	 * @param destFile
	 *            保存的目标路径
	 */
	public static boolean saveFile(File file, String destFile) {
		try {
			destFile = destFile.replaceAll("\\\\", "/");
			if (destFile.lastIndexOf(".") == -1 || destFile.lastIndexOf("/") == -1) {
				return false;
			}
			// 如果不存在该文件夹，就创建
			String tempOutFile = destFile.substring(0, destFile.lastIndexOf("/"));
			if (!new File(tempOutFile).exists()) {
				new File(tempOutFile).mkdirs();
			}

			// 获得上传对象的输入流
			InputStream inputStream = new FileInputStream(file);

			if (inputStream != null) {
				// 建立文件输出流
				OutputStream outputStream = new FileOutputStream(destFile);

				// 建立缓冲区
				byte[] bs = new byte[1024];
				int count = 0;
				// 从输入流中读出数据放入缓冲区中,并给count赋予写入缓冲区的总字节数,如果count大于0，就说明还有数据，则继续写入。
				while ((count = inputStream.read(bs)) > 0) {
					// 将缓冲区数据写入文件，每次写入的长度为count字节
					outputStream.write(bs, 0, count);
				}
				// 关闭输入流
				inputStream.close();
				// 关闭文件输出流
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 生成随机名字，不可能重复
	 */
	public static String getRandomName() {
		Random r = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		StringBuffer sb = new StringBuffer();
		sb.append(r.nextInt(100));
		sb.append(r.nextInt(100));
		sb.append("_");
		sb.append(sdf.format(new Date()));
		sb.append("_");
		sb.append(r.nextInt(100));
		sb.append(r.nextInt(100));
		return sb.toString();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            要创建的文件夹路径
	 */
	public static void mkdir(String path) {
		if (path == null || path.equals("")) {
			return;
		}
		path = path.replaceAll("\\\\", "/");
		int lastIndex = path.lastIndexOf("/");
		if (lastIndex == -1) {
			return;
		}
		lastIndex = path.lastIndexOf(".");
		if (lastIndex != -1) {
			path = path.substring(0, path.lastIndexOf("/"));
		}
		File targetPath = new File(path);
		if (!targetPath.exists()) {
			targetPath.mkdirs();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            要删除的文件或文件夹路径
	 */
	public static boolean delFile(String filePath) {
		if (filePath == null || filePath.equals("")) {
			return false;
		}
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 用递归 删除文件夹及文件夹下所有的文件
	 * 
	 * @param 要删除的文件夹路径
	 * @return
	 */
	public static boolean delFolder(String folderPath) {
		try {
			File file = new File(folderPath);

			// 如果文件不存在，直接返回
			if (!file.exists()) {
				return false;
			}

			// 如果是具体的文件，而不是文件夹的话，直接删除
			if (file.isFile()) {
				file.delete();
			}

			// 如果是文件夹
			if (file.isDirectory()) {
				String[] fileList = file.list();
				for (int i = 0; i < fileList.length; i++) {
					String tempStr = folderPath + File.separator + fileList[i];
					File tempFile = new File(tempStr);
					if (tempFile.isFile()) {
						tempFile.delete();
					}

					// 如果是文件夹，用递归
					if (tempFile.isDirectory()) {
						delFolder(tempStr);
						tempFile.delete();
					}
				}
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是图片文件
	 * 
	 * @param args
	 */
	public static boolean isImageFile(File file) {
		try {
			if (file == null || file.getName() == "") {
				return false;
			}
			if (!file.exists()) {
				return false;
			}
			BufferedImage image = ImageIO.read(file);
			if (image == null || image.getWidth() == 0 || image.getHeight() == 0) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static boolean existFile(String filePath) {
		try {
			if (filePath == null || filePath == "") {
				return false;
			}
			File file = new File(filePath);
			return file.exists();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得图片大小
	 * 
	 * @param path
	 *            图片路径
	 */
	public static long getPicSize(String path) {
		File file = new File(path);
		return file.length();
	}

	/**
	 * 根据给定的文件路径读取文件内容
	 * 
	 * @param filePath
	 *            文件全路径
	 * @return 读取到的内容
	 */
	public static String read(String filePath) {
		if (filePath == null || filePath.isEmpty()) {
			return null;
		}
		if (filePath.lastIndexOf(".") == -1) {
			return null;
		}
		InputStream is = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				return null;
			}
			is = new FileInputStream(file);
			byte buffer[] = new byte[1024];
			int len = 0;
			StringBuffer result = new StringBuffer();
			while ((len = is.read(buffer, 0, buffer.length)) > 0) {
				result.append(new String(buffer, 0, len));
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	/**
	 * 把内容写到相应的文件中
	 * 
	 * @param conent
	 *            要写的文件内容
	 * @param filePath
	 *            文件路径
	 */
	public static boolean write(String content, String filePath) {
		if (filePath == null || filePath.isEmpty()) {
			return false;
		}
		filePath = filePath.replaceAll("\\\\", "/");
		if (filePath.lastIndexOf(".") == -1 || filePath.lastIndexOf("/") == -1) {
			return false;
		}

		String folder = filePath.substring(0, filePath.lastIndexOf("/"));
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdirs();
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
			bw.write(content);
			bw.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param inputFile
	 *            源图片
	 * @param outFile
	 *            处理后图片
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param proportion
	 *            是否等比缩放
	 */
	public static boolean compressPic(String inputFile, String outFile, int width, int height, boolean proportion) {
		try {
			// 获得源文件
			File file = new File(inputFile);
			if (!file.exists()) {
				return false;
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return false;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = width; // 输出的图片宽度
					newHeight = height; // 输出的图片高度
				}

				// 如果图片小于目标图片的宽和高则不进行转换
				if (img.getWidth(null) < width && img.getHeight(null) < height) {
					newWidth = img.getWidth(null);
					newHeight = img.getHeight(null);
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

				// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的,优先级比速度高 生成的图片质量比较好 但速度慢
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outFile);
				// JPEGImageEncoder可适用于其他图片类型的转换
				// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				// encoder.encode(tag);
				ImageIO.write(tag, "JPEG", out);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * 裁剪图片
	 * 
	 * @param srcFile
	 *            源图片
	 * @param outFile
	 *            产生新图片路径
	 * @param x
	 *            x轴的开始坐标
	 * @param y
	 *            y轴的开始坐标
	 * @param width
	 *            所裁剪的宽度
	 * @param height
	 *            所裁剪的高度
	 */
	public static boolean cutPic(String srcFile, String outFile, int x, int y, int width, int height) {

		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 如果源图片不存在
			if (!new File(srcFile).exists()) {
				return false;
			}

			// 读取图片文件
			is = new FileInputStream(srcFile);

			// 获取文件格式
			String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);

			// ImageReader声称能够解码指定格式
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();

			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			// 输入源中的图像将只按顺序读取
			reader.setInput(iis, true);

			// 描述如何对流进行解码
			ImageReadParam param = reader.getDefaultReadParam();

			// 图片裁剪区域
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标
			param.setSourceRegion(rect);

			// 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			File tempOutFile = new File(outFile);
			if (!tempOutFile.exists()) {
				tempOutFile.mkdirs();
			}
			ImageIO.write(bi, ext, new File(outFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

	}

	private static void convertToJPG(String image) {
		try {
			BufferedImage bi = ImageIO.read(new FileInputStream(image));
			FileOutputStream os = new FileOutputStream(image);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bi); // JPEG编码
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * wxr BT需求 截取策略
	 * 
	 * @param srcFile
	 * @param outFile
	 * @return
	 */
	public static boolean cutPicSquare(String srcFile, String outFile) {

		try {
			BufferedImage bufferedImage = ImageIO.read(new File(srcFile));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			int stand = width >= height ? height : width;
			if (stand == height) {
				int x = Math.round((width - height) / 2);
				int y = Math.round(0);
				cutPic(srcFile, outFile, x, y, stand, stand);
			} else {
				int x = Math.round(0);
				int y = Math.round((height - width) / 2);
				cutPic(srcFile, outFile, x, y, stand, stand);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * main方法测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String srcFile = "d:/tmp/h.png";
		String outFile = "d:/tmp/f.png";
		// convertToJPG(srcFile);
		cutPicSquare(srcFile, outFile);

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(outFile));
			compressPic(outFile, outFile, bufferedImage.getWidth(), bufferedImage.getHeight(), true); //
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}