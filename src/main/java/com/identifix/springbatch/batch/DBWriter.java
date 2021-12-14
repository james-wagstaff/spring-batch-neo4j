package com.identifix.springbatch.batch;

import com.identifix.springbatch.model.CsvFile;
import com.identifix.springbatch.repository.CsvFileRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBWriter implements ItemWriter<CsvFile> {
	
	private final CsvFileRepository csvFileRepository;

	@Override
	public void write(List<? extends CsvFile> items) throws Exception {
		csvFileRepository.save(items,-1);
	}
}
