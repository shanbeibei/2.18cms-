package com.shanbei.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.bobo.common.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shanbei.dao.ArticleRepository;
import com.shanbei.dao.ArticleRes;
import com.shanbei.domain.Article;
import com.shanbei.domain.ArticleWithBLOBs;
import com.shanbei.domain.Category;
import com.shanbei.domain.Channel;
import com.shanbei.domain.Comment;
import com.shanbei.domain.Favorite;
import com.shanbei.domain.User;
import com.shanbei.server.ArticleService;
import com.shanbei.server.ChannelService;
import com.shanbei.server.CommentService;
import com.shanbei.server.FavoriteService;


/**
 * 
 * @ClassName: MyController 
 * @Description: 个人中心
 * @author: charles
 * @date: 2019年12月12日 上午10:03:48
 */
@RequestMapping("my")
@Controller
public class MyController {
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private ArticleRes articleRes;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private FavoriteService service;
	
	@Autowired
	KafkaTemplate kafkaTemplate;
	//个人中心首页
	@RequestMapping(value = {"","/","index"})
	public String index() {
		
		return "my/index";
		
	}
	
	/**
	 * 查詢我的評論
	 * @Title: comments 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("article/comments")
	public String comments(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment();
		comment.setUserId(user.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "my/article/comments";
		
	}
	/**
	 * 
	 * @Title: publish 
	 * @Description: 去发布文章
	 * @return
	 * @return: String
	 */
	@GetMapping("article/publish")
	public String publish() {
		
		return "my/article/publish";
	}
    /**
     * 
     * @Title: selectChannels 
     * @Description: 所有栏目
     * @return
     * @return: List<Channel>
     */
	@ResponseBody
	@GetMapping("channel/selects")
	public List<Channel> selectChannels(){
		return channelService.selects();
	}
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Channel>
	 */
	@ResponseBody
	@GetMapping("category/selectsByChannelId")
	public List<Category> selectsByChannelId(Integer channelId){
		return channelService.selectsByChannelId(channelId);
	}
	
	
	/**
	 * 
	 * @Title: update
	 * @Description: 修改文章
	 * @param user
	 * @return
	 * @return: boolean
	 */
	@Autowired
	ArticleRepository articleRepository;
	
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		//修改文章的时候,同理要修改es索引库,最终来实现mysql的数据,和es索引库的数据保持一致
//		articleRepository.save(article);
//		return articleService.updateByPrimaryKeySelective(article)>0;
		if (article.getId() != null && article.getDeleted() != null) {

			if (article.getDeleted() == 1) {
				ArticleWithBLOBs selectByPrimaryKey2 = articleService.selectByPrimaryKey(article.getId());
				articleRes.delete(selectByPrimaryKey2);
			} else if (article.getDeleted() == 0) {
				ArticleWithBLOBs selectByPrimaryKey2 = articleService.selectByPrimaryKey(article.getId());
				if (selectByPrimaryKey2 != null) {
					if (selectByPrimaryKey2.getStatus() == 1) {

						articleRes.save(selectByPrimaryKey2);
					}
				}
			}
		}
		return articleService.updateByPrimaryKeySelective(article) > 0;

	}
	
	/**
	 * 
	 * @Title: article 
	 * @Description: 文章详情
	 * @param id
	 * @return
	 * @return: String
	 */
	@GetMapping("article/article")
	public String article(Integer id,Model model) {
		
		ArticleWithBLOBs a = articleService.selectByPrimaryKey(id);
		model.addAttribute("a", a);
		return "my/article/article";
	}
	/**
	 * 
	 * @Title: articles 
	 * @Description: 我的文章列表
	 * @param model
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@GetMapping("article/articles")
	public String articles(Model model, HttpServletRequest request,Article article, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());//只查询当前用户的文章
		
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		model.addAttribute("info", info);
		
		return "my/article/articles";
		
	}
	
	
	
	@RequestMapping("shou/add")
	@ResponseBody
	public boolean add(String text,String url) {//添加方法 
		if(StringUtil.isHttpUrl(url)) {
			Favorite fa = new Favorite(null,text,url,170,"2020/2/18 10:39:00");
			service.add(fa);
			return true;
		}else {
			return false;
		}
		
	}

	
	/**
	 * 
	 * @Title: favorite 
	 * @Description: 我的收藏列表
	 * @param model
	 * @param 
	 * @return
	 * @return: String
	 */
	
	
	@GetMapping("article/favorites")
	public String favorites(Model model,HttpServletRequest request,Article article,@RequestParam(defaultValue = "1") Integer pageNum) {
		PageHelper.startPage(pageNum, 100);
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());//只查询当前用户的文章
		List<Favorite> list = service.select();
		PageInfo<Favorite> info=new PageInfo<Favorite>(list);
		model.addAttribute("info", info);
		model.addAttribute("list", list);
		return "my/article/favorites";
	}
	/*
	 * 删除
	 */
	@MessageMapping("article/delete")
	public boolean delete(Integer id) {
		try {
			service.delete(id);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: publish 
	 * @Description: 发布文章
	 * @param file
	 * @param article
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("article/publish")
	public boolean publish( MultipartFile file, ArticleWithBLOBs article,HttpServletRequest request) throws IllegalStateException, IOException {
		String json = JSON.toJSONString(article);


		System.err.println(json);


		kafkaTemplate.send("articles", json);
		String path="d:/pic/";//文件存放路径
		//判断上传文件是否为空,若不为空,则上传
		
		if(!file.isEmpty()) {
		    //获取原始文件名称
			String filename = file.getOriginalFilename();
			//防止文件重名
			String newFileName = UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
			//把文件写入硬盘
			file.transferTo(new File(path,newFileName));
			//数据库存储文件地址
			article.setPicture(newFileName);
		}
		//初始化操作
		
		article.setCreated(new Date());//发布时间默认系统时间
		article.setStatus(0);//文章状态 0:待审核
		article.setHits(0);//点击量
		article.setDeleted(0);//是否删除// 0:未删除
		article.setUpdated(new Date());//更新时间
	//从session获取当前登录人的信息。用来指定发布人
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());//发布人
		article.setHot(0);//非热文章
		articleRes.save(article);
		return articleService.insertSelective(article)>0;
		
	}
	
}
