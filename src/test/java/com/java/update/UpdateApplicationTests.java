package com.java.update;

import com.java.update.api.SKExampleApi;
import com.java.update.api.SKExampleRequest;
import com.java.update.repository.SKExample;
import com.java.update.repository.SKExampleObj;
import com.java.update.repository.SKExampleRepository;
import com.java.update.service.SKExampleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UpdateApplicationTests {

	@Autowired
	SKExampleService service;

	@Autowired
    SKExampleRepository repository;

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
	void modifySKExample_skExampleCurrentIsZero_CurrentIsOneHundredSuccessfully() throws InterruptedException {
		Integer threadCount = 1000;
		CountDownLatch latch = new CountDownLatch(threadCount);
		SKExampleRequest request = new SKExampleRequest(1, 1);

		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				service.modifySKExample(request);
				latch.countDown();
			}).start();
		}
		latch.await();

		Integer current = repository.findById(request.getId()).get().getObj().getCurrent();
		assertEquals(threadCount, current);
	}
}
