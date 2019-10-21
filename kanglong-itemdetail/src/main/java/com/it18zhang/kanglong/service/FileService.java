package com.it18zhang.kanglong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 文件服务类
 */
@Service
public class FileService {

	// 注入商品详情页目录
	@Value("${itemdetail.html.dir}")
	private String pageDir ;

	// 注入Thymeleaf模板
	@Autowired
	private TemplateEngine templateEngine  ;

	/**
	 * 判断指定spu的页面是否存在
	 */
	public boolean htmlExists(Long spuId){
		return new File(pageDir , spuId + ".html").exists() ;
	}

	/**
	 * 生成静态页
	 */
	public void genHtml(Map<String,Object> model , Long spuId){
		Context ctx = new Context() ;
		ctx.setVariables(model);

		// 备份文件
		File bakFile = null ;
		// 判断页面是否存在
		if(htmlExists(spuId)){
			// 先备份文件
			bakFile = backupOldHtml(spuId) ;
		}

		// 生成静态页面的临时文件
		File tmpFile = new File(spuId + ".html") ;

		// 目标页面
		File destFile = new File(pageDir , spuId + ".html") ;

		try {
			PrintWriter pw = new PrintWriter(tmpFile, "UTF-8");
			templateEngine.process("item" , ctx,pw) ;
			pw.close();
			// 临时页面替换目标页
			copyFile(tmpFile,destFile);
			// 删除备份文件
			if(bakFile != null){
				bakFile.delete() ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			copyFile(bakFile,destFile);
		}
		finally {
			//删除临时文件
			if(tmpFile  != null) tmpFile.delete() ;
		}
	}

	/**
	 * 备份原页面
	 */
	public File backupOldHtml(Long spuId){
		File oldFile = new File(pageDir, spuId + ".html") ;
		File bakFile = new File(spuId + ".html.bak") ;
		oldFile.renameTo(bakFile) ;
		return bakFile ;
	}

	/**
	 * 复制文件
	 */
	public void copyFile(File src ,File dest){
		try {
			FileInputStream fis = new FileInputStream(src) ;
			FileOutputStream fos = new FileOutputStream(dest) ;
			byte[] buf = new byte[1024] ;
			int len = -1;
			while((len = fis.read(buf)) != -1){
				fos.write(buf , 0 , len);
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除静态页
	 */
	public void deleteHtml(Long id) {
		File f = new File(pageDir , id + ".html") ;
		if(f.exists()){
			f.delete() ;
		}
	}
}
