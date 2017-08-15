package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class InitDataBaseTests {
	@Autowired
	UserDAO userDao;
	@Autowired
	QuestionDAO questionDao;
	@Test
	public void initDateBase() {
		Random random = new Random();
		for (int i=0;i<11;++i){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d",i));
			user.setPassword("");
			user.setSalt("");
		//	userDao.addUser(user);

			user.setPassword("XX");
			userDao.updatePassword(user);

			Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime()+1000*3600*i);
			System.out.println(date.getTime()+"");
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setTitle(String.format("TITLE{%d}",i));
			question.setContent(String.format("Blalaalal Content %d",i) );

			questionDao.addQuestion(question);
		}

		Assert.assertEquals("XX", userDao.selectById(15).getPassword());
		userDao.deleteById(1);
		Assert.assertNull(userDao.selectById(1));

		questionDao.selectLatestQuestions(0,0,10);
	}

}
