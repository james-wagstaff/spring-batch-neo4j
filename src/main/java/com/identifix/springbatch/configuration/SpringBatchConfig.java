package com.identifix.springbatch.configuration;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;

import com.identifix.springbatch.model.CsvFile;

@SuppressWarnings("hiding")
@Configuration
@EnableBatchProcessing
@Primary
public class SpringBatchConfig extends DefaultBatchConfigurer {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			ItemReader<CsvFile> itemReader,
			ItemWriter<CsvFile> itemWriter) {
		Step step = stepBuilderFactory.get("File batch")
				.<CsvFile, CsvFile>chunk(100)
				.reader(itemReader)
				.writer(itemWriter)
				.build();
		return jobBuilderFactory.get("Spring batch Job")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}
	
	@Bean
	public FlatFileItemReader<CsvFile> itemReader(@Value("${file}") File resource) {
		FlatFileItemReader<CsvFile> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(resource));
		reader.setLinesToSkip(1);
		reader.setName("CSV File Reader");
		reader.setLineMapper(lineMapper());
		return reader;
		
	}

	public LineMapper<CsvFile> lineMapper() {
		DefaultLineMapper<CsvFile> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter("|");
		tokenizer.setStrict(false);
		tokenizer.setNames("connectorPartNumber","cavityCount","nonUsedTwo","normalizedPartNumber","viewFile","viewExt","gender","nonUsedSeven","description","repairKitPartNumber","nonUsedTen","nonUsedEleven","isoFile","wireFile");
		BeanWrapperFieldSetMapper<CsvFile> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(CsvFile.class);
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
}
