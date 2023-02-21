package com.java.update;

import com.java.update.api.SKExampleRequest;
import com.java.update.dao.SKExample;
import com.java.update.dao.SKExampleDAO;
import com.java.update.dao.SKExampleObj;
import com.java.update.service.SKExampleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;

import static com.java.update.api.SKExampleProcessingType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UpdateApplicationTests {

	@Autowired
	SKExampleService service;

	@Autowired
	SKExampleDAO<SKExample> repository;

	@BeforeEach
	void initDB() {
		SKExample skExample = new SKExample();
		SKExampleObj skExampleObj = new SKExampleObj();
		skExample.setId(1);
		skExampleObj.setCurrent(0);
		skExample.setObj(skExampleObj);
		repository.save(skExample);
	}

	@AfterEach
	void resetDB() {
		initDB();
	}

	@Test
	void modifySKExample_skExampleCurrentIsZeroWithForUpdate_currentIsOneHundredSuccessfully() throws InterruptedException {
		Integer threadCount = 1000;
		CountDownLatch latch = new CountDownLatch(threadCount);
		SKExampleRequest request = new SKExampleRequest(1, 1);

		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				service.modifySKExample(request, FOR_UPDATE);
				latch.countDown();
			}).start();
		}
		latch.await();

		Integer current = repository.findById(request.getId()).get().getObj().getCurrent();
		assertEquals(threadCount, current);
	}

	@Test
	void modifySKExample_skExampleCurrentIsZeroWithIsolation_currentIsOneHundredSuccessfully() throws InterruptedException {
		Integer threadCount = 1000;
		CountDownLatch latch = new CountDownLatch(threadCount);
		SKExampleRequest request = new SKExampleRequest(1, 1);

		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				service.modifySKExample(request, ISOLATION);
				latch.countDown();
			}).start();
		}
		latch.await();

		Integer current = repository.findById(request.getId()).get().getObj().getCurrent();
		assertEquals(threadCount, current);
	}
}
