package com.identifix.springbatch.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.identifix.springbatch.model.CsvFile;

@Repository
public interface CsvFileRepository extends Neo4jRepository<CsvFile, String>{
	
}
