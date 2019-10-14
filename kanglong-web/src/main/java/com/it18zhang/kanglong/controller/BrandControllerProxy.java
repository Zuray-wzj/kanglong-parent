package com.it18zhang.kanglong.controller;

import com.it18zhang.kanglong.common.entity.Brand;
import com.it18zhang.kanglong.common.entity.Category;
import com.it18zhang.kanglong.common.vo.CategoryVO;
import com.it18zhang.kanglong.common.vo.PageResult;
import com.it18zhang.kanglong.service.api.BrandServiceApi;
import com.it18zhang.kanglong.service.api.CategoryServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * BrandController
 */
@Controller
@RequestMapping("/brand")
public class BrandControllerProxy {


	//注入brandService
	@Autowired
	private BrandServiceApi brandServiceApi;

	//注入CategoryServiceApi
	@Autowired
	private CategoryServiceApi categoryServiceApi ;
	@RequestMapping("/index")
	public String manage(Model model){
		//远程查询所有的品牌
		PageResult<Brand> result = brandServiceApi.findPagingAndSortByName(1, Integer.MAX_VALUE , "" , false , "").getBody()  ;
		//查询所有品类
		List<CategoryVO> categories = categoryServiceApi.findTree().getBody() ;

		//发送ui组件
		model.addAttribute("list" , result.getList()) ;
		model.addAttribute("categories" , categories) ;

		return "/brand/manage" ;
	}


	//保存品牌
	@PostMapping("/saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(Brand brand , Long[] cids){
		return brandServiceApi.saveOrUpdate(brand , cids).getBody() ;
	}

	/**
	 * 上传图片 , 返回图片的url地址
	 */
	@PostMapping("/upload")
	@ResponseBody
	public String uploadload(MultipartFile image){
		//String prefix = "http://image.kanglong.it18zhang.com/" ;
		String prefix = "http://localhost:63342/kanglong-parent/kanglong-web/static/img/" ;

		System.out.println("uploading!");
		try {
			long ms = System.currentTimeMillis() ;
			//获得原始名称
			String origName = image.getOriginalFilename() ;
			String fileName = origName.substring(0 , origName.lastIndexOf(".")) ;
			String ext = origName.substring(origName.lastIndexOf(".")) ;
			String newFileName = fileName + "_" + ms + ext ;
			//3423
			File file9000 = new File("/code/wzj_Mac/idea_demo/kanglong-parent/kanglong-web/src/main/resources/static/img" , newFileName) ;
			//File file9001 = new File("D:\\downloads\\nginx-1.6.3\\html\\9001" , newFileName) ;
			copyFile(image.getInputStream() , file9000);
			//copyFile(image.getInputStream() , file9001);
			System.out.println(prefix + newFileName);
			return prefix + newFileName ;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage() ;
		}
	}

	/**
	 * 复制文件
	 */
	private void copyFile(InputStream in , File dest){
		try {
			FileOutputStream fout = new FileOutputStream(dest) ;
			byte[] buf = new byte[1024] ;
			int len = 0 ;

			while((len = in.read(buf)) != -1){
				fout.write(buf , 0  , len);
			}
			fout.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@GetMapping("/deletebyid")
	@ResponseBody
	public void deleteById(@RequestParam("id") Long id){
		brandServiceApi.deleteById(id);
	}

	/**
	 * 按照id查询Brand
	 */
	@GetMapping("/findbyid")
	@ResponseBody
	public Brand findById(@RequestParam("id") Long id){
		return brandServiceApi.findById(id).getBody() ;
	}

	//按照品类查询品牌
	@GetMapping("/findbycid")
	@ResponseBody
	public List<Brand> findByCid(@RequestParam("cid") Long cid){
		return brandServiceApi.findByCid(cid).getBody() ;
	}
}
