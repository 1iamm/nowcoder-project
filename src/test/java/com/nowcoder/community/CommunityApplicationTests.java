package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.AlphaDaoHibernateImpl;
import com.nowcoder.community.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)  //设置配置类为CommunityApplication
//	实现接口ApplicationContextAware,获得容器
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;


//	传入的参数applicationContext就是容器
//	当我们实现了ApplicationContextAware接口的时候，spring容器会自动检测到，调用set方法把自身传入，我们只需要拿其存入一个变量即可
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext(){
		System.out.println(applicationContext);
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());
	}

	@Test
	public void testBeanManagement() {
		AlphaService alphaService= applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);

		alphaService= applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Autowired  //依赖注入
	@Qualifier("alphaHibernate") //bean的名字，这样注入的就不会是优先级最高的bean了
	private AlphaDao alphaDao;

	@Test
	public void testDI() {
		System.out.println(alphaDao.select());
	}
}
