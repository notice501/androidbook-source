package com.pms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 解压apk工具类
 * 
 * @author machao
 * 
 */
public class UnzipManifest {
	public static int BUFFER = 4096;

	/**
	 * 解压apk，将AndroidManifest.xml放到临时路径
	 * 
	 * @param sZipPathFile
	 * @param tmpManifestXmlPath
	 */
	public static void unzip(String sZipPathFile, String tmpManifestXmlPath) {
		try {
			FileInputStream fins = new FileInputStream(sZipPathFile);
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			byte ch[] = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				if (ze.getName().equals("AndroidManifest.xml")) {
					File zfile = new File(tmpManifestXmlPath);
					deleteFile(zfile);
					zfile.createNewFile();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.flush();
					fouts.close();
					break;
				}
			}
			fins.close();
			zins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}
}
