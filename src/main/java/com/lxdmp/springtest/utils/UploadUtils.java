package com.lxdmp.springtest.utils;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;
import org.apache.log4j.Logger;

public class UploadUtils
{
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
			if(".png".equalsIgnoreCase(suffix))
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
