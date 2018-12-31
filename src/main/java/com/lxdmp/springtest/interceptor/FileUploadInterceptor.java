/*
 * 后续使用validator机制来对上传文件进行验证,不再使用interceptor的方法.
 */
package com.lxdmp.springtest.interceptor;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//import org.apache.commons.lang.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadInterceptor implements HandlerInterceptor
{
	/*
	 * 限制上传文件的类型与大小.
	 */
	static private final long FILE_SIZE_DEFAULT_LIMIT = 1024*1024*1024; // 1G
	private static final Logger logger = LoggerFactory.getLogger(FileUploadInterceptor.class);

	private List<String> fileTypesPermitted = new ArrayList<String>();
	private long fileSizeLimit;

	public FileUploadInterceptor(String[] fileTypesPermitted)
	{
		this(fileTypesPermitted, FILE_SIZE_DEFAULT_LIMIT);
	}

	public FileUploadInterceptor(String[] fileTypesPermitted, long fileSizeLimit)
	{
		for(int i=0; i<fileTypesPermitted.length; ++i)
		{
			String suffix = fileTypesPermitted[i];
			int firstIndex = suffix.indexOf(".");
			int lastIndex = suffix.lastIndexOf(".");
			if(firstIndex>=0 && lastIndex>=0)
			{
				if(firstIndex!=lastIndex){
					continue;
				}else{
					if(firstIndex>0)
						continue;
					else
						suffix = suffix.substring(1, suffix.length());
				}
			}
			this.fileTypesPermitted.add(suffix);
		}
		this.fileSizeLimit = fileSizeLimit;

		{
			String s = String.format("supported image types : %s.", 
				StringUtils.arrayToCommaDelimitedString(this.fileTypesPermitted.toArray())
			);
			logger.info(s);
		}
	}

    @Override
    public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler
	)throws Exception
	{
		boolean validReq = true;
		ModelAndView invalidReqView = null;

        if(request instanceof MultipartHttpServletRequest)
		{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator<String> iterator = files.keySet().iterator();

            while(iterator.hasNext() && validReq)
			{
                String formKey = (String) iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(formKey);

                if(!isFileTypeValid(multipartFile)) // 图片类型
				{
					invalidReqView = new ModelAndView();
					invalidReqView.addObject("fileTypeInvalid", true);
					String tips = StringUtils.arrayToCommaDelimitedString(this.fileTypesPermitted.toArray());
					invalidReqView.addObject("tips", tips);
					validReq = false;
					//logger.info("image format invalid");
					//logger.info(tips);
                }
				else if(!isFileSizeValid(multipartFile)) // 图片大小
				{
					invalidReqView = new ModelAndView();
					invalidReqView.addObject("fileSizeInvalid", true);
					invalidReqView.addObject("tips", String.format("%.2f", this.fileSizeLimit/1024.0));
					validReq = false;
					//logger.info("image size invalid");
				}
            }
        }

		if(!validReq)
		{
			//logger.info("check not passed");
			invalidReqView.addObject("jumpUrl", request.getRequestURL());
			invalidReqView.addObject("jumpDelay", 5);
			invalidReqView.setViewName("fileUploadError");
			throw new ModelAndViewDefiningException(invalidReqView);

			// 可通过ModelAndViewDefiningException在interceptor的preHandle中跳转到指定的页面.
		}
		return true;
    }

    private boolean isFileTypeValid(MultipartFile multipartFile)
	{
		String fileName = multipartFile.getOriginalFilename();
		for(int i=0; i<this.fileTypesPermitted.size(); ++i)
		{
			String fileTypePermitted = this.fileTypesPermitted.get(i);
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(fileTypePermitted.equalsIgnoreCase(suffix))
				return true;
		}
        return false;
    }

	private boolean isFileSizeValid(MultipartFile multipartFile)
	{
		return multipartFile.getSize()<=this.fileSizeLimit;
	}

	@Override
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	{
	}

	@Override
	public void afterCompletion(
		HttpServletRequest request, HttpServletResponse response, Object handler, Exception exceptionIfAny)
	{
	}
}

