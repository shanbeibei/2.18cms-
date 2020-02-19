package com.shanbei;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.bobo.common.utils.StreamUtil;
import com.bobo.common.utils.UserUtils;
import com.shanbei.dao.ArticleRepository;
import com.shanbei.domain.Article;
import com.shanbei.domain.ArticleWithBLOBs;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans.xml")
public class SendArticlesKafka {
	@Autowired
	private ArticleRepository articleRepository;


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Test
	public void testSendArticel() throws IOException {
		File file = new File("D:/1708D22");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			String title = file2.getName().replace(".txt", "");
			String content = StreamUtil.readFile(file2, "utf8");
			ArticleWithBLOBs awb = new ArticleWithBLOBs();
			int hot = UserUtils.getNum(0, 1);
			int channelId = UserUtils.getNum(1,9);
			int userId = UserUtils.getNum(1,200);
			int hits = UserUtils.getNum(0, 100);
			String created = UserUtils.getBirthday(2019, 2020);
			awb.setTitle(title);
			awb.setContent(content);
			awb.setSummary(awb.getContent().substring(1,40));
			awb.setHot(hot);
			awb.setChannelId(channelId);
			awb.setUserId(userId);
			awb.setHits(hits);
			awb.setStatus(1);
			awb.setDeleted(0);
			awb.setCreated(Date.valueOf(created));
			String jsonString = JSON.toJSONString(awb);
			kafkaTemplate.send("articles", jsonString);
		}
	}
}
