package ro.utcn.sd.boti.stackoverflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.entity.Tag;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.memory.InMemoryRepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.service.StackoverflowService;

import java.util.List;

public class StackoverflowApplicationTests {

	private static RepositoryFactory createMockedFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();

		User u1 = new User("boti", "boti", new Integer(1));
		Question q1 = new Question(u1,"Asd lol", "Bdsa", "2019-03-23 15:13:54");
		Question q2 = new Question(u1,"qwe lol", "Dsadsada", "2019-03-21 15:13:54");
		Question q3 = new Question(u1,"zxc", "Fdsadsadsa", "2019-03-26 15:13:54");
		Tag t1 = new Tag("yolo");
		Tag t2 = new Tag("123");
		Tag t3 = new Tag("what");
		q1.getTags().add(t2);
		q3.getTags().add(t1);
		q3.getTags().add(t2);
		t2.getQuestions().add(q1);
		t2.getQuestions().add(q3);
		t1.getQuestions().add(q3);

		factory.getUserRepository().save(u1);
		factory.getQuestionRepository().save(q1);
		factory.getQuestionRepository().save(q2);
		factory.getQuestionRepository().save(q3);
		factory.getTagRepository().save(t1);
		factory.getTagRepository().save(t2);
		factory.getTagRepository().save(t3);

		return factory;
	}

	@Test
	public void testUserPassword() {
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		User user = service.findByUserName("boti").orElse(null);

		Assert.assertNotNull(user);
		Assert.assertEquals("boti", user.getPassword());
	}

	@Test
	public void testListQuestions() {
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		List<Question> questions = service.listQuestions();

		Assert.assertEquals(3, questions.size());
		//expect most recent question on top
		Assert.assertEquals("zxc", questions.get(0).getTitle());
	}

	@Test
	public void testRemoveQuestion(){
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		service.removeQuestion(2);

		Assert.assertEquals(2, factory.getQuestionRepository().findAll().size());
		Assert.assertTrue(factory.getQuestionRepository().findById(1).isPresent());
		Assert.assertTrue(factory.getQuestionRepository().findById(3).isPresent());
	}

	@Test
	public void testSearchInTitle() {
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		List<Question> questions = service.searchInTitle("lol");

		Assert.assertEquals(2, questions.size());
		Assert.assertTrue(questions.get(0).getTitle().toLowerCase().contains("lol"));
		Assert.assertTrue(questions.get(1).getTitle().toLowerCase().contains("lol"));
	}

	@Test
	public void testSearchByTag() {
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		List<Question> questions = service.searchByTag(2);

		Assert.assertEquals(2, questions.size());
		// question 1 and 3 tagged
		Assert.assertTrue(questions.get(0).getId() != 2 );
		Assert.assertTrue(questions.get(1).getId() != 2);
	}

	@Test(expected = QuestionNotFoundException.class)
	public void testRemoveThrowsWithNotExistingId() {
		RepositoryFactory factory = createMockedFactory();
		StackoverflowService service = new StackoverflowService(factory);

		service.removeQuestion(888);
	}
}
