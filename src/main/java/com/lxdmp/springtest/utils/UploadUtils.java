package com.lxdmp.springtest.utils;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;
import org.apache.log4j.Logger;

public class UploadUtils
{
	/*
	 * 依赖于ImageMagick包的convert来进行图片文件的格式转换,上传的图片都另存为png格式供后续使用.
	 */
	private static final Logger logger = Logger.getLogger(UploadUtils.class);

	public static void saveProductImage(MultipartFile productImage, String imageSavePath)
	{
		//logger.info("img save path : "+imgTotalPath);
		if(productImage==null || productImage.isEmpty())
			return;

		String fileName = productImage.getOriginalFilename();
		if(fileName.lastIndexOf(".")<0)
			throw new RuntimeException("Can not determine product image format, no file suffix");
			
		try{
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(suffix.equalsIgnoreCase("png"))
			{
				productImage.transferTo(new File(imageSavePath));
			}else{
				String sep = File.separator;
				String tmp_storage_path = System.getProperty("java.io.tmpdir")+sep+fileName;
				productImage.transferTo(new File(tmp_storage_path));
				Process p = new ProcessBuilder("convert", tmp_storage_path, imageSavePath).start();
			}
		}catch(Exception e){
			throw new RuntimeException("Product Image saving failed", e);
		}
	}
}

